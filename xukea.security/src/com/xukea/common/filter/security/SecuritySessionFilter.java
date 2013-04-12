package com.xukea.common.filter.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.util.Assert;

import com.xukea.common.util.cache.Config;

/**
 * 
 * @author Administrator
 *
 */
public class SecuritySessionFilter extends ConcurrentSessionFilter {
    private LogoutHandler[] handlers = new LogoutHandler[] {new SecurityContextLogoutHandler()};

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
    
    /**
     * 退出处理
     * @param request
     * @param response
     */
    public void doLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (LogoutHandler handler : handlers) {
            handler.logout(request, response, auth);
        }
    }
    
    /**
     * 注册logoutHandler
     */
    public void setLogoutHandlers(LogoutHandler[] handlers) {
        Assert.notNull(handlers);
        this.handlers = handlers;
        super.setLogoutHandlers(handlers);
    }

}