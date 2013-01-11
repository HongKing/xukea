package com.xukea.login.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.UserBasicInfo;
import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.main.role.model.Menu;
import com.xukea.main.role.service.MenuService;
import com.xukea.main.user.model.User;


/**
 * 登录Controller
 * @author 石头
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseRestSpringController<User, Long>{

	@Resource
	private MenuService menuService; 
	
	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, User obj) {
		ModelAndView result = new ModelAndView();
		result.setViewName("/login/login");
		return result;
	}

	@RequestMapping(value="/login")
	public String getPageLogin(HttpServletRequest request, HttpServletResponse response) {
		return "/login/login";
	}

	/**
	 * 登录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserBasicInfo userInfo = new UserBasicInfo();
		
		//TODO get user info
		User user = new User();
		user.setId(0);
		user.setUserName("system");
		user.setRealName("系统用户");
		user.setEmail("xuhz@broaderonline.com");
		
		//set user to UserBasicInfo
		userInfo.setUserId(user.getId());
		userInfo.setUserName(user.getUserName());
		userInfo.setRealName(user.getRealName());
		userInfo.setEmail(user.getEmail());
		
		//get menu
		List<Menu> menus = menuService.getSubMenuByUID(user.getId(), "");
		
		userInfo.setAttribute("MENU", menus);
		UserBasicInfo.saveToSession(request, userInfo);
		
		return "redirect:/index";
	}

	/**
	 * 退出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		//删除在线状态等session销毁处理迁移至SessionListener中处理，由session.invalidate()触发执行
		session.invalidate();
		return new ModelAndView("redirect:/login");
	}
}
 

	 

	

