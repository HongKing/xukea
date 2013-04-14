package com.xukea.demo.userinfo.service;


import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.xukea.common.util.sequence.service.SequenceService;
import com.xukea.demo.userinfo.dao.UserExpDao;
import com.xukea.demo.userinfo.dao.UserInfoDao;
import com.xukea.demo.userinfo.model.UserInfo;
import com.xukea.framework.base.BaseService;
import com.xukea.framework.util.PageList;

@Service
public class UserInfoService extends BaseService{

	@Resource
	private SequenceService sequenceService; //序号自动生成的Service

	@Resource
	private UserInfoDao userInfoDao;         //用户基本数据Dao
	
	@Resource
	private UserExpDao  userExpDao;          //用户扩展数据Dao

	@Resource
	private DataSourceTransactionManager transactionManager; //事务管理器

	/**
	 * 用户注册
	 * 
	 * @param userName
	 * @param password
	 * @param hobby
	 * @return
	 */
	public long registe(String username, String password, String hobby){
		//获得用户ID
		long uid = sequenceService.getNextId("USER_INFO");
		//自定义事物
		TransactionDefinition tstDef = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus  tstStatus = transactionManager.getTransaction(tstDef);
		try{
			//记录用户基本数据
			userInfoDao.insert(uid, username, password);
			//记录扩展数据
			userExpDao.insert(uid, hobby);
			
			transactionManager.commit(tstStatus);   //事物提交
		}catch(Exception e){
			uid = -1;
			transactionManager.rollback(tstStatus); //事物回滚
		}
		return uid;
	}
	
	/**
	 * 分页查询数据
	 * @param page
	 * @return
	 */
	public PageList<UserInfo> getPageForList(PageList<UserInfo> page){
		// 可以在此处做一些逻辑处理再返回，比如格式化等
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("userName", "u");
//		map.put("sex", 10);
//		map.put("phone", "p");
		return userInfoDao.getPageForList(page, map);
	}
	
	/**
	 * 插入用户信息
	 * @param data
	 */
	public void insert(UserInfo data){
		//获得用户ID
		long uid = sequenceService.getNextId("USER_INFO");
		data.setId(uid);
		userInfoDao.insert(data);
	}
	
	/**
	 * 更新用户信息
	 * @param data
	 */
	public void update(UserInfo data){
		userInfoDao.update(data);
	}
	
	/**
	 * 删除用户信息
	 * @param uid
	 */
	public void delete(long uid){
		userInfoDao.delete(uid);
	}
	
	/**
	 * 获取用户信息
	 * @param uid
	 */
	public UserInfo getById(long uid){
		return userInfoDao.getById(uid);
	}
}
