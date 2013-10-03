package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.domain.view.IItemModel;
import deco2800.arcade.mixmaze.domain.view.IItemModel.ItemType;
import deco2800.arcade.mixmaze.domain.view.IMixMazeModel;
import deco2800.arcade.mixmaze.domain.view.IMixMazeModel.Difficulty;
import deco2800.arcade.mixmaze.domain.view.IPlayerModel;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.view.IMixMazeModel.Difficulty.*;

/**
 * Mix maze model represents the running game state
 */
public class MixMazeModel implements IMixMazeModel {

	final Logger logger = LoggerFactory.getLogger(MixMazeModel.class);

	/**
	 * Enumeration for representing the current
	 * state of the game
	 */
	private enum GameState {
		/**
		 * The game has not yet been played
		 */
		NOT_STARTED,
		/**
		 * The game is currently in progress
		 */
		RUNNING,
		/**
		 * The game has been played and ended
		 */
		END
	}

	/**
	 * Thrown when operations requiring the game to be started are attempted.
	 */
	/*
	private final static IllegalStateException NOT_STARTED = new IllegalStateException("The game has not started.");
	*/

	/**
	 * Thrown when operations requiring the game to not be in progress are attempted.
	 */
	/*
	private final static IllegalStateException STARTED = new IllegalStateException("The game has already started.");
	*/

	/**
	 * Thrown when an operation using coordinates out of
	 * the range of of <CODE>board</CODE> are used
	 */
	/*
	private final static IllegalArgumentException COORDS_OUT_OF_RANGE = new IllegalArgumentException("The specified coordinates(x, y) are out of range.");
	*/

	// Game data
	private GameState state;
	private int boardSize;
	private TileModel[][] board;
	private ItemModel[][] items;

	// Game settings
	private Difficulty difficulty;
	private int gameMaxTime;
	private Date gameStartTime;
	private Date gameEndTime;

	// Player data
	private PlayerModel[] player;

	// Item spawning data
	private Random spawner;
	private long lastSpawned;

	/**
	 * Constructor
	 *
	 * @param size The size of the game board
	 * @param difficulty The game difficulty
	 * @param maxSeconds The maximum time of a game session in seconds
	 * @throws IllegalArgumentException If <code>size</code> is not in range
	 * from 5 to 10, or <code>maxMinutes</code> is not in range from 2 to 15.
	 */
	public MixMazeModel(int size, Difficulty difficulty, int maxSeconds) {
		/*
		if(size < 5 || size > 10) {
			throw new IllegalArgumentException("size must be between 5 and 10.");
		}
		*/

		/*
		if(maxSeconds < 30 || maxSeconds > 900) {
			throw new IllegalArgumentException("maxSeconds must be between 30 and 900.");
		}
		*/

		// Initialize default fields
		spawner = new Random();
		state = GameState.NOT_STARTED;

		// Initialize board/items data
		boardSize = size;
		board = new TileModel[boardSize][boardSize];
		items = new ItemModel[boardSize][boardSize];
		for(int row = 0; row < boardSize; ++row) {
			for(int column = 0; column < boardSize; ++column) {
				TileModel[] adjTiles = new TileModel[4];
				int[] tileX = new int[] { (column - 1), column, (column + 1), column };
				int[] tileY = new int[] { row, (row - 1), row, (row + 1) };
				for(int tileDir = 0; tileDir < 4; ++tileDir) {
					if(checkCoordinates(tileX[tileDir], tileY[tileDir])) {
						adjTiles[tileDir] = getBoardTile(tileX[tileDir], tileY[tileDir]);
					}
				}
				board[row][column] = new TileModel(column, row, adjTiles);
			}
		}

		// Set game settings
		this.difficulty = difficulty;
		gameMaxTime = maxSeconds;

		player = new PlayerModel[2];
		// Initialize player 1
		player[0] = new PlayerModel(1);
		player[0].setX(0);
		player[0].setY(0);
		player[0].setDirection(EAST);
		onPlayerMove(player[0], 0, 0);

		// Initialize player 2
		player[1] = new PlayerModel(2);
		player[1].setX(boardSize - 1);
		player[1].setY(boardSize - 1);
		player[1].setDirection(WEST);
		onPlayerMove(player[1], (boardSize - 1), (boardSize - 1));
	}

	@Override
	public void switchAction(int id) {
		player[id - 1].switchAction();
	}

	@Override
	public void useAction(int id) {
		PlayerModel p = player[id - 1];

		p.useAction(board[p.getY()][p.getX()]);
	}

	/**
	 * Specifies if the game is running
	 * @return <CODE>true</CODE> if running, <CODE>false</CODE> otherwise
	 */
	public boolean isRunning() {
		return state == GameState.RUNNING;
	}

