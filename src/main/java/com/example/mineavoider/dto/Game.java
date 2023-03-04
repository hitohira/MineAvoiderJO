package com.example.mineavoider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
	private Long gameNo;
	private Long player1No;
	private Long player2No;
	private String status;
	private Long currRoundNo;
}
