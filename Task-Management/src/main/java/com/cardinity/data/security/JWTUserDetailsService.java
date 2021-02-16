package com.cardinity.data.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cardinity.data.model.User;
import com.cardinity.project.exception.CustomException;
import com.cardinity.user.service.UserService;

@Service
public class JWTUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	  
	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = userService.getUserByName(username);
	     if (user == null) throw new CustomException("User doesn't exist!");
	     Collection<GrantedAuthority> authorities = new ArrayList<>();
	     user.getRoles().forEach(role -> { 
	    	 authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
	     }); 
	     return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities); 
	 }
}
