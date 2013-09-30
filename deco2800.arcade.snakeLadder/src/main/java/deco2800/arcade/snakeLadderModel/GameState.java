package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.snakeLadder.SnakeLadder;

public abstract class GameState {
	public static final GameState GAMEOVER = null;

	public abstract void handleInput(SnakeLadder context);
	
}
