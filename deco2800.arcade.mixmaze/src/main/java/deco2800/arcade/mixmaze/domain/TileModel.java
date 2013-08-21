package deco2800.arcade.mixmaze.domain;

public class TileModel {
	private int tileX;
	private int tileY;
	
	private WallModel[] walls;
	
	private PlayerModel boxer;
	private ItemModel spawnedItem;
	
	public int getX() {
		return tileX;
	}
	
	public int getY() {
		return tileY;
	}
	
	public WallModel getWall(int direction) {
		return walls[direction];
	}
	
	public boolean hasWall(int direction) {
		return walls[direction] != null;
	}
	
	public boolean buildWall(int direction) {
		if(hasWall(direction)) {
			return false;
		}
		
		walls[direction] = new WallModel();
		return true;
	}
	
	public TileModel(int x, int y) {
		tileX = x;
		tileY = y;
		walls = new WallModel[4];
	}
}
