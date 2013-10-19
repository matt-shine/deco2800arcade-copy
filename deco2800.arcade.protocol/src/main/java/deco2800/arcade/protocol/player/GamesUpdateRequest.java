package deco2800.arcade.protocol.player;

public class GamesUpdateRequest extends PlayerNetworkObject {
	private int playerID;
	private String gameID;
	private boolean add;
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public boolean isAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
}
