package deco2800.arcade.protocol.player;

public class AgeUpdateRequest extends PlayerNetworkObject {
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
