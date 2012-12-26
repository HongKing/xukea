package com.xukea.framework.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public class BaseEntity implements Serializable {
	private Logger log = Logger.getLogger(this.getClass());
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstants.FORMAT_TIMESTAMP);
	
	/**
	 * 重写toString方法，便于Bean对象的日志存放
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass());
		try {
            Field[] fieldlist = this.getClass().getDeclaredFields();
            for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                fld.setAccessible(true); //设置访问私有属性的权限
                
                String key  = fld.getName();
                Class  type = fld.getType();
                Object val  = fld.get(this);
                
                // 如果是日期格式，则将其格式化
                if( val!=null 
                	&& (    "class java.util.Date".equals(type.toString()) 
                		 || "class java.sql.Date".equals(type.toString())
                	   )
                ){
                	val = dateFormat.format(val);
                }
                
                sb.append("\r\n"+ key +"\t:\t"+ val);
            }
        } catch (Exception e) {
        	log.error(e);
        }
        
        return sb.toString();
	}
}
