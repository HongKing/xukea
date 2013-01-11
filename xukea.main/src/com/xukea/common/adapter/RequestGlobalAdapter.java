package com.xukea.common.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.exception.PageNotFoundException;
import com.xukea.common.exception.UnauthorizedException;
import com.xukea.common.util.cache.MenuCache;
import com.xukea.framework.base.BaseRequestAdapter;
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
	public boolean preHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
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
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		log.debug("postHandle");
	}

	/**
	 * 返回处理（已经渲染了页面）
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.debug("afterCompletion");
		if(ex!=null){
			log.error(ex);
		}
	}
}