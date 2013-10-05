package deco2800.arcade.mixmaze.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * <tr><th>Level<th>Move through walls<th>Move through boxes
	 * <th>Picks spawn<th>TNTs spawn
	 * <tr><td>Beginner<td>Yes<td>Yes<td>Frequently<td>Frequently
	 * <tr><td>Intermediate<td>Yes<td>Yes only through your own
	 * <td>Frequently<td>Rarely
	 * <tr><td>Advanced<td>No<td>No<td>Moderately<td>Rarely
	 * </table>
	 */
	public enum Difficulty {
		BEGINNER,
		INTERMEDIATE,
		ADVANCED
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

	/**
	 * Thrown when attempting to operate a game that is not running.
	 */
	private final static IllegalStateException NOT_STARTED = 
			new IllegalStateException("The game has not started.");

	/**
	 * Thrown when attempting to start a game that is currently running.
	 */
	private final static IllegalStateException STARTED = 
			new IllegalStateException("The game has already started.");

	/**
	 * Thrown when using coordinates out of the <code>board</code> range.
	 */
	private final static IllegalArgumentException COORDS_OUT_OF_RANGE = 
			new IllegalArgumentException("The specified coordinates(x, y) are out of range.");

	/* Game data */
	
	/** Game state */
	private GameState state;
	
	/** Board size */
	private int boardSize;
	
	/** Tiles on the game board (rows by columns, i.e y by x) */
	private TileModel[][] board;
	
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
	 * @param size		the size of the game board
	 * @param difficulty	the game difficulty
	 * @param maxSeconds	the time limit of this game session
	 * @throws IllegalArgumentException
	 * If <code>size</code> is not in range from 5 to 10,
	 * or <code>maxSeconds</code> is not in range from 30 to 900.
	 */
	public MixMazeModel(int size, Difficulty difficulty, int maxSeconds) {
		if(size < 5 || size > 10)
			throw new IllegalArgumentException("size must be between 5 and 10.");
		if(maxSeconds < 30 || maxSeconds > 900)
			throw new IllegalArgumentException("maxSeconds must be between 30 and 900.");

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
		items = new ItemModel[boardSize][boardSize];
		/* XXX: too many levels of nested loops */
		for (int y = 0; y < boardSize; ++y) {
			for (int x = 0; x < boardSize; ++x) {
				TileModel[] adjTiles = new TileModel[4];
				int[] tileX = new int[] {(x - 1), x, (x + 1), x};
				int[] tileY = new int[] {y, (y - 1), y, (y + 1)};
				for (int tileDir = 0; tileDir < 4; ++tileDir) {
					if(checkCoordinates(tileX[tileDir], tileY[tileDir])) {
						adjTiles[tileDir] = getBoardTile(tileX[tileDir], tileY[tileDir]);
					}
				}
				board[y][x] = new TileModel(x, y, adjTiles, this);
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
		if (!checkCoordinates(x, y))
			throw COORDS_OUT_OF_RANGE;

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
	public void usePlayerAction(int id) {
		PlayerModel p = player[id - 1];

		p.useAction(board[p.getY()][p.getX()]);
	}
	
	@Override
	public void movePlayer(int id, int direction) {
		if (state != GameState.RUNNING)
			throw NOT_STARTED;
		else if (!isDirection(direction))
			throw NOT_A_DIRECTION;

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
	
	@Override
	public void startGame() {
		if (state != GameState.NOT_STARTED)
			throw STARTED;

		state = GameState.RUNNING;
		gameStartTime = Calendar.getInstance().getTime();
	}
	
	@Override
	public PlayerModel endGame() {
		if (state != GameState.RUNNING)
			throw NOT_STARTED;

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
	 * Gets the current number of built boxes for the
	 * specified player.
	 * 
	 * @param player	the specified player
	 * @return number of boxes built by specified player
	 */
	int getPlayerScore(PlayerModel player) {
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
	 * determines if it is in range.
	 * 
	 * @param x	the column number
	 * @param y	the row number
	 * @return <code>true</code> if specified (x, y) is valid,
	 * <code>false</code> otherwise
	 */
	private boolean checkCoordinates(int x, int y) {
		return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
	}

	/**
	 * Checks if any player is at the specified position.
	 *
	 * @param x	the column number
	 * @param y	the row number
	 * @return <code>true</code> if player is present at specified (x, y),
	 * <code>false</code> otherwise
	 */
	private boolean hasPlayerAtPosition(int x, int y) {
		return (player[0].getX() == x && player[0].getY() == y)
				|| (player[1].getX() == x && player[1].getY() == y);
	}

	/**
	 * Gets the current maximum number of items that
	 * can spawn at the same time.
	 *
	 * @return maximum number of items that can spawn at
	 * the same time
	 */
	private int getMaxItemCount() {
		int res = 0;

		if (difficulty == Difficulty.BEGINNER)
			res = 6;
		else if (difficulty == Difficulty.INTERMEDIATE)
			res = 4;
		else if (difficulty == Difficulty.ADVANCED)
			res = 2;
		return res;
	}

	/**
	 * Gets the current number of items spawned.
	 * 
	 * @return amount of items currently spawned
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
	 * 
	 * @return <code>BrickModel</code>, <code>PickModel</code>
	 * or <code>TNTModel</code>
	 */
	private ItemModel getRandomItem()
	{
		double spawnFactor = spawner.nextDouble();
		double brickFactor = 0.33;
		double pickFactor = 0.66;
		if(difficulty == Difficulty.INTERMEDIATE) {
			brickFactor = 0.5;
			pickFactor = 0.7;
		} else if(difficulty == Difficulty.ADVANCED) {
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

	ItemModel.Type getSpawnedItemType(int x, int y) {
		ItemModel item = getSpawnedItem(x, y);
		return (item == null) ? ItemModel.Type.NONE : item.getType();
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
	ItemModel getSpawnedItem(int x, int y) {
		if (!checkCoordinates(x, y))
			throw COORDS_OUT_OF_RANGE;

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
		if (!checkCoordinates(x, y))
			throw COORDS_OUT_OF_RANGE;

		items[y][x] = item;
		if (item == null)
			board[y][x].updateType(ItemModel.Type.NONE);
		else
			board[y][x].updateType(item.getType());
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
	
	/**
	 * Checks if there is an enclosed area starting from <code>tile</code>.
	 * Ignore the direction on the specified tile
	 * where a wall is most recently built/destroyed.
	 * 
	 * @param tile		the tile to start
	 * @param ignoreDir	the direction on the tile to ignore 
	 * @return a two dimensional array indicating whether each box is part 
	 * of the enclosed area.
	 */
	boolean checkBoxes(TileModel tile, int ignoreDir, boolean[][] marker) {
		int x = tile.getX();
		int y = tile.getY();
		
		if (hasUnbuiltEdgeWall(tile))
			return false;
		
		marker[y][x] = true;
		if (!(ignoreDir == WEST || tile.isWallBuilt(WEST) 
				|| checkTile(board[y][x-1], marker)))
			return false;
		if (!(ignoreDir == NORTH || tile.isWallBuilt(NORTH) 
				|| checkTile(board[y-1][x], marker)))
			return false;
		if (!(ignoreDir == EAST || tile.isWallBuilt(EAST) 
				|| checkTile(board[y][x+1], marker)))
			return false;
		if (!(ignoreDir == SOUTH || tile.isWallBuilt(SOUTH) 
				|| checkTile(board[y+1][x], marker)))
			return false;
		return true;
	}
	
	private boolean checkTile(TileModel t, boolean[][] marker) {
		int x = t.getX();
		int y = t.getY();
		
		logger.debug("check tile ({}, {})", y, x);
		/* already visited */
		if (marker[y][x]) {
			logger.debug("already marked");
			return true;
		}
		
		/* area unbounded */
		if (hasUnbuiltEdgeWall(t)) {
			logger.debug("has unbuilt edge wall");
			return false;
		}
		
		/* mark the tile and check each direction */
		marker[y][x] = true;
		if (!(t.isWallBuilt(WEST) || checkTile(board[y][x-1], marker))) {
			logger.debug("west adjacent tile failed");
			return false;
		}
		if (!(t.isWallBuilt(NORTH) || checkTile(board[y-1][x], marker))) {
			logger.debug("north adjacent tile failed");
			return false;
		}
		if (!(t.isWallBuilt(EAST) || checkTile(board[y][x+1], marker))) {
			logger.debug("east adjacent tile failed");
			return false;
		}
		if (!(t.isWallBuilt(SOUTH) || checkTile(board[y+1][x], marker))) {
			logger.debug("south adjacent tile failed");
			return false;
		}
		logger.debug("success");
		return true;
	}
	
	private boolean hasUnbuiltEdgeWall(TileModel t) {
		int x = t.getX();
		int y = t.getY();
		
		return ((x == 0 && !t.isWallBuilt(WEST))
				|| x == boardSize - 1 && !t.isWallBuilt(EAST)
				|| y == 0 && !t.isWallBuilt(NORTH)
				|| y == boardSize - 1 && !t.isWallBuilt(SOUTH))
		       ? true : false;
	}
}
