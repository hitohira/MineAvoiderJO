package com.example.mineavoider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mauser {
	private Long userNo;
	
	private String userId;
	
	private String password;
	
	/**
	 * common(default) or admin
	 */
	private String status;
	
	private Long totalScore;
	
}
