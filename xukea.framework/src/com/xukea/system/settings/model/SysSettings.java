package com.xukea.system.settings.model;

import java.util.Date;
import com.xukea.framework.base.BaseEntity;


/**
 * 系统设置Bean
 * @author 
 * @version
 */
public class SysSettings extends BaseEntity {
	
	 private String	name; 
	 private String	value; 
	 private Date	editDate; 
	 private String	remark; 
	 private boolean canEdit;
	 private boolean canDelete;

	 public String getName(){ 
	 	 return this.name; 
	 } 
	 public void setName(String name){ 
	 	 this.name=name; 
	 } 
	 public String getValue(){ 
	 	 return this.value; 
	 } 
	 public void setValue(String value){ 
	 	 this.value=value; 
	 } 
	 public Date getEditDate(){ 
	 	 return this.editDate; 
	 } 
	 public void setEditDate(Date editDate){ 
	 	 this.editDate=editDate; 
	 }
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	public boolean isCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	} 
}