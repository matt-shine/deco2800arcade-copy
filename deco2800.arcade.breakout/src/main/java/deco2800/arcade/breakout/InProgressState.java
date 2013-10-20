package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import deco2800.arcade.breakout.screens.GameScreen;
/**
 * Handles when the game is in progress
 * @author Carlie Smits
 *
 */
public class InProgressState extends GameState {

	private Intersector intersect;
	
	public InProgressState() {
		intersect = new Intersector();
	}
	
	/**
	 * Handles the current state of the game when game play has
	 * been initiated.
	 * It handles the movement of the paddle, the movement
	 * of powerups, the movement of the ball, when a person has
	 * won the game, when a collision has occurred and when the game
	 * has been paused or muted 
	 */
	@Override
	public void handleState(GameScreen context) {
		
		if (context.getBall() != null) {
			context.getBall().move(Gdx.graphics.getDeltaTime());
			context.getPaddle().update(context.getBall());
			handleBrickCollision(context);
		} else {
			context.getPaddle().update(context.getPowerupBall());
		}
		if (context.getPowerupBall() != null) {
			context.getPowerupBall().move(Gdx.graphics.getDeltaTime());
			handlePowerupBallBrickCollision(context);
		}
		context.getPowerupManager().moveAll();
		if (context.getBrickNum() == 0) {
			context.setLevel(context.getLevel() + 1);
			context.destroyPowerupBall();
			if (context.getLevel() > 10) {
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
			context.inGamePause();
		}
		
		if (Gdx.input.isKeyPressed(Keys.M)) {
			context.mute();
		}
	}
	
	/**
	 * 
	 * @param c - a circle formation representing the game ball
	 * @param r - a rectangular formation representing the game bricks
	 * @return the x and y coordinates of where a collision with the
	 * ball and rectangle had occurred
	 */
	private boolean overlap(Circle c, Rectangle r) {
		return Intersector.overlapCircleRectangle(c, r);
	}
	
	/**
	 * Handles the velocity of the powerup ball after the powerup ball 
	 * has collided with the edges of the screen or paddle
	 * @param context - the current game screen
	 */
	private void handlePowerupBallOtherCollision(GameScreen context) {
		if (overlap(context.getPowerupBall().ballCirc, context.getPaddle().paddleShape)
				&& context.getPowerupBall().getYVelocity() < 0) {
			context.getPowerupBall().updateVelocity(context.getLastHitX(), 
					context.getLastHitY(), context.getPaddle());
			context.bump.play();
			context.incrementBumpCount();
			context.getPowerupBall().bounceY(0);

		}

		if (context.getPowerupBall().ballCirc.y >= context.SCREENHEIGHT - 2*context.getPowerupBall().getRadius()) {
			context.setLastHitX(context.getPowerupBall().getX());
			context.setLastHitY(context.getPowerupBall().getY());
			context.getPowerupBall().bounceY(-5);
		}

		if (context.getPowerupBall().ballCirc.x - context.getPowerupBall().getRadius() <= 0) {
			context.setLastHitX(context.getPowerupBall().getX());
			context.setLastHitY(context.getPowerupBall().getY());
			context.getPowerupBall().bounceX(5);
		}
		
		if (context.getPowerupBall().ballCirc.x + context.getPowerupBall().getRadius() > context.SCREENWIDTH) {
			context.setLastHitX(context.getPowerupBall().getX());
			context.setLastHitY(context.getPowerupBall().getY());
			context.getPowerupBall().bounceX(-5);
		}

		if (context.getPowerupBall().ballCirc.y <= 0) {
			context.destroyPowerupBall();
			if (context.getNumBalls() <= 0) {
				context.roundOver();
			}
			
		}
		
		handlePowerupCollision(context);
	}
	
	/**
	 * 
	 * Handles the velocity of the powerup ball and updates the score 
	 * after it has hit a brick
	 * @param context - the current game screen
	 */
	private void handlePowerupBallBrickCollision(GameScreen context) {
		for (Brick b : context.getBrickArray()) {
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
	
	/**
	 * Handles the velocity of the regular ball after the regular ball has 
	 * collided with the edges of the screen or the paddle
	 * @param context - the current game screen
	 */
	private void handleOtherCollision(GameScreen context) {
		if (overlap(context.getBall().ballCirc, context.getPaddle().paddleShape)
				&& context.getBall().getYVelocity() < 0) {
			context.getBall().updateVelocity(context.getLastHitX(), context.getLastHitY(), context.getPaddle());
			context.bump.play();
			context.incrementBumpCount();
			context.getBall().bounceY(0);
		}

		if (context.getBall().ballCirc.y >= context.SCREENHEIGHT - 2*context.getBall().getRadius()) {
			context.setLastHitX(context.getBall().getX());
			context.setLastHitY(context.getBall().getY());
			context.getBall().bounceY(-5);
		}
		if (context.getBall().ballCirc.x - context.getBall().getRadius() <= 0) {
			context.setLastHitX(context.getBall().getX());
			context.setLastHitY(context.getBall().getY());
			context.getBall().bounceX(5);
		}
		if (context.getBall().ballCirc.x + context.getBall().getRadius() > context.SCREENWIDTH) {
			context.setLastHitX(context.getBall().getX());
			context.setLastHitY(context.getBall().getY());
			context.getBall().bounceX(-5);
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
	
	/**
	 * Detects whether a powerup has been collected by the paddle or
	 * has been missed
	 * @param context - the current game screen
	 */
	public void handlePowerupCollision(GameScreen context) {
		context.getPowerupManager().checkCollision(context.getPaddle().paddleShape);
		context.getPowerupManager().checkBelowScreen();
	}
	
	/**
	 * Handles the velocity of the ball and updates the score 
	 * after it has hit a brick
	 * @param context - current game screen
	 */
	public void handleBrickCollision(GameScreen context) {
		for (Brick b : context.getBrickArray()) {
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
