package deco2800.arcade.mixmaze.domain;

public class MixMazeModel {
	private PlayerModel player1;
	private PlayerModel player2;
	
	private int boardWidth;
	private int boardHeight;
	private TileModel[][] board;
	
	public PlayerModel getPlayer1() {
		return player1;
	}
	
	public PlayerModel getPlayer2() {
		return player2;
	}
	
	public TileModel[][] getTiles() {
		return board;
	}
	
	public MixMazeModel(int width, int height) {
		player1 = new PlayerModel(1);
		player1 = new PlayerModel(2);
		
		// Initialize board
		width = boardWidth;
		height = boardHeight;
		board = new TileModel[height][width];
		for(int i = 0; i < height; ++i) {
			for(int j = 0; j < width; ++j) {
				board[i][j] = new TileModel(i, j);
			}
		}
	}
}
