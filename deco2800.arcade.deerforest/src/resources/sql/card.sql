CREATE TABLE CARD
(
PlayerID int NOT NULL,
DeckID int NOT NULL,
CardID int NOT NULL,
--Add Image Section
Type varchar(255),
Name varchar(255),
Attack int,
Health int,
--Keys
PRIMARY KEY (CardID),
FOREIGN KEY (DeckID) REFERENCE DECK(DeckID),
FOREIGN KEY (PlayerID) REFERENCE DEER_PLAYER(PlayerID) 
);