package deco2800.arcade.mixmaze.domain;

public class TileModel {
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
		return walls[direction];
	}
	
	public TileModel(int x, int y) {
		tileX = x;
		tileY = y;
		walls = new WallModel[] {
			new WallModel(),
			new WallModel(),
			new WallModel(),
			new WallModel()
		};
	}
}
