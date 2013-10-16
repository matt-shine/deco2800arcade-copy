package deco2800.arcade.mixmaze.domain;

/**
 * IMixMazeModel specifies the interface between the model and the view.
 */
public interface IMixMazeModel {

	/**
	 * Returns the current size of the board.
	 * 
	 * @return the board size
	 */
	int getBoardSize();

	/**
	 * Returns the maximum duration of the current game.
	 * 
	 * @return Maximum duration in seconds
	 */
	int getGameMaxTime();

	/**
	 * Returns the tile at the specified position.
	 * 
	 * @param x
	 *            the column number
	 * @param y
	 *            the row number
	 * @return <code>TileModel</code> at position (<code>x</code>,
	 *         <code>y</code>).
	 * @throws IllegalArgumentException
	 *             if the specified position is invalid.
	 */
	TileModel getBoardTile(int x, int y);

	/**
	 * Returns the player model of the specified <code>id</code>.
	 * 
	 * @param id
	 *            the player id
	 * @return the requested <code>PlayerModel</code>.
	 */
	PlayerModel getPlayer(int id);

	/**
	 * Moves the player specified by <code>id</code> in the given
	 * <code>direction</code>, or turns the player to that direction if not
	 * already facing it.
	 * 
	 * @param id
	 *            the player id
	 * @param direction
	 *            the movement direction
	 * @throws IllegalStateException
	 *             if the game has not started.
	 * @throws IllegalArgumentException
	 *             if the player of the specified <code>id</code> does not
	 *             exist, or <code>direction</code> is invalid.
	 */
	void movePlayer(int id, int direction);

	/**
	 * Switches the action of the player specified by <code>id</code>.
	 * 
	 * @param id
	 *            the player id
	 */
	void switchPlayerAction(int id);

	/**
	 * Uses the current action of the player specified by <code>id</code>.
	 * 
	 * @param id
	 *            the player id
	 */
	void usePlayerAction(int id);

	/**
	 * Starts the game.
	 */
	void startGame();

	/**
	 * Ends the current game.
	 * 
	 * @return the winner or <code>null</code> in the event of a tie
	 */
	PlayerModel endGame();

	/**
	 * Executes a round of item spawning.
	 * <p>
	 * This method only spawns items every <code>10 / getMaxItemCount()</code>
	 * seconds.
	 */
	void spawnItems();
}
