package com.example.mineavoider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandLog {
	private Long gameNo;
	private Long roundNo;
	private Long handNo;
	private String command;
	private java.sql.Date logDate;
}
