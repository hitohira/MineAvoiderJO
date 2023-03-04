package com.example.mineavoider.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.mineavoider.dao.UserDAO;
import com.example.mineavoider.dto.Mauser;

@Service
public class LoginDbUserDetailsService implements UserDetailsService {
	@Autowired
	private UserDAO userDAO;
	

	@Override
	public DbUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Mauser user = null;
		try {
			user = userDAO.findOne(userId);
		
		}
		catch(Exception e) {
			throw new UsernameNotFoundException("Database error");
		}
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new DbUserDetails(user);
	}
}
