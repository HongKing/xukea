package com.xukea.common.util.json;

import java.text.DecimalFormat;

import com.xukea.framework.base.BaseConstants;


import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * JSON格式化：数字
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class JsonNumberValueProcessor implements JsonValueProcessor {
    public static final String DEFAULT_PATTERN = BaseConstants.FORMAT_NUMBER_2;  
    private DecimalFormat formater;  

    /**   
     * 构造方法
     */  
    public JsonNumberValueProcessor() {  
    	this(DEFAULT_PATTERN);
    }
    
    /**   
     * 构造方法
     * 
     * @param pattern 数字格式
     */  
    public JsonNumberValueProcessor(String pattern) {
    	super();
        try {
        	formater = new DecimalFormat(pattern);  
        } catch (Exception ex) {
        	formater = new DecimalFormat(DEFAULT_PATTERN);  
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
            value = 0;
        }
        return formater.format(value);  
    }
}