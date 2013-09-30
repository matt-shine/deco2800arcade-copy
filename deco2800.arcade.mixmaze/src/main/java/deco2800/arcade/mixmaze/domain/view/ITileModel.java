package deco2800.arcade.mixmaze.domain.view;

/**
 * ITileModel
 */
public interface ITileModel {

	static final String LOG = "TileModel: ";

	/**
	 * Checks if the wall on the specified <code>direction</code> is built.
	 *
	 * @param direction	the direction
	 * @return <code>true</code> if the wall is built, <code>false</code>
	 * otherwise.
	 */
	boolean isWallBuilt(int direction);

	/**
	 * Returns the id of the boxer.
	 *
	 * @return 0 if the box is not built, otherwise the boxer id.
	 */
	int getBoxerId();

	/**
	 * Return the column number of this tile on game board.
	 * Origin is at the top left corner.
	 *
	 * @return the column number
	 */
	int getX();

	/**
	 * Return the row number of this tile on game board.
	 * Origin is at the top left corner.
	 *
	 * @return the row number
	 */
	int getY();

}
