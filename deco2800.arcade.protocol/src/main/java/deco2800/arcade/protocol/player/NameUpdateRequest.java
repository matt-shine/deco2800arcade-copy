package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class NameUpdateRequest extends NetworkObject {
	private int playerID;
	private String name;
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
