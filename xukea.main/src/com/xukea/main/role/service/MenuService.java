package com.xukea.main.role.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xukea.framework.base.BaseService;
import com.xukea.main.role.dao.MenuDao;
import com.xukea.main.role.model.Menu;

/**
 * 菜单Service
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Service
public class MenuService extends BaseService {
	
	@Resource
	private MenuDao menuDao; 
	
    /**
     * 查询所有菜单信息
     * @return
     */
	public List<Menu> getAllMenuList(){
		return menuDao.getAllMenuList();
	}

    /**
     * 根据用户ID获取子菜单
     * 
     * @param uid
     * @param preMenuCode
     * @return
     */
    public List<Menu> getSubMenuByUID(long uid, String preMenuCode){
    	List<Menu> menus = menuDao.getSubMenuByUID(uid, preMenuCode);
    	for(Menu item : menus){
    		List<Menu> tempSub = getSubMenuByUID(uid, item.getCode()+"001"); //子菜单
    		item.setSubMenu(tempSub);

    		List<Menu> tempQuick = getQuickMenuByUID(uid, item.getCode()+"002");//快捷菜单
    		item.setQuickMenu(tempQuick);
    		
    		List<Menu> resuorce = menuDao.getSubMenuByUID(uid, item.getCode()+"003");//菜单资源
    		item.setResuorce(resuorce);
    	}
    	return menus;
    }

    /**
     * 根据用户ID获取快捷菜单
     * 
     * @param map
     * @return
     */
    public List<Menu> getQuickMenuByUID(long uid, String preMenuCode){
    	List<Menu> menus = menuDao.getSubMenuByUID(uid, preMenuCode);
    	for(Menu item : menus){
    		List<Menu> temp = getQuickMenuByUID(uid, item.getCode());
    		item.setQuickMenu(temp);
    	}
    	return menus;
    }
}
