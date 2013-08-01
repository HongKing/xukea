package com.xukea.system.message.model;


import com.xukea.framework.base.BaseEntity;


/**
 * 待发邮件
 * @author 
 * @version
 */
public class EmailWait extends BaseEntity {
	
	 private long	id; 
	 private String	subject; 
	 private String	content; 
	 private String	toaddr; 
	 private String	fileIds; 

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
}