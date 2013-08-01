package com.xukea.framework.util.editor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.xukea.framework.base.BaseConstants;

/**
 * Date 类型转换
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2011.12.26
 */
public class DateEditor extends CustomDateEditor{
	
	private final static DateFormat dateFormat = new SimpleDateFormat( BaseConstants.FORMAT_DATE_TIME );
	
	public DateEditor(){
	    super(dateFormat, true);
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		if (text==null || text.trim().equals("")) {
			text = "";
		}else if( text.length() == BaseConstants.FORMAT_DATE.length() ){
			//将 yyyy-MM-dd 转换为 yyyy-MM-dd HH:mm:ss
			SimpleDateFormat tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_DATE );
			try {
				Date date = tempFormat.parse(text);
				text = dateFormat.format(date);
			} catch (ParseException e) {
				text = text + " 00:00:00";
			}
		}else if( text.length() == BaseConstants.FORMAT_TIME.length() ){
			//将 HH:mm:ss 转换为 yyyy-MM-dd HH:mm:ss
			SimpleDateFormat tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_TIME );
			try {
				Date date = tempFormat.parse(text);
				text = dateFormat.format(date);
			} catch (ParseException e) {
				text = "1970-01-01 " + text;
			}
		}else if( text.length() == BaseConstants.FORMAT_TIME_HM.length() ){
			//将 HH:mm 转换为 yyyy-MM-dd HH:mm:ss
			SimpleDateFormat tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_TIME_HM );
			try {
				Date date = tempFormat.parse(text);
				text = dateFormat.format(date);
			} catch (ParseException e) {
				text = "1970-01-01 " + text +":00";
			}
		}else if( text.length() == BaseConstants.FORMAT_MONTH.length() ){
			//将 yyyy-MM 转换为 yyyy-MM-dd HH:mm:ss
			SimpleDateFormat tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_MONTH );
			try {
				Date date = tempFormat.parse(text);
				text = dateFormat.format(date);
			} catch (ParseException e) {
				text = text + "-01 00:00:00";
			}
		}
		
		super.setAsText(text);
	}
	
}