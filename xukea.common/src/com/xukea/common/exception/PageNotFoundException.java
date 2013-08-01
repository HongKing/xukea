package com.xukea.common.exception;

import com.xukea.framework.base.BaseException;

/**
 * 自定义异常:404
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
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