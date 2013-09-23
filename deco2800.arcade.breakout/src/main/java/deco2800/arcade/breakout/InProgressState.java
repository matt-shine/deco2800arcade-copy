package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

public class InProgressState extends GameState {

	private Intersector intersect;
	
	public InProgressState() {
		intersect = new Intersector();
	}
	
	@Override
	public void handleState(GameScreen context) {
		context.getPaddle().update(context.getBall());
		context.getBall().move(Gdx.graphics.getDeltaTime());
		
		handleBrickCollision(context);

		if (context.getBrickNum() == 0) {
			context.setLevel(context.getLevel() + 1);
			if (context.getLevel() > 9) {
				context.win();
			} else {
				context.dispose();
				context.gamearea();
			}
		}

		handleOtherCollision(context);
		
		if (Gdx.input.isButtonPressed(Keys.ESCAPE)) {
			context.pause();
		}
		
		if (Gdx.input.isButtonPressed(Keys.CONTROL_LEFT)) {
			context.roundOver();
			context.bonusLives(1);
		}
		
		if (Gdx.input.isButtonPressed(Keys.ALT_LEFT)) {
			context.setLevel(context.getLevel() + 1);
			context.dispose();
			context.gamearea();
		}

	}
	
	private boolean overlap(Circle c, Rectangle r) {
		return Intersector.overlapCircleRectangle(c, r);
	}
	
	private void handleOtherCollision(GameScreen context) {
		if (overlap(context.getBall().ballCirc, context.getPaddle().paddleShape)
				&& context.getBall().getYVelocity() < 0) {
			context.getBall().updateVelocity(context.getLastHitX(), context.getLastHitY(), context.getPaddle());
			context.bump.play();
			context.getBall().bounceY();

		}

		if (context.getBall().ballCirc.y >= context.SCREENHEIGHT - 2*context.getBall().getRadius()) {
			System.out.println("balls y: " + context.getBall().ballCirc.y);
			context.setLastHitX(context.getBall().getX());
			context.setLastHitY(context.getBall().getY());
			context.getBall().bounceY();
		}

		if (context.getBall().ballCirc.x - context.getBall().getRadius() <= 0
				|| context.getBall().ballCirc.x + context.getBall().getRadius() > context.SCREENWIDTH) {
			context.setLastHitX(context.getBall().getX());
			context.setLastHitY(context.getBall().getY());
			context.getBall().bounceX();
		}

		if (context.getBall().ballCirc.y <= 0) {
			context.roundOver();
		}

		
	}

	public void handleBrickCollision(GameScreen context) {
		for (Brick b : context.bricks) {
			if (b.getState()) {
				if (b.checkLeftCollision(context.getBall().ballCirc)) {
					b.setState(false);
					if (Math.abs(context.getBall().getXVelocity()) < 80) {
						context.updateGameState(2);
						break;
					}
					context.updateGameState(0);
					break;
				}
				if (b.checkRightCollision(context.getBall().ballCirc)) {
					b.setState(false);
					if (Math.abs(context.getBall().getXVelocity()) < 80) {
						context.updateGameState(2);
						break;
					}
					context.updateGameState(0);
					break;
				}
				if (b.checkTopCollision(context.getBall().ballCirc)) {
					b.setState(false);
					context.updateGameState(1);
					break;
				}
				if (b.checkBottomCollision(context.getBall().ballCirc)) {
					b.setState(false);
					context.updateGameState(1);
					break;
				}
			}
		}
	}

}
