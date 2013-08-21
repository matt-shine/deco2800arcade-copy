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
		width = boardWidth;
		height = boardHeight;
		board = new TileModel[height][width];
	}
}
