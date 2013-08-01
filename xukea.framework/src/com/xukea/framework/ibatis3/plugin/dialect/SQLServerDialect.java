package com.xukea.framework.ibatis3.plugin.dialect;

/**
 * SQL Server分页<br>
 * 注：仅支持2005以上的版本
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class SQLServerDialect extends Dialect{

	public boolean supportsLimitOffset(){
		return true;
	}
	
	public boolean supportsLimit() {
		return true;
	}

	/**
	 * Add a LIMIT clause to the given SQL SELECT
	 *
	 * The LIMIT SQL will look like:
	 *
	 * WITH query AS
	 *      (SELECT TOP 100 percent ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) as __row_number__, * from table_name)
	 * SELECT *
	 * FROM query
	 * WHERE __row_number__ BETWEEN :offset and :lastRows
	 * ORDER BY __row_number__
	 * 
	 * @param sql            The SQL statement to base the limit query off of.
	 * @param offset         Offset of the first row to be returned by the query (zero-based)
	 * @param last           Maximum number of rows to be returned by the query
	 * @return A new SQL statement with the LIMIT clause applied.
	 */
	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		StringBuffer pagingBuilder = new StringBuffer();
		String loweredString = sql.toLowerCase();
		
		// 截取查询语句
		String distinctStr = "";
		String sqlPartString = sql;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;  // "select".length();
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15; // "select distinct".length();
			}
			sqlPartString = sqlPartString.substring(index);
		}
		pagingBuilder.append(sqlPartString);

		// 获取排序语句
		// if no ORDER BY is specified use fake ORDER BY field to avoid errors
		String orderby = getOrderByPart(loweredString);
		if (orderby == null || "".equals(orderby)) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		// 拼装新的SQL
		StringBuffer result = new StringBuffer();
		result.append("WITH query AS (SELECT ")
				.append(distinctStr)
				.append("TOP 100 PERCENT ")
				.append(" ROW_NUMBER() OVER (")
				.append(orderby)
				.append(") as __row_number__, ")
				.append(pagingBuilder)
				.append(") SELECT * FROM query WHERE __row_number__ BETWEEN ")
				.append(offset+1).append(" AND ").append( offset+limit )
				.append(" ORDER BY __row_number__");

		return result.toString();
	}

	private static String getOrderByPart(String sql) {
		int orderByIndex = sql.indexOf("order by");
		if (orderByIndex != -1) {
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}
}
