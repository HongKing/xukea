package com.xukea.common.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.xukea.common.util.WebUtil;
import com.xukea.common.util.cache.Config;


/**
 * Spring Security 登录成功处理
 * 
 * @author Administrator
 *
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
    	//TODO 登陆成功之后的日志记录等业务处理

        if (WebUtil.isAjaxRequest(request)) {
            WebUtil.outputSuccessJSON(request, response, "");
        } else {
        	// 设置登陆成功之后的跳转页面
        	String targetUrl = Config.getInstance().getString("security.url.login.success");
        	this.setTargetUrlParameter(targetUrl);
        	super.onAuthenticationSuccess(request, response, authentication);
        }
    	
    }
}