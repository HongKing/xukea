package com.xukea.common.validator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.xukea.common.util.Config;
import com.xukea.framework.base.BaseValidator;


/**
 * 验证码验证类
 * @author FishBoy
 * @version
 */
public class CapthcaValidator extends BaseValidator{
	
	// session名称
	private static String sname = Config.getInstance().getString("captcha.sname"); 
	
	@Override
	public void validate(Object obj, Errors errors) {
	}
	
	public boolean validate(HttpServletRequest request, String vnum){
		String snum = (String)request.getSession().getAttribute(sname);
		request.getSession().removeAttribute(sname);
		if(snum==null || "".equals(snum)){
			return false;
		}else{
			return snum.equalsIgnoreCase(vnum);
		}
	}
	
	public static boolean isValidate(HttpServletRequest request){
		String snum = (String)request.getSession().getAttribute(sname);
		return snum!=null;
	}
}