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

}
 

	 

	

