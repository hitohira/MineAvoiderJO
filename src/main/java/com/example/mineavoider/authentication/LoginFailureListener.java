package com.example.mineavoider.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

import com.example.mineavoider.dao.ActionLogDAO;
import com.example.mineavoider.dao.UserDAO;
import com.example.mineavoider.dto.Mauser;

@Component
public class LoginFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent>{
	@Autowired
	ActionLogDAO actionLogDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Override
	public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
		try {
			String userId = (String)event.getAuthentication().getPrincipal();
	
			Mauser user = userDAO.findOne(userId);
		
			if(user != null) {
				actionLogDAO.addAuditLog(user.getUserNo(), "nin");
			}
			else {
				// user not registered
			}
		}
		catch(Exception e) {
			
		}
		
	}
	
}
