package com.xukea.main.plugin.model;

import java.util.Date;
import com.xukea.framework.base.BaseEntity;


/**
 * 
 * @author 
 * @version
 */
public class PluginInfo extends BaseEntity {
	
	 private String	code; 
	 private String	name; 
	 private String	version; 
	 private int	status; 
	 private int	level; 
	 private Date	setupDate; 
	 private Date	uploadDate; 

	 public String getCode(){ 
	 	 return this.code; 
	 } 
	 public void setCode(String code){ 
	 	 this.code=code; 
	 } 
	 public String getName(){ 
	 	 return this.name; 
	 } 
	 public void setName(String name){ 
	 	 this.name=name; 
	 } 
	 public String getVersion(){ 
	 	 return this.version; 
	 } 
	 public void setVersion(String version){ 
	 	 this.version=version; 
	 } 
	 public int getStatus(){ 
	 	 return this.status; 
	 } 
	 public void setStatus(int status){ 
	 	 this.status=status; 
	 } 
	 public int getLevel(){ 
	 	 return this.level; 
	 } 
	 public void setLevel(int level){ 
	 	 this.level=level; 
	 } 
	 public Date getSetupDate(){ 
	 	 return this.setupDate; 
	 } 
	 public void setSetupDate(Date setupDate){ 
	 	 this.setupDate=setupDate; 
	 } 
	 public Date getUploadDate(){ 
	 	 return this.uploadDate; 
	 } 
	 public void setUploadDate(Date uploadDate){ 
	 	 this.uploadDate=uploadDate; 
	 } 
}