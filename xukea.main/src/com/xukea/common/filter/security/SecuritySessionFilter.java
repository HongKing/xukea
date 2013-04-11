package com.xukea.common.filter.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import com.xukea.common.util.cache.Config;

/**
 * 
 * @author Administrator
 *
 */
public class SecuritySessionFilter extends ConcurrentSessionFilter {

    /**
     * @deprecated Use constructor which injects the <tt>SessionRegistry</tt>.
     */
	public SecuritySessionFilter() {
    }
	
    public SecuritySessionFilter(SessionRegistry sessionRegistry) {
    	// 默认设置登录页面的URL
        this(sessionRegistry, Config.getInstance().getString("security.url.login.formurl"));
    }

    public SecuritySessionFilter(SessionRegistry sessionRegistry, String expiredUrl) {
    	super(sessionRegistry, expiredUrl);
    }

    /**
     * SESSION失效后的跳转页面
     */
    @Override
    protected String determineExpiredUrl(HttpServletRequest request, SessionInformation info) {
        return Config.getInstance().getString("security.url.login.formurl");
    }
}