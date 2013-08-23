package com.xukea.framework.ibatis3;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

import com.xukea.framework.util.PageList;
import com.xukea.framework.util.sqlparser.SqlDeParser;

/**
 * SQL执行模板
 *
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class BaseSqlSessionTemplate extends SqlSessionTemplate {
	private final Logger log = Logger.getLogger(getClass());
	private CCJSqlParserManager parserManager = new CCJSqlParserManager();// SQL语法解析器
	  
	public BaseSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	/**
	 * 分页查询
	 * @param statement
	 * @param page
	 * @param pageSize
	 * @return
	 */
    public <T> PageList<T> selectPageList(String statement, int page, int pageSize){
    	return this.selectPageList(statement, null, page, pageSize);
    }

	/**
	 * 分页查询
	 * @param statement
	 * @param parameter
	 * @param page
	 * @param pageSize
	 * @return
	 */
    public <T> PageList<T> selectPageList(String statement, Object parameter, int page, int pageSize){
    	if(pageSize<=0){
    		pageSize = PageList.DEFAULT_PAGE_SIZE;
    	}
    	
    	PageList<T> pageList = new PageList<T>();
    	pageList.setPageNumber(page);
    	pageList.setPageSize(pageSize);
    	return this.selectPageList(statement, parameter, pageList);
    }
    
	/**
	 * 分页查询
	 * @param statement
	 * @param pageList
	 * @return
	 */
    public <T> PageList<T> selectPageList(String statement, PageList<T> pageList){
    	return this.selectPageList(statement, null, pageList);
    }

	/**
	 * 分页查询
	 * @param statement
	 * @param parameter
	 * @param pageList
	 * @return
	 */
    public <T> PageList<T> selectPageList(String statement, Object parameter, PageList<T> pageList){
	    Configuration configuration = this.getSqlSessionFactory().getConfiguration();
    	MappedStatement ms = configuration.getMappedStatement(statement);
    	BoundSql boundSql  = ms.getBoundSql(parameter);
    	
    	// 创建查询总数的MappedStatement对象
    	// a. 拼接查询总记录数的SQL
		String sql = buildCountSql(boundSql.getSql());
    	BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
    	// b. 生成MappedStatement的builder
    	MappedStatement.Builder msBuilder = BaseSqlSessionUtil.copyMappedStatement(ms, newBoundSql);
    	// c. 给builder绑定ResultMap为Integer
		ResultMap.Builder rmBuilder = new ResultMap.Builder(
				ms.getConfiguration(), 
				ms.getDatabaseId()+"-Inline", 
				Integer.class,
				new ArrayList<ResultMapping>(),
				null);
		List<ResultMap> resultMaps = new ArrayList<ResultMap>();
		resultMaps.add(rmBuilder.build());
		msBuilder.resultMaps(resultMaps);
		
		// 执行查询总记录数语句
    	BaseSqlSession session = (BaseSqlSession)this.getSqlSessionFactory().openSession();
		List<Integer> countList = session.selectList(msBuilder.build(), parameter, RowBounds.DEFAULT);
		session.close();
		// 给PageList对象设置总记录数
		pageList.setTotalCount(countList.get(0));
		// 设置分页对象
		RowBounds rowBounds = new RowBounds(pageList.getFirstResultNumber(), pageList.getPageSize());
		// 执行分页查询
		List<T> list = this.selectList(statement, parameter, rowBounds);
		pageList.setResult(list);
		
    	return pageList;
    }

    /**
     * 生成统计SQL<br>
     * 采用JSQLParser语法解析器解析SQL语句，避免出现SQL注入等BUG
     * 
     * @param sql
     * @return
     */
    private String buildCountSql(String sql){
    	// 解析SQL
    	Select select = null;
    	try {
    		select = (Select)parserManager.parse(new StringReader(sql));
		} catch (JSQLParserException e) {
			log.error("parser sql error: < "+ sql +" >", e);
			return oldBBuildCountSql(sql);
		}
		PlainSelect ps = (PlainSelect) select.getSelectBody();

		// 去除排序、分组等语句
		ps.setGroupByColumnReferences(null);
		ps.setOrderByElements(null);
		ps.setHaving(null);
		ps.setTop(null);
		ps.setLimit(null);
		
		if(ps.getDistinct()!=null){
			return "SELECT count(*) FROM ( "+ deParserSql(select) +" ) mybatis_count_table_1900";
		}else{
			// 替换查询列为统计值
			SelectExpressionItem newCountSelect = new SelectExpressionItem();
			newCountSelect.setExpression(new Column(new Table(), "count(*)"));
			List<SelectItem> list = new ArrayList<SelectItem>();
			list.add(newCountSelect);
			ps.setSelectItems(list);
			
			return deParserSql(select);
		}
    }

    /**
     * 生成统计SQL<br>
     * 功能较弱，无纠错处理，不灵活
     * 
     * @param sql
     * @return
     */
    private String oldBBuildCountSql(String sql){
    	// 去除换行符等字符
    	sql = sql.trim();
		sql = sql.replaceAll("\\n", " ");
		sql = sql.replaceAll("\\r", " ");
		sql = sql.replaceAll("\\t", " ");
		
		int idx = -1;
		// 去除select语句
		idx = sql.toLowerCase().indexOf(" from ");
		if(idx!=-1){
			sql = sql.substring(idx);
		}
		// 去除order by排序语句
		idx = sql.toLowerCase().indexOf(" order by");
		if(idx!=-1){
			sql = sql.substring(0, idx);
		}
		// 去除group on分组语句
		idx = sql.toLowerCase().indexOf(" group on");
		if(idx!=-1){
			sql = sql.substring(0, idx);
		}
		// 去除for update语句
		idx = sql.toLowerCase().indexOf(" for update");
		if(idx!=-1){
			sql = sql.substring(0, idx);
		}
		
		// 拼接统计SELECT
		sql = "SELECT count(*) " + sql;
		
		return sql;
    }
    
    /**
     * 获取解析后的SQL语句
     * @param select
     * @return
     */
    private String deParserSql(Select select){
		StringBuilder stringBuffer = new StringBuilder();
		SqlDeParser deParser = new SqlDeParser(stringBuffer);
		deParser.visit(select);
		return stringBuffer.toString();
    }
} 
