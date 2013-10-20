package deco2800.arcade.protocol.player;

public class BlockedUpdateRequest  extends PlayerNetworkObject {
	private int playerID;
	private int playerID2;
	private boolean add;
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public int getPlayerID2() {
		return playerID2;
	}
	public void setPlayerID2(int playerID2) {
		this.playerID2 = playerID2;
	}
	public boolean isAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
}
