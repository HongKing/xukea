package com.xukea.common.adapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.xukea.common.UserBasicInfo;
import com.xukea.common.exception.UnLoginException;
import com.xukea.common.util.WebUtil;
import com.xukea.framework.base.BaseRequestAdapter;


/**
 * 拦截器：需登录才能访问
 */
public class LoginAdapter extends BaseRequestAdapter {
	private final Logger log = Logger.getLogger(getClass());
	private final String loginCtrl = "^/login.*";
	
	/**
	 * 预处理
	 */
	@Override
	public boolean preHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String from = request.getRequestURI().substring(request.getContextPath().length());
		
		// 是否登录页面
		if(isLoginUrl(from)){
			return true;
		}
		// 是否静态页面
		if(isStaticUrl(request, from)){
			return true;
		}
		
		// 用户登录拦截
		HttpSession session = request.getSession();
		UserBasicInfo user = (UserBasicInfo) session.getAttribute(UserBasicInfo.SESSION_NAME);
		if(user==null){
			//如果不是AJAX请求，则记录登录前的访问页面，便于登录后跳转
			if(!WebUtil.isAjaxRequest(request)){
				String refer = (String)session.getAttribute("REDIRECT_URL");
				if(refer==null){
					refer = request.getHeader("Referer");
					session.setAttribute("REDIRECT_URL", refer);
				}
			}
			throw new UnLoginException();
		}
		
		return true;

//		// 用户权限控制
//		//TODO
//		throw new ForbiddenException();
	}
	
	
	
	//------------------------------------------------
	private boolean isLoginUrl(String from ){
		Pattern pattern = Pattern.compile(loginCtrl);
		Matcher match = pattern.matcher(from);
		return match.matches();
	}
}
