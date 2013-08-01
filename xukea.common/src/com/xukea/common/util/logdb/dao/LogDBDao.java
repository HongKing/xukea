package com.xukea.common.util.logdb.dao;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;

/**
 * 系统日志DAO
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
@Repository
public class LogDBDao extends BaseDao {
	
	/**
	 * 保存日志到数据库
	 * @param namespace
	 * @param data
	 */
	public void saveLog2DB(String namespace, Object data){
		getSqlSessionTemplate().insert(namespace + ".insert", data);
	}
}
