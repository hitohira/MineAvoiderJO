package com.example.mineavoider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Round {
	private Long gameNo;
	private Long roundNo;
	private String board;
	private Long currHandNo;
}
