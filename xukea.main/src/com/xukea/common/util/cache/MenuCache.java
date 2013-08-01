package com.xukea.common.util.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xukea.framework.base.BaseCache;
import com.xukea.framework.util.ContextUtil;
import com.xukea.main.role.model.Menu;
import com.xukea.main.role.service.MenuService;

/**
 * 所有菜单资源缓存<br>
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class MenuCache extends BaseCache<Menu>{
	private static String GROUP_NAME     = "menu";
	private static int    REFRESH_PERIOD = 24*60*60;
	private static Object lock = new Object(); 
	private static MenuCache instance = null;
	
	private MenuService menuService;

	/**
	 * 构造方法，service需在这里手动获取bean
	 */
	private MenuCache(){
		super(GROUP_NAME, REFRESH_PERIOD);
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
	 * 根据URL查找数据库中对应的code
	 * @param url
	 * @return
	 */
	public String getUrlCode(String url){
		List<String> list = getUrlCodes(url, false);
		// 返回找到的第一个code值
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return "";
		}
	}

	/**
	 * 根据URL查找数据库中所有匹配的code
	 * @param url   待查询的URL
	 * @param flag  是否遍历所有的menu
	 * @return
	 */
	public List<String> getUrlCodes(String url, boolean flag){
		List<String> list = new ArrayList<String>();
		List<Menu> menus = this.getList("ALL_MENU");
		if(menus==null) return list;

		url = url.replaceAll("[/]+", "/");//将连续的多个/替换为一个/
		for(Menu item : menus){
			Pattern pattern = Pattern.compile(item.getUrl());
			Matcher match = pattern.matcher(url);
			if(match.matches()){
				list.add(item.getCode());
				if(!flag) break; // 不遍历所有的menu
			}
		}
		return list;
	}
	
	/**
	 * 刷新缓存
	 */
	public void refresh(){
		this.removeAll(); // 删除所有缓存
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
			
			//缓存当前对象
			this.put(item.getCode(), item);
//			this.put(item.getShortWord(), item);
			
			//所有菜单只缓存有URL的对象
			menus.add(item);
		}
		this.putList("ALL_MENU", menus);
	}
	
}