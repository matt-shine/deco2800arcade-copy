package deco2800.arcade.protocol.highscore;

import java.util.HashMap;
import deco2800.arcade.protocol.UserRequest;

public class AddScoreRequest extends UserRequest{
	public String Username;
	public String Game_ID;
	public HashMap<String, Integer> scoreMap;
}
