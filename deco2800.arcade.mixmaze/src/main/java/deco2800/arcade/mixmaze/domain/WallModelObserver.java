package deco2800.arcade.mixmaze.domain;

/**
 * Observer of WallModel.
 */
public interface WallModelObserver {

	/**
	 * Updates the building status of the wall.
	 * 
	 * @param isBuilt
	 *            indicates whether the wall is built or not
	 */
	void updateWall(boolean isBuilt);
}