	/**
	 * Specifies if the game has ended
	 * @return <CODE>true</CODE> if ended, <CODE>false</CODE> otherwise
	 */
	public boolean isEnded() {
		return state == GameState.END;
	}

	/**
	 * Gets the current size of the board
	 * @return the size of the board
	 */
	public int getBoardSize() {
		return boardSize;
	}

	/**
	 * Gets the tile at the position (x, y)
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 * @return <CODE>TileModel</CODE> at position (x, y)
	 * @throws IllegalArgumentException if the specified
	 * (x, y) position is out of range
	 */
	public TileModel getBoardTile(int x, int y) {
		/*
		if (!checkCoordinates(x, y)) {
			throw COORDS_OUT_OF_RANGE;
		}
		*/
		return board[y][x];
	}

	/**
	 * Gets the current level of game difficulty.
	 * @return <CODE>MixMazeDifficulty</CODE> representing
	 * current game difficulty
	 */
	public Difficulty getGameDifficulty() {
		return difficulty;
	}

	/**
	 * Gets the maximum duration of the current game
	 * @return Maximum duration in seconds
	 */
	public int getGameMaxTime() {
		return gameMaxTime;
	}

	/**
	 * Gets the time the game started
	 * @return <CODE>Date</CODE> representing game start time
	 */
	public Date getGameStartTime() {
		return gameStartTime;
	}

	/**
	 * Gets the time the game ended
	 * @return <CODE>Date</CODE> representing game end time
	 */
	public Date getGameEndTime() {
		return gameEndTime;
	}

	/**
	 * Gets player one model
	 * @return <CODE>PlayerModel</CODE> representing player one
	 */
	public IPlayerModel getPlayer1() {
		return player[0];
	}

	/**
	 * Gets player two model
	 * @return <CODE>PlayerModel</CODE> representing player two
	 */
	public IPlayerModel getPlayer2() {
		return player[1];
	}

	/**
	 * Gets the current number of built boxes for the
	 * specified player
	 * @param player The specified player
	 * @return Number of boxes built by specified player
	 */
	public int getPlayerScore(IPlayerModel player) {
		int boxes = 0;
		for(int row = 0; row < boardSize; ++row) {
			for(int column = 0; column < boardSize; ++column) {
				TileModel tile = getBoardTile(column, row);
				if(tile.isBox() && tile.getBoxer() == player) {
					boxes++;
				}
			}
		}
		return boxes;
	}

	/**
	 * Checks the specified (x, y) position and
	 * determines if it is in range
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 * @return <CODE>true</CODE> if specified (x, y) is valid,
	 * <CODE>false</CODE> otherwise
	 */
	private boolean checkCoordinates(int x, int y) {
		return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
	}

	/**
	 * Checks if any player is at the specified position.
	 *
	 * @param x X position on the game board
	 * @param y y position on the game board
	 * @return <CODE>true</CODE> if player is present at specified (x, y),
	 * <CODE>false</CODE> otherwise
	 */
	private boolean hasPlayerAtPosition(int x, int y) {
		return (player[0].getX() == x && player[0].getY() == y)
				|| (player[1].getX() == x && player[1].getY() == y);
	}

	/**
	 * Starts the current game
	 */
	public void startGame() {
		/*
		if (state != GameState.NOT_STARTED) {
			throw STARTED;
		}
		*/
		state = GameState.RUNNING;
		gameStartTime = Calendar.getInstance().getTime();
	}

	/**
	 * Ends the current game.
	 * @return <CODE>PlayerModel</CODE> representing player with the
	 * most built boxes or <CODE>null</CODE> in the event of a tie
	 */
	public IPlayerModel endGame() {
		/*
		if (state != GameState.RUNNING) {
			throw NOT_STARTED;
		}
		*/

		// End game
		state = GameState.END;
		gameEndTime = Calendar.getInstance().getTime();
		int player1Score = getPlayerScore(player[0]);
		int player2Score = getPlayerScore(player[1]);
		if(player1Score != player2Score) {
			return (player1Score > player2Score) ? player[0] : player[1];
		} else {
			return null;
		}
	}

	/**
	 * Gets the current maximum number of items that
	 * can spawn at the same time
	 *
	 * @return Maximum number of items that can spawn at
	 * the same time
	 */
	private int getMaxItemCount() {
		int res = 0;

		if (difficulty == BEGINNER)
			res = 6;
		else if (difficulty == INTERMEDIATE)
			res = 4;
		else if (difficulty == ADVANCED)
			res = 2;
		return res;
	}

