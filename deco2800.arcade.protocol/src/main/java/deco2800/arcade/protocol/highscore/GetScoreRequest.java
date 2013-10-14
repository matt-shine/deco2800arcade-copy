package deco2800.arcade.protocol.highscore;


import deco2800.arcade.protocol.UserRequest;

public class GetScoreRequest extends UserRequest {
	public String username;
	public String game_ID;
	public int player_ID;
	
	//The type of request that is being fetched
	public int requestID;
	
	//For requests that return a number of players, how many will be returned
	public int limit;
	
	//For multi-score requests that request a specific score type
	public String type;
	
	//Communicates whether the highest score or lowest score is best.
	public boolean highestIsBest;
	
	
}
