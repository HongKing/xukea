package com.xukea.common;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.xukea.common.util.cache.Config;



/**
 * 用户基本信息(该类不可更改名字）
 * @author 石头
 *
 */
public class UserBasicInfo {
	private static final String SESSION_NAME = Config.getInstance().getString("user.basic.info.sessname");

	/************ 常用属性 *************/
	private long   userId;   //用户ID
	private String userName; //用户名
	private String realName; //真实名
	private String email;    //邮箱

	/************ 扩展属性 *************/
	private HashMap<String, Object> attribute = new HashMap<String, Object>();
	
	/**
	 * 保存用户基本信息对象到SESSION
	 * 
	 * @param request
	 * @param user
	 */
	public static void saveToSession(HttpServletRequest request, UserBasicInfo user){
		request.getSession().setAttribute(SESSION_NAME, user);
	}
	
	/**
	 * 从SESSION中获取用户对象
	 * 
	 * @param request
	 * @return
	 */
	public static UserBasicInfo getFromSession(HttpServletRequest request){
		return (UserBasicInfo)request.getSession().getAttribute(SESSION_NAME);
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 获取扩展信息
	 * @param key
	 * @return
	 */
	public Object getAttribute(String key) {
		return attribute.get(key);
	}
	
	/**
	 * 设置扩展属性
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setAttribute(String key, Object value) throws Exception {
		if(attribute.containsKey(key)){
			throw new Exception("the key is existence : "+key);
		}
		this.attribute.put(key, value);
	}
}
