package com.example.mineavoider;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import com.example.mineavoider.dao.UserDAO;

@SpringBootTest
public class UserDAOTests {
	
	@Autowired
	UserDAO userDAO;

	@Test
	void registerUser() {
		long userNo = userDAO.addUser("testuser1", "P@ssword1");
		assertEquals(userNo,userDAO.findOne("testuser1").getUserNo());
		
		userNo = userDAO.addUser("testuser2", "P@ssword1");
		assertEquals(userNo,userDAO.findOne("testuser2").getUserNo());
	}
	
	@Test
	void registerUserFail() {
		long userNo = userDAO.addUser("testuser1", "P@ssword1");
		assertEquals(userNo,userDAO.findOne("testuser1").getUserNo());
		assertThrows(DataAccessException.class, () -> userDAO.addUser("testuser1","P@ssword2"));
	}
	
	@Test
	void deleteAll() {
		int result = userDAO.deleteAll();
		assertThat(result).isGreaterThanOrEqualTo(0);
	}
	
	
	@BeforeEach
	void beforeEach() {
		userDAO.deleteAll();
	}
	
	@AfterEach
	void afterEach() {
		userDAO.deleteAll();
	}
	
}
