package com.xukea.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.xukea.framework.base.BaseConstants;


/**
 * 时期处理
 * @author FishBoy
 * @date   2012.02.09
 */
public class DateUtil {
	
	/**
	 * 按整数形式返回“yyyyMMdd”
	 * @return
	 */
	public static Integer getDate2Int(){
		return getDate2Int(new Date());
	}

	/**
	 * 按整数形式返回“yyyyMMdd”
	 * @return
	 */
	public static Integer getDate2Int(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		return date==null ? null : new Integer(sdf.format(date));
	}
	
	/**
	 * 返回当前日期时间“yyyy-MM-dd HH:mm:ss”
	 * @return
	 */
	public static String getDate4Time(){
		return getDate4Time( new Date() );
	}

	/**
	 * 返回指定日期时间“yyyy-MM-dd HH:mm:ss”
	 * @return
	 */
	public static String getDate4Time(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.FORMAT_DATE_TIME);
		return date==null ? null : sdf.format(date);
	}

	/**
	 * 返回指定日期时间“yyyy-MM-dd HH:mm:ss”
	 * @return
	 */
	public static String getDate4Time(Date date, int afterDay){
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.FORMAT_DATE_TIME);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, 0+afterDay);
		date = cal.getTime();
		return date==null ? null : sdf.format(date);
	}
	
	/**
	 * 返回当前日期“yyyy-MM-dd”
	 * @return
	 */
	public static String getDate2String(){
		return getDate2String( new Date() );
	}

	/**
	 * 返回指定日期“yyyy-MM-dd”
	 * @return
	 */
	public static String getDate2String(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.FORMAT_DATE);
		try{
			return sdf.format(date);
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 返回指定日期“yyyyMMddHHmmss”
	 * @return
	 */
	public static String getDate2StringWithOutTag(){
		return getDate2StringWithOutTag(new Date() );
	}
	
	/**
	 * 返回指定日期“yyyyMMddHHmmss”
	 * @return
	 */
	public static String getDate2StringWithOutTag(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try{
			return sdf.format(date);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 返回指定日期“yyyy-MM-dd”
	 * @return
	 */
	public static String getDate2String(Date date, int afterDay){
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.FORMAT_DATE);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, 0+afterDay);
		date = cal.getTime();
		return date==null ? null : sdf.format(date);
	}
	
	/**
	 * 返回当前时间“HH:mm:ss”
	 * @return
	 */
	public static String getTime2String(){
		return getTime2String( new Date() );
	}

	/**
	 * 返回指定时间“HH:mm:ss”
	 * @return
	 */
	public static String getTime2String(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.FORMAT_TIME);
		return date==null ? null : sdf.format(date);
	}
	
	/**
	 * 格式化时间（单位：分）
	 * @param time
	 * @return
	 */
	public static String formateTime(long time){
		long day = time/(24*60);
		long hor = (time- day*24*60)/60;
		long min = time - day*24*60 - hor*60;
		
		if(day>30){
			return "一月前";
		}else if(day>7){
			return "一星期前";
		}else{
			return day +"天"+ hor +"时"+ min +"分";
		}
		
	}
	
	/**
	 * 是否大于当前时间
	 * @param text
	 * @return
	 */
	public static boolean compareGreaterNow(String text){
		// 空值则返回 false
		if (text==null || text.trim().equals("")) {
			return false;
		}
		// 根据传入数据判断所采用的格式
		SimpleDateFormat tempFormat;
		text = text.trim();
		if( text.length() == BaseConstants.FORMAT_DATE.length() ){
			tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_DATE );
		}else if( text.length() == BaseConstants.FORMAT_TIME.length() ){
			tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_TIME );
		}else if( text.length() == BaseConstants.FORMAT_TIME_HM.length() ){
			tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_TIME_HM );
		}else{
			tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_DATE_TIME );
		}
		// 时间比较
		try {
			Date date1 = tempFormat.parse( text );
			Date date2 = tempFormat.parse( tempFormat.format(new Date()) ); // 将当前时间格式化为带比较时间的格式
			return date1.getTime() > date2.getTime();
		} catch (ParseException e) {
			return false;
		}
	}
}
