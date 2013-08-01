package com.xukea.framework.util.sendmail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 发件帐号密码
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012.02.09
 */
public class SMTPAuthenticator extends Authenticator {
	private String	user;
	private String	pswd;
	
	public SMTPAuthenticator(String user, String pswd) {
		this.user = user;
		this.pswd = pswd;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, pswd);
	}
}
