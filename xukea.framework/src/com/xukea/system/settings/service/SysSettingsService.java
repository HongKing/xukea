package com.xukea.system.settings.service;

 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xukea.framework.base.BaseService;
import com.xukea.system.settings.dao.SysSettingsDao;
import com.xukea.system.settings.model.SysSettings;

/**
 * 系统设置Service
 * @author 石头
 *
 */
@Component
public class SysSettingsService extends BaseService<SysSettings, Long>{

	@Resource
	private SysSettingsDao sysSetingsDao;

	/**
	 * 根据键名获取相应的对象
	 * @param name
	 * @return
	 */
	public SysSettings getByName(String name){
		return sysSetingsDao.getByName(name);
	}
	
	/**
	 * 根据键名获取相应的对象列表
	 * @param preName
	 * @return
	 */
	public List<SysSettings> getListByName(String preName){
		Map<String, Object> map = new HashMap<String, Object>();
		if(preName!=null && !"".equals(preName)){
			map.put("preName", preName);
		}
		
		return sysSetingsDao.getList(map);
	}

	/**
	 * 插入（或者更新）系统设置
	 * @param name
	 * @param value
	 * @param remark
	 */
	public void insertOrUpdage(String name, String value, String remark){
		SysSettings data = new SysSettings();
		data.setName(name);
		data.setValue(value);
		data.setRemark(remark);
		try{
			sysSetingsDao.insert(data);
		}catch(Exception e){
			sysSetingsDao.update(data);
		}
	}

	/**
	 * 删除单条
	 * @param name
	 * @return
	 */
	public int deleteByName(String name){
		if(name==null || "".equals(name)) return -1;
		
		return sysSetingsDao.delete(name);
	}
	
	/**
	 * 删除preName开头的配置
	 * @param preName
	 * @return
	 */
	public int deleteByPreName(String preName){
		if(preName==null || "".equals(preName)) return -1;
		
		return sysSetingsDao.deleteByPreName(preName);
	}
}
