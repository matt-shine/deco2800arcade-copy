package deco2800.arcade.mixmaze.domain;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.ItemModel.Type.*;

/**
 * Mix maze model represents the running game state.
 */
public class MixMazeModel {
	public enum MixMazeDifficulty {
		Beginner,
		Intermediate,
		Advanced
	}

	// Exceptions
	public final static IllegalStateException NOT_STARTED = new IllegalStateException("The game has not started.");
	public final static IllegalArgumentException COORDSOUTOFRANGE = new IllegalArgumentException("The specified coordinates(x, y) are out of range.");

	// Game state
	private boolean running = false;
	private boolean ended = false;

	// Board data
	private int boardSize;
	private TileModel[][] board;
	private Thread spawnerThread;

	// Game settings
	private MixMazeDifficulty gameDifficulty;
	private int gameMaxTime;
	private long gameStartTime = -1;
	private long gameEndTime = -1;

	// Player data
	private PlayerModel player1;
	private PlayerModel player2;

	public boolean isRunning() {
		return running;
	}

	public boolean isEnded() {
		return ended;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public TileModel getBoardTile(int x, int y) {
		if(!checkCoordinates(x, y)) {
			throw COORDSOUTOFRANGE;
		}
		return board[y][x];
	}

	public MixMazeDifficulty getGameDifficulty() {
		return gameDifficulty;
	}

	public int getGameMaxTime() {
		return gameMaxTime;
	}

	public long getGameStartTime() {
		return gameStartTime;
	}

	public long getGameEndTime() {
		return gameEndTime;
	}

	public PlayerModel getPlayer1() {
		return player1;
	}

	public PlayerModel getPlayer2() {
		return player2;
	}

	public int getPlayerScore(int pid) {
		if (pid == 1) {
			return getPlayerScore(player1);
		} else {
			return getPlayerScore(player2);
		}
	}

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
	 * Returns the number of bricks the specified player has.
	 *
	 * @param pid the player id, can be either 1 or 2
	 * @return the number of bricks
	 */
	public int getPlayerBrick(int pid) {
		PlayerModel p = (pid == 1) ? player1 : player2;

		return p.getBrick().getAmount();
	}

	private boolean hasItem(int pid, ItemModel.Type type) {
		PlayerModel p = (pid == 1) ? player1 : player2;
		ItemModel item = null;

		switch (type) {
		case PICK:
			item = p.getPick();
			break;
		case TNT:
			item = p.getTNT();
			break;
		}

		return (item == null) ? false : true;
	}

	/**
	 * Returns if the specified player has a pick.
	 *
	 * @param pid the player id, can be either 1 or 2
	 * @return true if the player has pick, otherwise false
	 */
	public boolean hasPick(int pid) {
		return hasItem(pid, PICK);
	}

	/**
	 * Returns if the specified player has a TNT.
	 *
	 * @param pid the player id, can be either 1 or 2
	 * @return true if the player has TNT, otherwise false
	 */
	public boolean hasTNT(int pid) {
		return hasItem(pid, TNT);
	}

	private boolean checkCoordinates(int x, int y) {
		return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
	}

	private boolean isPlayerAtPosition(int x, int y) {
		if(!checkCoordinates(x, y)) {
			throw COORDSOUTOFRANGE;
		}
		return (player1.getX() == x && player1.getY() == y) || (player2.getX() == x && player2.getY() == y);
	}

	public void startGame() {
		if(running || ended) {
			throw new IllegalStateException("The game has already been started.");
		}

		spawnerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!ended) {
					try {
						for(int row = 0; row < boardSize; ++row) {
							for(int column = 0; column < boardSize; ++column) {
								getBoardTile(column, row).spawnItem();
							}
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) { }
				}
			}
		});
		spawnerThread.start();

		running = true;
		gameStartTime = System.currentTimeMillis();
	}

	public PlayerModel endGame() {
		if(!running || ended) {
			throw new IllegalStateException("The game has not been started or has already ended.");
		}
		running = false;
		ended = true;
		gameEndTime = System.currentTimeMillis();

		int player1Score = getPlayerScore(player1);
		int player2Score = getPlayerScore(player2);
		if(player1Score != player2Score) {
			return (player1Score > player2Score) ? player1 : player2;
		} else {
			return null;
		}
	}

	public void movePlayer(PlayerModel player, int direction) {
		if(!running) {
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
		} else if(player.canMove() && checkCoordinates(nextX, nextY) && !isPlayerAtPosition(nextX, nextY)) {
			player.move();
			getBoardTile(player.getX(), player.getY()).onPlayerEnter(player);
		}
	}

	/**
	 * Constructor.
	 *
	 * @param size 		the size of the game board
	 * @param difficulty	the game difficulty
	 * @param maxMinutes	the time of a game session in minutes
	 * @throws IllegalArgumentException If <code>size</code> is not in range
	 * 				    from 5 to 10, or
	 * 				    <code>maxMinutes</code> is not in
	 * 				    range from 2 to 15.
	 */
	public MixMazeModel(int size, MixMazeDifficulty difficulty, int maxMinutes) {
		if(size < 5 || size > 10) {
			throw new IllegalArgumentException("size must be between 5 and 10.");
		}

		if(maxMinutes < 2 || maxMinutes > 15) {
			throw new IllegalArgumentException("maxMinutes must be between 2 and 15.");
		}

		// Initialize board
		boardSize = size;
		board = new TileModel[boardSize][boardSize];
		for(int row = 0; row < boardSize; ++row) {
			for(int column = 0; column < boardSize; ++column) {
				WallModel[] adjWalls = new WallModel[4];
				int[] xChecks = new int[] { (column - 1), column, (column + 1), column };
				int[] yChecks = new int[] { row, (row - 1), row, (row + 1) };
				for(int tileDir = 0; tileDir < 4; ++tileDir) {
					if(checkCoordinates(xChecks[tileDir], yChecks[tileDir])) {
						TileModel adjTile = getBoardTile(xChecks[tileDir], yChecks[tileDir]);
						if(adjTile != null) {
							adjWalls[tileDir] = adjTile.getWall(getPolarDirection(tileDir));
						}
					}
				}
				board[row][column] = new TileModel(column, row, adjWalls);
			}
		}

		// Set game settings
		gameDifficulty = difficulty;
		gameMaxTime = maxMinutes;

		// Initialize player 1
		player1 = new PlayerModel(1);
		player1.setX(0);
		player1.setY(0);
		player1.setDirection(EAST);

		// Initialize player 2
		player2 = new PlayerModel(2);
		player2.setX(boardSize - 1);
		player2.setY(boardSize - 1);
		player2.setDirection(WEST);
	}
}
