package com.xukea.main.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xukea.framework.base.BaseService;
import com.xukea.main.user.dao.UserDao;
import com.xukea.main.user.model.User;

/**
 * 用户Service
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Service
public class UserService extends BaseService {

	@Resource
	private UserDao userDao; 
	
	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	public User getUserByUserName(String username){
		return userDao.getUserByUserName(username);
	}
	
}