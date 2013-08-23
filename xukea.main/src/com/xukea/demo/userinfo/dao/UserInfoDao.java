package com.xukea.demo.userinfo.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.xukea.demo.userinfo.model.UserInfo;
import com.xukea.framework.base.BaseDao;
import com.xukea.framework.util.PageList;

/**
 * 
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Repository
public class UserInfoDao extends BaseDao{
	private String namespace = "com.xukea.demo.userinfo.model.UserInfo";
	
    /**
     * 设置数据源2
     * @param sqlSessionFactory
     */
    public void setSqlSessionFactory2(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
    }
    
    /**
     * 插入用户信息
     * @param uid
     * @param userName
     * @param password
     */
    public void insert(long uid, String userName, String password) {
    	UserInfo data = new UserInfo();
    	data.setId(uid);
    	data.setUserName(userName);
    	data.setPassword(password);
    	
    	this.insert(data);
    }

    /**
     * 插入用户信息
     * @param data
     */
    public void insert(UserInfo data) {
    	getSqlSessionTemplate().insert(namespace +".insert", data);
    }

    /**
     * 更新用户信息
     * @param data
     */
    public void update(UserInfo data) {
    	getSqlSessionTemplate().update(namespace +".update", data);
    }

    /**
     * 删除用户信息
     * @param uid
     */
    public void delete(long uid) {
    	getSqlSessionTemplate().delete(namespace +".delete", uid);
    }

    /**
     * 获取用户信息
     * @param uid
     */
    public UserInfo getById(long uid) {
    	return (UserInfo)getSqlSessionTemplate().selectOne(namespace +".getById", uid);
    }
    
	/**
	 * 分页查询数据
	 * @param page
	 * @return
	 */
	public PageList<UserInfo> getPageForList(PageList<UserInfo> page, HashMap<String, Object> map){
		return getSqlSessionTemplate().selectPageList(namespace +".getList", map, page);
	}

}
