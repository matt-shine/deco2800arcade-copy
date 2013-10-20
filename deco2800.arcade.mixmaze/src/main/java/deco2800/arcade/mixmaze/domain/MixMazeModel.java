package deco2800.arcade.mixmaze.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.arcade.mixmaze.Achievements;
import deco2800.arcade.mixmaze.domain.ItemModel.Type;
import static deco2800.arcade.mixmaze.domain.Direction.*;

/**
 * Mix maze model represents a full game session.
 */
public class MixMazeModel implements IMixMazeModel {

	final Logger logger = LoggerFactory.getLogger(MixMazeModel.class);

	/**
	 * Game difficulty specifies certain rules of the game play.
	 * <p>
	 * <table border=1>
	 * <tr>
	 * <th>Level</th>
	 * <th>Move through walls</th>
	 * <th>Move through boxes</th>
	 * <th>Picks spawn</th>
	 * <th>TNTs spawn</th>
	 * </tr>
	 * <tr>
	 * <td>Beginner</td>
	 * <td>Yes</td>
	 * <td>Yes</td>
	 * <td>Frequently</td>
	 * <td>Frequently</td>
	 * </tr>
	 * <tr>
	 * <td>Intermediate</td>
	 * <td>Yes</td>
	 * <td>Yes only through your own</td>
	 * <td>Frequently</td>
	 * <td>Rarely</td>
	 * </tr>
	 * <tr>
	 * <td>Advanced</td>
	 * <td>No</td>
	 * <td>No</td>
	 * <td>Moderately</td>
	 * <td>Rarely</td>
	 * </tr>
	 * </table>
	 */
	public enum Difficulty {
		BEGINNER (5),
		INTERMEDIATE (3),
		ADVANCED (2);

		final int maxItems;

		Difficulty(int maxItems) {
			this.maxItems = maxItems;
		}
	}

	/**
	 * Game state
	 */
	private enum GameState {

		/** The game has not yet been played */
		NOT_STARTED,

		/** The game is currently in progress */
		RUNNING,

		/** The game has been played and ended */
		END
	}

	private final class TileWatcher implements TileModelObserver {
		private TileModel watching;
		private int watchX;
		private int watchY;
		
		public TileWatcher(TileModel tile) {
			watching = tile;
			watchX = tile.getX();
			watchY = tile.getY();
		}
		
		@Override
		public void updateBoxer(int id) {
			if(id != 0 && isItemSpawned(watchX, watchY)) {
				items[watchY][watchX] = null;
				watching.updateType(Type.NONE);
			}
		}

		@Override
		public void updateType(Type type) {
		}
	}
	
	/**
	 * Thrown when attempting to operate a game that is not running.
	 */
	private final static IllegalStateException NOT_STARTED = new IllegalStateException(
			"The game has not started.");

	/**
	 * Thrown when attempting to start a game that is currently running.
	 */
	private final static IllegalStateException STARTED = new IllegalStateException(
			"The game has already started.");

	/**
	 * Thrown when using coordinates out of the <code>board</code> range.
	 */
	private final static IllegalArgumentException COORDS_OUT_OF_RANGE = new IllegalArgumentException(
			"The specified coordinates(x, y) are out of range.");

	/* Game data */

	/** Game state */
	private GameState state;

	/** Board size */
	private int boardSize;

	/** Tiles on the game board */
	private TileModel[][] board;
	private TileWatcher[][] boardWatchers;
	
	/** Items on the game board (one per tile) */
	private ItemModel[][] items;

	/** Players in this game */
	private PlayerModel[] player;

	/* Game setting */

	/** Game difficulty */
	private Difficulty difficulty;

	/** Time limit of this game */
	private int gameMaxTime;

	/** Start time of this game */
	private Date gameStartTime;

	/** End time of this game */
	private Date gameEndTime;

	/** Item spawner */
	private Random spawner;

	/*
	 * TODO: doc
	 */
	private long lastSpawned;

