package deco2800.arcade.protocol.highscore;


import deco2800.arcade.protocol.UserRequest;

public class AddScoreRequest extends UserRequest {
	public int player_id;
	public String game_ID;
	public String scoreQueue;
}
