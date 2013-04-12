package com.xukea.common.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * SESSION监听
 */
public class SessionListener implements HttpSessionListener {
	private final Logger log = Logger.getLogger(getClass());
	
	/**
	 * 会话创建
	 */
	public void sessionCreated(HttpSessionEvent event) {
        HttpSession    session     = event.getSession();
		log.debug("session created:"+session.getId());
    }

	/**
	 * 会话销毁
	 * 
	 * 触发条件：
	 *     1.执行session.invalidate()方法
	 *     2.服务器自动销毁超时的session
	 */
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession    session     = event.getSession();
//        ServletContext application = session.getServletContext();
		log.debug("session destroyed:"+session.getId());
    }
    
    

}
