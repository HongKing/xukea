package com.xukea.common.util;

import com.xukea.framework.base.BaseConstants;


/**
 * 主框架系统常量<br>
 * 该类主要用于主框架中，对于各个插件，请尽量不要采用该类处理
 * @author FishBoy
 * @version
 */
public final class MainConstants extends BaseConstants{
	
	/******************************************
	 * 插件状态
	 ******************************************/
	public final static int PLUGIN_STATUS_YQY = 1; //已启用
	public final static int PLUGIN_STATUS_YAZ = 2; //已安装
	public final static int PLUGIN_STATUS_WAZ = 3; //未安装
	public final static int PLUGIN_STATUS_DSJ = 4; //待升级
	public final static int PLUGIN_STATUS_YSC = 5; //已删除
	public final static int PLUGIN_STATUS_KAZ = 6; //可安装
	public final static int PLUGIN_STATUS_KSJ = 7; //可升级
	
	public final static String getPluginStatus(int type){
		switch(type){
		case PLUGIN_STATUS_YQY :  return "已启用";
		case PLUGIN_STATUS_YAZ :  return "已安装";
		case PLUGIN_STATUS_WAZ :  return "未安装";
		case PLUGIN_STATUS_DSJ :  return "待升级";
		case PLUGIN_STATUS_YSC :  return "已删除";
		case PLUGIN_STATUS_KAZ :  return "可安装";
		case PLUGIN_STATUS_KSJ :  return "可升级";
		default                :  return "未知";
		}
	}
	
}