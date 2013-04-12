package com.xukea.login.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.framework.base.BaseRestSpringController;


/**
 * 系统默认的controller处理
 * @author 石头
 *
 */
@Controller
@RequestMapping("/")
public class SiteController extends BaseRestSpringController<Object, Long>{
	
	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Object obj) {
		ModelAndView result = new ModelAndView();
		result.setViewName("/index");
		return result;
	}

	/**
	 * 错误页面
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/error/{code}")
	public String getPageLogin(@PathVariable String code) {
		String errorPage = "/commons/error";
		if("404".equals(code)){
			errorPage = "/commons/404";
		}else if("403".equals(code)){
			errorPage = "/commons/403";
		}
		return errorPage;
	}
}
 

	 

	

