package com.xukea.main.role.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xukea.framework.base.BaseService;
import com.xukea.main.role.dao.RoleDao;
import com.xukea.main.role.model.Role;

/**
 * 角色Service
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Service
public class RoleService extends BaseService {

	@Resource
	private RoleDao roleDao; 

    /**
     * 根据用户ID获取用户拥有的权限<br>
     * 包含通用权限"PUBLIC_ROLE"
     * @param userID
     * @return
     */
	public List<Role> getRoleByUserId(long userID){
		return roleDao.getRoleByUserId(userID);
	}

    /**
     * 获取资源对应的角色信息
     * @param menuCode
     * @return
     */
	public List<Role> getRoleByMenuCode(String menuCode){
		return roleDao.getRoleByMenuCode(menuCode);
	}
	
}