package deco2800.arcade.protocol.highscore;


import deco2800.arcade.protocol.UserRequest;

public class AddScoreRequest extends UserRequest{
	public String Username;
	public String Game_ID;
	public String scoreQueue;
}
