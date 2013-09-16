package deco2800.arcade.protocol.highscore;


import deco2800.arcade.protocol.UserRequest;

public class GetScoreResponse extends UserRequest {
	public String Username;
	public String Game_ID;
	public String data;
	public int columnNumbers;
}
