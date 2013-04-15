package com.xukea.main.role.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.main.role.model.Role;

/**
 * 角色管理Controller
 * @author 石头
 *
 */
@Controller
@RequestMapping("/main/role")
public class RoleController extends BaseRestSpringController<Role, String>{
	
	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Role obj) {
		ModelAndView result = new ModelAndView();
		result.setViewName("/main/role/index");
		return result;
	}

	/**
	 * 角色查询：列表（默认第一页）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list")
	public String getPage4List() {
		return "redirect:/main/role/list/1";
	}
	
	/**
	 * 角色查询：列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list/{page}")
	public String getPage4List(HttpServletRequest request, HttpServletResponse response) {
		return "/main/role/index";
	}
	
	/**
	 * 添加角色：页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/new")
	public String getPage4Add(HttpServletRequest request, HttpServletResponse response) {
		return "/main/role/add";
	}
	
	/**
	 * 编辑角色：页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/edit/{rid}")
	public String getPage4Edit(HttpServletRequest request, HttpServletResponse response) {
		return "/main/role/edit";
	}
}
