package com.xukea.login.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.framework.base.BaseRestSpringController;


/**
 * 登录Controller
 * @author 石头
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseRestSpringController<Object, Long>{
	
	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Object obj) {
		ModelAndView result = new ModelAndView();
		result.setViewName("/index");
		return result;
	}
}
 

	 

	

