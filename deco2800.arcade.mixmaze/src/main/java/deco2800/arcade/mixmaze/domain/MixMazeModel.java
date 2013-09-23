package deco2800.arcade.mixmaze.domain;

import static deco2800.arcade.mixmaze.domain.Direction.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Mix maze model represents the running game state
 */
public class MixMazeModel {
	/**
	 * Enumeration for representing different
	 * states of game difficulty
	 */
	public enum MixMazeDifficulty {
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
	private final static IllegalStateException NOT_STARTED = new IllegalStateException("The game has not started.");
	
	/**
	 * Thrown when operations requiring the game to not be in progress are attempted.
	 */
	private final static IllegalStateException STARTED = new IllegalStateException("The game has already started.");
	
	/**
	 * Thrown when an operation using coordinates out of
	 * the range of of <CODE>board</CODE> are used
	 */
	private final static IllegalArgumentException COORDSOUTOFRANGE = new IllegalArgumentException("The specified coordinates(x, y) are out of range.");

	// Game data
	private GameState state;
	private int boardSize;
	private TileModel[][] board;
	private ItemModel[][] items;

	// Game settings
	private MixMazeDifficulty gameDifficulty;
	private int gameMaxTime;
	private Date gameStartTime;
	private Date gameEndTime;

	// Player data
	private PlayerModel player1;
	private PlayerModel player2;

	// Item spawning data
	private Random spawner;
	private long lastSpawned;
	
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
		if(!checkCoordinates(x, y)) {
			throw COORDSOUTOFRANGE;
		}
		return board[y][x];
	}
	
