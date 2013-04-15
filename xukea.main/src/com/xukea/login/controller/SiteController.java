package com.xukea.login.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.exception.ForbiddenException;
import com.xukea.common.exception.PageNotFoundException;
import com.xukea.common.exception.UnLoginException;
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
	public void getPageLogin(@PathVariable String code) throws Exception {
		if("403".equals(code)){
			throw new ForbiddenException();
		}else if("404".equals(code)){
			throw new PageNotFoundException();
		}else if("500".equals(code)){
			throw new Exception("500 exception");
		}else if("unlogin".equals(code)){
			throw new UnLoginException();
		}else{
			throw new Exception("other exception");
		}
	}
}
 

	 

	

