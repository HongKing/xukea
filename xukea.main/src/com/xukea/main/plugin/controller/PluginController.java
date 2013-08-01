package com.xukea.main.plugin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.framework.base.BaseRestSpringController;

/**
 * 插件管理Controller
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Controller
@RequestMapping("/main/plugin")
public class PluginController extends BaseRestSpringController {
	
	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView();
		result.setViewName("/main/plugin/index");
		return result;
	}

}