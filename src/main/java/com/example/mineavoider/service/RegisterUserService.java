package com.example.mineavoider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mineavoider.dao.ActionLogDAO;
import com.example.mineavoider.dao.UserDAO;
import com.example.mineavoider.dto.Mauser;
import com.example.mineavoider.exception.BusinessException;
import com.example.mineavoider.exception.SystemException;
import com.example.mineavoider.form.LoginForm;

@Service
@Transactional
public class RegisterUserService {
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	ActionLogDAO actionLogDAO;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public long registerUser(LoginForm loginForm) throws BusinessException, SystemException{
		String userId = loginForm.getUserId();
		String password = loginForm.getPassword();
		
		long userNo;
		try {
			Mauser user = userDAO.findOne(userId);
			if(user != null) {
				throw new BusinessException("User Already Exists");
			}
			userNo = userDAO.addUser(userId, passwordEncoder.encode(password));
			
			actionLogDAO.addAuditLog(userNo, "reg");
			
		}
		catch(Exception e) {
			throw new SystemException("database error");
		}
		return userNo;
	}
}
