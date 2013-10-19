package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class BioUpdateRequest extends NetworkObject {
	private int playerID;
	private String bio;
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
}
