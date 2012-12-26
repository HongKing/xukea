package com.xukea.framework.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Spring 的 applicationContext 静态变量
 * 
 * @author  FishBoy
 * @version 1.0
 * @date    2012-01-05
 */
public class ContextUtil {
	private static ApplicationContext applicationContext;
	
	private static Logger log = Logger.getLogger(ContextUtil.class);
	
	public static void setApplicationContext(ApplicationContext applicationContext) {
		synchronized (ContextUtil.class) {
			log.debug("setApplicationContext, notifyAll");
			ContextUtil.applicationContext = applicationContext;
			ContextUtil.class.notifyAll();
		}
	}
	 
	public static ApplicationContext getApplicationContext() {
		synchronized (ContextUtil.class) {
		while (applicationContext == null) {
			try {
				log.debug("getApplicationContext, wait...");
				ContextUtil.class.wait(60000);
				if (applicationContext == null) {
					log.warn("Have been waiting for ApplicationContext to be set for 1 minute", new Exception());
				}
			} catch (InterruptedException ex) {
				log.debug("getApplicationContext, wait interrupted");
			}
		}
		return applicationContext;
		}
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 实现DisposableBean接口,在Context关闭时清理静态变量.
	 */
	public void destroy() throws Exception {
		ContextUtil.clear();
	}
	
	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clear() {
		log.debug("清除SpringContextHolder中的ApplicationContext:"+ applicationContext);
		applicationContext = null;
	}
	
	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
}