package deco2800.arcade.mixmaze.domain;

public class MixMazeModel {
	// Board data
	private int boardWidth;
	private int boardHeight;
	private TileModel[][] board;
	
	// Player data
	private PlayerModel player1;
	private PlayerModel player2;
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	public PlayerModel getPlayer1() {
		return player1;
	}
	
	public PlayerModel getPlayer2() {
		return player2;
	}
	
	private boolean checkCoordinates(int x, int y) {
		return x >= 0 && x < boardWidth && y >= 0 && y < boardHeight;
	}
	
	public void movePlayer(PlayerModel player, int direction) {
		// Player is not null
		if(player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}
		
		// Check specified direction is valid
		if(!Direction.isDirection(direction)) {
			throw Direction.NOTADIRECTION;
		}
		
		if(player.canMove()) {
			if(player.getDirection() != direction) {
				player.setDirection(direction);
			} else if(checkCoordinates(player.getNextX(), player.getNextY())) {
				player.move();
			}
		}
	}
	
	public TileModel getTile(int row, int column) {
		// Check arguments are in range.
		if(!checkCoordinates(column, row)) {
			throw new IllegalArgumentException("Specified row or column is out of range.");
		}
		
		// Return tile at the specified position
		return board[row][column];
	}
	
	public MixMazeModel(int width, int height) {
		// Initialize board
		boardWidth = width;
		boardHeight = height;
		board = new TileModel[height][width];
		for(int row = 0; row < boardHeight; ++row) {
			for(int column = 0; column < boardWidth; ++column) {
				board[row][column] = new TileModel(column, row);
			}
		}
		
		// Initialize player 1
		player1 = new PlayerModel(1);
		player1.setX(0);
		player1.setY(0);
		player1.setDirection(Direction.EAST);
		
		// Initialize player 2
		player2 = new PlayerModel(2);
		player2.setX(boardWidth - 1);
		player2.setY(boardHeight - 1);
		player2.setDirection(Direction.WEST);
	}
}
