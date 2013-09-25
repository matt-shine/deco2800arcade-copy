package deco2800.arcade.mixmaze.domain.view;

/**
 * ITileModel
 */
public interface ITileModel {

	static final String LOG = "TileModel: ";

	/**
	 * Returns the adjacent wall specified by <code>direction</code>.
	 *
	 * @param direction a direction specifying the adjacent wall this tile.
	 * @return the adjacent wall in the specified <code>direction</code>
	 * @throws IllegalArgumentException If <code>direction</code> is not one
	 * of <code>WEST</code>, <code>NORTH</code>, <code>EAST</code>,
	 * or <code>SOUTH</code>.
	 */
	IWallModel getWall(int direction);

	/**
	 * Returns the boxer of this tile.
	 *
	 * @return the <code>player</code>, if there is a complete box, <code>null</code> otherwise
	 */
	IPlayerModel getBoxer();

}
