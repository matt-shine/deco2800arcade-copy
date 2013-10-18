package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class EmailUpdateRequest extends NetworkObject {
	private int playerID;
	private String email;
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
