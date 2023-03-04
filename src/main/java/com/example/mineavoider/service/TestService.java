package com.example.mineavoider.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mineavoider.dao.TestDAO;
import com.example.mineavoider.dto.Test;

@Service
public class TestService {
	@Autowired
	private TestDAO testDAO;
	
	public List<Test> getTestList(){
		List<Test> testList = null;
		try {
			testList = testDAO.findAllTest();
		}
		catch(SQLException e) {
			
		}
		finally {
			
		}
		return testList;
	}
}