	/**
	 * Gets the current number of items spawned
	 * @return Amount of items currently spawned
	 */
	private int getItemCount() {
		int count = 0;
		for(int row = 0; row < boardSize; ++row) {
			for(int column = 0; column < boardSize; ++column) {
				if(items[row][column] != null) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Gets a random item depending on the current
	 * game difficulty.
	 * @return <CODE>BrickModel</CODE>, <CODE>PickModel</CODE>
	 * or <CODE>TNTModel</CODE>
	 */
	private ItemModel getRandomItem()
	{
		double spawnFactor = spawner.nextDouble();
		double brickFactor = 0.33;
		double pickFactor = 0.66;
		if(difficulty == INTERMEDIATE) {
			brickFactor = 0.5;
			pickFactor = 0.7;
		} else if(difficulty == ADVANCED) {
			brickFactor = 0.85;
			pickFactor = 0.95;
		}

		if(spawnFactor <= brickFactor) {
			return new BrickModel((spawner.nextInt(4) + 1));
		} else if(spawnFactor <= pickFactor) {
			return new PickModel();
		} else {
			return new TNTModel();
		}
	}

	@Override
	public void spawnItems() {
		double spawnWait = (10 * 1000) / getMaxItemCount();
		if(getItemCount() < getMaxItemCount() && (System.currentTimeMillis() - lastSpawned) >= spawnWait) {
			for(int i = getItemCount(); i < getMaxItemCount(); ++i) {
				ItemModel toSpawn = getRandomItem();
				if(toSpawn != null) {
					int x = spawner.nextInt(boardSize);
					int y = spawner.nextInt(boardSize);
					while(getSpawnedItem(x, y) != null) {
						x = spawner.nextInt(boardSize);
						y = spawner.nextInt(boardSize);
					}
					setSpawnedItem(toSpawn, x, y);
				}
			}
			lastSpawned = System.currentTimeMillis();
		}
	}

	public ItemType getSpawnedItemType(int x, int y) {
		IItemModel item = getSpawnedItem(x, y);
		return (item == null) ? ItemType.NONE : item.getType();
	}

	/**
	 * Gets the item at the specified (x, y) position
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 * @return the item at the specified
	 * position or <code>null</code> if it does not exist
	 * @throws IllegalArgumentException if the specified
	 * (x, y) position is out of range
	 */
	IItemModel getSpawnedItem(int x, int y) {
		/*
		if (!checkCoordinates(x, y)) {
			throw COORDS_OUT_OF_RANGE;
		}
		*/
		return items[y][x];
	}

	/**
	 * Set the item at the specified (x, y) position
	 *
	 * @param item The specified item, can be <code>null</code>
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 */
	private void setSpawnedItem(ItemModel item, int x, int y) {
		/*
		if(!checkCoordinates(x, y)) {
			throw COORDS_OUT_OF_RANGE;
		}
		*/
		items[y][x] = item;
		if (item == null)
			board[y][x].updateType(ItemType.NONE);
		else
			board[y][x].updateType(item.getType());
	}

	@Override
	public void movePlayer(int id, int direction) {
		/*
		if (state != GameState.RUNNING)
			throw NOT_STARTED;
		else if (!isDirection(direction))
			throw NOT_A_DIRECTION;
			*/

		PlayerModel p = player[id - 1];
		int nextX = p.getNextX();
		int nextY = p.getNextY();

		if (p.getDirection() != direction) {
			p.setDirection(direction);
		} else if (canMove(p, nextX, nextY,
				getPolarDirection(direction))) {
			p.move();
			onPlayerMove(p, nextX, nextY);
		}
	}

	/**
	 * Checks if the player <code>p</code> can move to the specified
	 * tile from direction <code>dir</code>.
	 *
	 * @param p	the player
	 * @param x	the column number
	 * @param y	the row number
	 * @param dir	the tile direction from which the player tries to enter
	 * @return <code>true</code> if the player can move to the specified
	 * tile, <code>false</code> otherwise.
	 */
	private boolean canMove(PlayerModel p, int x, int y, int dir) {
		boolean res = checkCoordinates(x, y)
				&& !hasPlayerAtPosition(x, y);

		/* No extra movement restriction on BEGINNER */

		if (difficulty == INTERMEDIATE) {
			PlayerModel boxer = getBoardTile(x, y).getBoxer();

			res = res && (boxer == null || boxer == p);
			logger.debug("canMove INTERMEDIATE: {}", res);
		} else if (difficulty == ADVANCED) {
			res = res && !getBoardTile(x, y).getWall(dir).isBuilt();
			logger.debug("canMove ADVANCED: {}", res);
		}

		return res;
	}


	/**
	 * Performs operations after a player moves onto a new tile.
	 * Currently this method only picks up the item if available.
	 * The movement to the specified tile must be validated beforehand.
	 *
	 * @param x	the column number
	 * @param y	the row number
	 */
	private void onPlayerMove(PlayerModel player, int x, int y) {
		ItemModel item = (ItemModel) getSpawnedItem(x, y);

		if (item != null && player.pickupItem(item))
			setSpawnedItem(null, x, y);
	}

}
