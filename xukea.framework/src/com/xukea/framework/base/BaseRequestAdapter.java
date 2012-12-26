package com.xukea.framework.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class BaseRequestAdapter extends HandlerInterceptorAdapter {
	private final String staticCtrl = "/static/";
	
	/**
	 * 预处理
	 */
	@Override
	public boolean preHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	/**
	 * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染）
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}

	/**
	 * 返回处理（已经渲染了页面）
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	
	//------------------------------------------------
	/**
	 * 判断URL是否是REST URL
	 */
	protected boolean isRestUrl(String from){
		String temp = from.substring(from.lastIndexOf("/"));
		return !(temp.indexOf(".")>-1);
	}
	/**
	 * 判断URL是否是静态文件
	 * @param request
	 * @param from
	 * @return
	 */
	protected boolean isStaticUrl(HttpServletRequest request, String from){
		// 静态文件过滤
		Boolean isStatic = (Boolean)request.getAttribute("IS_STATIC");
		if(isStaticPage(isStatic)){
			return true;
		}
		if(isStaticPage(from)){
			return true;
		}
		return false;
	}
	
	private boolean isStaticPage(String from ){
		return from.startsWith(staticCtrl);
	}
	private boolean isStaticPage(Boolean isStatic){
		if(isStatic!=null && isStatic){
			return true;
		}else{
			return false;
		}
	}

}