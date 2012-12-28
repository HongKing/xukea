package com.xukea.framework.ibatis3;


import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.xukea.framework.base.BaseStringUtil;



public class BaseSqlSessionUtil{

	/**
	 * MappedStatement 对象copy
	 * @param ms
	 * @param sqlSource
	 * @return
	 */
    public static MappedStatement.Builder copyMappedStatement(MappedStatement ms, BoundSql boundSql) {
    	SqlSource sqlSource = new BaseSqlSessionUtil.BoundSqlSqlSource(boundSql);
    	
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource, ms.getSqlCommandType());
		
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.databaseId(ms.getDatabaseId());
		

		String keyProperty = BaseStringUtil.arrayToCommaDelimitedString(ms.getKeyProperties());
		builder.keyProperty(keyProperty);

		String keyColumns = BaseStringUtil.arrayToCommaDelimitedString(ms.getKeyColumns());
		builder.keyColumn(keyColumns);
		
		builder.keyGenerator(ms.getKeyGenerator());
		
		//setStatementTimeout()
		builder.timeout(ms.getTimeout());
		
		//setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());
		
		//setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		
		//setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		
		return builder;
	}
    
	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
}