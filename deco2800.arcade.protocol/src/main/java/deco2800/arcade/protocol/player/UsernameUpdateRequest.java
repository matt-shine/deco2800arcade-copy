package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class UsernameUpdateRequest extends NetworkObject {
	public int playerID;
	public String username;
}
