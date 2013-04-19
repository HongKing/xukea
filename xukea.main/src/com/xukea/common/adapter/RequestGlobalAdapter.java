package com.xukea.common.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.xukea.common.exception.PageNotFoundException;
import com.xukea.common.exception.UnauthorizedException;
import com.xukea.common.util.cache.MenuCache;
import com.xukea.framework.base.BaseRequestAdapter;
import com.xukea.framework.base.BaseSpringController;
import com.xukea.framework.util.LicenseSign;


/**
 * 拦截器：所有请求均会执行（包括静态请求）
 */
public class RequestGlobalAdapter extends BaseRequestAdapter {
	private final Logger log = Logger.getLogger(getClass());

	/**
	 * 预处理<br/>
	 * 访问统计等
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("preHandle");

		// 授权控制
		if(!LicenseSign.getInstance().isSigened()){
			throw new UnauthorizedException("授权信息无效");
		}else if(!LicenseSign.getInstance().isExpiry()){
			throw new UnauthorizedException("授权过期，请获取新的授权信息");
		}

		String from = request.getRequestURI().substring(request.getContextPath().length());
		
		// 是否是REST URL
		if(isRestUrl(from)){
			//当前URL所对应的code，用于选中菜单用
			String thisUrlCode = MenuCache.getInstance().getUrlCode(from);
			request.setAttribute("THIS_URL_CODE", thisUrlCode);
			return true;
		}

		// 是否静态页面
		if(isStaticUrl(request, from)){
			return true;
		}
		
		//其他URL则是未找到
		throw new PageNotFoundException();
	}

	/**
	 * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染）
	 */
	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("postHandle");

		// 如果是资源文件不做处理
		if(handler instanceof ResourceHttpRequestHandler){
			return;
		}
		// 有配置的URL Mapping处理时
		if(handler instanceof HandlerMethod){
			HandlerMethod temp = (HandlerMethod) handler;
			// 只要不是由BaseSpringController子类处理的内容,都认为是无效处理,作为PageNotFoundException异常处理
			if( temp.getBean() instanceof BaseSpringController){
				return ;
			}
		}
		// 采用WEB容器默认的servlet处理时（一般是当访问的URL无对应的URL Mapping处理时，即404错误）
		if(handler instanceof DefaultServletHttpRequestHandler){
			// 此处抛出异常后，能被全局异常处理捕获
			// 全局捕获异常后，主要目的是用来记录日志
			// 但最终响应的页面则是web.xml里面配置的default servlet的error-page来处理
			throw new PageNotFoundException("URL [ "+ request.getRequestURL() +" ]");
		}
	}

	/**
	 * 返回处理（已经渲染了页面,这里抛出的异常不影响前端的页面展现）
	 */
	@Override
	public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.debug("afterCompletion");
		if(ex!=null){
			log.error(ex);
		}
	}
}