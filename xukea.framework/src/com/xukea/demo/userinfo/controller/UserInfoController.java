package com.xukea.demo.userinfo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.demo.userinfo.model.UserInfo;
import com.xukea.demo.userinfo.service.UserInfoService;
import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.framework.util.PageList;


@Controller
@RequestMapping("/demo/userinfo")
public class UserInfoController extends BaseRestSpringController<UserInfo, Long>{

	// URL基础路径
	private final String ACT_URL = "/demo/userinfo";
	
	// JSP文件夹目录
	private final String JSP_DIR = "/demo/userinfo";
	
	/**
	 * 默认数据源的事务管理器
	 */
	@Resource
	private DataSourceTransactionManager transactionManager;
	
	/**
	 * UserInfo的Service
	 */
	@Resource
	private UserInfoService userInfoService;

	
	/** 首页 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, UserInfo userInfo) {
		ModelAndView result = new ModelAndView();
		result.setViewName("redirect:" + ACT_URL + "/list");
		return result;
	}

	/** 列表 */
	@RequestMapping(value="/list")
	public String getList(HttpServletRequest request, HttpServletResponse response){
		return "redirect:" + ACT_URL + "/list/1";
	}
	
	/** 列表 */
	@RequestMapping(value="/list/{page}")
	public String getListByPage(HttpServletRequest request, HttpServletResponse response, @PathVariable int page){
		PageList<UserInfo> pageList = new PageList<UserInfo>();
		pageList.setPageNumber(page);
		pageList = userInfoService.getPageForList(pageList);
		request.setAttribute("pageList", pageList);
		return JSP_DIR + "/list";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, UserInfo userInfo) throws Exception {
		return new ModelAndView(JSP_DIR + "/new");
	}

	/** 保存新增 */
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, UserInfo userInfo) throws Exception {
		userInfoService.insert(userInfo);
		return new ModelAndView("redirect:" + ACT_URL + "/list");
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws Exception {
		UserInfo userInfo = (UserInfo)userInfoService.getById(id);
		return new ModelAndView(JSP_DIR + "/show", "userInfo", userInfo);
	}
	
	/** 编辑 */
	@RequestMapping(value="/{id}/edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws Exception {
		UserInfo userInfo = (UserInfo)userInfoService.getById(id);
		return new ModelAndView(JSP_DIR + "/edit", "userInfo", userInfo);
	}
	
	/** 保存更新 */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws Exception {
		UserInfo userInfo = (UserInfo)userInfoService.getById(id);
		bind(request, userInfo);
		userInfoService.update(userInfo);
		return new ModelAndView("redirect:" + ACT_URL + "/list");
	}
	
	/** 删除 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		userInfoService.delete(id);
		return new ModelAndView("redirect:" + ACT_URL + "/list");
	}

	/** 批量删除 */
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public ModelAndView batchDelete(HttpServletRequest request, HttpServletResponse response, @RequestParam("items") Long[] items) {
		TransactionDefinition tstDef = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus  tstStatus = transactionManager.getTransaction(tstDef);
		try{
			for(int i = 0; i < items.length; i++) {
				userInfoService.delete(items[i]);
			}
			transactionManager.commit(tstStatus);
		}catch(Exception e){
			transactionManager.rollback(tstStatus);
		}
		return new ModelAndView("redirect:" + ACT_URL + "/list");
	}
	
}

