package com.xukea.framework.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.util.WebUtil;

public class BaseExceptionResolver implements HandlerExceptionResolver {//extends SimpleMappingExceptionResolver

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    	return null;
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