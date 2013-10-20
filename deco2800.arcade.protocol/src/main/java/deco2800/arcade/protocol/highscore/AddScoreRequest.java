package deco2800.arcade.protocol.highscore;


import deco2800.arcade.protocol.UserRequest;

/**
 * This protocol object is sent from client to server, containing information
 * about a new highscore that is to be added to the database.
 * 
 * @author TeamA
 */
public class AddScoreRequest extends UserRequest {
	//The username of the user that the score is being scored for
	public String username;
	
	//The gameID of the game that the score is being stored for
	public String game_ID;
	
	//The scores that are being stored. Formatted as comma separated 
	//<Type>, <Scores> values.
	public String scoreQueue;
}
