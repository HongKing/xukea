package com.xukea.common.util;

import java.util.HashMap;

/**
 * 当前运行线程局部变量管理
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @date   2013.08.21
 */
public class ThreadLocalInstance {
	// 本地线程，用于存储线程公共对象
    private static ThreadLocal<HashMap<String, Object>> threadLocalSession = new ThreadLocal<HashMap<String, Object>>();
        
	/**
	 * 获取本地执行线程
	 * @return
	 */
	public static ThreadLocal<HashMap<String, Object>> getThreadLocalSession() {
		return threadLocalSession;
	}
	
	/**
	 * 获取在线程内设置存储的对象
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		HashMap<String, Object> map = threadLocalSession.get();
		if(map==null){
			return null;
		}else{
			return map.get(key);
		}
	}
	
	/**
	 * 在线程内设置存储对象
	 * 
	 * @param key
	 * @param obj
	 */
	public static void set(String key, Object obj) {
		HashMap<String, Object> map = threadLocalSession.get();
		if(map==null){
			map = new HashMap<String, Object>();
		}
		map.put(key, obj);
		
	    threadLocalSession.set(map);
	}
	
	/**
	 * 移除在线程内设置存储的指定对象
	 * 
	 * @param key
	 */
	public static void remove(String key) {
		HashMap<String, Object> map = threadLocalSession.get();
		if(map==null) return;
		map.remove(key);
		threadLocalSession.set(map);
	}
	
	/**
	 * 移除在线程内设置存储的所有对象，释放资源
	 */
	public static void remove() {
		threadLocalSession.remove();
	}
}