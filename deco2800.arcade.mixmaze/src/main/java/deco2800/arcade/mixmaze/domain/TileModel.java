package deco2800.arcade.mixmaze.domain;

public class TileModel {
	// Tile data
	private int tileX;
	private int tileY;
	private WallModel[] walls;
	
	public int getX() {
		return tileX;
	}
	
	public int getY() {
		return tileY;
	}
	
	public WallModel getWall(int direction) {
		// Check the specified direction is in range.
		if(!Direction.isDirection(direction)) {
			throw Direction.NOTADIRECTION;
		}
		return walls[direction];
	}
	
	public TileModel(int x, int y) {
		tileX = x;
		tileY = y;
		walls = new WallModel[4];
		for(int i = 0; i < 4; ++i) {
			walls[i] = new WallModel(i);
		}
	}
}
