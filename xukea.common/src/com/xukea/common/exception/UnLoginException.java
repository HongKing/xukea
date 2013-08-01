package com.xukea.common.exception;

import com.xukea.framework.base.BaseException;

/**
 * 自定义异常：未登录
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
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