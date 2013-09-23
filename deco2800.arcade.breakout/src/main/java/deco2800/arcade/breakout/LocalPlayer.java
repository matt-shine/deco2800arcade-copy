package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class LocalPlayer extends Paddle {

	public int paddleSpeed = 550;

	/**
	 * 
	 */

	public LocalPlayer(Vector2 position) {
		super(position);
	}

	/**
	 * Retrieves the X and Y positions of the touched position. Based on whether
	 * the position is on the left or right side of the screen the paddle will
	 * move towards that side at a paddleSpeed. Alternatively the Paddle
	 * operation will also look for LEFT and RIGHT arrows as well as A and D to
	 * move the paddle.
	 */
	public void update(Ball ball) {
		super.update(ball);
		if (Gdx.input.isTouched()) {
			Vector2 touchPos = new Vector2();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY());

			if (touchPos.x > Breakout.SCREENWIDTH / 2)
				movement(paddleSpeed * Gdx.graphics.getDeltaTime());

			if (touchPos.x < Breakout.SCREENWIDTH / 2)
				movement(-paddleSpeed * Gdx.graphics.getDeltaTime());
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)
				|| Gdx.input.isKeyPressed(Keys.D))
			movement(paddleSpeed * 1.15f * Gdx.graphics.getDeltaTime());

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))
			movement(-paddleSpeed * 1.15f * Gdx.graphics.getDeltaTime());

	}
}