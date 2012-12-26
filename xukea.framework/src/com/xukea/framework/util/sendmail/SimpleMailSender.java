package com.xukea.framework.util.sendmail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**  
 * 邮件发送器  
 * @author FishBoy
 * @version
 * @date   2012.02.09
 */   
public class SimpleMailSender  {   

	/**  
	 * 以文本格式发送邮件  
	 * @param mailInfo 待发送的邮件的信息  
	 * @throws MessagingException 
	 */
    public boolean sendTextMail(MailSenderInfo mailInfo) throws MessagingException {
    	return send(mailInfo, false);
	} 

	/**
	 * 以HTML格式发送邮件
	 * @param mailInfo 待发送的邮件信息
	 * @throws MessagingException 
	 */ 
	public boolean sendHtmlMail(MailSenderInfo mailInfo) throws MessagingException{ 
		return send(mailInfo, true);
	}
	
	/**
	 * 邮件发送
	 * @param mailInfo
	 * @param isHtml
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	private boolean send(MailSenderInfo mailInfo, boolean isHtml) throws MessagingException{
		// 判断是否需要身份认证  
    	SMTPAuthenticator authenticator = null;
    	Properties pro = mailInfo.getProperties();
    	if (mailInfo.isValidate()) {
    		// 如果需要身份认证，则创建一个密码验证器
    		authenticator = new SMTPAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
    	}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session   
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);   
		// 根据session创建一个邮件消息 
		Message mailMessage = new MimeMessage(sendMailSession); 
		// 创建邮件发送者地址 
		Address from = null;
		try{ 
			from = new InternetAddress(mailInfo.getFromAddress(), mailInfo.getFromName()); 
		}catch(UnsupportedEncodingException e){
			from = new InternetAddress(mailInfo.getFromAddress()); 
		}
		// 设置邮件消息的发送者 
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中 
		Address[] to = new InternetAddress[mailInfo.getToAddress().size()];
		int i = 0;
		for(String temp : mailInfo.getToAddress()){
			to[i++] = new InternetAddress( temp );
		}
		mailMessage.setRecipients( Message.RecipientType.TO, to );
		// 设置邮件消息的主题 
		mailMessage.setSubject(mailInfo.getSubject()); 
		// 设置邮件消息发送的时间 
		mailMessage.setSentDate(new Date()); 

		// 邮件对象，MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象 
		Multipart mainPart = new MimeMultipart(); 
		
		// 邮件正文
		BodyPart mailBody = new MimeBodyPart();
		if(isHtml){ 
			// 设置HTML内容 
			mailBody.setContent(mailInfo.getContent(), "text/html; charset="+ mailInfo.getCharset());  
		}else{
			// 设置非HTML内容 
			mailBody.setText(mailInfo.getContent());
		}
		mainPart.addBodyPart(mailBody); //添加邮件正文至邮件对象
		
		// 邮件附件
		for(MailAttachment item : mailInfo.getAttachFiles()){
			BodyPart attachment = new MimeBodyPart();
			FileDataSource fds  = new FileDataSource(item.getAbsoluteFile());// 得到附件数据源
			attachment.setDataHandler(new DataHandler(fds)); // 得到附件本身
            //设置文件名，并解决中文名乱码问题 
			String fileName = item.getAliasName();
			try {
				attachment.setFileName(MimeUtility.encodeText( fileName ));
			} catch (UnsupportedEncodingException e) {
				attachment.setFileName(fileName);
			}
			mainPart.addBodyPart(attachment);  //添加邮件附件至邮件对象
		}

		// 添加邮件内容对象
		mailMessage.setContent(mainPart); 
		// 保存邮件 
		mailMessage.saveChanges();
		// 发送邮件 
		Transport.send(mailMessage);
		return true;
	}
}