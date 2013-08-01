package com.xukea.system.settings.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.system.settings.model.SysSettings;

/**
 * 系统设置Dao
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Repository
public class SysSettingsDao  extends BaseDao{
    private String namespace = "com.xukea.system.settings.model.SysSettings";

	/**
	 * 插入
	 * @param sysSettings
	 * @return
	 */
	public void insert(SysSettings sysSettings) {
		getSqlSessionTemplate().insert(namespace + ".insert", sysSettings);
	}
	
	/**
	 * 更新
	 * @param sysSettings
	 * @return
	 */
	public void update(SysSettings sysSettings) {
		getSqlSessionTemplate().update(namespace + ".update", sysSettings);
	}
	
	/**
	 * 获取系统设置列表
	 * @param map
	 * @return
	 */
	public List<SysSettings> getList(Map<String, Object> map){
		return getSqlSessionTemplate().selectList(namespace + ".getList", map);
	}

	
	/**
	 * 获取系统设置单条详情
	 * @param name
	 * @return
	 */
	public SysSettings getByName(String name){
		return (SysSettings)getSqlSessionTemplate().selectOne(namespace + ".getByName", name);
	}

	/**
	 * 删除单条
	 * @param name
	 * @return
	 */
	public int delete(String name){
		return getSqlSessionTemplate().delete(namespace + ".delete", name);
	}
	
	/**
	 * 删除preName开头的配置
	 * @param preName
	 * @return
	 */
	public int deleteByPreName(String preName){
		return getSqlSessionTemplate().delete(namespace + ".deleteByPreName", preName);
	}
}
