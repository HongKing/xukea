package com.xukea.framework.util.sendmail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 发送邮件需要使用的基本信息  
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012.02.09
 */
public class MailSenderInfo {
	
    // 发送邮件的服务器的IP和端口   
    private String mailServerHost;   
    private String mailServerPort = "25";  
    // 是否需要身份验证   
    private boolean validate = false;  
    
    // 登陆邮件发送服务器的用户名和密码   
    private String userName;   
    private String password;
    
    // 邮件发送者的地址   
    private String fromAddress; 

    // 邮件发送者的地址   
    private String fromName; 
    
    // 邮件接收者的地址   
    private List<String> toAddress = new ArrayList<String>();   

    // 邮件附件的文件名   
    private List<MailAttachment> attachFiles = new ArrayList<MailAttachment>();
    
    // 邮件主题   
    private String subject;   
    // 邮件的文本内容   
    private String content;
    // 邮件编码
    private String charset = "UTF-8";
    
    /**  
     * 获得邮件会话属性  
     */ 
    public Properties getProperties(){   
      Properties p = new Properties();   
      p.put("mail.smtp.host", this.mailServerHost);   
      p.put("mail.smtp.port", this.mailServerPort);   
      p.put("mail.smtp.auth", validate ? "true" : "false");   
      return p;   
    }
    
    public String getMailServerHost() {   
      return mailServerHost;   
    }   
    public void setMailServerHost(String mailServerHost) {   
      this.mailServerHost = mailServerHost;   
    }  
    public String getMailServerPort() {   
      return mailServerPort;   
    }  
    public void setMailServerPort(String mailServerPort) {   
      this.mailServerPort = mailServerPort;   
    }  
    public boolean isValidate() {   
      return validate;   
    }  
    public void setValidate(boolean validate) {   
      this.validate = validate;   
    }
    public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public List<MailAttachment> getAttachFiles() {
		return attachFiles;
	}
	public void setAttachFiles(List<MailAttachment> attachFiles) {
		this.attachFiles = attachFiles;
	}
	public String getFromAddress() {   
      return fromAddress;   
    }   
    public void setFromAddress(String fromAddress) {   
      this.fromAddress = fromAddress;   
    }  
    public String getPassword() {   
      return password;   
    }  
    public void setPassword(String password) {   
      this.password = password;   
    }  
    public List<String> getToAddress() {   
      return toAddress;   
    }   
    public void setToAddress(List<String> toAddress) {
      this.toAddress = toAddress;   
    }   
    public String getUserName() {   
      return userName;   
    }  
    public void setUserName(String userName) {   
      this.userName = userName;   
    }  
    public String getSubject() {   
      return subject;   
    }  
    public void setSubject(String subject) {   
      this.subject = subject;   
    }  
    public String getContent() {   
      return content;   
    }  
    public void setContent(String textContent) {   
      this.content = textContent;   
    }
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}

    public void setToAddress(String toAddress) {
        this.toAddress.add(toAddress);
    }   
    public void setAttachFiles(MailAttachment file) {
        this.attachFiles.add(file);
    }   
}