package com.xukea.framework.base;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 验证基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class BaseValidator implements Validator {
	protected final Logger log = Logger.getLogger(this.getClass());
 
	public boolean supports(Class<?> aClass) {
		return true;
	}
 
	public void validate(Object obj, Errors errors) {
		
	}
}