package com.xukea.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.xukea.framework.util.ContextUtil;
import com.xukea.main.role.model.Role;
import com.xukea.main.role.service.RoleService;
import com.xukea.main.user.model.User;
import com.xukea.main.user.service.UserService;

/**
 * 用户权限信息等基本数据封装
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class MyUserDetailService implements UserDetailsService {

	private RoleService roleService;
	
	private UserService userService;
	
	/**
	 * 根据用户名获取用户权限等基本信息
	 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    	registerService();
        // 获取用户对象
        User user = userService.getUserByUserName(username);
        // 获取用户权限
		List<Role> roles = roleService.getRoleByUserId(user.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for(Role role : roles){
            GrantedAuthority temp = new SimpleGrantedAuthority(role.getCode());
            grantedAuthorities.add(temp);
        }
        // 生成SpringSecurity所需的UserDetail
        MyUserDetail userDetail = new MyUserDetail(user.getUserName(), user.getPassword(), true, true, true, true, grantedAuthorities);
        return userDetail;
    }

    /**
     * 注册service，没有采用注解是因为在初始化的时候，bean内容还未加载完毕，导致启动时有空指针抛出
     */
    private void registerService(){
    	if(roleService==null){
    		roleService = ContextUtil.getBean(RoleService.class);
    	}
    	if(userService==null){
    		userService = ContextUtil.getBean(UserService.class);
    	}
    }
}