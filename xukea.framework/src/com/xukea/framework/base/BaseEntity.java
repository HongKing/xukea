package com.xukea.framework.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

/**
 * Entity对象基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class BaseEntity implements Serializable, Cloneable {
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
                	|| "class java.sql.Date".equalsIgnoreCase(type.toString())
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

	/**
	 * 对象拷贝（浅拷贝）
	 * @return
	 */
	public BaseEntity clone() {
		return clone(false);
	}
	
	/**
	 * 对象拷贝
	 * 
	 * @param flag 拷贝模式  <b>true</b>：深拷贝 <b>false</b>：浅拷贝 
	 * @return
	 */
	public BaseEntity clone(boolean flag) {
		if(flag){
			// 深拷贝：采用对象序列化/反序列化的机制，进行对象的深度copy
			try {
				// 序列化
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bout);
				out.writeObject(this);
				out.close();
				// 反序列化
				ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
				ObjectInputStream in = new ObjectInputStream(bin);
				Object ret = in.readObject();
				in.close();
				return (BaseEntity) ret;
			} catch (Exception e) {
				return null;
			}
		}else{
			// 浅拷贝：Object的clone只是对象的影子copy，除了基础数据和String类型copy的是值，其他复杂类型还是copy的应用
			BaseEntity o = null;
			try {
				o = (BaseEntity)super.clone();
			} catch(CloneNotSupportedException e) {
				log.error("BaseEntity clone failed", e);
			}
			return o;
		}
	}
}
