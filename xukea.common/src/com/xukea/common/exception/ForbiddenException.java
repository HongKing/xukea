package com.xukea.common.exception;

import com.xukea.framework.base.BaseException;

/**
 * 自定义异常：403
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
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