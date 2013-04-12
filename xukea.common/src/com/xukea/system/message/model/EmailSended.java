package com.xukea.system.message.model;

import java.util.Date;
import com.xukea.framework.base.BaseEntity;


/**
 * 已发邮件
 * @author 
 * @version
 */
public class EmailSended extends BaseEntity {
	
	 private long	id; 
	 private String	subject; 
	 private String	content; 
	 private String	toaddr; 
	 private String	fileIds; 
	 private Date	sendTime; 

	 public long getId(){ 
	 	 return this.id; 
	 } 
	 public void setId(long id){ 
	 	 this.id=id; 
	 } 
	 public String getSubject(){ 
	 	 return this.subject; 
	 } 
	 public void setSubject(String subject){ 
	 	 this.subject=subject; 
	 } 
	 public String getContent(){ 
	 	 return this.content; 
	 } 
	 public void setContent(String content){ 
	 	 this.content=content; 
	 } 
	 public String getToaddr(){ 
	 	 return this.toaddr; 
	 } 
	 public void setToaddr(String toaddr){ 
	 	 this.toaddr=toaddr; 
	 } 
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	} 
	 public Date getSendTime(){ 
	 	 return this.sendTime; 
	 } 
	 public void setSendTime(Date sendTime){ 
	 	 this.sendTime=sendTime; 
	 } 
}