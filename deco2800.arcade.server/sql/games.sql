CREATE TABLE GAMES(gameID INT NOT NULL AUTO_INCREMENT,
ID VARCHAR (30) NOT NULL,
NAME VARCHAR(30) NOT NULL,
PRICE INT NOT NULL,
DESCRIPTION VARCHAR (500) NOT NULL,
ICONPATH VARCHAR (100),
PRIMARY KEY(gameID));

TRUNCATE TABLE GAMES;
INSERT INTO GAMES values (0, 'Pong', 'Pong', 0, 'Tennis, without that annoying 3rd dimension!', '');
INSERT INTO GAMES values (1, 'deerforest', 'deerforest', 0, 'N/A', '');
INSERT INTO GAMES values (2, 'mixmaze', 'Mix Maze', 0, 'N/A', '');
INSERT INTO GAMES values (3, 'Breakout', 'Breakout', 0, 'Bounce the ball off your paddle to keep it from falling off the bottom of the screen.', '');
INSERT INTO GAMES values (4, 'burningskies', 'Burning Skies', 0, 'N/A', '');
INSERT INTO GAMES values (5, 'Checkers', 'Checkers', 0, 'It is checkers', '');
INSERT INTO GAMES values (6, 'chess', 'Chess', 0, 'N/A', '');
INSERT INTO GAMES values (7, 'Connect4', 'Connect4', 0, 'Fun old connect 4!', '');
INSERT INTO GAMES values (8, 'landInvaders', 'LandInvaders', 0, 'funny game!', '');
INSERT INTO GAMES values (9, 'pacman', 'Pac man', 0, 'An implementation of the classic arcade game Pac ', '');
INSERT INTO GAMES values (10, 'Raiden', 'Raiden', 0, 'Flight fighter', '');
INSERT INTO GAMES values (11, 'towerdefence', 'Tower Defence', 0, 'N/A', '');
INSERT INTO GAMES values (12, 'Wolfenstein 3D', 'Wolfenstein 3D', 0, 'Killin Natzis', '');
INSERT INTO GAMES values (13, 'snakeLadder', 'snakeLadder', 0, 'N/A', '');
INSERT INTO GAMES values (14, 'tictactoe', 'Tic Tac Toe', 0, 'N/A', '');
INSERT INTO GAMES values (15, 'junglejump', 'Jungle Jump', 0, 'N/A', '');
INSERT INTO GAMES values (16, 'soundboard', 'UQ Soundboard', 0, 'The epic DECO2800 Soundboard!! Enjoy the master sounds of UQ!', '');