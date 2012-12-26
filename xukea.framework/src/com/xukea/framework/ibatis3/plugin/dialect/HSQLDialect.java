package com.xukea.framework.ibatis3.plugin.dialect;

/**
 * HSQL分页
 * 
 * @author 石头
 *
 */
public class HSQLDialect extends Dialect{

	public boolean supportsLimitOffset(){
		return false;
	}
	
	public boolean supportsLimit() {
		return true;
	}

	/**
	 * 分页语句
	 */
	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		String loweredString = sql.toLowerCase();

		String sqlPartString = sql;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;  // "select".length();
			sqlPartString = sqlPartString.substring(index);
		}
		
		String limitString = "";
		if(offset > 0){
			limitString = " limit "+ offsetPlaceholder +" "+ limitPlaceholder ;
		}else{
			limitString = " top "+ limitPlaceholder ;
		}
		
		return "SELECT " + limitString + sqlPartString;
	}
    
}
