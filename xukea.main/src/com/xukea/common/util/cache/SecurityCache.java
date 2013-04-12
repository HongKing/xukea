package com.xukea.common.util.cache;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import com.xukea.framework.base.BaseCache;
import com.xukea.framework.util.ContextUtil;
import com.xukea.main.role.model.Menu;
import com.xukea.main.role.model.Role;
import com.xukea.main.role.service.RoleService;


/**
 * URL资源访问权限信息缓存<br>
 * 
 * @author  FishBoy
 * @version 1.0
 * @date    2012-02-16
 */
public class SecurityCache extends BaseCache<List<ConfigAttribute>>{
	private static String GROUP_NAME     = "security";
	private static int    REFRESH_PERIOD = 24*60*60;
	private static Object lock = new Object(); 
	private static SecurityCache instance = null;

	private RoleService roleService; 

	/**
	 * 构造方法，service需在这里手动获取bean
	 */
	private SecurityCache(){
		super(GROUP_NAME, REFRESH_PERIOD);
		// Service需要手动加载
		roleService = ContextUtil.getBean(RoleService.class);
	}
	
	/**
	 * 单例工厂
	 * @return
	 */
	public static SecurityCache getInstance() {
		if (instance == null) {
			synchronized( lock ){   
                if (instance == null){   
                    instance = new SecurityCache();   
                }   
            }
		}
		return instance;
	}
	
	/**
	 * 获取访问URL所需的权限
	 * @param url 
	 * @return
	 */
	public List<ConfigAttribute> getRole4URL(String url){
		List<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
		// url对应的内码
		List<String> list = MenuCache.getInstance().getUrlCodes(url, true);
		// 获取权限
		for(String code : list){
			List<ConfigAttribute> temp = this.get(code);
			result.addAll(temp);
		}
		return result;
	}
	
	/**
	 * 刷新缓存
	 */
	public void refresh(){
		this.removeAll(); // 删除所有缓存
		cacheFromDB();     // 缓存DB中的数据
	}
	
	private void cacheFromDB(){
    	// 获取系统所有的URL
        List<Menu> menus = MenuCache.getInstance().getList("ALL_MENU");
        // 设置URL与角色的对应关系
        for(Menu menu : menus){
        	// 角色列表
        	List<Role> roles = roleService.getRoleByMenuCode(menu.getCode());
            List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
        	for(Role role : roles){
        		// 转换角色名为Spring Security的格式
        		ConfigAttribute temp = new SecurityConfig(role.getShortWord());
        		list.add(temp);
        	}
            this.put(menu.getCode(), list);
        }
	}
	
}