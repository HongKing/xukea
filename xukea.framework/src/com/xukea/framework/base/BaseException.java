package com.xukea.framework.base;


public class BaseException extends Exception{
	
	private static final long serialVersionUID = -6163426663650604774L;

	public BaseException(){
		super();
	}

	public BaseException(String str){
		super(str);
	}
}