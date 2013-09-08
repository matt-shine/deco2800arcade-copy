package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class LocalPlayer extends Paddle {

	public int KBPADDLESPEED = 550;
	public static final int SCREENWIDTH = 1280;

	/**
	 * 
	 */

	public LocalPlayer(Vector2 position) {
		super(position);
	}

	public void update(Ball ball) {
		super.update(ball);
		if (Gdx.input.isTouched()) {
			Vector2 touchPos = new Vector2();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY());

			if (touchPos.x > SCREENWIDTH / 2)
				movement(KBPADDLESPEED * Gdx.graphics.getDeltaTime());

			if (touchPos.x < SCREENWIDTH / 2)
				movement(-KBPADDLESPEED * Gdx.graphics.getDeltaTime());
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)
				|| Gdx.input.isKeyPressed(Keys.D))
			movement(KBPADDLESPEED * Gdx.graphics.getDeltaTime());

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))
			movement(-KBPADDLESPEED * Gdx.graphics.getDeltaTime());

	}
}