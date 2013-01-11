package com.xukea.main.plugin.dao;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.main.plugin.model.PluginInfo;


/**
 * 插件信息
 * @author 
 * @version
 */
@Repository
public class PluginInfoDao  extends BaseDao<PluginInfo, String>{
    private String namespace = "com.xukea.main.plugin.model.PluginInfo";
    
	
}
