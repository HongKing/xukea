package com.xukea.main.role.dao;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.main.role.model.Role;


/**
 * 
 * @author 
 * @version
 */
@Repository
public class RoleDao  extends BaseDao<Role, String>{
    private String namespace = "com.xukea.main.role.model.Role";
	
}
