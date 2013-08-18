package deco2800.arcade.model;

public class GamePlayToken {
	
	private Game g;
	
	private int plays;
	
	public GamePlayToken(Game g, int plays) {
		this.g = g;
		this.plays = plays;
	}
	
	public Game getGame() {
		return g;
	}
	
	public int getPlays() {
		return plays;
	}

}
