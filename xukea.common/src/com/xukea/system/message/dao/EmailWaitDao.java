package com.xukea.system.message.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.system.message.model.EmailWait;


/**
 * 待发送邮件Dao
 * @author 
 * @version
 */
@Repository
public class EmailWaitDao  extends BaseDao {
    private String namespace = "com.xukea.system.message.model.EmailWait";
	
	/**
	 * 新增待发邮件
	 * @param email
	 * @return
	 */
	public int addEmail(EmailWait email){
		return getSqlSessionTemplate().insert(namespace + ".insert", email);
	}
	
	/**
	 * 获取待发邮件列表
	 * @return
	 */
	public List<EmailWait> getListEmail(){
		return getSqlSessionTemplate().selectList(namespace + ".getEmailList");
	}
	
	/**
	 * 删除待发邮件
	 * @param id
	 * @return
	 */
	public int deleteEmailById(long id){
		return getSqlSessionTemplate().delete(namespace + ".delete", id);
	}
	
}
