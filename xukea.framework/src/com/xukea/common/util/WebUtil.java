package com.xukea.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP处理相关的辅助类
 */
public final class WebUtil {
	
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return boolean
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		//在服务器端判断request来自异步还是同步请求
		boolean isAjaxRequest = (request.getHeader("x-requested-with") != null)? true:false; 
		return isAjaxRequest;
	}
	
    /**
     * 将HTML中的特殊字符转义
     * @param text
     * @return 转义后的字符
     */
    public static String htmlEncode(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll(" ", "&nbsp;");
        text = text.replaceAll("\"", "&quot;");
        text = text.replaceAll("\'", "&apos;");
        return text.replaceAll("\n", "<br/>");
    }
    
    /**
     * 请HTML特殊字符转义，包括换行和回车
     * 换行转换为$#10,回车$#13
     * @param text
     * @return  转义后的字符
     */
    public static String htmlOutEncode(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll(" ", "&nbsp;");
        text = text.replaceAll("\"", "&quot;");
        text = text.replaceAll("\'", "&apos;");
        text = text.replaceAll("\n", "&#10"); //换行
        return text.replaceAll("\r", "&#13"); //回车
    }
    
    /**
     * 请HTML特殊字符转义，包括换行和回车
     * 换行=<br/>,回车=""
     * @param text
     * @return  转义后的字符
     */
    public static String htmlOutEncode1(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll(" ", "&nbsp;");
        text = text.replaceAll("\"", "&quot;");
        text = text.replaceAll("\'", "&apos;");
        text = text.replaceAll("\n", "<br/>"); //换行
        return text.replaceAll("\r", ""); //回车
    }

	/**
	 * 字符串类型反转换
	 * @param text
	 * @return
	 */
    public static String htmlDecode(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("&lt;", "<");
        text = text.replaceAll("&gt;", ">");
        text = text.replaceAll("&nbsp;", " ");
        text = text.replaceAll("&quot;", "\"");
        text = text.replaceAll("<br/>", "\n");
        text = text.replaceAll("&apos;", "\'");
        return text; 

    }
    
	 /**
	  * 将字符串中的”单引号”、“双引号”、“回车换行”、“\符号“转义，方便JSON传输
	  */
	public static String coverExcape(String source) {
		if(source != null) {
			source = source.replace("\\", "\\\\");
			source = source.replace("\'", "\\\'");
			source = source.replace("\t", "\\t");
			source = source.replace("\"", "\\\"");
			source = source.replace("\r\n", "\\r\\n");
			return source;
		} else {
			return "";
		}		 
	}
	  
    
    /**
     * 获得浏览器信息
     * @param request
     * @return String
     */
    public static String getClientInfo(HttpServletRequest request) {
    	String browser = request.getHeader("user-agent"); 
	 	if (browser != null) {
	 		return browser;
	 	}
	 	return "";
    }
    
    /**
     * 得到客户端IP
     * @param request
     * @return String
     */
    public static String getClientIp(HttpServletRequest request) {
    	String clientIP = request.getHeader("X-Forwarded-For") ;
	 	if (clientIP == null) {
	 		clientIP = request.getRemoteAddr() ;
	 	}
	 	return clientIP;
    }
    
    /**
     * 获取请求参数（字符串）
     * @param request
     * @param key
     * @param defval
     * @return
     */
    public static String getValueString(HttpServletRequest request, String key, String defval){
    	String temp = (String) request.getParameter(key);
    	return temp==null ? defval : temp;
    }

    /**
     * 获取请求参数（long）
     * @param request
     * @param key
     * @param defval
     * @return
     */
    public static long getValueLong(HttpServletRequest request, String key, long defval){
    	try{
        	String temp = (String) request.getParameter(key);
    		return temp==null ? defval : Long.parseLong(temp);
    	}catch(Exception e){
    		return defval;
    	}
    }

    /**
     * 获取请求参数（int）
     * @param request
     * @param key
     * @param defval
     * @return
     */
    public static int getValueInt(HttpServletRequest request, String key, int defval){
    	try{
        	String temp = (String) request.getParameter(key);
    		return temp==null ? defval : Integer.parseInt(temp);
    	}catch(Exception e){
    		return defval;
    	}
    }

    /**
     * 获取请求参数（boolean）
     * @param request
     * @param key
     * @param defval
     * @return
     */
    public static boolean getValueBoolean(HttpServletRequest request, String key, boolean defval){
    	try{
        	String temp = (String) request.getParameter(key);
        	return temp==null ? defval : Boolean.parseBoolean(temp);
    	}catch(Exception e){
    		return defval;
    	}
    }
    
    /**
     * 获取请求参数（Date "yyyy-MM-dd"）
     * @param request
     * @param key
     * @param defval
     * @return
     */
    public static Date getValueDate(HttpServletRequest request, String key, Date defval){
    	try{
    		String temp = (String) request.getParameter(key);
    		DateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DATE);
    		return temp==null ? defval : dateFormat.parse(temp);
    	}catch(Exception e){
    		return defval;
    	}
    }

    /**
     * 获取请求参数（Date "HH:mm:ss"）
     * @param request
     * @param key
     * @param defval
     * @return
     */
    public static Date getValueTime(HttpServletRequest request, String key, Date defval){
    	try{
    		String temp = (String) request.getParameter(key);
    		DateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_TIME);
    		return temp==null ? defval : dateFormat.parse(temp);
    	}catch(Exception e){
    		return defval;
    	}
    }

    /**
     * 获取请求参数（Date "yyyy-MM-dd HH:mm:ss"）
     * @param request
     * @param key
     * @param defval
     * @return
     */
    public static Date getValueDate4Time(HttpServletRequest request, String key, Date defval){
    	try{
    		String temp = (String) request.getParameter(key);
    		DateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DATE_TIME);
    		return temp==null ? defval : dateFormat.parse(temp);
    	}catch(Exception e){
    		return defval;
    	}
    }
}
