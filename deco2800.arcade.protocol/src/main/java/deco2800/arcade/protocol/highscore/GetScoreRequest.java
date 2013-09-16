package deco2800.arcade.protocol.highscore;


import deco2800.arcade.protocol.UserRequest;

public class GetScoreRequest extends UserRequest {
	public String Username;
	public String Game_ID;
	public int requestType;
}