	/**
	 * Gets the current level of game difficulty.
	 * @return <CODE>MixMazeDifficulty</CODE> representing
	 * current game difficulty
	 */
	public MixMazeDifficulty getGameDifficulty() {
		return gameDifficulty;
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
	public PlayerModel getPlayer1() {
		return player1;
	}
	
	/**
	 * Gets player two model
	 * @return <CODE>PlayerModel</CODE> representing player two
	 */
	public PlayerModel getPlayer2() {
		return player2;
	}

	/**
	 * Gets the current number of built boxes for the 
	 * specified player
	 * @param player The specified player
	 * @return Number of boxes built by specified player
	 */
	public int getPlayerScore(PlayerModel player) {
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
	 * Checks if a player is currently at
	 * the specified (x, y) position
	 * @param x X position on the game board
	 * @param y y position on the game board
	 * @return <CODE>true</CODE> if player is present at specified (x, y), 
	 * <CODE>false</CODE> otherwise
	 */
	private boolean isPlayerAtPosition(int x, int y) {
		if(!checkCoordinates(x, y)) {
			throw COORDSOUTOFRANGE;
		}
		return (player1.getX() == x && player1.getY() == y) 
				|| (player2.getX() == x && player2.getY() == y);
	}
	
	/**
	 * Starts the current game
	 */
	public void startGame() {
		if (state != GameState.NOT_STARTED) {
			throw STARTED;
		}
		state = GameState.RUNNING;
		gameStartTime = Calendar.getInstance().getTime();
	}
	
	/**
	 * Ends the current game.
	 * @return <CODE>PlayerModel</CODE> representing player with the 
	 * most built boxes or <CODE>null</CODE> in the event of a tie
	 */
	public PlayerModel endGame() {
		if (state != GameState.RUNNING) {
			throw NOT_STARTED;
		}
		
		// End game
		state = GameState.END;
		gameEndTime = Calendar.getInstance().getTime();
		int player1Score = getPlayerScore(player1);
		int player2Score = getPlayerScore(player2);
		if(player1Score != player2Score) {
			return (player1Score > player2Score) ? player1 : player2;
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the current maximum number of items that
	 * can spawn at the same time
	 * @return Maximum number of items that can spawn at
	 * the same time
	 */
	private int getMaxItemCount() {
		if(gameDifficulty == MixMazeDifficulty.Beginner) {
			return 6;
		} else if(gameDifficulty == MixMazeDifficulty.Intermediate) {
			return 4;
		} else {
			return 2;
		}
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
		if(gameDifficulty == MixMazeDifficulty.Intermediate) {
			brickFactor = 0.5;
			pickFactor = 0.7;
		} else if(gameDifficulty == MixMazeDifficulty.Advanced) {
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
	
	/**
	 * Executes a round of item spawning, 
	 * will only spawn items every <CODE>10 / getMaxItemCount()</CODE>
	 * seconds
	 */
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
	 * Gets the item at the specified (x, y) position
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 * @return <CODE>ItemModel</CODE> at the specified (x, y) 
	 * position or <CODE>null</CODE> if there is none
	 * @throws IllegalArgumentException if the specified 
	 * (x, y) position is out of range
	 */
	public ItemModel getSpawnedItem(int x, int y) {
		if(!checkCoordinates(x, y)) {
			throw COORDSOUTOFRANGE;
		}
		return items[y][x];
	}
	
	/**
	 * Set the item at the specified (x, y) position
	 * @param item The specified item, can be <CODE>null</CODE>
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 */
	private void setSpawnedItem(ItemModel item, int x, int y) {
		if(!checkCoordinates(x, y)) {
			throw COORDSOUTOFRANGE;
		}
		items[y][x] = item;
	}
	
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
	public void movePlayer(PlayerModel player, int direction) {
		if(state != GameState.RUNNING) {
			throw NOT_STARTED;
		}

		// Player is not null
		if(player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}

		// Check specified direction is valid
		if(!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}

		int nextX = player.getNextX(), nextY = player.getNextY();
		if(player.getDirection() != direction) {
			player.setDirection(direction);
			return;
		}
		
		if(checkCoordinates(nextX, nextY)  && !isPlayerAtPosition(nextX, nextY)) {
			boolean isBlocked = false;
			if(gameDifficulty != MixMazeDifficulty.Beginner) {
				TileModel nextTile = getBoardTile(nextX, nextY);
				isBlocked = (gameDifficulty == MixMazeDifficulty.Intermediate) ?
						nextTile.isBox() : nextTile.getWall(getPolarDirection(direction)).isBuilt();
			}
			
			if(!isBlocked) {
				player.move();
				onPlayerMove(player, nextX, nextY);
			}
		}
	}
	
	/**
	 * Performs operations after a player moves onto
	 * a new tile
	 * @param x X position on the game board
	 * @param y Y position on the game board
	 * @throws IllegalArgumentException if the specified 
	 * (x, y) position is out of range
	 */
	private void onPlayerMove(PlayerModel player, int x, int y) {
		if(!checkCoordinates(x, y)) {
			throw COORDSOUTOFRANGE;
		}
		
		ItemModel item = getSpawnedItem(x, y);
		if(item != null && player.pickupItem(item)) {
			setSpawnedItem(null, x, y);
		}
	}
	
	/**
	 * <CODE>MixMazeModel</CODE> constructor
	 * @param size The size of the game board
	 * @param difficulty The game difficulty
	 * @param maxSeconds The maximum time of a game session in seconds
	 * @throws IllegalArgumentException If <code>size</code> is not in range
	 * from 5 to 10, or <code>maxMinutes</code> is not in range from 2 to 15.
	 */
	public MixMazeModel(int size, MixMazeDifficulty difficulty, int maxSeconds) {
		if(size < 5 || size > 10) {
			throw new IllegalArgumentException("size must be between 5 and 10.");
		}

		if(maxSeconds < 30 || maxSeconds > 900) {
			throw new IllegalArgumentException("maxSeconds must be between 30 and 900.");
		}
		
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
		gameDifficulty = difficulty;
		gameMaxTime = maxSeconds;

		// Initialize player 1
		player1 = new PlayerModel(1);
		player1.setX(0);
		player1.setY(0);
		player1.setDirection(EAST);
		onPlayerMove(player1, 0, 0);

		// Initialize player 2
		player2 = new PlayerModel(2);
		player2.setX(boardSize - 1);
		player2.setY(boardSize - 1);
		player2.setDirection(WEST);
		onPlayerMove(player2, (boardSize - 1), (boardSize - 1));
	}
}
