package com.xukea.common;

import java.util.HashMap;

import com.xukea.common.util.Config;


/**
 * 用户基本信息(需完善，该类不可更改名字）
 * TODO
 */
public class UserBasicInfo {
	public static final String SESSION_NAME = Config.getInstance().getString("user.basic.info.sessname");
	
	private long   userId;   //用户ID
	private String userName; //用户名
	private String realName; //真实名
	private String email;    //邮箱
	
	private HashMap<String, Object> attribute = new HashMap<String, Object>();

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Object getAttribute(String key) {
		return attribute.get(key);
	}
	public void setAttribute(String key, Object value) {
		this.attribute.put(key, value);
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
