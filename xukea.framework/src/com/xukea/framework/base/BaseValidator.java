package com.xukea.framework.base;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BaseValidator implements Validator {
	protected final Logger log = Logger.getLogger(this.getClass());
 
	public boolean supports(Class<?> aClass) {
		return true;
	}
 
	public void validate(Object obj, Errors errors) {
		
	}
}