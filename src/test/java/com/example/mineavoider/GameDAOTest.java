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
import com.example.mineavoider.dto.Game;
import com.example.mineavoider.dto.Mauser;

@SpringBootTest
@Transactional
public class GameDAOTest {
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	GameDAO gameDAO;
	
	@Autowired
	RoundDAO roundDAO;
	
	@BeforeEach
	void beforeEach() {
		roundDAO.deleteAll();
		gameDAO.deleteAll();
		userDAO.deleteAll();
		userDAO.addUser("testuser", "P@ssw0rd");
		userDAO.addUser("testuser2", "Second@2");
	}
	
	@Test
	void addGame() {
		Mauser user = userDAO.findOne("testuser");
		long gameNo = gameDAO.addGame(user.getUserNo());

		Game game = gameDAO.findByGameNoAndPlayerNo(gameNo,user.getUserNo());
		
		Game trueGame = new Game(gameNo,user.getUserNo(),null,"W",null);
		assertEquals(trueGame,game);
	}
	
	@Test
	void addGame2() {
		Mauser user = userDAO.findOne("testuser");
		long gameNo = gameDAO.addGame(user.getUserNo());

		Game game = gameDAO.findByGameNoAndPlayerNo(gameNo,user.getUserNo());
		
		Game trueGame = new Game(gameNo,user.getUserNo(),null,"W",null);
		assertEquals(trueGame,game);
		
		long gameNo2 = gameDAO.addGame(user.getUserNo());

		Game game2 = gameDAO.findByGameNoAndPlayerNo(gameNo2,user.getUserNo());
		
		Game trueGame2 = new Game(gameNo2,user.getUserNo(),null,"W",null);
		assertEquals(trueGame2,game2);
		
		List<Game> games = gameDAO.findByPlayerNo(user.getUserNo());
		List<Game> trueGames = new ArrayList<Game>();
		trueGames.add(trueGame);
		trueGames.add(trueGame2);
		
		assertEquals(trueGames, games);
	}
	
	@Test
	void update() {
		Mauser user = userDAO.findOne("testuser");
		Mauser user2 = userDAO.findOne("testuser2");
		
		long gameNo = gameDAO.addGame(user.getUserNo());
		
		boolean result;
		result = gameDAO.updatePlayer2No(gameNo, user2.getUserNo());
		assertTrue(result);
		
		result = gameDAO.updateCurrRoundNo(gameNo, 1);
		assertTrue(result);
		
		result = gameDAO.updateStatus(gameNo, "A");
		assertTrue(result);
		
		Game game = gameDAO.findByGameNoAndPlayerNo(gameNo, user.getUserNo());
		Game trueGame = new Game(gameNo,user.getUserNo(), user2.getUserNo(), "A", 1L);
		assertEquals(trueGame,game);
	}
	
	@Test
	void updateStatus() {
		Mauser user = userDAO.findOne("testuser");
		
		long gameNo = gameDAO.addGame(user.getUserNo());
		
		boolean result;
		result = gameDAO.updateStatus(gameNo, "A");
		assertTrue(result);
		
		result = gameDAO.updateStatus(gameNo, "I");
		assertTrue(result);
		
		assertThrows(DataAccessException.class, () ->  gameDAO.updateStatus(gameNo, "K"));
		assertThrows(DataAccessException.class, () ->  gameDAO.updateStatus(gameNo, "AB"));		
		
	}
	
	
	@AfterEach
	void afterEach() {
		gameDAO.deleteAll();
		userDAO.deleteAll();
	}
	
}
