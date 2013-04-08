package com.xukea.main.role.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xukea.common.util.MainConstants;
import com.xukea.framework.base.BaseDao;
import com.xukea.main.role.model.Menu;


/**
 * 菜单处理
 * @author 
 * @version
 */
@Repository
public class MenuDao  extends BaseDao{
    private String namespace = "com.xukea.main.role.model.Menu";
    
    /**
     * 根据用户ID获取相关菜单
     * @param uid
     * @param preMenuCode
     * @return
     */
    public List<Menu> getSubMenuByUID(long uid, String preMenuCode){
    	preMenuCode = preMenuCode==null ? "" : preMenuCode;
    	
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("userId", uid);
    	map.put("preCode", preMenuCode);
    	map.put("pluginStatus", MainConstants.PLUGIN_STATUS_YQY); //已启用的插件
    	
    	return this.getSqlSessionTemplate().selectList( namespace + ".getSubMenuByUID", map );
    }
    
    /**
     * 查询所有菜单信息
     * @return
     */
    public List<Menu> getAllMenuList(){
    	return this.getSqlSessionTemplate().selectList( namespace + ".getAllMenuList" );
    }
}
