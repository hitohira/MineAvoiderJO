package com.example.mineavoider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionLog {
	private Long logNo;
	private Long userNo;
	private String status;
	private Long gameNo;
	private Long roundNo;
	private Long changeScore;
	private java.sql.Date logDate;
}
