package com.example.mineavoider.authentication;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.mineavoider.dto.Mauser;

public class DbUserDetails implements UserDetails {
	//https://www.docswell.com/s/MasatoshiTada/KGVY9K-spring-security-intro#p38
	
	private final Mauser user;
	private final Collection<? extends GrantedAuthority> authorities;
	
	public DbUserDetails(Mauser user) {
		this.user = user;
		this.authorities = Collections.singleton(new SimpleGrantedAuthority(user.getStatus()));
	}
	
	public Mauser getUser() {
		return user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getUserId();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authorities;
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
	
}
