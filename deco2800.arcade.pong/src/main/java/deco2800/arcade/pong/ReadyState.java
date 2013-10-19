package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ReadyState extends GameState {

	@Override
	public void handleInput(Pong context) {
		if (Gdx.input.isTouched()) {
			context.startPoint();
		}
	}

}
