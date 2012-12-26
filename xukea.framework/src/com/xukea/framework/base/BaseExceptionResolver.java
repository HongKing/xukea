package com.xukea.framework.base;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class BaseExceptionResolver implements HandlerExceptionResolver {//extends SimpleMappingExceptionResolver

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    	return null;
    }

    /**
     * JSON输出
     * 
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