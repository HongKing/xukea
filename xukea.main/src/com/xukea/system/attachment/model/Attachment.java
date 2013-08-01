package com.xukea.system.attachment.model;

import java.util.Date;
import com.xukea.framework.base.BaseEntity;

/**
 * 附件 Bean
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class Attachment extends BaseEntity {
	
	 public Attachment() {
		super();
	}
	private long	id; 
	 private String	fileName; 
	 private String	fileUrl; 
	 private float	fileSize; 
	 private String	fileType; 
	 private String	remark; 
	 private long	uploadUser; 
	 private Date	uploadDate; 

	 public Attachment(long id, String fileName, String fileUrl, float fileSize, String fileType, 
			String remark, long uploadUser, Date uploadDate) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.fileSize = fileSize;
		this.fileType = fileType;
		this.remark = remark;
		this.uploadUser = uploadUser;
		this.uploadDate = uploadDate;
	}
	 
	 public long getId(){ 
	 	 return this.id; 
	 } 
	 public void setId(long id){ 
	 	 this.id=id; 
	 } 
	 public String getFileName(){ 
	 	 return this.fileName; 
	 } 
	 public void setFileName(String fileName){ 
	 	 this.fileName=fileName; 
	 } 
	 public String getFileUrl(){ 
	 	 return this.fileUrl; 
	 } 
	 public void setFileUrl(String fileUrl){ 
	 	 this.fileUrl=fileUrl; 
	 } 
	 public float getFileSize(){ 
	 	 return this.fileSize; 
	 } 
	 public void setFileSize(float fileSize){ 
	 	 this.fileSize=fileSize; 
	 } 
	 public String getFileType(){ 
	 	 return this.fileType; 
	 } 
	 public void setFileType(String fileType){ 
	 	 this.fileType=fileType; 
	 } 
	 public String getRemark(){ 
	 	 return this.remark; 
	 } 
	 public void setRemark(String remark){ 
	 	 this.remark=remark; 
	 } 
	 public long getUploadUser(){ 
	 	 return this.uploadUser; 
	 } 
	 public void setUploadUser(long uploadUser){ 
	 	 this.uploadUser=uploadUser; 
	 } 
	 public Date getUploadDate(){ 
	 	 return this.uploadDate; 
	 } 
	 public void setUploadDate(Date uploadDate){ 
	 	 this.uploadDate=uploadDate; 
	 } 
}