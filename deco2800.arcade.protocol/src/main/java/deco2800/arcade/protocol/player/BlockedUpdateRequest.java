package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class BlockedUpdateRequest  extends NetworkObject {
	public int playerID;
	public int playerID2;
	public boolean add;
}
