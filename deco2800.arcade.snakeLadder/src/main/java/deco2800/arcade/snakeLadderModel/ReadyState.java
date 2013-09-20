package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.snakeLadder.SnakeLadder;

public class ReadyState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		if (Gdx.input.isTouched()) {
    		context.startPoint();
    		context.gamePlayer.getDnumber(context.getDice().getDiceNumber());
    		context.AIPlayer.getDnumber2();
    	}
	}

}
