package com.xukea.framework.util.editor;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;

/**
 * boolean 基础类型转换
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2011.12.26
 */
public class BooleanEditor extends CustomBooleanEditor{  
	
	public BooleanEditor(){
		super(true);
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		if (text==null || text.trim().equals("")) {
			// 默认设置为false
			text = "false";
		}
		super.setAsText(text);
	}
	
}