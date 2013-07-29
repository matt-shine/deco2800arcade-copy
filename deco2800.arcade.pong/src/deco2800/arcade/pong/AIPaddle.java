package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class AIPaddle extends Paddle {

	public static final int AIPADDLESPEED = 150;
	
	public AIPaddle(Vector2 position) {
		super(position);
	}
	/**
	 * Updates the position of the ball with regards to the paddle.
	 */	
	@Override
	public void update(Ball ball) {
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
