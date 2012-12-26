package com.xukea.framework.util.editor;


import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 * double 基础类型转换
 * 
 * @author 石头
 *
 */
public class DoubleEditor extends CustomNumberEditor{  
	public DoubleEditor(){  
	   super(Double.class,true);  
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		if (text==null || text.trim().equals("")) {
			 // Treat empty String as null value. 
			setValue(0d);
		} else {
			// Use default valueOf methods for parsing text.
			setValue(Double.parseDouble(text));
		}
	}  
}