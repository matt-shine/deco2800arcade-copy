package deco2800.arcade.mixmaze.domain;

public class MixMazeModel {
	// Player data
	private PlayerModel player1;
	private PlayerModel player2;
	
	// Board data
	private int boardWidth;
	private int boardHeight;
	private TileModel[][] board;
	
	public PlayerModel getPlayer1() {
		return player1;
	}
	
	public PlayerModel getPlayer2() {
		return player2;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	private boolean checkCoordinates(int x, int y) {
		return x >= 0 && x < boardWidth && y >= 0 && y < boardHeight;
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
		// Initialize Players
		player1 = new PlayerModel(1);
		player2 = new PlayerModel(2);
		
		// Initialize board
		width = boardWidth;
		height = boardHeight;
		board = new TileModel[height][width];
		for(int row = 0; row < height; ++row) {
			for(int column = 0; column < width; ++column) {
				board[row][column] = new TileModel(column, row);
			}
		}
	}
}
