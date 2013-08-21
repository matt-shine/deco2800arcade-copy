package deco2800.arcade.mixmaze.domain;

public class TileModel {
	private int tileX;
	private int tileY;
	
	private WallModel westWall;
	private WallModel northWall;
	private WallModel eastWall;
	private WallModel southWall;
	private PlayerModel boxer;
	
	private ItemModel spawnedItem;
	
	public int getX() {
		return tileX;
	}
	
	public int getY() {
		return tileY;
	}
	
	public WallModel getWestWall() {
		return westWall;
	}
	
	public WallModel getNorthWall() {
		return northWall;
	}
	
	public WallModel getEastWall() {
		return eastWall;
	}
	
	public WallModel getSouthWall() {
		return southWall;
	}
	
	public TileModel(int x, int y) {
		tileX = x;
		tileY = y;
	}
}
