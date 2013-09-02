package deco2800.arcade.mixmaze.domain;

public class PlayerModel {
	private int playerID;
	private int playerX;
	private int playerY;
	private int playerDirection;
	private long lastMoved;
	
	public int getPlayerID() {
		return playerID;
	}
	
	public int getX() {
		return playerX;
	}
	
	public int getNextX() {
		if(!Direction.isXDirection(playerDirection)) {
			return playerX;
		}
		return Direction.isPositiveDirection(playerDirection) ? (playerX + 1) : (playerX - 1);
	}
	
	public void setX(int x) {
		playerX = x;
	}
	
	public int getY() {
		return playerY;
	}
	
	public int getNextY() {
		if(!Direction.isYDirection(playerDirection)) {
			return playerY;
		}
		return Direction.isPositiveDirection(playerDirection) ? (playerY + 1) : (playerY - 1);
	}
	
	public void setY(int y) {
		playerY = y;
	}
	
	public boolean canMove() {
		return (System.currentTimeMillis() - lastMoved) >= (0.5 * 1000);
	}
	
	public void move() {
		playerX = getNextX();
		playerY = getNextY();
		lastMoved = System.currentTimeMillis();
	}
	
	public int getDirection() {
		return playerDirection;
	}
	
	public void setDirection(int direction) {
		if(!Direction.isDirection(direction)) {
			throw Direction.NOTADIRECTION;
		}
		playerDirection = direction;
	}
	
	public PlayerModel(int id) {
		playerID = id;
	}
}
