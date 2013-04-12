package com.xukea.common.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;


/**
 * Spring Security用户信息
 * @author Administrator
 *
 */
public class MyUserDetail extends org.springframework.security.core.userdetails.User {
	
	public MyUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }
	
	public MyUserDetail(String username, String password, boolean enabled, boolean accountNonExpired, 
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
}