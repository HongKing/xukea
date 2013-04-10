package com.xukea.common.util.cache;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.xukea.framework.base.BaseCache;
import com.xukea.framework.util.ContextUtil;
import com.xukea.system.settings.model.SysSettings;
import com.xukea.system.settings.service.SysSettingsService;


/**
 * 系统设置缓存<br>
 * 
 * @author  FishBoy
 * @version 1.0
 * @date    2012-02-16
 */
public class Config extends BaseCache<SysSettings>{
	
	private static String GROUP_NAME     = "settings";
	private static int    REFRESH_PERIOD = 24*60*60;
	
	private static Object lock = new Object(); 
	private static Config instance = null;
	
	private SysSettingsService sysSettingsService;

	/**
	 * 构造方法，service需在这里手动获取bean
	 */
	private Config(){
		super(GROUP_NAME, REFRESH_PERIOD);
	}
	
	/**
	 * 单例工厂
	 * @return
	 */
	public static Config getInstance() {
		if (instance == null) {
			synchronized( lock ){   
                if (instance == null){   
                    instance = new Config();   
                }   
            }
		}
		return instance;
	}

	
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		SysSettings temp = get(key);
		return temp==null ? null : temp.getValue();
	}

	/**
     * 得到key的值（字符串）<br>
     * 默认值：空字符串
     * @param  key 取得其值的键
     * @return 字符串
     */
    public String getString(String key) {
    	String value = getValue(key);
        return value==null ? "" : value;
    }

	/**
     * 得到key的值（字符串）<br>
     * 默认值：空字符串
     * @param  key 取得其值的键
     * @return 字符串
     */
    public String getString(String key, String charset) {
    	String value = getString(key);
        try {
			value = new String(value.getBytes(), charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return value;
    }
    
	/**
     * 得到key的值（数字）<br>
     * 默认值：0
     * @param  key 取得其值的键
     * @return 数字
     */
    public int getInt(String key){
    	String val = getString(key);
    	try{
    		return Integer.parseInt(val);
    	}catch(Exception e){
    		return 0;
    	}
    }

	/**
     * 得到key的值（boolean）<br>
     * 默认值：false
     * @param  key 取得其值的键
     * @return 数字
     */
    public boolean getBoolean(String key){
    	String val = getString(key);
    	try{
    		return Boolean.parseBoolean(val);
    	}catch(Exception e){
    		return false;
    	}
    }
	
	/**
	 * 刷新缓存
	 */
	@Override
	public void refresh(){
		this.removeAll(); // 删除所有缓存
		cacheFromFile();   // 缓存Config中的数据
		cacheFromDB();     // 缓存DB中的数据
	}
	

	/**
	 * 从Config中获取数据，并缓存
	 */
	private void cacheFromFile(){
		String configName    = "config/config.properties";
		Properties propertie = new Properties();
		InputStream inputStrem = Thread.currentThread().getContextClassLoader().getResourceAsStream(configName);
//		inputStrem = this.getClass().getClassLoader().getResourceAsStream(configName);
		try {
			propertie.load(inputStrem); //读取config内容
			Enumeration enu = propertie.propertyNames();
			while(enu.hasMoreElements()){
				String key = (String)enu.nextElement();
				String val = propertie.getProperty(key);
				SysSettings temp = new SysSettings();
				temp.setName(key);
				temp.setValue(val);
				this.put(key, temp);//缓存当前对象
			}
		}catch (Exception e) {
			log.error("Load config file error. File path is "+configName, e);
		}
	}
	
	/**
	 * 从DB中获取数据，并缓存
	 * @param obj
	 */
	private void cacheFromDB(){
		boolean flag = assetSysSettingsService();
		if(!flag){
			log.debug("config cache, db is not ready");
			return;
		}
		List<SysSettings> list = sysSettingsService.getListByName( null );
		for(SysSettings temp : list){
			this.put(temp.getName(), temp); //缓存当前对象
		}
	}
	
	/**
	 * 检测Service是否准备就绪
	 * @return
	 */
	private boolean assetSysSettingsService(){
		if(sysSettingsService==null){
			// Service需要手动加载
			try{
				sysSettingsService = ContextUtil.getBean(SysSettingsService.class);
			}catch(Exception e){
				log.error(e);
				return false;
			}
		}
		return true;
	}
}