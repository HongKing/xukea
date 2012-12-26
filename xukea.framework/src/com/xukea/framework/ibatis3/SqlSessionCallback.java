package com.xukea.framework.ibatis3;

import org.apache.ibatis.session.SqlSession;

/**
 * 回调接口，主要用于执行SQL
 */
public interface SqlSessionCallback {
	public Object doInSession(SqlSession session);
}