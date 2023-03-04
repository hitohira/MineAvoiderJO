package com.example.mineavoider;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.mineavoider.dao.ActionLogDAO;
import com.example.mineavoider.dao.GameDAO;
import com.example.mineavoider.dao.RoundDAO;
import com.example.mineavoider.dao.UserDAO;
import com.example.mineavoider.dto.ActionLog;
import com.example.mineavoider.dto.Mauser;

@SpringBootTest
@Transactional
public class ActionLogDAOTest {
	@Autowired
	ActionLogDAO actionLogDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	GameDAO gameDAO;
	
	@Autowired
	RoundDAO roundDAO;
	
	long userNo;
	long gameNo;
	long roundNo;
	
	@BeforeEach
	void beforeEach() {
		roundDAO.deleteAll();
		gameDAO.deleteAll();
		userDAO.deleteAll();
		
		userDAO.addUser("testuser", "P@ssw0rd");
		Mauser user = userDAO.findOne("testuser");
		userNo = user.getUserNo();
		
		userDAO.addUser("testuser2", "P@ssword2");
		Mauser user2 = userDAO.findOne("testuser2");
		
		gameNo = gameDAO.addGame(user.getUserNo());	
		gameDAO.updatePlayer2No(gameNo, user2.getUserNo());
		
		roundNo = 1;
		roundDAO.addRound(gameNo, roundNo, "board");
	}
	
	@Test
	void addAuditLog() {
		String status = "reg";
		long logNo = actionLogDAO.addAuditLog(userNo, status);
		
		String trueDate = new java.sql.Date((new java.util.Date()).getTime()).toString();
		ActionLog trueActionLog = new ActionLog(logNo,userNo,status,null,null,null,null);
		
		ActionLog actionLog = actionLogDAO.findByLogNo(logNo);
		String date = actionLog.getLogDate().toString();
		actionLog.setLogDate(null);
		
		assertEquals(trueActionLog,actionLog);
		assertEquals(trueDate,date);
	}
	
	@Test
	void addGameLog() {
		String status = "beg";
		long logNo = actionLogDAO.addGameLog(userNo, status, gameNo, roundNo, 1);
		
		String trueDate = new java.sql.Date((new java.util.Date()).getTime()).toString();
		ActionLog trueActionLog = new ActionLog(logNo,userNo,status,gameNo,roundNo,1L,null);
		
		ActionLog actionLog = actionLogDAO.findByLogNo(logNo);
		String date = actionLog.getLogDate().toString();
		actionLog.setLogDate(null);
		
		assertEquals(trueActionLog,actionLog);
		assertEquals(trueDate,date);
		
	}
	
	@AfterEach
	void afterEach() {
		actionLogDAO.deleteAll();
		roundDAO.deleteAll();
		gameDAO.deleteAll();
		userDAO.deleteAll();
	}
}
