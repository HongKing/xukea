package com.xukea.main.role.model;


import com.xukea.framework.base.BaseEntity;


/**
 * 
 * @author 
 * @version
 */
public class MdlGroup extends BaseEntity {
	
	 private String	code; 
	 private String	name; 
	 private String	shortWord; 
	 private String	remark; 
	 private String	pluginCoce; 
	 private int	orderIdx; 

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
	 public String getShortWord(){ 
	 	 return this.shortWord; 
	 } 
	 public void setShortWord(String shortWord){ 
	 	 this.shortWord=shortWord; 
	 } 
	 public String getRemark(){ 
	 	 return this.remark; 
	 } 
	 public void setRemark(String remark){ 
	 	 this.remark=remark; 
	 } 
	 public String getPluginCoce(){ 
	 	 return this.pluginCoce; 
	 } 
	 public void setPluginCoce(String pluginCoce){ 
	 	 this.pluginCoce=pluginCoce; 
	 } 
	 public int getOrderIdx(){ 
	 	 return this.orderIdx; 
	 } 
	 public void setOrderIdx(int orderIdx){ 
	 	 this.orderIdx=orderIdx; 
	 } 
}