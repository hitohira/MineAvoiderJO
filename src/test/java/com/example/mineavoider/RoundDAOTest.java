package com.example.mineavoider;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.example.mineavoider.dao.GameDAO;
import com.example.mineavoider.dao.RoundDAO;
import com.example.mineavoider.dao.UserDAO;
import com.example.mineavoider.dto.Mauser;
import com.example.mineavoider.dto.Round;

@SpringBootTest
@Transactional
public class RoundDAOTest {
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	GameDAO gameDAO;
	
	@Autowired
	RoundDAO roundDAO;
	
	long gameNo;
	
	@BeforeEach
	void beforeEach() {
		roundDAO.deleteAll();
		gameDAO.deleteAll();
		userDAO.deleteAll();
		
		userDAO.addUser("testuser", "P@ssw0rd");
		Mauser user = userDAO.findOne("testuser");
		
		userDAO.addUser("testuser2", "P@ssword2");
		Mauser user2 = userDAO.findOne("testuser2");
		
		gameNo = gameDAO.addGame(user.getUserNo());	
		gameDAO.updatePlayer2No(gameNo, user2.getUserNo());
	}
	
	@Test
	void addRound() {
		boolean result = roundDAO.addRound(gameNo, 1, "board");
		assertTrue(result);
			
		Round round = roundDAO.findByGameNoAndRoundNo(gameNo, 1);
		Round trueRound = new Round(gameNo,1L,"board",null);
		assertEquals(trueRound,round);
		
		result = roundDAO.addRound(gameNo, 2, "board2");
		assertTrue(result);
		
		result = roundDAO.updateBoard(gameNo, 2, "updated");
		assertTrue(result);
		
		result = roundDAO.updateCurrHandNo(gameNo, 2, 2);
		assertTrue(result);
		
		Round trueRound2 = new Round(gameNo,2L,"updated",2L);
		List<Round> trueRounds = new ArrayList<Round>();
		trueRounds.add(trueRound);
		trueRounds.add(trueRound2);
		
		List<Round> rounds = roundDAO.findByGameNo(gameNo);
		assertEquals(trueRounds,rounds);
	}
	
	@Test
	void addRoundFail() {
		boolean result = roundDAO.addRound(gameNo, 1, "board");
		assertTrue(result);
		assertThrows(DataAccessException.class, () -> roundDAO.addRound(gameNo,1,"board2"));
	}
	
	
	@AfterEach
	void afterEach() {
		roundDAO.deleteAll();
		gameDAO.deleteAll();
		userDAO.deleteAll();
	}
}
