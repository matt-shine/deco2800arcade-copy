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
	
	public boolean isBox() {
		return getWall(Direction.WEST).isBuilt() && 
				getWall(Direction.NORTH).isBuilt() && 
				getWall(Direction.EAST).isBuilt() && 
				getWall(Direction.SOUTH).isBuilt();
	}
	
	public TileModel(int x, int y) {
		tileX = x;
		tileY = y;
		walls = new WallModel[4];
		for(int direction = 0; direction < 4; ++direction) {
			walls[direction] = new WallModel(direction);
		}
	}
}
