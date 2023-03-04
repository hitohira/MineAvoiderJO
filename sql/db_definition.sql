/* madata user */


CREATE SEQUENCE user_seq MAXVALUE 100000000 NOCYCLE NOCACHE;
GRANT select ON user_seq TO mauser;

CREATE TABLE ma_user (
	user_no NUMBER DEFAULT user_seq.NEXTVAL PRIMARY KEY,
	user_id VARCHAR2(64) UNIQUE NOT NULL,
	password VARCHAR2(64) NOT NULL,
	status VARCHAR2(8) DEFAULT 'common' NOT NULL,
	total_score NUMBER DEFAULT 0 NOT NULL
);
GRANT select, insert, update, delete ON ma_user TO mauser;


CREATE TABLE ma_command_master (
	command CHAR(3) PRIMARY KEY
);
GRANT select ON ma_command_master TO mauser;

CREATE SEQUENCE game_seq MAXVALUE 1000000000000 NOCYCLE NOCACHE;
GRANT select ON game_seq TO mauser;

/* 
 * status => W = wait player2, A = active, I = inactive 
 */
CREATE TABLE ma_game (
	game_no NUMBER DEFAULT game_seq.NEXTVAL PRIMARY KEY,
	player1_no NUMBER REFERENCES ma_user(user_no),
	player2_no NUMBER REFERENCES ma_user(user_no),
	status CHAR(1) DEFAULT 'W' NOT NULL,
	curr_round_no NUMBER
);
GRANT select, insert, update, delete ON ma_game TO mauser;

CREATE TABLE ma_round (
	game_no NUMBER REFERENCES ma_game(game_no),
	round_no NUMBER,
	board VARCHAR2(36) NOT NULL,
	curr_hand_no NUMBER,
	PRIMARY KEY (game_no,round_no)
);
GRANT select, insert, update, delete ON ma_round TO mauser;



CREATE SEQUENCE log_seq MAXVALUE 1000000000000 NOCYCLE;
GRANT select ON log_seq TO mauser;


/*
 * hand_no = 0..37
 */
CREATE TABLE ma_hand_log (
	game_no NUMBER NOT NULL,
	round_no NUMBER NOT NULL,
	hand_no NUMBER NOT NULL,
	command CHAR(3) NOT NULL REFERENCES ma_command_master(command),
	log_date DATE DEFAULT sysdate NOT NULL,
	FOREIGN KEY (game_no,round_no) REFERENCES ma_round(game_no, round_no),
	PRIMARY KEY (game_no, round_no, hand_no)
);
GRANT select, insert, delete ON ma_hand_log TO mauser;


/* action log
 * status => 'reg' = register , 'in' = login, 'nin' = fail at login,  'game' = game created, 'beg' = round begin, 'end' = round end
 */
CREATE TABLE ma_action_log (
	log_no NUMBER DEFAULT log_seq.NEXTVAL PRIMARY KEY,
	user_no NUMBER NOT NULL REFERENCES ma_user(user_no),
	status VARCHAR2(4) NOT NULL,
	game_no NUMBER,
	round_no NUMBER,
	change_score NUMBER,
	log_date DATE DEFAULT sysdate NOT NULL,
	FOREIGN KEY (game_no, round_no) REFERENCES ma_round(game_no, round_no)
);
GRANT select, insert, delete ON ma_action_log TO mauser;



/* madata user */

BEGIN
	FOR player IN 0..1 LOOP
		FOR x IN 0..5 LOOP
			FOR y IN 0..5 LOOP
				INSERT INTO ma_command_master VALUES (player || x || y);
			END LOOP;
		END LOOP;
		
		INSERT INTO ma_command_master VALUES (player || 'PP');
		INSERT INTO ma_command_master VALUES (player || 'BG');
	END LOOP;

	INSERT INTO ma_command_master VALUES ('FIN');
END;
/

COMMIT;
