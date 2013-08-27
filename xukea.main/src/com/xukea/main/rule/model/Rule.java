package com.xukea.main.rule.model;

import com.xukea.framework.base.BaseEntity;

/**
 * 数据规则
 * 
 * @author 
 * @version
 */
public class Rule extends BaseEntity {
		
	/**
	 * ID
	 **/
	private java.lang.Long	id; 
	/**
	 * 角色CODE
	 * */
	private java.lang.String roleCode;
	
	/**
	 * 规则名称
	 **/
	private java.lang.String	name; 
	
	/**
	 * 数据表
	 **/
	private java.lang.String	tabName; 
	
	/**
	 * 业务规则
	 **/
	private java.lang.String	rules; 
	/**
	 * 表注释
	 * */
	private java.lang.String tabComment;
	/**
	 * 角色名
	 * */
	private java.lang.String roleName; 


	public java.lang.Long getId(){ 
		 return this.id; 
	} 
	public void setId(java.lang.Long id){ 
		 this.id=id; 
	} 
	public java.lang.String getName(){ 
		 return this.name; 
	} 
	public void setName(java.lang.String name){ 
		 this.name=name; 
	} 
	public java.lang.String getTabName(){ 
		 return this.tabName; 
	} 
	public void setTabName(java.lang.String tabName){ 
		 this.tabName=tabName; 
	} 
	public java.lang.String getRules(){ 
		 return this.rules; 
	} 
	public void setRules(java.lang.String rules){ 
		 this.rules=rules; 
	}
	public java.lang.String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode;
	}
	public java.lang.String getTabComment() {
		return tabComment;
	}
	public void setTabComment(java.lang.String tabComment) {
		this.tabComment = tabComment;
	}
	public java.lang.String getRoleName() {
		return roleName;
	}
	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	} 

}