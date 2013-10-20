package deco2800.arcade.protocol.player;

public class FriendInvitesUpdateRequest extends PlayerNetworkObject {
	private int playerID;
	private int friendID;
	private boolean add;
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public int getFriendID() {
		return friendID;
	}
	public void setFriendID(int friendID) {
		this.friendID = friendID;
	}
	public boolean isAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
}
