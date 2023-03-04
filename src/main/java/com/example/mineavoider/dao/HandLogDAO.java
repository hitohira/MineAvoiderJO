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

import com.example.mineavoider.dto.HandLog;
import com.example.mineavoider.exception.BusinessDataAccessException;

@Repository
public class HandLogDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public HandLog findByNo(long gameNo, long roundNo, long handNo) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo).addValue("round_no",roundNo).addValue("hand_no", handNo);
		String sql = "SELECT * FROM madata.ma_hand_log WHERE game_no = :game_no AND round_no = :round_no AND hand_no = :hand_no";
		List<HandLog> handLogs = null;
		
		handLogs = namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<HandLog>(HandLog.class));
		
		if(handLogs.size() != 1) {
			return null;
		}
		return handLogs.get(0);
	}
	
	public boolean addLog(long gameNo, long roundNo, long handNo, String command) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("game_no",gameNo).addValue("round_no",roundNo).addValue("hand_no", handNo).addValue("command", command);
		String sql = "INSERT INTO madata.ma_hand_log(game_no,round_no,hand_no,command) VALUES (:game_no, :round_no, :hand_no, :command)";
		
		int result = namedParameterJdbcTemplate.update(sql, param);
		
		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		return true;
	}
	
	public int deleteAll() throws DataAccessException {
		String sql = "DELETE FROM madata.ma_hand_log";
		int result = jdbcTemplate.update(sql);
		
		return result;
	}
}
