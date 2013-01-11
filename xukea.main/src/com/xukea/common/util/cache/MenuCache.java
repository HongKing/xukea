package com.xukea.common.util.cache;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.xukea.framework.base.BaseCache;
import com.xukea.framework.util.ContextUtil;
import com.xukea.main.role.model.Menu;
import com.xukea.main.role.service.MenuService;


/**
 * 所有菜单资源缓存<br>
 * 
 * @author  FishBoy
 * @version 1.0
 * @date    2012-02-16
 */
public class MenuCache {
	private final Logger log = Logger.getLogger(getClass());
	
	private static String GROUP_NAME     = "menu";
	private static int    REFRESH_PERIOD = 24*60*60;
	private static Object lock = new Object(); 
	private static BaseCache<Menu> cache = null;
	private static MenuCache instance = null;
	
	private MenuService menuService;

	/**
	 * 构造方法，service需在这里手动获取bean
	 */
	private MenuCache(){
		cache = new BaseCache<Menu>(GROUP_NAME, REFRESH_PERIOD);
		// Service需要手动加载
		menuService = ContextUtil.getBean(MenuService.class);
	}
	
	/**
	 * 单例工厂
	 * @return
	 */
	public static MenuCache getInstance() {
		if (instance == null) {
			synchronized( lock ){   
                if (instance == null){   
                    instance = new MenuCache();   
                }   
            }
		}
		return instance;
	}

	/**
	 * 新增缓存<br>
	 * 按照shortName保存
	 * 
	 * @param key
	 * @param value
	 */
	public void put(Menu value){
		this.put(value.getCode(), value);
		this.put(value.getShortWord(), value);
	}
	
	/**
	 * 新增缓存
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Menu value){
		cache.put(key, value);
	}
	
	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Menu get(String key){
		try {
			return cache.get(key);
		} catch (NeedsRefreshException e) {
			this.refresh(); //刷新缓存
			try {
				return cache.get(key);
			} catch (NeedsRefreshException ee) {
				//刷新缓存后，还有异常的话，说明该key对应的数据不存在
				log.error("There is no cache for : "+key);
				return null;
			}
		}
	}

	/**
	 * 新增缓存
	 * 
	 * @param key
	 * @param value
	 */
	public void putList(String key, List<Menu> value){
		cache.putList(key, value);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	public List<Menu> getList(String key){
		try {
			return cache.getList(key);
		} catch (NeedsRefreshException e) {
			this.refresh(); //刷新缓存
			try {
				return cache.getList(key);
			} catch (NeedsRefreshException ee) {
				//刷新缓存后，还有异常的话，说明该key对应的数据不存在
				log.error("There is no cache for : "+key);
				return null;
			}
		}
	}
	
	/**
	 * 根据URL查找数据库中对应的code
	 * @param url
	 * @return
	 */
	public String getUrlCode(String url){
		List<Menu> list = this.getList("ALL_MENU");
		if(list==null) return "";

		String code = "";
		url = url.replaceAll("[/]+", "/");//将连续的多个/替换为一个/
		for(Menu item : list){
			Pattern pattern = Pattern.compile(item.getUrl());
			Matcher match = pattern.matcher(url);
			if(match.matches()){
				code = item.getCode();
				break;
			}
		}
		return code;
	}
	
	/**
	 * 刷新缓存
	 */
	public void refresh(){
		cache.removeAll(); // 删除所有缓存
		cacheFromDB();     // 缓存DB中的数据
	}
	
	/**
	 * 从DB中获取数据，并缓存
	 * @param obj
	 */
	private void cacheFromDB(){
		List<Menu> list = menuService.getAllMenuList();
		List<Menu> menus = new ArrayList<Menu>();
		
		for(Menu item : list){
			String url = item.getUrl();
			if(url==null || "".equals(url)) continue;

			//替换URL中的变量为正则表达式，便于后期检索
			url = url.replaceAll("\\{[\\w]*\\}", "([^\\\\/]*(\\\\S)+)");//每个/分割之间必须有字符才能匹配
			url = url+"(/index)?"; //默认匹配index
			url = url+"(/)?";      //允许URL的结尾有“/”
			item.setUrl(url);
			
			this.put(item);  //缓存当前对象
			menus.add(item); //只缓存有URL的对象
		}
		this.putList("ALL_MENU", menus);
	}
	
}