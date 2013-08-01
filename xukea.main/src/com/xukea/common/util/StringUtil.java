package com.xukea.common.util;

import com.xukea.framework.base.BaseStringUtil;

/**
 * 字符串处理
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class StringUtil extends BaseStringUtil {
	/** 
     * 删除字符串中的html标签
     *  
     * @param str 
     * @return 
     */  
    public static String splitHtmlTag(String str) {  
    	if (isEmpty(str)) return "";
    	
        // 去掉所有html元素,  
        String temp = str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
        temp = temp.replaceAll("[(/>)<]", "");
        return temp;  
    }
    
}
