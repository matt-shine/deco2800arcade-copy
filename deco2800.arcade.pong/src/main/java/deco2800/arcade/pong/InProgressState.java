package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;

public class InProgressState extends GameState {

	@Override
	public void handleInput(Pong context) {
		Paddle left = context.getLeftPaddle();
		Paddle right = context.getRightPaddle();
		Ball ball = context.getBall();

		// Move the paddles (mouse)
		left.update(ball);
		right.update(ball);

		// Move the ball
		// ball.bounds.x -= ball.velocity.x * Gdx.graphics.getDeltaTime();
		ball.move(Gdx.graphics.getDeltaTime());

		// checking code for the slider achievement
		boolean hitLeft = ball.bounds.overlaps(left.bounds);
		boolean hitRight = ball.bounds.overlaps(right.bounds);

		if (hitLeft) {
			if (left.direction < 0) {
				context.incrementAchievement("pong.slider.moveUp");
			} else if (left.direction > 0) {
				context.incrementAchievement("pong.slider.moveDown");
			}
		}

		// If the ball hits a paddle then bounce it
		if (hitLeft || hitRight) {
			ball.bounceX();
		}

		// Bounce off the top or bottom of the screen
		if (ball.bounds.y <= 0
				|| ball.bounds.y >= context.SCREENHEIGHT - Ball.WIDTH) {
			ball.bounceY();
		}

		// If the ball gets to the left edge then player 2 wins
		if (ball.bounds.x <= 0) {
			context.endPoint(1);
		} else if (ball.bounds.x + Ball.WIDTH > context.SCREENWIDTH) {
			// If the ball gets to the right edge then player 1 wins
			context.endPoint(0);
			context.incrementAchievement("pong.win5Points");
		}
	}

}
