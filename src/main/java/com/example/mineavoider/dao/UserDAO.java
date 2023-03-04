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

import com.example.mineavoider.dto.Mauser;
import com.example.mineavoider.exception.BusinessDataAccessException;

@Repository
public class UserDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public Mauser findOne(String userId) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("user_id",userId);
		String sql = "SELECT * FROM madata.ma_user WHERE user_id = :user_id";
		List<Mauser> users = null;

		users = namedParameterJdbcTemplate.query(sql, param,  new BeanPropertyRowMapper<Mauser>(Mauser.class));
		
		if(users.size() != 1) {
			return null;
		}
		return users.get(0);
	}


	public long addUser(String userId, String password) throws DataAccessException {
		SqlParameterSource param = new MapSqlParameterSource().addValue("user_id",userId).addValue("password", password);
		String sql = "INSERT INTO madata.ma_user(user_id,password,status,total_score) VALUES (:user_id, :password, 'common', 0)";
		
		int result = namedParameterJdbcTemplate.update(sql,param);
		
		if (result != 1) {
			throw new BusinessDataAccessException("Update wrong number of record");
		}
		
		String sql2 = "SELECT madata.user_seq.CURRVAL FROM dual";
		long userNo = jdbcTemplate.queryForObject(sql2, Long.class);
		return userNo;
	}
	
	public int deleteAll() throws DataAccessException {
		String sql = "DELETE FROM madata.ma_user";
		int result = jdbcTemplate.update(sql);
		
		return result;
	}
}
