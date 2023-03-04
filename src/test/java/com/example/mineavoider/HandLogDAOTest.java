package com.example.mineavoider;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.example.mineavoider.dao.GameDAO;
import com.example.mineavoider.dao.HandLogDAO;
import com.example.mineavoider.dao.RoundDAO;
import com.example.mineavoider.dao.UserDAO;
import com.example.mineavoider.dto.HandLog;
import com.example.mineavoider.dto.Mauser;

@SpringBootTest
@Transactional
public class HandLogDAOTest {
	@Autowired
	HandLogDAO handLogDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	GameDAO gameDAO;
	
	@Autowired
	RoundDAO roundDAO;
	
	long gameNo;
	long roundNo;
	
	@BeforeEach
	void BeforeEach() {
		handLogDAO.deleteAll();
		roundDAO.deleteAll();
		gameDAO.deleteAll();
		userDAO.deleteAll();
		
		userDAO.addUser("testuser", "P@ssw0rd");
		Mauser user = userDAO.findOne("testuser");
		
		userDAO.addUser("testuser2", "P@ssword2");
		Mauser user2 = userDAO.findOne("testuser2");
		
		gameNo = gameDAO.addGame(user.getUserNo());	
		gameDAO.updatePlayer2No(gameNo, user2.getUserNo());
		
		roundNo = 1;
		roundDAO.addRound(gameNo, roundNo, "board");
	}
	
	@Test
	void addLog() {
		long handNo = 0;
		boolean result = handLogDAO.addLog(gameNo, roundNo, handNo, "1BG");
		assertTrue(result);

		String trueDate = new java.sql.Date((new java.util.Date()).getTime()).toString();
		HandLog trueHandLog = new HandLog(gameNo,roundNo,handNo,"1BG",null);
		
		HandLog handLog = handLogDAO.findByNo(gameNo,roundNo,handNo);
		String date = handLog.getLogDate().toString();
		handLog.setLogDate(null);
		
		assertEquals(trueHandLog,handLog);
		assertEquals(trueDate,date);
	}
	
	@Test
	void addLog2() {
		long handNo = 0;
		boolean result = handLogDAO.addLog(gameNo, roundNo, handNo, "1BG");
		assertTrue(result);
		
		result = handLogDAO.addLog(gameNo, roundNo, handNo+1, "0BG");
		assertTrue(result);
	}
	
	@Test
	void addLog2Fail() {
		long handNo = 0;
		boolean result = handLogDAO.addLog(gameNo, roundNo, handNo, "1BG");
		assertTrue(result);
		
		assertThrows(DataAccessException.class, () ->  handLogDAO.addLog(gameNo, roundNo, handNo, "0BG"));
	}
	
	@AfterEach
	void AfterEach() {
		handLogDAO.deleteAll();
		roundDAO.deleteAll();
		gameDAO.deleteAll();
		userDAO.deleteAll();
	}
	
}