	/**
	 * Constructor
	 * 
	 * @param size
	 *            the size of the game board
	 * @param difficulty
	 *            the game difficulty
	 * @param maxSeconds
	 *            the time limit of this game session
	 * @throws IllegalArgumentException
	 *             If <code>size</code> is not in range from 5 to 10, or
	 *             <code>maxSeconds</code> is not in range from 30 to 900.
	 */
	public MixMazeModel(int size, Difficulty difficulty, int maxSeconds) {
		if (size < 5 || size > 10) {
			throw new IllegalArgumentException("size must be between 5 and 10.");
		}
		if (maxSeconds < 30 || maxSeconds > 900) {
			throw new IllegalArgumentException(
					"maxSeconds must be between 30 and 900.");
		}

		// Initialize default fields
		spawner = new Random();
		state = GameState.NOT_STARTED;

		this.boardSize = size;
		this.difficulty = difficulty;
		this.gameMaxTime = maxSeconds;

		initBoard();
		initPlayers();
	}

	/**
	 * Initialises players.
	 */
	private void initPlayers() {
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

	/**
	 * Initialises the game board.
	 */
	private void initBoard() {
		board = new TileModel[boardSize][boardSize];
		boardWatchers = new TileWatcher[boardSize][boardSize];
		items = new ItemModel[boardSize][boardSize];
		/* XXX: too many levels of nested loops */
		for (int y = 0; y < boardSize; ++y) {
			for (int x = 0; x < boardSize; ++x) {
				TileModel[] adjTiles = new TileModel[4];
				int[] tileX = new int[] { (x - 1), x, (x + 1), x };
				int[] tileY = new int[] { y, (y - 1), y, (y + 1) };
				for (int tileDir = 0; tileDir < 4; ++tileDir) {
					if (checkCoordinates(tileX[tileDir], tileY[tileDir])) {
						adjTiles[tileDir] = getBoardTile(tileX[tileDir],
								tileY[tileDir]);
					}
				}
				board[y][x] = new TileModel(x, y, adjTiles);
				boardWatchers[y][x] = new TileWatcher(board[y][x]);
				board[y][x].addObserver(boardWatchers[y][x]);
			}
		}
	}

	@Override
	public int getBoardSize() {
		return boardSize;
	}

	@Override
	public int getGameMaxTime() {
		return gameMaxTime;
	}

	@Override
	public TileModel getBoardTile(int x, int y) {
		if (!checkCoordinates(x, y)) {
			throw COORDS_OUT_OF_RANGE;
		}
		return board[y][x];
	}

	@Override
	public PlayerModel getPlayer(int id) {
		return player[id - 1];
	}

	@Override
	public void switchPlayerAction(int id) {
		player[id - 1].switchAction();
	}

	@Override
	public PlayerModel.Action usePlayerAction(int id) {
		PlayerModel p = player[id - 1];
		PlayerModel.Action act = p.getAction();
		boolean res;

		res = p.useAction(board[p.getY()][p.getX()]);
		return res ? act : null; 
	}

	@Override
	public void movePlayer(int id, int direction) {
		if (state != GameState.RUNNING) {
			throw NOT_STARTED;
		} else if (!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}

		PlayerModel p = player[id - 1];
		int nextX = p.getNextX();
		int nextY = p.getNextY();

		if (p.getDirection() != direction) {
			p.setDirection(direction);
		} else if (canMove(p, nextX, nextY, getPolarDirection(direction))) {
			p.move();
			onPlayerMove(p, nextX, nextY);
		}
	}

	@Override
	public void startGame() {
		if (state != GameState.NOT_STARTED) {
			throw STARTED;
		}
		state = GameState.RUNNING;
		gameStartTime = Calendar.getInstance().getTime();
	}

	@Override
	public PlayerModel endGame() {
		if (state != GameState.RUNNING) {
			throw NOT_STARTED;
		}
		state = GameState.END;
		gameEndTime = Calendar.getInstance().getTime();
		int player1Score = getPlayerScore(player[0]);
		int player2Score = getPlayerScore(player[1]);
		Achievements.getInstance().incrementAchievement(
				Achievements.AchievementType.Playa);

		if (player1Score != player2Score) {
			return (player1Score > player2Score) ? player[0] : player[1];
		} else {
			return null;
		}
	}

	@Override
	public void spawnItems() {
		double spawnWait = (10 * 1000) / Math.max(10, getMaxItemCount());
		if (getItemCount() < getMaxItemCount()
				&& (System.currentTimeMillis() - lastSpawned) >= spawnWait) {
			for (int i = getItemCount(); i < getMaxItemCount(); ++i) {
				ItemModel toSpawn = getRandomItem();
				if (toSpawn != null) {
					int x = spawner.nextInt(boardSize);
					int y = spawner.nextInt(boardSize);
					while (getSpawnedItem(x, y) != null
							|| getBoardTile(x, y).isBoxBuilt()
							|| isPlayerAtPosition(x, y)) {
						x = spawner.nextInt(boardSize);
						y = spawner.nextInt(boardSize);
					}
					setSpawnedItem(toSpawn, x, y);
				}
			}
			lastSpawned = System.currentTimeMillis();
		}
	}

	/**
	 * Specifies if the game is running.
	 * 
	 * @return <code>true</code> if running, <code>false</code> otherwise
	 */
	boolean isRunning() {
		return state == GameState.RUNNING;
	}

	/**
	 * Specifies if the game has ended.
	 * 
	 * @return <code>true</code> if ended, <code>false</code> otherwise
	 */
	boolean isEnded() {
		return state == GameState.END;
	}

	/**
	 * Gets the current level of game difficulty.
	 * 
	 * @return the current game difficulty
	 */
	Difficulty getGameDifficulty() {
		return difficulty;
	}

	/**
	 * Gets the time the game started.
	 * 
	 * @return the start time
	 */
	Date getGameStartTime() {
		return gameStartTime;
	}

	/**
	 * Gets the time the game ended.
	 * 
	 * @return the end time
	 */
	Date getGameEndTime() {
		return gameEndTime;
	}

	/**
	 * Gets the current number of built boxes for the specified player.
	 * 
	 * @param player
	 *            the specified player
	 * @return number of boxes built by specified player
	 */
	int getPlayerScore(PlayerModel player) {
		int boxes = 0;
		for (int row = 0; row < boardSize; ++row) {
			for (int column = 0; column < boardSize; ++column) {
				TileModel tile = getBoardTile(column, row);
				if (tile.isBoxBuilt() && tile.getBoxer() == player) {
					boxes++;
				}
			}
		}
		return boxes;
	}

	/**
	 * Checks the specified (x, y) position and determines if it is in range.
	 * 
	 * @param x
	 *            the column number
	 * @param y
	 *            the row number
	 * @return <code>true</code> if specified (x, y) is valid,
	 *         <code>false</code> otherwise
	 */
	private boolean checkCoordinates(int x, int y) {
		return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
	}

	/**
	 * Checks if any player is at the specified position.
	 * 
	 * @param x
	 *            the column number
	 * @param y
	 *            the row number
	 * @return <code>true</code> if player is present at specified (x, y),
	 *         <code>false</code> otherwise
	 */
	private boolean isPlayerAtPosition(int x, int y) {
		return player[0].isAtLocation(x, y) || player[1].isAtLocation(x, y);
	}

	/**
	 * Gets the current maximum number of items that can spawn at the same time.
	 * 
	 * @return maximum number of items that can spawn at the same time
	 */
	private int getMaxItemCount() {
		int count = 0;

		for (int i = 0; i < boardSize; i++){
			for (int j = 0; j < boardSize; j++){
				if (!(board[i][j].isBoxBuilt()
						|| player[0].isAtLocation(j, i)
						|| player[1].isAtLocation(j, i))){
					count++;
				}
			}
		}
		return Math.min(difficulty.maxItems, count);
	}

	/**
	 * Gets the current number of items spawned.
	 * 
	 * @return amount of items currently spawned
	 */
	private int getItemCount() {
		int count = 0;
		for (int row = 0; row < boardSize; ++row) {
			for (int column = 0; column < boardSize; ++column) {
				if (items[row][column] != null) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Gets a random item depending on the current game difficulty.
	 * 
	 * @return <code>BrickModel</code>, <code>PickModel</code> or
	 *         <code>TNTModel</code>
	 */
	private ItemModel getRandomItem() {
		double spawnFactor = spawner.nextDouble();
		double brickFactor = 0.8;
		double pickFactor = 0.95;
		if (difficulty == Difficulty.INTERMEDIATE) {
			brickFactor = 0.8;
			pickFactor = 0.97;
		} else if (difficulty == Difficulty.ADVANCED) {
			brickFactor = 0.9;
			pickFactor = 0.99;
		}

		if (spawnFactor <= brickFactor) {
			return new BrickModel((spawner.nextInt(4) + 1));
		} else if (spawnFactor <= pickFactor) {
			return new PickModel();
		} else {
			return new TNTModel();
		}
	}

	ItemModel.Type getSpawnedItemType(int x, int y) {
		ItemModel item = getSpawnedItem(x, y);
		return (item == null) ? ItemModel.Type.NONE : item.getType();
	}

	/**
	 * Gets the item at the specified (x, y) position
	 * 
	 * @param x
	 *            X position on the game board
	 * @param y
	 *            Y position on the game board
	 * @return the item at the specified position or <code>null</code> if it
	 *         does not exist
	 * @throws IllegalArgumentException
	 *             if the specified (x, y) position is out of range
	 */
	ItemModel getSpawnedItem(int x, int y) {
		if (!checkCoordinates(x, y)) {
			throw COORDS_OUT_OF_RANGE;
		}
		return items[y][x];
	}

	public boolean isItemSpawned(int x, int y) {
		return getSpawnedItem(x, y) != null;
	}
	
	/**
	 * Set the item at the specified (x, y) position
	 * 
	 * @param item
	 *            The specified item, can be <code>null</code>
	 * @param x
	 *            X position on the game board
	 * @param y
	 *            Y position on the game board
	 */
	private void setSpawnedItem(ItemModel item, int x, int y) {
		if (!checkCoordinates(x, y)) {
			throw COORDS_OUT_OF_RANGE;
		}
		items[y][x] = item;
		if (item == null) {
			board[y][x].updateType(ItemModel.Type.NONE);
		} else {
			board[y][x].updateType(item.getType());
		}
	}

	/**
	 * Checks if the player <code>p</code> can move to the specified tile from
	 * direction <code>dir</code>.
	 * 
	 * @param p
	 *            the player
	 * @param x
	 *            the column number
	 * @param y
	 *            the row number
	 * @param dir
	 *            the tile direction from which the player tries to enter
	 * @return <code>true</code> if the player can move to the specified tile,
	 *         <code>false</code> otherwise.
	 */
	private boolean canMove(PlayerModel p, int x, int y, int dir) {
		boolean res = checkCoordinates(x, y) && !isPlayerAtPosition(x, y);

		/* No extra movement restriction on BEGINNER */

		if (difficulty == Difficulty.INTERMEDIATE) {
			PlayerModel boxer = getBoardTile(x, y).getBoxer();

			res = res && (boxer == null || boxer == p);
			logger.debug("canMove INTERMEDIATE: {}", res);
		} else if (difficulty == Difficulty.ADVANCED) {
			res = res && !getBoardTile(x, y).getWall(dir).isBuilt();
			logger.debug("canMove ADVANCED: {}", res);
		}

		return res;
	}

	/**
	 * Performs operations after a player moves onto a new tile. Currently this
	 * method only picks up the item if available. The movement to the specified
	 * tile must be validated beforehand.
	 * 
	 * @param x
	 *            the column number
	 * @param y
	 *            the row number
	 */
	private void onPlayerMove(PlayerModel player, int x, int y) {
		ItemModel item = (ItemModel) getSpawnedItem(x, y);

		if (item != null && player.pickupItem(item)) {
			setSpawnedItem(null, x, y);
		}
	}
}
