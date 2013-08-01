package com.xukea.framework.base;

/**
 * 系统常量
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class BaseConstants {
	
	/******************************************
	 * HTTP 信息
	 ******************************************/
	public final static double HTTP_OK             = 200;    //正常
	public final static double HTTP_FORBIDDEN      = 403;    //禁止访问
	public final static double HTTP_UNLOGIN        = 403.1;  //未登录
	public final static double HTTP_NOT_FOUND      = 404;    //资源不存在
	public final static double HTTP_SERVER_ERROR   = 500;    //服务器内部错误


	/******************************************
	 * 日期格式
	 ******************************************/
	public final static String FORMAT_MONTH     = "yyyy-MM";
	public final static String FORMAT_DATE      = "yyyy-MM-dd";
	public final static String FORMAT_TIME      = "HH:mm:ss";
	public final static String FORMAT_TIME_HM   = "HH:mm";
	public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.S";

	/******************************************
	 * 数字格式
	 ******************************************/
	public final static String FORMAT_NUMBER_0  = "#,##0";
	public final static String FORMAT_NUMBER_2  = "#,##0.00";
	public final static String FORMAT_NUMBER_3  = "#,##0.000";
}