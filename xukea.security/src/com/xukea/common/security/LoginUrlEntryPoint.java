package com.xukea.common.security;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.xukea.common.util.cache.Config;

/**
 * 登陆跳转页面
 * @author Administrator
 *
 */
public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {
	
	public LoginUrlEntryPoint(){
		this("/login");
	}
	
	public LoginUrlEntryPoint(String loginFormUrl){
		super(loginFormUrl);
	}

    public String getLoginFormUrl() {
    	// 登陆页面
        return Config.getInstance().getString("security.url.login.formurl");
    }
}