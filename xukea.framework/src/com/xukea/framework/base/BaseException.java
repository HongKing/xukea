package com.xukea.framework.base;

/**
 * 异常基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class BaseException extends Exception{
	
	private static final long serialVersionUID = -6163426663650604774L;

	public BaseException(){
		super();
	}

	public BaseException(String str){
		super(str);
	}
}