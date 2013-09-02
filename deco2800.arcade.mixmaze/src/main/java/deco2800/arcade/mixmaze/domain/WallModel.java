package deco2800.arcade.mixmaze.domain;

/*
 * WallModel represents a active/inactive wall on a tile.
 */
public class WallModel {
	private int direction;
	private boolean built;
	
	public int getDirection() {
		return direction;
	}
	
	public boolean isBuilt() {
		return built;
	}
	
	public WallModel(int dir) {
		if(!Direction.isDirection(dir)) {
			throw Direction.NOTADIRECTION;
		}
		direction = dir;
	}
}
