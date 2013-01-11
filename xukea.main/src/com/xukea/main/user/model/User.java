package com.xukea.main.user.model;

import java.util.Date;
import com.xukea.framework.base.BaseEntity;


/**
 * 
 * @author 
 * @version
 */
public class User extends BaseEntity {
	
	 private long	id; 
	 private String	userName; 
	 private String	realName; 
	 private String	password; 
	 private String	sex; 
	 private String	mobile; 
	 private String	email; 
	 private String	telephone; 
	 private String	address; 
	 private String	postcode; 
	 private String	status; 
	 private Date	addDate; 
	 private Date	editDate; 

	 public long getId(){ 
	 	 return this.id; 
	 } 
	 public void setId(long id){ 
	 	 this.id=id; 
	 } 
	 public String getUserName(){ 
	 	 return this.userName; 
	 } 
	 public void setUserName(String userName){ 
	 	 this.userName=userName; 
	 } 
	 public String getRealName(){ 
	 	 return this.realName; 
	 } 
	 public void setRealName(String realName){ 
	 	 this.realName=realName; 
	 } 
	 public String getPassword(){ 
	 	 return this.password; 
	 } 
	 public void setPassword(String password){ 
	 	 this.password=password; 
	 } 
	 public String getSex(){ 
	 	 return this.sex; 
	 } 
	 public void setSex(String sex){ 
	 	 this.sex=sex; 
	 } 
	 public String getMobile(){ 
	 	 return this.mobile; 
	 } 
	 public void setMobile(String mobile){ 
	 	 this.mobile=mobile; 
	 } 
	 public String getEmail(){ 
	 	 return this.email; 
	 } 
	 public void setEmail(String email){ 
	 	 this.email=email; 
	 } 
	 public String getTelephone(){ 
	 	 return this.telephone; 
	 } 
	 public void setTelephone(String telephone){ 
	 	 this.telephone=telephone; 
	 } 
	 public String getAddress(){ 
	 	 return this.address; 
	 } 
	 public void setAddress(String address){ 
	 	 this.address=address; 
	 } 
	 public String getPostcode(){ 
	 	 return this.postcode; 
	 } 
	 public void setPostcode(String postcode){ 
	 	 this.postcode=postcode; 
	 } 
	 public String getStatus(){ 
	 	 return this.status; 
	 } 
	 public void setStatus(String status){ 
	 	 this.status=status; 
	 } 
	 public Date getAddDate(){ 
	 	 return this.addDate; 
	 } 
	 public void setAddDate(Date addDate){ 
	 	 this.addDate=addDate; 
	 } 
	 public Date getEditDate(){ 
	 	 return this.editDate; 
	 } 
	 public void setEditDate(Date editDate){ 
	 	 this.editDate=editDate; 
	 } 
}