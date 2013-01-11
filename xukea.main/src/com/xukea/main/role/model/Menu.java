package com.xukea.main.role.model;


import java.util.List;

import com.xukea.framework.base.BaseEntity;


/**
 * 
 * @author 
 * @version
 */
public class Menu extends BaseEntity {
	
	 private String	code; 
	 private String	name; 
	 private String	shortWord; 
	 private String	url; 
	 private String	iconUrl; 
	 private String	pluginCoce; 
	 private int	orderIdx; 
	 private String	remark; 
	 private List<Menu> subMenu;   //子菜单
	 private List<Menu> quickMenu; //快捷菜单
	 private List<Menu> resuorce;  //资源

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
	 public String getUrl(){ 
	 	 return this.url==null? "" : this.url; 
	 } 
	 public void setUrl(String url){ 
	 	 this.url=url; 
	 } 
	 public String getIconUrl(){ 
	 	 return this.iconUrl==null? "" : this.iconUrl; 
	 } 
	 public void setIconUrl(String iconUrl){ 
	 	 this.iconUrl=iconUrl; 
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
	 public String getRemark(){ 
	 	 return this.remark; 
	 } 
	 public void setRemark(String remark){ 
	 	 this.remark=remark; 
	 }
	
	public boolean hasSubMenu(){
		if(subMenu==null || subMenu.size()==0){
			return false;
		}else{
			return true;
		}
	}
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	
	public boolean hasQuickMenu(){
		if(quickMenu==null || quickMenu.size()==0){
			return false;
		}else{
			return true;
		}
	}
	public List<Menu> getQuickMenu() {
		return quickMenu;
	}
	public void setQuickMenu(List<Menu> quickMenu) {
		this.quickMenu = quickMenu;
	}
	public List<Menu> getResuorce() {
		return resuorce;
	}
	public void setResuorce(List<Menu> resuorce) {
		this.resuorce = resuorce;
	} 
}