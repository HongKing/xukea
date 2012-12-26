package com.xukea.framework.util.editor;


import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 * int 基础类型转换
 * 
 * @author 石头
 *
 */
public class IntEditor extends CustomNumberEditor{  
	public IntEditor(){  
	   super(Integer.class,true);  
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		if (text==null || text.trim().equals("")) {
			 // Treat empty String as null value. 
			setValue(0);
		} else {
			// Use default valueOf methods for parsing text.
			setValue(Integer.parseInt(text));
		}
	}  
}