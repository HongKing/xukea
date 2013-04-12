package com.xukea.common.filter.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.xukea.common.UserBasicInfo;
import com.xukea.common.util.WebUtil;
import com.xukea.common.util.cache.Config;
import com.xukea.framework.util.ContextUtil;
import com.xukea.framework.util.crypto.MD5;
import com.xukea.main.role.model.Menu;
import com.xukea.main.role.model.Role;
import com.xukea.main.role.service.MenuService;
import com.xukea.main.role.service.RoleService;
import com.xukea.main.user.model.User;
import com.xukea.main.user.service.UserService;


/**
 * Spring Security 登录验证<br>
 * 本类只在attemptAuthentication中做用户信息验证<br>
 * 登录成功的后续处理见{@link com.xukea.common.security.authentication.LoginSuccessHandler}<br>
 * 登录失败的后续处理见{@link com.xukea.common.security.authentication.LoginFailureHandler}<br>
 * 
 * @author Administrator
 *
 */
public class SecurityLoginFilter extends AbstractAuthenticationProcessingFilter { //UsernamePasswordAuthenticationFilter
	private final Logger log = Logger.getLogger(getClass());
	
	private MenuService menuService;
	private RoleService roleService; 
	private UserService userService;
	
	public SecurityLoginFilter(){
		super("/login/login");
	}
	
	/**
	 * 用户登录验证
	 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("");
        }
    	registerService();

    	// 验证码校验
    	if(!validCaptchaValue(request)){
    		throw new AuthenticationServiceException("验证码错误");
    	}
    	
    	// 用户信息校验
    	User user = validUserInfo(request);
		
		//set user to UserBasicInfo
		UserBasicInfo userInfo = new UserBasicInfo();
		userInfo.setUserId(user.getId());
		userInfo.setUserName(user.getUserName());
		userInfo.setRealName(user.getRealName());
		userInfo.setEmail(user.getEmail());
		
		// get user's menu
		List<Menu> menus = menuService.getSubMenuByUID(user.getId(), "");
		try {
			userInfo.setAttribute("MENU", menus);
		} catch (Exception e) {
			log.debug("set user's menu to userinfo: ", e);
		}
		
		// get user's role
		List<Role> roles = roleService.getRoleByUserId(user.getId());
		try {
			userInfo.setAttribute("ROLES", roles);
		} catch (Exception e) {
			log.debug("set user's role to userinfo: ", e);
		}
		
		// save to session
		UserBasicInfo.saveToSession(request, userInfo);
		
        // UsernamePasswordAuthenticationToken实现 Authentication
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        this.setDetails(request, authRequest);
        // 运行UserDetailsService的loadUserByUsername，获取重新封装用户权限信息
        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
    /**
     * 验证码校验
     * @param request
     * @return
     */
    private boolean validCaptchaValue(HttpServletRequest request){
    	// TODO 验证码校验
    	return true;
    }
    
    /**
     * 用户信息校验
     * @param request
     * @return
     * @throws AuthenticationException
     */
    private User validUserInfo(HttpServletRequest request) throws AuthenticationException{
    	// 获取request参数
    	String username = WebUtil.getValueString(request, "username", "");
    	String password = WebUtil.getValueString(request, "password", "");
    	
    	// 基本校验request参数
    	if("".equals(username)){
    		throw new AuthenticationServiceException("用户名不能为空");
    	}
    	if("".equals(password)){
    		throw new AuthenticationServiceException("密码不能为空");
    	}
    	
    	// 查询用户信息
    	User user = userService.getUserByUserName(username); 
    	if(user==null){
    		throw new AuthenticationServiceException("用户不存在");
    	}
    	
    	// 密码校验
    	String newpswd = MD5.encrypt(password);
    	newpswd = MD5.encrypt(newpswd+username);
		if(!newpswd.equalsIgnoreCase(user.getPassword())){
    		throw new AuthenticationServiceException("密码错误");
		}
		
		return user;
    }
    
    /**
     * 设置UserDetails
     * @param request
     * @param authRequest
     */
    public void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
    	authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 获取决策管理器
     */
    public AuthenticationManager getAuthenticationManager() {
        return super.getAuthenticationManager();
    }

    /**
     * 登录拦截采用的URL
     */
    public String getFilterProcessesUrl() {
        return Config.getInstance().getString("security.url.login.action");
    }
    
    /**
     * 注册service，没有采用注解是因为在初始化的时候，bean内容还未加载完毕，导致启动时有空指针抛出
     */
    private void registerService(){
    	if(menuService==null){
    		menuService = ContextUtil.getBean(MenuService.class);
    	}
    	if(roleService==null){
    		roleService = ContextUtil.getBean(RoleService.class);
    	}
    	if(userService==null){
    		userService = ContextUtil.getBean(UserService.class);
    	}
    }
}
