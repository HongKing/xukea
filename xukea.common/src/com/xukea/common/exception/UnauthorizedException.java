package com.xukea.common.exception;

import com.xukea.framework.base.BaseException;

/**
 * 自定义异常：未授权
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
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