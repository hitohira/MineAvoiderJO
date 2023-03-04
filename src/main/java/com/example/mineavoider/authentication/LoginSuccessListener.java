package com.example.mineavoider.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.example.mineavoider.dao.ActionLogDAO;
import com.example.mineavoider.dto.Mauser;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
	@Autowired
	ActionLogDAO actionLogDAO;
	
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		try {
			DbUserDetails dbUserDetails = (DbUserDetails) event.getAuthentication().getPrincipal();
			Mauser user = dbUserDetails.getUser();
			actionLogDAO.addAuditLog(user.getUserNo(), "in");
		}
		catch(Exception e) {
			
		}
	}
}
