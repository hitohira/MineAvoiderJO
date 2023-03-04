package com.example.mineavoider.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.mineavoider.dto.Test;
import com.example.mineavoider.mapper.TestRowMapper;

@Repository
public class TestDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Test> findAllTest() throws SQLException {
		String sql = "SELECT id, data FROM madata.test";
		return jdbcTemplate.query(sql, new TestRowMapper());
	}
}
