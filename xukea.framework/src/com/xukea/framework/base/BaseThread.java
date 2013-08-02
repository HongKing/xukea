package com.xukea.framework.base;

import org.apache.log4j.Logger;


/**
 * 线程基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public abstract class BaseThread extends Thread {
	protected final Logger log = Logger.getLogger(getClass());
	
	public static final int NEW      = 0; //新建
	public static final int WAITING  = 1; //就绪
	public static final int WORKING  = 2; //工作中
	public static final int STOPED   = 3; //已停止
	
	protected boolean running = false;    //是否允许该线程继续运行
	protected int     status  = STOPED;   //当前线程状态

	public BaseThread(ThreadGroup group, String tname){
		super(group, tname);
	}
	
	public abstract void run();
	
	/**
	 * 启动线程
	 */
	public void startThread() {
		this.setRunning(true);
		try{
			this.start();
		}catch(Exception e){
		}
	}
	
	/**
	 * 停止线程
	 */
	public void stopThread() {
		this.setRunning(false);
	}

	public boolean isRunning() {
		return running;
	}

	public int getStatus() {
		return status;
	}
	
	protected void setRunning(boolean running) {
		this.running = running;
	}
	
	protected void setStatus(int status) {
		this.status = status;
	}
	
}
