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

import com.example.mineavoider.dto.Round;
import com.example.mineavoider.exception.BusinessDataAccessException;

@Repository
public class RoundDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public Round findByGameNoAndRoundNo(long gameNo, long roundNo) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo).addValue("round_no", roundNo);
		String sql = "SELECT * FROM madata.ma_round WHERE game_no = :game_no AND round_no = :round_no";
		List<Round> rounds = null;
		
		rounds = namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Round>(Round.class));
		
		if(rounds.size() != 1) {
			return null;
		}
		return rounds.get(0);
	}
	
	public List<Round> findByGameNo(long gameNo) throws DataAccessException{
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo);
		String sql = "SELECT * FROM madata.ma_round WHERE game_no = :game_no";
		List<Round> rounds = null;
		
		rounds = namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Round>(Round.class));
		
		return rounds;
	}

	public boolean addRound(long gameNo, long roundNo, String board) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo).addValue("round_no", roundNo).addValue("board", board);
		String sql = "INSERT INTO madata.ma_round(game_no,round_no,board) VALUES (:game_no, :round_no, :board)";
		
		int result = namedParameterJdbcTemplate.update(sql,param);
		
		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		return true;
	}
	
	public boolean updateBoard(long gameNo, long roundNo, String board) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo).addValue("round_no", roundNo).addValue("board", board);
		String sql = "UPDATE madata.ma_round SET board = :board WHERE game_no = :game_no AND round_no = :round_no";
		
		int result = namedParameterJdbcTemplate.update(sql, param);

		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		return true;
	}
	
	public boolean updateCurrHandNo(long gameNo, long roundNo, long currHandNo) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo).addValue("round_no", roundNo).addValue("curr_hand_no", currHandNo);
		String sql = "UPDATE madata.ma_round SET curr_hand_no = :curr_hand_no WHERE game_no = :game_no AND round_no = :round_no";
		
		int result = namedParameterJdbcTemplate.update(sql, param);

		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		return true;		
	}
	
	public int deleteAll() throws DataAccessException {
		String sql = "DELETE FROM madata.ma_round";
		int result = jdbcTemplate.update(sql);
		
		return result;
	}
}
