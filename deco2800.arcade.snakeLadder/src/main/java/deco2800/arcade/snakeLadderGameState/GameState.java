package deco2800.arcade.snakeLadderGameState;

import deco2800.arcade.snakeLadder.SnakeLadder;

public abstract class GameState {
	public static final GameState GAMEOVER = null;

	/**
	 * Implement this method to handle the actions to be performed for each state
	 * @param context snakeLadder game context
	 */
	public abstract void handleInput(SnakeLadder context);
	
}
