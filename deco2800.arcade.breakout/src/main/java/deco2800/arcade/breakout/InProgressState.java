package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InProgressState extends GameState {

	@Override
	public void handleState(GameScreen context) {
		context.getPaddle().update(context.getBall());
		context.getBall().move(Gdx.graphics.getDeltaTime());
		for (Brick b : context.bricks) {
			if (b.getState()) {
				if (b.checkLeftCollision(context.getBall().bounds)) {
					b.setState(false);
					if (Math.abs(context.getBall().getXVelocity()) < 60) {
						context.updateGameState(2);
						break;
					}
					context.updateGameState(0);
					break;
				}
				if (b.checkRightCollision(context.getBall().bounds)) {
					b.setState(false);
					if (Math.abs(context.getBall().getXVelocity()) < 60) {
						context.updateGameState(2);
						break;
					}
					context.updateGameState(0);
					break;
				}
				if (b.checkTopCollision(context.getBall().bounds)) {
					b.setState(false);
					context.updateGameState(1);
					break;
				}
				if (b.checkBottomCollision(context.getBall().bounds)) {
					b.setState(false);
					context.updateGameState(1);
					break;
				}
			}
		}

		if (context.getBrickNum() == 0) {
			context.setLevel(context.getLevel() + 1);
			if (context.getLevel() > 3) {
				context.win();
			} else {
				context.dispose();
				context.gamearea();
			}
		}

		if (context.getBall().bounds.overlaps(context.getPaddle().paddleShape)
				&& context.getBall().getYVelocity() < 0) {
			context.getBall().updateVelocity(context.getLastHitX(), context.getLastHitY(), context.getPaddle());
			context.bump.play();
			context.getBall().bounceY();

		}

		if (context.getBall().bounds.y >= context.SCREENHEIGHT - Ball.WIDTH) {
			context.setLastHitX(context.getBall().getX());
			context.setLastHitY(context.getBall().getY());
			context.getBall().bounceY();
		}

		if (context.getBall().bounds.x <= 0
				|| context.getBall().bounds.x + Ball.WIDTH > context.SCREENWIDTH) {
			context.setLastHitX(context.getBall().getX());
			context.setLastHitY(context.getBall().getY());
			context.getBall().bounceX();
		}

		if (context.getBall().bounds.y <= 0) {
			context.roundOver();
		}

		if (Gdx.input.isButtonPressed(Keys.ESCAPE)) {
			context.pause();
		}

	}

}
