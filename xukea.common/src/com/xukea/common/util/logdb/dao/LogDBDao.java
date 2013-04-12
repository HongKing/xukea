package com.xukea.common.util.logdb.dao;


import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;


@Repository
public class LogDBDao extends BaseDao{
	
	/**
	 * 保存日志到数据库
	 * @param namespace
	 * @param data
	 */
	public void saveLog2DB(String namespace, Object data){
		getSqlSessionTemplate().insert(namespace + ".insert", data);
	}
}
