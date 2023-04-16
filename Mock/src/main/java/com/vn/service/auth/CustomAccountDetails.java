package com.vn.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vn.entity.Account;
import com.vn.entity.Role;

public class CustomAccountDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Account account;
	
	public CustomAccountDetails(Account account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Role role = account.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        authorities.add(new SimpleGrantedAuthority(role.getName()));
          
        return authorities;
	}
	
//    public boolean hasRole(String roleName) {
//        return this.account.hasRole(roleName);
//    }

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getFullName() {
		return account.getFullName();
	}
	
	public void setFullName(String fullName) {
		 this.account.setFullName(fullName);
	}
	

}
