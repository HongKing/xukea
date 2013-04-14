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
		result.setViewName("redirect:/main/user/list/1");
		return result;
	}

	/**
	 * 用户查询：列表（默认第一页）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list")
	public String getPage4List() {
		return "redirect:/main/user/list/1";
	}
	
	/**
	 * 用户查询：列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list/{page}")
	public String getPage4List(HttpServletRequest request, HttpServletResponse response) {
		return "/main/user/index";
	}
	
	/**
	 * 添加用户：页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/new")
	public String getPage4Add(HttpServletRequest request, HttpServletResponse response) {
		return "/main/user/add";
	}
	
	/**
	 * 编辑用户：页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/edit/{uid}")
	public String getPage4Edit(HttpServletRequest request, HttpServletResponse response) {
		return "/main/user/edit";
	}
}
 

	 

	

