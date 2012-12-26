package com.xukea.common.util;


import com.xukea.common.util.cache.SysSettingsCache;

/**
 * 系统设置读取<br>
 * 配合系统设置缓存实现
 * 
 * @author FishBoy
 * @version
 */
public final class Config {
	
	private static Config config = null;

	public static Config getInstance() {
		if (config == null) {
			config = new Config();
		}
		return config;
	}
	
	private Config(){
	}
	
	/**
	 * 更新系统配置数据
	 */
	public void refresh(){
		SysSettingsCache.getInstance().refresh();
	}
	
	/**
     * 得到key的值（字符串）<br>
     * 默认值：空字符串
     * @param  key 取得其值的键
     * @return 字符串
     */
    public String getString(String key) {
    	String value = SysSettingsCache.getInstance().getValue(key);
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
}