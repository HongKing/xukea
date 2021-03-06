package com.xukea.demo.userinfo.model;

import java.util.Date;

import com.xukea.framework.base.BaseEntity;

/**
 * 
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class UserInfo extends BaseEntity {
	private static final long serialVersionUID = -4109586893438723856L;

	/**
	 * ID
	 */
	private long id;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 性别
	 */
	private long sex;
	
	/**
	 * 联系电话
	 */	
	private String phone;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 创建日期
	 */
	private Date createDate;
	
	public UserInfo(){
		
	}
	
	public UserInfo(long id, String userName, String password, String realName,
			long sex, String phone, String email, Date createDate) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.sex = sex;
		this.phone = phone;
		this.email = email;
		this.createDate = createDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getSex() {
		return sex;
	}

	public void setSex(long sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}

