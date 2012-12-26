package com.xukea.framework.ibatis3.plugin.dialect;

/**
 * MySQL 分页
 *  
 * @author 石头
 *
 */
public class MySQLDialect extends Dialect{

	public boolean supportsLimitOffset(){
		return true;
	}
	
    public boolean supportsLimit() {   
        return true;   
    }  
	
	/**
	 * 分页语句
	 */
	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        if (offset > 0) {   
        	return sql + " limit "+offsetPlaceholder+","+limitPlaceholder; 
        } else {   
            return sql + " limit "+limitPlaceholder;
        }  
	}   
  
}
