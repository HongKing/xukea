package com.xukea.framework.base;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.CharacterEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

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
 * @author FishBoy
 *
 */
public class BaseSpringController extends MultiActionController {
	
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
		System.out.println("============================================ddddddddddddddddddddddddddd");
		return super.handleRequestInternal(request, response);
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("============================================eeeeeeeeeeeeeeeeeeeeeeeeeeee");
		return super.handleRequest(request, response);
	}
	
    /**
     * 
     * @param request
     * @param response
     * @param cntype
     * @param data
     */
    protected void output(HttpServletRequest request, HttpServletResponse response, String cntype, Object data){
		try {
			response.setContentType(cntype);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
			out.println( data );
			out.close();
//			response.getWriter().write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * JSON输出
     * 
     * @param request
     * @param response
     * @param data
     */
	protected void outputJSON(HttpServletRequest request, HttpServletResponse response, String data){
		String jsoncb = request.getParameter("jsoncallback");
		String cntype = "application/json; charset=utf-8";
		if(jsoncb!=null && !"".equals(jsoncb) && !"?".equals(jsoncb)){
			data = jsoncb +"("+ data +")";
			cntype = "application/x-javascript; charset=utf-8";
		}
		output(request, response, cntype, data);
	}
	
    /**
     * JSON输出：成功状态
     * 
     * @param response
     * @param data
     */
	protected void outputSuccessJSON(HttpServletRequest request, HttpServletResponse response, String data){
		JSONObject json = new JSONObject();
		json.put("xukea_type"  , "success");
		json.put("xukea_status", BaseConstants.HTTP_OK);
		json.put("xukea_msg"   , "");
		json.put("data"         , data);
		outputJSON(request, response, json.toString());
	}

    /**
     * JSON输出：出错状态
     * 
     * @param response
     * @param msg
     */
	protected void outputErrorJSON(HttpServletRequest request, HttpServletResponse response, String msg){
		outputErrorJSON(request, response, BaseConstants.HTTP_SERVER_ERROR, msg);
	}
	
    /**
     * JSON输出：出错状态
     * 
     * @param response
     * @param code
     * @param msg
     */
	protected void outputErrorJSON(HttpServletRequest request, HttpServletResponse response, double code, String msg){
		JSONObject json = new JSONObject();
		json.put("xukea_type"  , "error");
		json.put("xukea_status", code);
		json.put("xukea_msg"   , msg);
		json.put("data"         , "");
		outputJSON(request, response, json.toString());
	}
}
