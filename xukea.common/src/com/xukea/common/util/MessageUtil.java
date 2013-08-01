package com.xukea.common.util;

import java.util.ArrayList;
import java.util.List;

import com.xukea.framework.util.ContextUtil;
import com.xukea.framework.util.sendmail.MailAttachment;
import com.xukea.framework.util.sendmail.SendMail;
import com.xukea.system.message.service.SendEmailService;

/**
 * 消息通知（邮件、站内消息、短信）
 * TODO 需完善 
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class MessageUtil {
	private static Object lock = new Object(); 
	private static MessageUtil instance = null;
	
	private SendEmailService sendEmailService;
	
	/**
	 * 构造方法，service需在这里手动获取bean
	 */
	private MessageUtil(){
		// Service需要手动加载
		sendEmailService  = ContextUtil.getBean(SendEmailService.class);
	}
	
	/**
	 * 单例工厂
	 * @return
	 */
	public static MessageUtil getInstance() {
		if (instance == null) {
			synchronized( lock ){   
                if (instance == null){   
                    instance = new MessageUtil();   
                }   
            }
		}
		return instance;
	}

	/**
	 * 发送邮件（单个，带附件）
	 * @param subject
	 * @param content
	 * @param toAddr
	 * @param file
	 * @return
	 */
	public boolean sendMail(String subject, String content, String toEmail, MailAttachment file){
		List<String> emails = new ArrayList<String>();
		emails.add(toEmail);
		
		List<MailAttachment> files = new ArrayList<MailAttachment>();
		if(file!=null){
			files.add(file);
		}
		
		return sendMail(subject, content, emails, files);
	}

	/**
	 * 发送邮件（群发，带多附件）
	 * @param subject
	 * @param content
	 * @param emails
	 * @param files
	 * @return
	 */
	public boolean sendMail(String subject, String content, List<String> toEmails, List<MailAttachment> files){
		SendMail mail = new SendMail();
		mail.setSubject(subject);
		mail.setContent(content);
//		mail.addEmail(toEmail); //单个地址
		mail.setEmailList(toEmails);
//		mail.addAttachment(file); //单个附件
		if( files!=null && files.size()>0 ){
			mail.setAttachmentList(files);
		}
		
		return mail.send();
	}
	
	/**
	 * 待发邮件入库
	 * @param subject
	 * @param content
	 * @param toAddr
	 * @return
	 */
	public long addEmail2DB(String subject, String content, String toAddr){
		try{
			return sendEmailService.addEmail(subject, content, toAddr);
		}catch(Exception e){
			return -1;
		}
	}
	
}