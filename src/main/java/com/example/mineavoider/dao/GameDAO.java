package com.example.mineavoider.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.mineavoider.dto.Game;
import com.example.mineavoider.exception.BusinessDataAccessException;

@Repository
public class GameDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public Game findByGameNoAndPlayerNo(long gameNo, long playerNo) throws DataAccessException  {
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo).addValue("player1_no",playerNo).addValue("player2_no", playerNo);
		String sql = "SELECT * FROM madata.ma_game WHERE game_no = :game_no AND (player1_no = :player1_no OR player2_no = :player2_no)";
		List<Game> games = null;
		
		games = namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Game>(Game.class));
		
		if(games.size() != 1) {
			return null;
		}
		return games.get(0);
	}
	
	public List<Game> findByPlayerNo(long playerNo) throws DataAccessException  {
		SqlParameterSource param = new MapSqlParameterSource().addValue("player1_no",playerNo).addValue("player2_no", playerNo);
		String sql = "SELECT * FROM madata.ma_game WHERE player1_no = :player1_no OR player2_no = :player2_no";
		List<Game> games = null;
		
		games = namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Game>(Game.class));
		return games;
	}
	
	public long addGame(long player1No) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("player1_no",player1No);
		String sql = "INSERT INTO madata.ma_game(player1_no) VALUES (:player1_no)";
		
		int result = namedParameterJdbcTemplate.update(sql,param);
		
		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		
		String sql2 = "SELECT madata.game_seq.CURRVAL FROM dual";
		long gameNo = jdbcTemplate.queryForObject(sql2, Long.class);
		return gameNo;
	}

	public boolean updatePlayer2No(long gameNo, long player2No) throws DataAccessException{
		SqlParameterSource param = new MapSqlParameterSource().addValue("player2_no", player2No).addValue("game_no",gameNo);
		String sql = "UPDATE madata.ma_game SET player2_no = :player2_no WHERE game_no = :game_no";
		
		int result = namedParameterJdbcTemplate.update(sql, param);
		
		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		return true;
	}
	
	
	public boolean updateCurrRoundNo(long gameNo, long currRoundNo) throws DataAccessException{
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo).addValue("curr_round_no", currRoundNo);
		String sql = "UPDATE madata.ma_game SET curr_round_no = :curr_round_no WHERE game_no = :game_no";
		
		int result = namedParameterJdbcTemplate.update(sql, param);
		
		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		return true;
	}
	
	public boolean updateStatus(long gameNo, String status) throws DataAccessException {
		if(status != "W" && status != "A" && status != "I") {
			throw new BusinessDataAccessException("Invalid status");
		}
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo).addValue("status", status);
		String sql = "UPDATE madata.ma_game SET status = :status WHERE game_no = :game_no";
		
		int result = namedParameterJdbcTemplate.update(sql, param);
		
		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		return true;
	}
	
	public int deleteAll() throws DataAccessException {
		String sql = "DELETE FROM madata.ma_game";
		int result = jdbcTemplate.update(sql);
		
		return result;
	}
}
