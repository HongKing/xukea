package com.xukea.login.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.UserBasicInfo;
import com.xukea.common.filter.security.SecurityLoginFilter;
import com.xukea.common.util.WebUtil;
import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.framework.util.ContextUtil;
import com.xukea.framework.util.crypto.MD5;
import com.xukea.main.role.model.Menu;
import com.xukea.main.role.model.Role;
import com.xukea.main.role.service.MenuService;
import com.xukea.main.role.service.RoleService;
import com.xukea.main.user.model.User;
import com.xukea.main.user.service.UserService;

/**
 * 登录Controller
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseRestSpringController{

	@Resource
	private MenuService menuService;
	
	@Resource
	private RoleService roleService; 

	@Resource
	private UserService userService;
	
	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView();
		result.setViewName("/login/login");
		return result;
	}

	@RequestMapping(value="/login")
	public String getPageLogin(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/login";
	}

	/**
	 * 登录（如果采用spring security，则登陆将由{@link com.xukea.common.filter.security.LoginFilter}来验证）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 获取request参数
    	String username = WebUtil.getValueString(request, "username", "");
    	String password = WebUtil.getValueString(request, "password", "");
    	
    	// TODO 验证码校验

    	// 基本校验request参数
    	if("".equals(username)){
    		request.setAttribute("ERROR_MSG", "用户名不能为空");
    		return "redirect:/login";
    	}
    	if("".equals(password)){
    		request.setAttribute("ERROR_MSG", "密码不能为空");
    		return "redirect:/login";
    	}
    	
    	// 查询用户信息
    	User user = userService.getUserByUserName(username); 
    	if(user==null){
    		request.setAttribute("ERROR_MSG", "用户不存在");
    		return "redirect:/login";
    	}
    	
    	// 密码校验
    	String newpswd = MD5.encrypt(password);
    	newpswd = MD5.encrypt(newpswd+username);
		if(!newpswd.equalsIgnoreCase(user.getPassword())){
    		request.setAttribute("ERROR_MSG", "密码错误");
    		return "redirect:/login";
		}
		
		//set user to UserBasicInfo
		UserBasicInfo userInfo = new UserBasicInfo();
		BeanUtils.copyProperties(user, userInfo);
		
		// get user's menu
		List<Menu> menus = menuService.getSubMenuByUID(user.getId(), "");
		userInfo.setAttribute("MENU", menus);
		
		// get user's role
		List<Role> roles = roleService.getRoleByUserId(user.getId());
		userInfo.setAttribute("ROLES", roles);
		
		/****************** 整合用户数据到Sping security ***********************/
		// 生成用户token
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
		SecurityLoginFilter loginFilter = ContextUtil.getBean(SecurityLoginFilter.class);
        loginFilter.setDetails(request, authToken);
        // 生成Spring Security的UserDetail对象中的权限等信息
        Authentication authentication = loginFilter.getAuthenticationManager().authenticate(authToken);
        // 更新Spring Security中的用户权限信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
		// save to session
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
		
		/****************** 删除Sping security中对应的UserDetails ***********************/
		SecurityContextHolder.clearContext();

		/****************** 删除SESSION中的数据 ***********************/
		HttpSession session = request.getSession();
		//删除在线状态等session销毁处理迁移至SessionListener中处理，由session.invalidate()触发执行
		session.invalidate();
		
		return new ModelAndView("redirect:/login");
	}
}