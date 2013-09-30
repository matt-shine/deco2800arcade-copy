package deco2800.arcade.pong;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {

	public abstract void handleInput(Pong context);
		
}
