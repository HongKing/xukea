package com.xukea.common;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.xukea.common.util.cache.Config;
import com.xukea.main.user.model.User;

/**
 * 用户基本信息(该类不可更改名字）
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2011.12.26
 */
public class UserBasicInfo extends User {
	private static final String SESSION_NAME = Config.getInstance().getString("user.basic.info.sessname");

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
