package com.example.mineavoider.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.mineavoider.dto.Test;

public class TestRowMapper implements RowMapper<Test> {
	@Override
	public Test mapRow(ResultSet resultSet, int i) throws SQLException{
		int id = resultSet.getInt("id");
		String data = resultSet.getString("data");
		return new Test(id,data);
	}
}
