package com.xukea.common.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.xukea.common.util.WebUtil;
import com.xukea.common.util.cache.Config;

/**
 * 无权限访问异常处理，只有登录后的无效访问才处理，未登录状态将不进入该类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    private String errorPage;
    
    private boolean forwardToDestination = false;
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
	@Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException, ServletException {
		
        String msg = exception.getMessage();
        if (WebUtil.isAjaxRequest(request)) {
            WebUtil.outputErrorJSON(request, response, msg);
        } else {
        	// 自定义异常信息保存
        	request.setAttribute("ERROR_MSG", msg);
        	// Spring Security标准保存
            saveException(request, exception);
            
        	// 如果没有设置跳转页面，则重新获取
        	if(errorPage==null || "".equals(errorPage)){
        		this.errorPage = Config.getInstance().getString("security.url.access.denied");
        	}
        	// 重新获取后还为空，则跳转至默认页面
        	if(errorPage==null || "".equals(errorPage)){
        		response.sendError(HttpServletResponse.SC_FORBIDDEN, exception.getMessage());
        	}else{
                if (forwardToDestination) {
                    request.getRequestDispatcher(errorPage).forward(request, response);
                } else {
                    redirectStrategy.sendRedirect(request, response, errorPage);
                }
        	}
        }
    }

    protected boolean isUseForward() {
        return forwardToDestination;
    }

    /**
     * If set to <tt>true</tt>, performs a forward to the failure destination URL instead of a redirect. Defaults to
     * <tt>false</tt>.
     */
    public void setUseForward(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    /**
     * The error page to use. Must begin with a "/" and is interpreted relative to the current context root.
     *
     * @param errorPage the dispatcher path to display
     *
     * @throws IllegalArgumentException if the argument doesn't comply with the above limitations
     */
    public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }

        this.errorPage = errorPage;
    }

    /**
     * Caches the {@code AuthenticationException} for use in view rendering.
     * <p>
     * If {@code forwardToDestination} is set to true, request scope will be used, otherwise it will attempt to store
     * the exception in the session. If there is no session and {@code allowSessionCreation} is {@code true} a session
     * will be created. Otherwise the exception will not be stored.
     */
    protected final void saveException(HttpServletRequest request, AccessDeniedException exception) {
        if (forwardToDestination) {
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        } else {
            HttpSession session = request.getSession(false);

            if (session != null ) {
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            }
        }
    }
}  