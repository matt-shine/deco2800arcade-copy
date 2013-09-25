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
	enum MixMazeDifficulty {
		/**
		 * Allows movement through all obstacles
		 * All items spawn frequently
		 */
		Beginner,
		/**
		 * Allows movement through walls, but not boxes
		 * All items excluding TNT spawn frequently
		 */
		Intermediate,
		/**
		 * Disallows movement through both walls and boxes
		 * Bricks spawn frequently, Picks moderately, TNT rarely
		 */
		Advanced
	}

	/**
	 * Specifies if the game is running
	 * @return <CODE>true</CODE> if running, <CODE>false</CODE> otherwise
	 */
	boolean isRunning();

	/**
	 * Specifies if the game has ended
	 * @return <CODE>true</CODE> if ended, <CODE>false</CODE> otherwise
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
	 * @return <CODE>TileModel</CODE> at position (x, y)
	 * @throws IllegalArgumentException if the specified
	 * (x, y) position is out of range
	 */
	ITileModel getBoardTile(int x, int y);

	/**
	 * Gets the current level of game difficulty.
	 * @return <CODE>MixMazeDifficulty</CODE> representing
	 * current game difficulty
	 */
	MixMazeDifficulty getGameDifficulty();

	/**
	 * Gets the maximum duration of the current game
	 * @return Maximum duration in seconds
	 */
	int getGameMaxTime();

	/**
	 * Gets the time the game started
	 * @return <CODE>Date</CODE> representing game start time
	 */
	Date getGameStartTime();

	/**
	 * Gets the time the game ended
	 * @return <CODE>Date</CODE> representing game end time
	 */
	Date getGameEndTime();

	/**
	 * Gets player one model
	 * @return <CODE>PlayerModel</CODE> representing player one
	 */
	IPlayerModel getPlayer1();

	/**
	 * Gets player two model
	 * @return <CODE>PlayerModel</CODE> representing player two
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
	 * @return <CODE>PlayerModel</CODE> representing player with the
	 * most built boxes or <CODE>null</CODE> in the event of a tie
	 */
	IPlayerModel endGame();

	/**
	 * Executes a round of item spawning,
	 * will only spawn items every <CODE>10 / getMaxItemCount()</CODE>
	 * seconds
	 */
	void spawnItems();

	/**
	 * Gets the item at the specified (x, y) position
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 * @return <CODE>ItemModel</CODE> at the specified (x, y)
	 * position or <CODE>null</CODE> if there is none
	 * @throws IllegalArgumentException if the specified
	 * (x, y) position is out of range
	 */
	IItemModel getSpawnedItem(int x, int y);

	/**
	 * Moves the specified player in the specified direction, or
	 * will turn the specified player in the specified direction
	 * if they are not currently facing that direction
	 * @param player The specified player
	 * @param direction The specified direction
	 * of movement
	 * @throws IllegalStateException if the game has not been started,
	 * the specified player is <CODE>null</CODE> or the specified
	 * direction is not a direction
	 */
	void movePlayer(IPlayerModel player, int direction);
}
