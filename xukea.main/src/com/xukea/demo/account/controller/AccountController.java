package com.xukea.demo.account.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.xukea.common.UserBasicInfo;
import com.xukea.demo.account.model.Account;
import com.xukea.demo.account.service.AccountService;
import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.framework.util.PageList;


@Controller
@RequestMapping("/demo/account")
public class AccountController extends BaseRestSpringController<Account, Long>{

	@Resource
	private AccountService accountService;
	
	// URL基础路径
	private final String ACT_URL = "/demo/account";
	
	// JSP文件夹目录
	private final String JSP_DIR = "/demo/account";

	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Account userInfo) {
		return new ModelAndView("redirect:" + ACT_URL + "/list");
	}

	/**
	 * 列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list")
	public String getList(HttpServletRequest request, HttpServletResponse response){
		return "redirect:" + ACT_URL + "/list/1";
	}
	
	/**
	 * 列表（分页）
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list/{page}")
	public String getListByPage(HttpServletRequest request, HttpServletResponse response, @PathVariable int page){
		PageList<Account> pageList = new PageList<Account>();
		pageList.setPageNumber(page);
		pageList = accountService.getPageForList(pageList);
		request.setAttribute("pageList", pageList);
		return JSP_DIR + "/list";
	}
	
}

