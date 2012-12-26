package com.xukea.framework.base;


import java.util.List;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 缓存基类
 * 
 * @author FishBoy
 *
 * @param <T>
 */
public class BaseCache<T> extends GeneralCacheAdministrator {

	//过期时间(单位为秒);  
	private int refreshPeriod;

	//关键字前缀字符;  
	private String group;

	/**
	 * 
	 * @param group
	 * @param refreshPeriod
	 */
	public BaseCache(String group, int refreshPeriod) {
		super();
		this.group = group;
		this.refreshPeriod = refreshPeriod;
	}

	/**
	 * 添加被缓存的对象
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, T value) {
		this.putInCache(this.group + "_" + key, value, new String[]{group});
	}

	/**
	 * 获取被缓存的对象
	 * 
	 * @param key
	 * @return
	 * @throws NeedsRefreshException
	 */
	public T get(String key) throws NeedsRefreshException {
		try {
			return (T)this.getFromCache(this.group + "_" + key, this.refreshPeriod);
		} catch (NeedsRefreshException e) {
			//如果一个NeedsRefreshException出现 必须调用putInCache或cancelUpdate来避免死锁情况发生.
			this.cancelUpdate(this.group + "_" + key);
			throw e;
		}
	}

	/**
	 * 添加被缓存的List对象
	 * 
	 * @param key
	 * @param value
	 */
	public void putList(String key, List<T> value) {
		this.putInCache(this.group + "_" + key + "_list", value, new String[]{group});
	}
	
	/**
	 * 获取被缓存的List对象
	 * 
	 * @param key
	 * @return
	 * @throws NeedsRefreshException
	 */
	public List<T> getList(String key) throws NeedsRefreshException {
		try {
			return (List<T>) this.getFromCache(this.group + "_" + key + "_list", this.refreshPeriod);
		} catch (NeedsRefreshException e) {
			this.cancelUpdate(this.group + "_" + key);
			throw e;
		}
	}
	
	/**
	 * 删除被缓存的对象
	 * 
	 * @param key
	 */
	public void remove(String key) {
		this.flushEntry(this.group + "_" + key);
	}

	/**
	 * 删除被缓存的List对象
	 * 
	 * @param key
	 */
	public void removeList(String key) {
		this.flushEntry(this.group + "_" + key + "_list");
	}
	
	/**
	 * 删除所有的缓存对象
	 */
	public void removeAll() {
		this.flushGroup(group);
	}
}