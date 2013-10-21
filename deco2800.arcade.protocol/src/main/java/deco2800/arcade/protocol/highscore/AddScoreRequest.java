package deco2800.arcade.protocol.highscore;


import deco2800.arcade.protocol.UserRequest;

public class AddScoreRequest extends UserRequest {
	//The username of the user that the score is being scored for
	public String username;
	
	//The gameID of the game that the score is being stored for
	public String game_ID;
	
	//The scores that are being stored. Formatted as comma separated 
	//<Type>, <Scores> values.
	public String scoreQueue;
}
