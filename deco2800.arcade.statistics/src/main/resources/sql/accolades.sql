CREATE TABLE IF NOT EXISTS Accolades (
accolade_id INT(11) NOT NULL AUTO_INCREMENT,
game_id INT(32) NOT NULL,
name VARCHAR(16) NOT NULL,
string VARCHAR(64) NOT NULL,
unit VARCHAR(16) NOT NULL,
modifier VARCHAR(64),
tag VARRCHAR(32),
image VARCHAR(32),
popup INT(32),
popup_message VARCHAR(64),
PRIMARY KEY (accolade_id)
/** //no other tables made yet, so i don't know what the foreign key 
* //points to.
* FOREIGN KEY(game_id) REFERENCES games(game_id)
* );
**/
);
