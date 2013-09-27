package deco2800.arcade.mixmaze.domain.view;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static deco2800.arcade.mixmaze.domain.Direction.*;

/**
 * IMixMazeModel
 */
public interface IMixMazeModel {

	/**
	 * Enumeration for representing different
	 * states of game difficulty
	 */
	enum Difficulty {
		/**
		 * Allows movement through all obstacles
		 * All items spawn frequently
		 */
		BEGINNER,
		/**
		 * Allows movement through walls, but not boxes
		 * All items excluding TNT spawn frequently
		 */
		INTERMEDIATE,
		/**
		 * Disallows movement through both walls and boxes
		 * Bricks spawn frequently, Picks moderately, TNT rarely
		 */
		ADVANCED
	}

	/**
	 * Specifies if the game is running
	 * @return <code>true</code> if running, <code>false</code> otherwise
	 */
	boolean isRunning();

	/**
	 * Specifies if the game has ended
	 * @return <code>true</code> if ended, <code>false</code> otherwise
	 */
	boolean isEnded();

	/**
	 * Gets the current size of the board
	 * @return the size of the board
	 */
	int getBoardSize();

	/**
	 * Gets the tile at the position (x, y)
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 * @return <code>TileModel</code> at position (x, y)
	 * @throws IllegalArgumentException if the specified
	 * (x, y) position is out of range
	 */
	ITileModel getBoardTile(int x, int y);

	/**
	 * Returns the current level of game difficulty.
	 *
	 * @return <code>Difficulty</code> representing
	 * current game difficulty
	 */
	Difficulty getGameDifficulty();

	/**
	 * Gets the maximum duration of the current game
	 * @return Maximum duration in seconds
	 */
	int getGameMaxTime();

	/**
	 * Gets the time the game started
	 * @return <code>Date</code> representing game start time
	 */
	Date getGameStartTime();

	/**
	 * Gets the time the game ended
	 * @return <code>Date</code> representing game end time
	 */
	Date getGameEndTime();

	/**
	 * Gets player one model
	 * @return <code>PlayerModel</code> representing player one
	 */
	IPlayerModel getPlayer1();

	/**
	 * Gets player two model
	 * @return <code>PlayerModel</code> representing player two
	 */
	IPlayerModel getPlayer2();

	/**
	 * Gets the current number of built boxes for the
	 * specified player
	 * @param player The specified player
	 * @return Number of boxes built by specified player
	 */
	int getPlayerScore(IPlayerModel player);

	/**
	 * Starts the current game
	 */
	void startGame();

	/**
	 * Ends the current game.
	 * @return <code>PlayerModel</code> representing player with the
	 * most built boxes or <code>null</code> in the event of a tie
	 */
	IPlayerModel endGame();


	/**
	 * Gets the item at the specified (x, y) position
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 * @return <code>ItemModel</code> at the specified (x, y)
	 * position or <code>null</code> if there is none
	 * @throws IllegalArgumentException if the specified
	 * (x, y) position is out of range
	 */
	IItemModel getSpawnedItem(int x, int y);

	/**
	 * Executes a round of item spawning,
	 * will only spawn items every <code>10 / getMaxItemCount()</code>
	 * seconds
	 */
	void spawnItems();

	/**
	 * Moves the specified player in the specified direction, or
	 * turns the specified player in the specified direction
	 * if they are not currently facing that direction
	 *
	 * @param player 	the player
	 * @param direction	the direction of the movement
	 * @throws IllegalStateException if the game has not started.
	 * @throws IllegalArgumentException if <code>player</code> is null
	 * or <code>direction</code> is invalid.
	 */
	void movePlayer(IPlayerModel player, int direction);

}
