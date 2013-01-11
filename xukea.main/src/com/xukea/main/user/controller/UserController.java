package com.xukea.main.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.main.user.model.User;

/**
 * 系统用户管理Controller
 * @author 石头
 *
 */
@Controller
@RequestMapping("/main/user")
public class UserController extends BaseRestSpringController<User, Long>{
	
	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, User obj) {
		ModelAndView result = new ModelAndView();
		result.setViewName("/main/user/index");
		return result;
	}

}
 

	 

	

