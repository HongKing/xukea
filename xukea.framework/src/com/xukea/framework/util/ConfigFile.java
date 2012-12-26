package com.xukea.framework.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件处理
 * @author FishBoy
 * @version
 */
public final class ConfigFile {
	
	private static ConfigFile config = null;
	private String configName = "config/config.properties";
	private Properties propertie;

	public static ConfigFile getInstance() {
		if (config == null) {
			config = new ConfigFile();
		}
		return config;
	}

	public ConfigFile(){
		readFromFile(configName);
	}

	public ConfigFile(String configName){
		readFromFile(configName);
	}
	
	/**
	 * 从文件读取配置信息
	 * @param configName
	 */
	private void readFromFile(String configName){
		InputStream inputStrem = Thread.currentThread().getContextClassLoader().getResourceAsStream(configName);
//		inputStrem = this.getClass().getClassLoader().getResourceAsStream(configName);
		propertie  = new Properties();
		try {
			propertie.load(inputStrem);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 得到key的值（字符串）<br>
     * 默认值：空字符串
     * @param  key 取得其值的键
     * @return 字符串
     */
    public String getString(String key) {
        if(propertie.containsKey(key)){
            String value = propertie.getProperty(key);//得到某一属性的值 
            return value;
        }else{
            return "";
        }
    }

	/**
     * 得到key的值（字符串）<br>
     * 默认值：空字符串
     * @param  key 取得其值的键
     * @return 字符串
     */
    public String getString(String key, String charset) {
        if(propertie.containsKey(key)){
            String value = propertie.getProperty(key);//得到某一属性的值 
            try {
				value = new String(value.getBytes(), charset);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
            return value;
        }else{
            return "";
        }
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
}