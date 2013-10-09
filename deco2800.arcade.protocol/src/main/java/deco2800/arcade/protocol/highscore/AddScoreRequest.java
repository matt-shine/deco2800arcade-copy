package deco2800.arcade.protocol.highscore;


import deco2800.arcade.protocol.UserRequest;

public class AddScoreRequest extends UserRequest {
	public String username;
	public String game_ID;
	public String scoreQueue;
}
