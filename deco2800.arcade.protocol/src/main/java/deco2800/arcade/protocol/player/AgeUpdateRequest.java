package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class AgeUpdateRequest extends NetworkObject {
	private int playerID;
	private String age;
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
}
