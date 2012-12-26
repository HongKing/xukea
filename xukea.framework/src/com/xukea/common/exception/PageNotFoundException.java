package com.xukea.common.exception;

import com.xukea.framework.base.BaseException;


/**
 * 自定义异常:404
 */
public class PageNotFoundException extends BaseException{
	
	private static final long serialVersionUID = 3798079252590226379L;

	public PageNotFoundException(){
		super();
	}

	public PageNotFoundException(String str){
		super(str);
	}
}