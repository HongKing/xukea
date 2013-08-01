package com.xukea.common.util.logdb.model;

import java.util.Date;
import com.xukea.framework.base.BaseEntity;

/**
 * 系统日志 BEAN 对象
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class SysErrorLog extends BaseEntity {
	
	 private Date	errTime; 
	 private String	errLevel; 
	 private String	errFrom;
	 private String errValue;
	 private String	msgShort; 
	 private String	msgAll; 
	 
	 public SysErrorLog(Date errTime, String errLevel, String errFrom, String errValue, String msgShort, String msgAll) {
		super();
		this.errTime = errTime;
		this.errLevel = errLevel;
		this.errFrom = errFrom;
		this.errValue = errValue;
		this.msgShort = msgShort;
		this.msgAll = msgAll;
	}
	 
	public Date getErrTime(){ 
	 	 return this.errTime; 
	 } 
	 public void setErrTime(Date errTime){ 
	 	 this.errTime=errTime; 
	 } 
	 public String getErrLevel(){ 
	 	 return this.errLevel; 
	 } 
	 public void setErrLevel(String errLevel){ 
	 	 this.errLevel=errLevel; 
	 } 
	 public String getErrFrom(){ 
	 	 return this.errFrom; 
	 } 
	 public void setErrFrom(String errFrom){ 
	 	 this.errFrom=errFrom; 
	 } 
	 public String getErrValue() {
		return errValue;
	}

	public void setErrValue(String errValue) {
		this.errValue = errValue;
	}
	public String getMsgShort(){ 
	 	 return this.msgShort; 
	 } 
	 public void setMsgShort(String msgShort){ 
	 	 this.msgShort=msgShort; 
	 } 
	 public String getMsgAll(){ 
	 	 return this.msgAll; 
	 } 
	 public void setMsgAll(String msgAll){ 
	 	 this.msgAll=msgAll; 
	 } 
}