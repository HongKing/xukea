package com.xukea.common.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.framework.base.BaseRequestAdapter;

/**
 * 拦截器：执行方法之前（仅当URL有匹配的注入方法可执行时才有效，否则报404错误）
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class RequestBeforeMethodAdapter extends BaseRequestAdapter {
	private final Logger log = Logger.getLogger(getClass());
	
	/**
	 * 预处理（Controller处理之前）
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("preHandle");
		
		//权限控制：动作控制
		return true;
	}

	/**
	 * 后处理（Controller处理结束并返回ModelAndView，但未进行页面渲染）
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		log.debug("postHandle");
	}

	/**
	 * 返回处理（已经渲染了页面）
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.debug("afterCompletion");
		if(ex!=null){
			log.error(ex);
		}
	}
}