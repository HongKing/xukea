package com.xukea.common.exception;

import com.xukea.framework.base.BaseException;


/**
 * 自定义异常：403
 */
public class ForbiddenException extends BaseException{

	private static final long serialVersionUID = -3488521884024265240L;

	public ForbiddenException(){
		super();
	}

	public ForbiddenException(String str){
		super(str);
	}
}