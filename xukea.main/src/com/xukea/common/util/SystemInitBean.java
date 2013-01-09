package com.xukea.common.util;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.xukea.common.util.cache.SysSettingsCache;
import com.xukea.common.util.thread.ThreadManager;


/**
 * 系统初始化代码
 * 注：该类的名字不可变更
 * 
 * @author   FishBoy
 * @version  1.0
 * @date     2012.01.06
 */
public class SystemInitBean implements InitializingBean, DisposableBean, BeanFactoryAware, ServletContextAware{ 
	private Logger log = Logger.getLogger(SystemInitBean.class);
	
	/**
	 * 系统初始化代码
	 */
	public void afterPropertiesSet() throws Exception {
		log.debug("系统初始化");
		SysSettingsCache.getInstance().refresh();   //缓存系统设置
//		
		ThreadManager.getInstance().startSendEmailThread(); //线程启动：异步发送邮件线程
	}

	public void setServletContext(ServletContext arg0) {
		log.debug("SystemInitBean setServletContext");
	}

	public void destroy() throws Exception {
		log.debug("SystemInitBean destroy");
//		ThreadManager.getInstance().stopSendEmailThread(); //线程停止：异步发送邮件线程
	}

	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		log.debug("SystemInitBean setBeanFactory");
	}

}