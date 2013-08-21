package deco2800.arcade.mixmaze.domain;

public class PlayerModel {
	private int playerID;
	private int playerX;
	private int playerY;
	
	public int getPlayerID() {
		return playerID;
	}
	
	public int getX() {
		return playerX;
	}
	
	public int getY() {
		return playerY;
	}
	
	public void moveTo(int x, int y) {
		playerX = x;
		playerY = y;
	}
	
	public PlayerModel(int id) {
		playerID = id;
	}
}
