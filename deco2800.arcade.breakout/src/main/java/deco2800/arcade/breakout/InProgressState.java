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
		
		if (context.getBall() != null) {
			context.getBall().move(Gdx.graphics.getDeltaTime());
			context.getPaddle().update(context.getBall());
		} else {
			context.getPaddle().update(context.getPowerupBall());
		}
		if (context.getPowerupBall() != null) {
			context.getPowerupBall().move(Gdx.graphics.getDeltaTime());
		}
		context.getPowerupManager().moveAll();
		if (context.getPowerupBall() != null) {
			handlePowerupBallBrickCollision(context);
		}
		if (context.getBall() != null) {
			handleBrickCollision(context);
		}
		
		if (context.getBrickNum() == 0) {
			context.setLevel(context.getLevel() + 1);
			if (context.getLevel() > 9) {
				context.win();
			} else {
				context.dispose();
				context.gamearea();
			}
		}
		
		if (context.getBall() != null) {
			handleOtherCollision(context);
		}
		if (context.getPowerupBall() != null) {
			handlePowerupBallOtherCollision(context);
		}
		
		if (Gdx.input.isKeyPressed(Keys.P)) {
			context.pause();
		}
		
		if (Gdx.input.isKeyPressed(Keys.M)) {
			context.mute();
		}
		
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			context.roundOver();
			context.cheatBonus(1);
		}
		
		if (Gdx.input.isKeyPressed(Keys.L)) {
			context.setLevel(context.getLevel() + 1);
			context.dispose();
			context.gamearea();
		}

	}
	
	private boolean overlap(Circle c, Rectangle r) {
		return Intersector.overlapCircleRectangle(c, r);
	}
	
	private void handlePowerupBallOtherCollision(GameScreen context) {
		if (overlap(context.getPowerupBall().ballCirc, context.getPaddle().paddleShape)
				&& context.getPowerupBall().getYVelocity() < 0) {
			context.getPowerupBall().updateVelocity(context.getLastHitX(), context.getLastHitY(), context.getPaddle());
			context.bump.play();
			context.getPowerupBall().bounceY();

		}

		if (context.getPowerupBall().ballCirc.y >= context.SCREENHEIGHT - 2*context.getPowerupBall().getRadius()) {
			System.out.println("balls y: " + context.getPowerupBall().ballCirc.y);
			context.setLastHitX(context.getPowerupBall().getX());
			context.setLastHitY(context.getPowerupBall().getY());
			context.getPowerupBall().bounceY();
		}

		if (context.getPowerupBall().ballCirc.x - context.getPowerupBall().getRadius() <= 0
				|| context.getPowerupBall().ballCirc.x + context.getPowerupBall().getRadius() > context.SCREENWIDTH) {
			context.setLastHitX(context.getPowerupBall().getX());
			context.setLastHitY(context.getPowerupBall().getY());
			context.getPowerupBall().bounceX();
		}

		if (context.getPowerupBall().ballCirc.y <= 0) {
			context.setNumBalls(context.getNumBalls() - 1);
			context.destroyPowerupBall();
			if (context.getNumBalls() <= 0) {
				context.roundOver();
			}
			
		}
		
		handlePowerupCollision(context);
		
	}
	
	private void handlePowerupBallBrickCollision(GameScreen context) {
		for (Brick b : context.bricks) {
			if (b.getState()) {
				if (b.checkLeftCollision(context.getPowerupBall().ballCirc)) {
					b.setState(false);
					if (Math.abs(context.getPowerupBall().getXVelocity()) < 80) {
						context.updateGameState(2, b, true);
						break;
					}
					context.updateGameState(0, b, true);
					break;
				}
				if (b.checkRightCollision(context.getPowerupBall().ballCirc)) {
					b.setState(false);
					if (Math.abs(context.getPowerupBall().getXVelocity()) < 80) {
						context.updateGameState(2, b, true);
						break;
					}
					context.updateGameState(0, b, true);
					break;
				}
				if (b.checkTopCollision(context.getPowerupBall().ballCirc)) {
					b.setState(false);
					context.updateGameState(1, b, true);
					break;
				}
				if (b.checkBottomCollision(context.getPowerupBall().ballCirc)) {
					b.setState(false);
					context.updateGameState(1, b, true);
					break;
				}
			}
		}
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
			context.setNumBalls(context.getNumBalls() - 1);
			context.destroyBall();
			if (context.getNumBalls() <= 0) {
				context.roundOver();
			}
			
		}
		
		handlePowerupCollision(context);
		
	}
	
	public void handlePowerupCollision(GameScreen context) {
		context.getPowerupManager().checkCollision(context.getPaddle().paddleShape);
		context.getPowerupManager().checkBelowScreen();
	}

	public void handleBrickCollision(GameScreen context) {
		for (Brick b : context.bricks) {
			if (b.getState()) {
				if (b.checkLeftCollision(context.getBall().ballCirc)) {
					b.setState(false);
					if (Math.abs(context.getBall().getXVelocity()) < 80) {
						context.updateGameState(2, b, false);
						break;
					}
					context.updateGameState(0, b, false);
					break;
				}
				if (b.checkRightCollision(context.getBall().ballCirc)) {
					b.setState(false);
					if (Math.abs(context.getBall().getXVelocity()) < 80) {
						context.updateGameState(2, b, false);
						break;
					}
					context.updateGameState(0, b, false);
					break;
				}
				if (b.checkTopCollision(context.getBall().ballCirc)) {
					b.setState(false);
					context.updateGameState(1, b, false);
					break;
				}
				if (b.checkBottomCollision(context.getBall().ballCirc)) {
					b.setState(false);
					context.updateGameState(1, b, false);
					break;
				}
			}
		}
	}

}
