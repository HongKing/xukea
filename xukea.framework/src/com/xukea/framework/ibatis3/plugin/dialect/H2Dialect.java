package com.xukea.framework.ibatis3.plugin.dialect;

/**
 * H2分页
 * 
 * @author 石头
 *
 */
public class H2Dialect extends Dialect {

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
		if (offset > 0) {   
        	return sql + " limit "+ limitPlaceholder +" offset "+ offsetPlaceholder; 
        } else {   
            return sql + " limit "+ limitPlaceholder;
        }
	}
}