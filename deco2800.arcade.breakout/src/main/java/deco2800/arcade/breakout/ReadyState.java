package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class ReadyState extends GameState {

	@Override
	public void handleState(GameScreen context) {
		context.setStatus("Press Space or Touch the screen to Start!");
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched()) {
			context.Start();
		} else if (context.getSequence() != null) {
			if (Gdx.input.isKeyPressed(context.getSequence()[context
					.getCurrentButton()])) {
				context.setCurrentButton(context.getCurrentButton() + 1);
			}
			if (context.getSequence().length == context.getCurrentButton()) {
				context.setCurrentButton(0);
				context.bonusLives(2);
			}
		}

	}

}
