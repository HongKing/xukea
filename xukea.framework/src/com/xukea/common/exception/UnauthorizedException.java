package com.xukea.common.exception;

import com.xukea.framework.base.BaseException;



/**
 * 自定义异常：未授权
 */
public class UnauthorizedException extends BaseException{

	private static final long serialVersionUID = 1005032654285448574L;

	public UnauthorizedException(){
		super();
	}

	public UnauthorizedException(String str){
		super(str);
	}
}