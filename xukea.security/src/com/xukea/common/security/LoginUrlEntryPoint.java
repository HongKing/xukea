package com.xukea.common.security;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.xukea.common.util.cache.Config;

/**
 * 登陆跳转页面
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
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