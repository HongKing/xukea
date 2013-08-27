package com.xukea.common.mybatis.plugin;

import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.util.TablesNamesFinder;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.xukea.common.UserBasicInfo;
import com.xukea.common.util.ThreadLocalInstance;
import com.xukea.common.util.cache.DataPolicyCache;
import com.xukea.framework.ibatis3.BaseSqlSessionUtil;
import com.xukea.framework.util.sqlparser.SqlDeParser;
import com.xukea.main.role.model.Role;
import com.xukea.main.rule.model.Rule;

/**
 * MyBatis插件：数据权限处理
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @date   2013.08.21
 */
@Intercepts({@Signature(
		type   = Executor.class,
		method = "query",
		args   = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DataPolicyInterceptor implements Interceptor{
	private static int IDX_STATEMENT = 0;
	private static int IDX_PARAMETER = 1;
	private static int IDX_ROWBOUNDS = 2;
	private static int IDX_RS_HANDLE = 3;

	private final Logger log = Logger.getLogger(getClass());
	private CCJSqlParserManager parserManager = new CCJSqlParserManager(); // SQL语法解析器
	private TablesNamesFinder   tableFinder   = new TablesNamesFinder();   // Table Name查找器
	
	private List<Rule> rules;
	private UserBasicInfo user;
	private boolean   hasRule  = false;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
	}

	/**
	 * 数据权限处理
	 * @param queryArgs
	 */
	private void processIntercept(final Object[] queryArgs) {
		// 获取当前用户对象
		user = (UserBasicInfo)ThreadLocalInstance.get("UserBasicInfo");
		if(user==null) return;
		
		// 获取当前对象所在的角色
		List<Role> roles = (List<Role>)user.getAttribute("ROLES");
		if(roles==null) return;
		
		// 根据roels获取对应的数据权限，若为空，则返回
		rules = DataPolicyCache.getInstance().getRuleByRole(roles);
		if(rules==null || rules.size()==0) return;
		
		// 解析当前SQL语句
		MappedStatement ms = (MappedStatement)queryArgs[IDX_STATEMENT];
		Object   parameter = queryArgs[IDX_PARAMETER];
		BoundSql boundSql = ms.getBoundSql(parameter);
		String sql = boundSql.getSql().trim();
		Statement statement = null;
		try {
			statement = parserManager.parse(new StringReader(sql));
		} catch (JSQLParserException e) {
			log.error("parser sql error: < "+ sql +" >", e);
			return;
		}
//		// 如果不是Select语句，则不处理
//		// 该plugin拦截query方法，所以应该都是select语句，因此该层判断不需要
//		if(!(statement instanceof Select) ) return;
		
		Select select = (Select) statement;
		
		// 获取select中的所有表，先整体判断是否需要解析，提高效率
		// TODO 对于SQL语句解析可以提供一个解析后的缓存处理
		boolean flag = false;
		List<String> tableList = tableFinder.getTableList(select);
		for(String tableName : tableList){
			for(Rule rule : rules){
				if(tableName.equalsIgnoreCase(rule.getTabName().trim())){
					flag = true;
					break;
				}
			}
			if(flag) break;
		}
		if(!flag) return; // 如果没有需要处理的表，则直接返回，不处理
		
		// 根据规则添加条件
		SelectBody newBody = addRule2SQL( select.getSelectBody() );
		
		// 如果SQL中增加了规则，则返回新的SQL
		if(hasRule){
			// 重置SQL中的WHERE条件
			select.setSelectBody(newBody);
			// 生成SQL语句
			StringBuilder stringBuffer = new StringBuilder();
			SqlDeParser deParser = new SqlDeParser(stringBuffer);
			deParser.visit(select);
			// 替换SQL语句中的常量
			sql = replaceSqlConstant(stringBuffer.toString());
			
			// 拼装新的SQL语句
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			MappedStatement.Builder builder = BaseSqlSessionUtil.copyMappedStatement(ms, newBoundSql);
			queryArgs[IDX_STATEMENT] = builder.build();
		}
	}
	
	/**
	 * 根据规则为SQL语句添加相关条件
	 * 
	 * @param select
	 * @return
	 */
	private SelectBody addRule2SQL(SelectBody select) {
		PlainSelect selectBody = (PlainSelect) select;
		Expression newWhere = null;
		
		// 1. 处理主表的规则
		for(Rule rule : rules){
			Table table = (Table) selectBody.getFromItem();
			if(table.getName().equalsIgnoreCase(rule.getTabName().trim())){
				Expression ruleWhere = getRuleSql(table, rule.getRules());
				newWhere = mergerWhere(newWhere, ruleWhere);
				// 只匹配最先找到的规则
				hasRule = true;
				break;
			}
		}
		
		// 2. 处理关联表的规则
		if(selectBody.getJoins()!=null){
			for(Join item : selectBody.getJoins()){
				Table table = (Table)item.getRightItem();
				for(Rule rule : rules){
					if(table.getName().equalsIgnoreCase(rule.getTabName().trim())){
						Expression ruleWhere = getRuleSql(table, rule.getRules());
						newWhere = mergerWhere(newWhere, ruleWhere);
						// 只匹配最先找到的规则
						hasRule = true;
						break;
					}
				}
			}
		}
		
		// 3. 处理WHERE中子查询表的规则
		Expression ruleWhere = getWhere(selectBody.getWhere());
		newWhere = mergerWhere(ruleWhere, newWhere);
		
		// 如果改变了WHERE条件，则用新的WHERE
		if(hasRule){
			selectBody.setWhere(newWhere);
		}
		return selectBody;
	}
	
	/**
	 * WHERE条件规则替换
	 * 
	 * @param where
	 * @return
	 */
	private Expression getWhere(Expression where){
		if(where==null) return null;
		
		// 如果是列对象，则直接返回
		if(where instanceof Column){
			return where;
		}
		
		// 如果是子查询，则处理子查询中的条件规则
		if(where instanceof SubSelect){
			SubSelect subSelect = (SubSelect) where;
			SelectBody newBody = addRule2SQL( subSelect.getSelectBody() );
			subSelect.setSelectBody(newBody);
			return subSelect;
		}
		
		// AND, OR 等表达式处理
		if(where instanceof BinaryExpression){
			BinaryExpression newWhere = (BinaryExpression) where;
			
			Expression left = newWhere.getLeftExpression();
			newWhere.setLeftExpression(getWhere(left));
			
			Expression right = newWhere.getRightExpression();
			newWhere.setRightExpression(getWhere(right));
			
			return newWhere;
		}

		// IN, NOT IN 处理
		if(where instanceof InExpression){
			InExpression newWhere = (InExpression) where;
			
			Expression left = newWhere.getLeftExpression();
			newWhere.setLeftExpression(getWhere(left));

			ItemsList leftItems = newWhere.getLeftItemsList();
			if(leftItems instanceof SubSelect){
				SubSelect temp = (SubSelect) getWhere( (SubSelect)leftItems );
				newWhere.setLeftItemsList(temp);
			}
			
			ItemsList rightItems = newWhere.getRightItemsList();
			if(rightItems instanceof SubSelect){
				SubSelect temp = (SubSelect) getWhere( (SubSelect)rightItems );
				newWhere.setRightItemsList(temp);
			}
			
			return newWhere;
		}

		// IS NULL, IS NOT NULL 处理
		if(where instanceof IsNullExpression){
			IsNullExpression newWhere = (IsNullExpression) where;
			
			Expression left = newWhere.getLeftExpression();
			newWhere.setLeftExpression(getWhere(left));
			
			return newWhere;
		}

		// EXISTS, NOT EXISTS 处理
		if(where instanceof ExistsExpression){
			ExistsExpression newWhere = (ExistsExpression) where;
			
			Expression right = newWhere.getRightExpression();
			newWhere.setRightExpression(getWhere(right));
			
			return newWhere;
		}
		
		//Between
		if(where instanceof Between){
			Between newWhere = (Between) where;

			Expression left = newWhere.getLeftExpression();
			newWhere.setLeftExpression(getWhere(left));

			Expression start = newWhere.getBetweenExpressionStart();
			newWhere.setBetweenExpressionStart(getWhere(start));

			Expression end = newWhere.getBetweenExpressionEnd();
			newWhere.setBetweenExpressionEnd(getWhere(end));
			
			return newWhere;
		}
		
		return where;
	}
	
	/**
	 * 获取匹配规则的SQL解析对象
	 * 
	 * @param table
	 * @param rule
	 * @return
	 */
	private Expression getRuleSql(Table table, String rule){
		String sql = "";
		if(table.getAlias()==null || "".equals(table.getAlias())){
			sql = "SELECT * FROM "+ table +" WHERE "+ rule;
		}else{
			sql = "SELECT * FROM "+ table +" WHERE "+ table.getAlias() +"."+ rule;
		}
		
		Select select = null;
		try {
			select = (Select)parserManager.parse(new StringReader(sql));
		} catch (JSQLParserException e) {
			log.error("parser sql error: < "+ sql +" >", e);
			return null;
		}
		
		PlainSelect selectBody = (PlainSelect) select.getSelectBody();
		return selectBody.getWhere();
	}
	
	/**
	 * 合并WHERE条件
	 * 
	 * @param oldWhere
	 * @param newWhere
	 * @return
	 */
	private Expression mergerWhere(Expression oldWhere, Expression newWhere){
		if(oldWhere!=null && newWhere!=null){
			// 都不为空，则以AND拼接
			return new AndExpression(oldWhere, newWhere);
		}else if(oldWhere!=null){
			// 新条件为空则返回旧的
			return oldWhere;
		}else if(newWhere!=null){
			// 旧条件为空则返回新的
			return newWhere;
		}else{
			// 都为空，则返回null
			return null;
		}
	}
	
	/**
	 * TODO 替换规则中的变量<br>
	 * 
	 * @param rule
	 * @return
	 */
	private String replaceSqlConstant(String rule){
		rule = rule.trim();
		rule = rule.replaceAll("@LOGIN_USER_ID@", user.getId()+""); // 当前登录用户ID
		return rule;
	}
	
}
