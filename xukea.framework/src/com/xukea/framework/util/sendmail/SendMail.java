package com.xukea.framework.util.sendmail;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xukea.common.util.cache.Config;

/**
 * 发送邮件功能，支持群发
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012.02.09
 */
public class SendMail {
	private final Logger log = Logger.getLogger(getClass());

	// 字符编码
	private static String CHARSET = Config.getInstance().getString("email.content.charset");

	// 邮件类型
	private static String CONTENT_TYPE = Config.getInstance().getString("email.content.type");

	// 发送服务器地址
	private static String SMTP_HOST = Config.getInstance().getString("email.smtp.host");

	// 发送服务器端口
	private static String SMTP_PORT = Config.getInstance().getString("email.smtp.port");

	// 发送服务器验证
	private static boolean SMTP_AUTH = Config.getInstance().getBoolean("email.smtp.auth");
	
	// 发件人帐号
	private static String SEND_USER = Config.getInstance().getString("email.mail");

	// 发件人密码
	private static String SEND_PSWD = Config.getInstance().getString("email.pswd");
	
	// 发件人姓名
	private static String SEND_NAME = Config.getInstance().getString("email.name", CHARSET);

	static{
		if("".equals(SEND_NAME)){
			SEND_NAME = SEND_USER;
		}
	}
	
	// 邮件标题
	private String subject; 
	
	// 邮件正文
	private String content; 
	
	// 收件人
	private List<String>          emailList = new ArrayList<String>();
	
	// 附件
	private List<MailAttachment> attachment = new ArrayList<MailAttachment>();

	
	/**
	 * 发送邮件
	 * @return
	 */
	public boolean send(){
		if (emailList==null || emailList.size() == 0){
			return false;
		}
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost( SMTP_HOST );   
	    mailInfo.setMailServerPort( SMTP_PORT );   
	    mailInfo.setValidate( SMTP_AUTH );   
	    mailInfo.setUserName( SEND_USER );   
	    mailInfo.setPassword( SEND_PSWD );
	    mailInfo.setCharset(  CHARSET );
	    mailInfo.setFromName( SEND_NAME );
	    mailInfo.setFromAddress( SEND_USER );
	    
	    mailInfo.setToAddress( emailList ); 
	    mailInfo.setSubject( subject );   
	    mailInfo.setContent( content );
	    
	    attachment = (attachment==null) ? new ArrayList<MailAttachment>(): attachment;
	    mailInfo.setAttachFiles(attachment);
	    
	    try {
		    SimpleMailSender sms = new SimpleMailSender();
		    if("text".equalsIgnoreCase(CONTENT_TYPE)){
				sms.sendTextMail(mailInfo);
		    }else{
		    	sms.sendHtmlMail(mailInfo);
		    }
		    return true;
	    } catch (Exception e) {
	    	log.error(e);
			return false;
		}
	}

	/**
	 * 添加收件人：单个
	 * @param email
	 */
	public void addEmail(String email) {
		if(emailList==null){
			emailList = new ArrayList<String>();
		}
		emailList.add(email);
	}
	
	/**
	 * 添加收件人：多个
	 * @param emailList
	 */
	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
	}

	/**
	 * 添加附件：单个
	 * @param file
	 */
	public void addAttachment(MailAttachment file) {
		if(attachment==null){
			attachment = new ArrayList<MailAttachment>();
		}
		attachment.add(file);
	}

	/**
	 * 添加附件：多个
	 * @param files
	 */
	public void setAttachmentList(List<MailAttachment> files) {
		this.attachment = files;
	}
	
	/**
	 * 设置邮件内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 设置邮件标题
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
