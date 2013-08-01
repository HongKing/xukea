package com.xukea.demo.userinfo.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;

/**
 * 用户Dao
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Repository
public class UserExpDao extends BaseDao{
	private String namespace = "com.xukea.demo.userinfo.model.UserExp";
	
    /**
     * 设置数据源2
     * @param sqlSessionFactory
     */
    public void setSqlSessionFactory2(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
    }
    
    /**
     * 
     * @param uid
     * @param userName
     * @param password
     */
    public void insert(long uid, String hobby) {
    	//TODO 
    }

}
