package com.xukea.common.util.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xukea.framework.base.BaseConstants;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * JSON格式化：日期
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
    public static final String DEFAULT_PATTERN = BaseConstants.FORMAT_DATE_TIME;  
    private DateFormat formater;  

    /**   
     * 构造方法
     */  
    public JsonDateValueProcessor() {  
    	this(DEFAULT_PATTERN);
    }
    
    /**   
     * 构造方法
     * 
     * @param pattern 日期格式
     */  
    public JsonDateValueProcessor(String pattern) {
    	super();
        try {
        	formater = new SimpleDateFormat(pattern);  
        } catch (Exception ex) {
        	formater = new SimpleDateFormat(DEFAULT_PATTERN);  
        }
    }
    
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);  
    }
    
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value);  
    }
    
    private Object process(Object value) {
        if (value == null) {
        	return "";
//            value = new Date();   //为null时返回当前日期，也可以返回"",看需要  
        }
        return formater.format((Date) value);  
    }
}