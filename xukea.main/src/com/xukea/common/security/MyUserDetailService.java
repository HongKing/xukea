package com.xukea.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户权限信息等基本数据封装
 * @author Administrator
 *
 */
public class MyUserDetailService implements UserDetailsService {
	
	/**
	 * 根据用户名获取用户权限等基本信息
	 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority grantedAuthority = null;

//        if ("admin".equals(arg0)) {
            grantedAuthority = new GrantedAuthorityImpl("ROLE_ADMIN");
//        }
//        else if ("manager".equals(arg0))
//        {
//            grantedAuthority = new GrantedAuthorityImpl("ROLE_MANAGER");
//        }
//        else
//        {
//            grantedAuthority = new GrantedAuthorityImpl("ROLE_USER");
//        }
        grantedAuthorities.add(grantedAuthority);

        User user = new User(username, "123456", true, true, true, true, grantedAuthorities);

        return user;
    }

}