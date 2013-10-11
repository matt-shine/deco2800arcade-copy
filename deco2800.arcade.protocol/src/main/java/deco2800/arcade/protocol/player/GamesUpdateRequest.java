package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class GamesUpdateRequest extends NetworkObject {
	public int playerID;
	public String gameID;
	public boolean add;
}
