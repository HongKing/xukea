package com.xukea.main.user.dao;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.main.user.model.User;

/**
 * 用户信息Dao
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
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
