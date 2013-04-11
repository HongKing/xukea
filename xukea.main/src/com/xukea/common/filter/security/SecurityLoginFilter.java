package com.xukea.common.filter.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.xukea.common.UserBasicInfo;
import com.xukea.common.util.cache.Config;
import com.xukea.framework.util.ContextUtil;
import com.xukea.main.role.model.Menu;
import com.xukea.main.role.service.MenuService;
import com.xukea.main.user.model.User;


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

	private MenuService menuService; 
	
	public SecurityLoginFilter(){
		super("/login/login");
	}
	
	/**
	 * 用户登录验证
	 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
    	registerService();

		//TODO get user info
		User user = new User();
		user.setId(0);
		user.setUserName("admin");
		user.setPassword("123456");
		user.setRealName("系统用户");
		user.setEmail("xuhz@broaderonline.com");
		
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
			e.printStackTrace();
		}
		
		// get user's role
		
		// save to session
		UserBasicInfo.saveToSession(request, userInfo);
		
        // UsernamePasswordAuthenticationToken实现 Authentication
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        this.setDetails(request, authRequest);
        
        // 运行UserDetailsService的loadUserByUsername，获取重新封装用户权限信息
        return this.getAuthenticationManager().authenticate(authRequest);

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
    }
}
