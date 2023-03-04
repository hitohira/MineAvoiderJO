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

import com.example.mineavoider.dto.ActionLog;
import com.example.mineavoider.exception.BusinessDataAccessException;

@Repository
public class ActionLogDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public ActionLog findByLogNo(long logNo) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("log_no",logNo);
		String sql = "SELECT * FROM madata.ma_action_log WHERE log_no = :log_no";
		List<ActionLog> actionLogs = null;
		
		actionLogs = namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<ActionLog>(ActionLog.class));
		
		if(actionLogs.size() != 1) {
			return null;
		}
		return actionLogs.get(0);
	}
	
	public long addAuditLog(long userNo, String status) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("user_no",userNo).addValue("status", status);
		String sql = "INSERT INTO madata.ma_action_log(user_no,status) VALUES (:user_no, :status)";
		
		int result = namedParameterJdbcTemplate.update(sql,param);
		
		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		
		String sql2 = "SELECT madata.log_seq.CURRVAL FROM dual";
		long logNo = jdbcTemplate.queryForObject(sql2, Long.class);
		return logNo;
	}
	
	public long addGameLog(long userNo, String status, long gameNo, long roundNo, long changeScore) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("user_no",userNo).addValue("status", status).addValue("game_no", gameNo).addValue("round_no", roundNo).addValue("change_score", changeScore);
		String sql = "INSERT INTO madata.ma_action_log(user_no,status,game_no,round_no,change_score) VALUES (:user_no, :status, :game_no, :round_no, :change_score)";
		
		int result = namedParameterJdbcTemplate.update(sql,param);
		
		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		
		String sql2 = "SELECT madata.log_seq.CURRVAL FROM dual";
		long logNo = jdbcTemplate.queryForObject(sql2, Long.class);
		return logNo;
	}
	
	public int deleteAll() throws DataAccessException {
		String sql = "DELETE FROM madata.ma_action_log";
		int result = jdbcTemplate.update(sql);
		
		return result;
	}
}
