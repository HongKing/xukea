package com.xukea.common.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.xukea.common.util.WebUtil;
import com.xukea.common.util.cache.Config;


/**
 * Spring Security 登录出错处理
 * 
 * @author Administrator
 *
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
    		AuthenticationException exception) throws IOException, ServletException {
    	//TODO 登录出错之后的日志记录等业务处理

        String msg = exception.getMessage();
        if (WebUtil.isAjaxRequest(request)) {
            WebUtil.outputErrorJSON(request, response, msg);
        } else {
        	request.setAttribute("ERROR_MSG", msg);
        	// 设置登录出错之后的跳转页面
        	String failureUrl = Config.getInstance().getString("security.url.login.failure");
        	this.setDefaultFailureUrl(failureUrl);
        	super.onAuthenticationFailure(request, response, exception);
        }
    }
    
    public void setUseForward(boolean forwardToDestination) {
    	super.setUseForward(true);
    }
}