package deco2800.arcade.checkers;

import com.badlogic.gdx.math.Vector2;

public class AIPieces extends Pieces {

	public static final int AIPADDLESPEED = 150;
	
	/**
	 * Initialise pieces at specified position.
	 */
	public AIPieces(Vector2 position) {
		super(position);
	}
	
	/**
	 * AI makes a move
	 */
	public void move() {
		
	}
	
/*	@Override
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
*/
}
