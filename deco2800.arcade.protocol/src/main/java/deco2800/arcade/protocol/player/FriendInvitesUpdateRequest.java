package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class FriendInvitesUpdateRequest extends NetworkObject {
	public int playerID;
	public int friendID;
	public boolean add;
}
