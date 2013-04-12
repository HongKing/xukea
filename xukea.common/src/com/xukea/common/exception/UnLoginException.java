package com.xukea.common.exception;

import com.xukea.framework.base.BaseException;



/**
 * 自定义异常：unLogin
 */
public class UnLoginException extends BaseException{

	private static final long serialVersionUID = -8766300149825612706L;

	public UnLoginException(){
		super();
	}

	public UnLoginException(String str){
		super(str);
	}
}