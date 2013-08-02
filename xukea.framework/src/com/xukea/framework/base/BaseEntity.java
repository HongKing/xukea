package com.xukea.framework.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DaoSupport;

/**
 * Entity对象基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class BaseEntity implements Serializable {
	protected Logger log = Logger.getLogger(this.getClass());
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstants.FORMAT_TIMESTAMP);
	
	/**
	 * 重写toString方法，便于Bean对象的日志存放
	 */
	public String toString(){
        return this.toJson().toString();
	}
	
	/**
	 * 转换为JSON对象
	 * @return
	 */
	public JSONObject toJson(){
		JSONObject json = null;
		try {
			json = new JSONObject();
            Field[] fieldlist = this.getClass().getDeclaredFields();
            for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                fld.setAccessible(true); //设置访问私有属性的权限
                
                String key  = fld.getName();
                Class  type = fld.getType();
                Object val  = fld.get(this);
                
                // 如果值为空，则不处理
                if(val==null){
                	json.put(key, null);
                	continue;
                }
                
                if(    "class java.util.Date".equalsIgnoreCase(type.toString()) 
                	|| "class java.sql.Date".equals(type.toString())
                ){
                    // 如果是日期格式，则将其格式化
                	val = dateFormat.format(val);
                }else if(Collection.class.isAssignableFrom(type)){
                    // 如果是集合
                	val = JSONArray.fromObject(val);
                }
                json.put(key, val);
            }
    		return json;
        } catch (Exception e) {
        	log.error(e);
        	return null;
        }
	}
	
}
