package com.xukea.framework.util.editor;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 * byte 基础类型转换
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2011.12.26
 */
public class ByteEditor extends CustomNumberEditor{  
	public ByteEditor(){  
	   super(Byte.class, true);  
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		if (text==null || text.trim().equals("")) {
			 // Treat empty String as null value. 
			setValue(0);
		} else {
			// Use default valueOf methods for parsing text.
			setValue(Byte.parseByte(text));
		}
	}
	
}