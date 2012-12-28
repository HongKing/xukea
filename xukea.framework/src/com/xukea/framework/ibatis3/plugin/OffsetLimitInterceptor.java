package com.xukea.framework.ibatis3.plugin;

import java.util.Properties;

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

import com.xukea.framework.ibatis3.BaseSqlSessionUtil;
import com.xukea.framework.ibatis3.plugin.dialect.Dialect;
import com.xukea.framework.util.PropertiesHelper;


/**
 * 为ibatis3提供基于方言(Dialect)的分页查询的插件
 * 
 * 将拦截Executor.query()方法实现分页方言的插入.
 * 
 * 配置文件内容:
 * <pre>
 * 	&lt;plugins>
 *		&lt;plugin interceptor="cn.org.xukea.ibatis3.plugin.OffsetLimitInterceptor">
 *			&lt;property name="dialectClass" value="cn.org.xukea.jdbc.dialect.MySQLDialect"/>
 *		&lt;/plugin>
 *	&lt;/plugins>
 * </pre>
 * 
 */
@Intercepts({@Signature(
		type   = Executor.class,
		method = "query",
		args   = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class OffsetLimitInterceptor implements Interceptor{
	static int IDX_STATEMENT = 0;
	static int IDX_PARAMETER = 1;
	static int IDX_ROWBOUNDS = 2;
	static int IDX_RS_HANDLE = 3;
	
	Dialect dialect;
	
	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	private void processIntercept(final Object[] queryArgs) {
		//queryArgs = query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler)
		MappedStatement ms = (MappedStatement)queryArgs[IDX_STATEMENT];
		Object parameter   = queryArgs[IDX_PARAMETER];
		final RowBounds rowBounds = (RowBounds)queryArgs[IDX_ROWBOUNDS];
		int offset = rowBounds.getOffset();
		int limit  = rowBounds.getLimit();
		
		if(dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			BoundSql boundSql = ms.getBoundSql(parameter);
			String sql = boundSql.getSql().trim();
			if (dialect.supportsLimitOffset()) {
				sql = dialect.getLimitString(sql, offset, limit);
				offset = RowBounds.NO_ROW_OFFSET;
			} else {
				sql = dialect.getLimitString(sql, 0, limit);
			}
			limit = RowBounds.NO_ROW_LIMIT;
			
			queryArgs[IDX_ROWBOUNDS] = new RowBounds(offset, limit);
			
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			MappedStatement.Builder builder = BaseSqlSessionUtil.copyMappedStatement(ms, newBoundSql);
			queryArgs[IDX_STATEMENT] = builder.build();
		}
	}
	
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String dialectClass = new PropertiesHelper(properties).getRequiredString("dialectClass");
		try {
			dialect = (Dialect)Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("cannot create dialect instance by dialectClass:"+dialectClass,e);
		} 
	}
	
}
