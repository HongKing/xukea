package com.xukea.main.rule.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.util.WebUtil;
import com.xukea.common.util.cache.DataPolicyCache;
import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.framework.util.PageList;
import com.xukea.main.role.model.Role;
import com.xukea.main.role.service.RoleService;
import com.xukea.main.rule.model.Rule;
import com.xukea.main.rule.service.RuleService;

/**
 * 数据权限Controller
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Controller
@RequestMapping("/main/rule")
public class RuleController extends BaseRestSpringController{
	
	@Resource 
	private RoleService roleService;
	
	@Resource
	private RuleService ruleService;

	@Override
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView result = new ModelAndView();
		result.setViewName("redirect:/main/rule/list/1");
		return result;
	}
	
	/**
	 * 转到数据权限页面
	 * */
	@RequestMapping(value="/list/{page}")
	public String toDataManage(HttpServletRequest request,@PathVariable int page){
		//查找所有的rule
		int pageSize =  WebUtil.getValueInt(request, "pageSize", PageList.DEFAULT_PAGE_SIZE);
		PageList<Rule> ruleList = ruleService.getRule4PageList(page, pageSize);
		request.setAttribute("ruleList", ruleList);
		return "/main/rule/data";
	}
}
