package com.xukea.framework.util.editor;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 * float 基础类型转换
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2011.12.26
 */
public class FloatEditor extends CustomNumberEditor{  
	public FloatEditor(){  
	   super(Float.class,true);  
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		if (text==null || text.trim().equals("")) {
			 // Treat empty String as null value. 
			setValue(0f);
		} else {
			// Use default valueOf methods for parsing text.
			setValue(Float.parseFloat(text));
		}
	}  
}