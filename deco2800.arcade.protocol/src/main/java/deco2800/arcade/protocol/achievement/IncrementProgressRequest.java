package deco2800.arcade.protocol.achievement;

import deco2800.arcade.protocol.UserRequest;
import deco2800.arcade.model.Player;

public class IncrementProgressRequest extends UserRequest {

	public String achievementID;
	public Player player;
	
}
