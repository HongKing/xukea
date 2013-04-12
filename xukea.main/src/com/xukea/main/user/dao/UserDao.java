package com.xukea.main.user.dao;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.main.user.model.User;


/**
 * 用户信息
 * @author 
 * @version
 */
@Repository
public class UserDao  extends BaseDao {
    private String namespace = "com.xukea.main.user.model.User";

	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	public User getUserByUserName(String username){
		return this.getSqlSessionTemplate().selectOne(namespace+".getUserByUserName", username);
	}
}
