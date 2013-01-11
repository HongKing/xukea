package com.xukea.main.plugin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.main.plugin.model.PluginInfo;


/**
 * 插件管理Controller
 * @author 石头
 *
 */
@Controller
@RequestMapping("/main/plugin")
public class PluginController extends BaseRestSpringController<PluginInfo, String>{
	
	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, PluginInfo obj) {
		ModelAndView result = new ModelAndView();
		result.setViewName("/main/plugin/index");
		return result;
	}

}
 

	 

	

