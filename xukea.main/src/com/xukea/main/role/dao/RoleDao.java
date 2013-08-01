package com.xukea.main.role.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.main.role.model.Role;

/**
 * 角色处理Dao
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Repository
public class RoleDao  extends BaseDao{
    private String namespace = "com.xukea.main.role.model.Role";
	
    /**
     * 根据用户ID获取用户拥有的权限<br>
     * 包含通用权限"PUBLIC_ROLE"
     * @param userID
     * @return
     */
	public List<Role> getRoleByUserId(long userID){
		return this.getSqlSessionTemplate().selectList( namespace + ".getRoleByUserId", userID);
	}
	
    /**
     * 获取资源对应的角色信息
     * @param menuCode
     * @return
     */
	public List<Role> getRoleByMenuCode(String menuCode){
		return this.getSqlSessionTemplate().selectList( namespace + ".getRoleByMenuCode", menuCode);
	}
}
