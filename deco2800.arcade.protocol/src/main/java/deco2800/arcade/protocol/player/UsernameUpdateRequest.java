package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class UsernameUpdateRequest extends NetworkObject {
	private int playerID;
	private String username;
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
