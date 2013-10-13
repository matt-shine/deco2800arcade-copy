package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;

public class ReadyState extends GameState {

	@Override
	public void handleInput(Pong context) {
		if (Gdx.input.isTouched()) {
    		context.startPoint();
    	}
	}	

}
