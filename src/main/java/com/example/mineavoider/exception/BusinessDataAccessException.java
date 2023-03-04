package com.example.mineavoider.exception;

import org.springframework.dao.DataAccessException;

public class BusinessDataAccessException extends DataAccessException{
	public BusinessDataAccessException(String message) {
		super(message);
	}
}
