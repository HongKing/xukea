package com.xukea.common.util.thread;

import org.apache.log4j.Logger;

/**
 * 线程管理类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class ThreadManager {
	private static Logger log  = Logger.getLogger(ThreadManager.class);
	private static Object lock = new Object(); 
	private static ThreadGroup   sysGroup = new ThreadGroup("sysGroup");
	private static ThreadManager instance = null;
	
	private SendEmailThread sendEmailThread = null;
	/**
	 * 单例工厂
	 * @return
	 */
	public static ThreadManager getInstance() {
		if (instance == null) {
			synchronized( lock ){   
                if (instance == null){   
                    instance = new ThreadManager();   
                }   
            }
		}
		return instance;
	}
	
	/**
	 * 邮件发送：启动
	 */
	public void startSendEmailThread(){
		if( sendEmailThread!=null && (sendEmailThread.isRunning() || sendEmailThread.isAlive()) ){
			log.debug("SendEmailThread线程未结束，请稍候启动");
			stopSendEmailThread();
			return ;
		}else{
			sendEmailThread = new SendEmailThread(sysGroup);
		}
		log.debug("start sendEmailThread");
		sendEmailThread.startThread();
	}

	/**
	 * 邮件发送：停止
	 */
	public void stopSendEmailThread(){
		log.debug("stop sendEmailThread");
		sendEmailThread.stopThread();
	}
}
