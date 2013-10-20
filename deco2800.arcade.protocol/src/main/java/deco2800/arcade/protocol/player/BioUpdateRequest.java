package deco2800.arcade.protocol.player;

public class BioUpdateRequest extends PlayerNetworkObject {
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
