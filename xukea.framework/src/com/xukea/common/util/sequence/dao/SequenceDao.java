package com.xukea.common.util.sequence.dao;


import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.xukea.common.util.sequence.model.Sequence;
import com.xukea.framework.base.BaseDao;


/**
 * 手动序列控制
 * @author 石头
 *
 */
@Repository
public class SequenceDao extends BaseDao{
	private String namespace = "com.xukea.common.util.sequence.model.Sequence";

	/**
	 * 插入
	 * @param sysSettings
	 * @return
	 */
	public void insert(Sequence data) {
		getSqlSessionTemplate().insert(namespace + ".insert", data);
	}
	
	/**
	 * 更新
	 * @param sysSettings
	 * @return
	 */
	public void update(Sequence data) {
		getSqlSessionTemplate().update(namespace + ".update", data);
	}
	
	/**
	 * 下一个ID
	 * @param tabName
	 * @return
	 */
	public Sequence getNextId(String tabName){
		return (Sequence)this.getSqlSessionTemplate().selectOne(namespace +".getNextId", tabName);
	}

	/**
	 * 下一个编号
	 * @param tabName
	 * @param preCode
	 * @return
	 */
	public String getNextCode(String tabName, String preCode){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tabName", tabName);
		map.put("preCode", preCode);
		return (String)this.getSqlSessionTemplate().selectOne(namespace +".getNextCode", map);
	}
}
