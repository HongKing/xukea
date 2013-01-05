package com.xukea.system.message.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xukea.common.util.sequence.service.SequenceService;
import com.xukea.framework.base.BaseService;
import com.xukea.system.message.dao.EmailSendedDao;
import com.xukea.system.message.dao.EmailWaitDao;
import com.xukea.system.message.model.EmailSended;
import com.xukea.system.message.model.EmailWait;


@Service
public class SendEmailService extends BaseService<EmailWait, Long>{

	@Resource
	private EmailWaitDao emailWaitDao;
	
	@Resource
	private EmailSendedDao emailSendedDao;

	@Resource
	private SequenceService sequenceService;
	
	/**
	 * 新增待发邮件
	 * 
	 * @param subject
	 * @param content
	 * @param toAddr
	 * @return
	 */
	public long addEmail(String subject, String content, String toAddr){
		long id = sequenceService.getNextId("MSG_EMAIL_WAIT");
		
		EmailWait email = new EmailWait();
		email.setId(id);
		email.setContent(content);
		email.setSubject(subject);
		email.setToaddr(toAddr);
		
		emailWaitDao.addEmail(email);
		return id;
	}
	
	/**
	 * 获取待发邮件列表
	 * @return
	 */
	public List<EmailWait> getListEmail(){
		return emailWaitDao.getListEmail();
	}
	
	/**
	 * 迁移待发邮件至已发邮件
	 * @param id
	 * @return
	 */
	public boolean deleteEmailById(EmailWait email){
		EmailSended sended = new EmailSended();
		sended.setId(email.getId());
		sended.setSubject(email.getSubject());
		sended.setContent(email.getContent());
		sended.setContent(email.getFileIds());
		sended.setToaddr(email.getToaddr());
		sended.setSendTime(new Date());
		
		try{
			emailSendedDao.addSendedEmail(sended);
			emailWaitDao.deleteEmailById(email.getId());
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
}
