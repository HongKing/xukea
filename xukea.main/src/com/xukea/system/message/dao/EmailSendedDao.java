package com.xukea.system.message.dao;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.system.message.model.EmailSended;

/**
 * 已发邮件Dao
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Repository
public class EmailSendedDao  extends BaseDao {
    private String namespace = "com.xukea.system.message.model.EmailSended";

	/**
	 * 新增已发邮件
	 * @param email
	 * @return
	 */
	public int addSendedEmail(EmailSended email){
		return getSqlSessionTemplate().insert(namespace + ".insert", email);
	}
}
