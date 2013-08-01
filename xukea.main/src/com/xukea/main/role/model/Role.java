package com.xukea.main.role.model;

import com.xukea.framework.base.BaseEntity;

/**
 * 角色Bean
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class Role extends BaseEntity {
	
	 private String	code; 
	 private String	name; 
	 private String	shortWord; 
	 private String	remark; 
	 private boolean	canEdit; 
	 private boolean	canDelet; 
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
	 public boolean getCanEdit(){ 
	 	 return this.canEdit; 
	 } 
	 public void setCanEdit(boolean canEdit){ 
	 	 this.canEdit=canEdit; 
	 } 
	 public boolean getCanDelet(){ 
	 	 return this.canDelet; 
	 } 
	 public void setCanDelet(boolean canDelet){ 
	 	 this.canDelet=canDelet; 
	 } 
	 public int getOrderIdx(){ 
	 	 return this.orderIdx; 
	 } 
	 public void setOrderIdx(int orderIdx){ 
	 	 this.orderIdx=orderIdx; 
	 } 
}