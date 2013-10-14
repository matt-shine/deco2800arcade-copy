CREATE TABLE Player_Accolades (
accolade_id int(11) NOT NULL,
player_id int(11) NOT NULL,
score int(32) NOT NULL,
PRIMARY KEY (accolade_id, player_id),
FOREIGN KEY (accolade_id) REFERENCE Game_Accolades(accolade_id)
/**, FOREIGN KEY (player_id) REFERENCE Players(accolade_id)
**/ );
