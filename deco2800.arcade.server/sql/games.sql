CREATE TABLE GAMES(gameID INT NOT NULL AUTO_INCREMENT,
ID VARCHAR (30) NOT NULL,
NAME VARCHAR(30) NOT NULL,
PRICE INT NOT NULL,
DESCRPTION VARCHAR (30) NOT NULL,
ICONPATH VARCHAR (30),
PRIMARY KEY(gameID));

INSERT INTO GAMES values (default, 'pong', 'Pong', 0, 'Tennis, without that annoying 3rd dimension!');
INSERT INTO GAMES values (default, 'deerforest', 'deerforest', 0, 'N/A');
INSERT INTO GAMES values (default, 'mixmaze', 'Mix Maze', 0, 'N/A');
INSERT INTO GAMES values (default, 'Breakout', 'Breakout', 0, 'N/A');
INSERT INTO GAMES values (default, 'burningskies', 'Burning Skies', 0, 'N/A');
INSERT INTO GAMES values (default, 'checkers', 'Checkers', 0, 'It is checkers');
INSERT INTO GAMES values (default, 'chess', 'Chess', 0, 'N/A');
INSERT INTO GAMES values (default, 'connect4', 'Connect4', 0, 'Fun old connect 4!');
INSERT INTO GAMES values (default, 'LandInvaders', 'LandInvaders', 0, 'funny game!');
INSERT INTO GAMES values (default, 'pacman', 'Pac man', 0, 'An implementation of the classic arcade game Pac ');
INSERT INTO GAMES values (default, 'Raiden', 'Raiden', 0, 'Flight fighter');
INSERT INTO GAMES values (default, 'towerdefence', 'Tower Defence', 0, 'N/A');