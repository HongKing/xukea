package com.xukea.framework.base;

import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 缓存基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public abstract class BaseCache<T> extends GeneralCacheAdministrator {
	protected final Logger log = Logger.getLogger(getClass());

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
	 * 获取被缓存的对象<br>
	 * 刷新缓存
	 * 
	 * @param key      键
	 */
	public T get(String key) {
		return get(key, true);
	}

	/**
	 * 获取被缓存的对象
	 * 
	 * @param key      键
	 * @param refresh  若取不到值，是否刷新
	 */
	public T get(String key, boolean refresh) {
		try {
			return (T)this.getFromCache(this.group + "_" + key, this.refreshPeriod);
		} catch (NeedsRefreshException e) {
			if(!refresh){
				log.debug("Not refresh cache, there is no cache for : "+key);
				return null;
			}
			//如果一个NeedsRefreshException出现 必须调用putInCache或cancelUpdate来避免死锁情况发生.
			this.cancelUpdate(this.group + "_" + key);
			this.refresh(); //刷新缓存
			// 刷新后，重新获取数据
			try {
				return (T)this.getFromCache(this.group + "_" + key, this.refreshPeriod);
			} catch (NeedsRefreshException ee) {
				//刷新缓存后，还有异常的话，说明该key对应的数据不存在
				log.error("After refresh cache, there is no cache for : "+key);
				return null;
			}
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
	 * 获取被缓存的List对象<br>
	 * 刷新缓存
	 * 
	 * @param key      键
	 */
	public List<T> getList(String key) {
		return getList(key, true);
	}
	
	/**
	 * 获取被缓存的List对象
	 * 
	 * @param key      键
	 * @param refresh  若取不到值，是否刷新
	 */
	public List<T> getList(String key, boolean refresh) {
		try {
			return (List<T>) this.getFromCache(this.group + "_" + key + "_list", this.refreshPeriod);
		} catch (NeedsRefreshException e) {
			if(!refresh){
				log.debug("Not refresh cache, there is no list cache for : "+key);
				return null;
			}
			//如果一个NeedsRefreshException出现 必须调用putInCache或cancelUpdate来避免死锁情况发生.
			this.cancelUpdate(this.group + "_" + key + "_list");
			this.refresh(); //刷新缓存
			// 刷新后，重新获取数据
			try {
				return (List<T>) this.getFromCache(this.group + "_" + key + "_list", this.refreshPeriod);
			} catch (NeedsRefreshException ee) {
				//刷新缓存后，还有异常的话，说明该key对应的数据不存在
				log.error("After refresh cache, there is no list cache for : "+key);
				return null;
			}
		}
	}
	
	/**
	 * 删除被缓存的对象
	 * 
	 * @param key
	 */
	public void remove(String key) {
		//获取缓存对象
		T temp = this.get(key, false);
		if(temp==null) return;
		//删除缓存
		this.flushEntry(this.group + "_" + key);
	}

	/**
	 * 删除被缓存的List对象
	 * 
	 * @param key
	 */
	public void removeList(String key) {
		//获取缓存对象
		List<T> temp = this.getList(key, false);
		if(temp==null) return;
		//删除缓存
		this.flushEntry(this.group + "_" + key + "_list");
	}
	
	/**
	 * 删除所有的缓存对象
	 */
	public void removeAll() {
		this.flushGroup(group);
	}

	/**
	 * 更新缓存
	 * 
	 * @param key
	 * @param value
	 */
	public void update(String key, T value){
		if(value==null) return;
		
		this.remove(key);
		this.put(key, value);
	}

	/**
	 * 更新缓存
	 * 
	 * @param key
	 * @param value
	 */
	public void update(String key, List<T> value){
		if(value==null) return;
		
		this.removeList(key);
		this.putList(key, value);
	}
	
	/**
	 * 更新缓存
	 */
	public abstract void refresh();
}