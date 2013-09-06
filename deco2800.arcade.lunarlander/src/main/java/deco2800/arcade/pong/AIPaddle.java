package deco2800.arcade.lunarlander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class AIPaddle extends Paddle {

	public static final int AIPADDLESPEED = 150;
	
	/**
	 * Initialise paddle at specified position.
	 */
	public AIPaddle(Vector2 position) {
		super(position);
	}
	
	@Override
	public void update(Ball ball) {
		super.update(ball);
		// If the ball is above us, move up
		float maxmove = AIPADDLESPEED*Gdx.graphics.getDeltaTime();
		float diff = ball.bounds.y - bounds.y;
		float move;
		if (diff < 0){
			move = Math.max(maxmove*-1, diff);
		} else {
			move = Math.min(maxmove, diff);
		}
		bounds.y += move;
	}

}
