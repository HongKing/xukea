package com.xukea.framework.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.CharacterEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.xukea.common.util.WebUtil;
import com.xukea.framework.util.ConvertRegisterHelper;
import com.xukea.framework.util.editor.BooleanEditor;
import com.xukea.framework.util.editor.ByteEditor;
import com.xukea.framework.util.editor.DateEditor;
import com.xukea.framework.util.editor.DoubleEditor;
import com.xukea.framework.util.editor.FloatEditor;
import com.xukea.framework.util.editor.IntEditor;
import com.xukea.framework.util.editor.LongEditor;
import com.xukea.framework.util.editor.ShortEditor;

/**
 * Spring 控制器基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class BaseSpringController extends MultiActionController {
    protected final Logger log = Logger.getLogger(this.getClass());
	
	static {
		//注册converters
		ConvertRegisterHelper.registerConverters();
	}
	
	public void copyProperties(Object target, Object source) {
		BeanUtils.copyProperties(target, source);
	}
	
    /**
     * 初始化binder的回调函数.
     *
     * @see MultiActionController#createBinder(HttpServletRequest,Object)
     */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Byte.class,       new CustomNumberEditor(Byte.class, true));
		binder.registerCustomEditor(Short.class,      new CustomNumberEditor(Short.class, true));
	    binder.registerCustomEditor(Integer.class,    new CustomNumberEditor(Integer.class, true));
	    binder.registerCustomEditor(Long.class,       new CustomNumberEditor(Long.class, true));
	    binder.registerCustomEditor(Float.class,      new CustomNumberEditor(Float.class, true));
	    binder.registerCustomEditor(Double.class,     new CustomNumberEditor(Double.class, true));
	    binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
	    binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
		binder.registerCustomEditor(Number.class,     new CustomNumberEditor(Number.class, true));
	    
	    binder.registerCustomEditor(String.class,   new StringMultipartFileEditor());
	    binder.registerCustomEditor(Date.class,     new DateEditor());
		binder.registerCustomEditor(Boolean.class,  new BooleanEditor());
	    binder.registerCustomEditor(boolean.class,  new BooleanEditor());
	    binder.registerCustomEditor(byte[].class,   new ByteArrayMultipartFileEditor());

	    binder.registerCustomEditor(byte.class,    new ByteEditor());
	    binder.registerCustomEditor(short.class,   new ShortEditor());
	    binder.registerCustomEditor(int.class,     new IntEditor());
	    binder.registerCustomEditor(long.class,    new LongEditor());
	    binder.registerCustomEditor(float.class,   new FloatEditor());
	    binder.registerCustomEditor(double.class,  new DoubleEditor());

	    binder.registerCustomEditor(char.class,    new CharacterEditor(true));

	}
	
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.handleRequestInternal(request, response);
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.handleRequest(request, response);
	}
	
    /**
     * AJAX 输出
     * @param request
     * @param response
     * @param cntype
     * @param data
     */
    protected void output(HttpServletRequest request, HttpServletResponse response, String cntype, Object data){
    	WebUtil.output(request, response, cntype, data);
    }
    
    /**
     * JSON输出
     * 
     * @param request
     * @param response
     * @param data
     */
	protected void outputJSON(HttpServletRequest request, HttpServletResponse response, String data){
		WebUtil.outputJSON(request, response, data);
	}
	
    /**
     * JSON输出：成功状态
     * 
     * @param response
     * @param data
     */
	protected void outputSuccessJSON(HttpServletRequest request, HttpServletResponse response, String data){
		WebUtil.outputSuccessJSON(request, response, data);
	}

    /**
     * JSON输出：出错状态
     * 
     * @param response
     * @param msg
     */
	protected void outputErrorJSON(HttpServletRequest request, HttpServletResponse response, String msg){
		WebUtil.outputErrorJSON(request, response, msg);
	}
	
    /**
     * JSON输出：出错状态
     * 
     * @param response
     * @param code
     * @param msg
     */
	protected void outputErrorJSON(HttpServletRequest request, HttpServletResponse response, double code, String msg){
		WebUtil.outputErrorJSON(request, response, code, msg);
	}
}
