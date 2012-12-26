package com.xukea.system.message.dao;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.system.message.model.EmailSended;


/**
 * 已发邮件Dao
 * @author 
 * @version
 */
@Repository
public class EmailSendedDao  extends BaseDao<EmailSended, Long>{
    private String namespace = "com.xukea.system.email.model.EmailSended";

	/**
	 * 新增已发邮件
	 * @param email
	 * @return
	 */
	public int addSendedEmail(EmailSended email){
		return getSqlSessionTemplate().insert(namespace + ".insert", email);
	}
}
