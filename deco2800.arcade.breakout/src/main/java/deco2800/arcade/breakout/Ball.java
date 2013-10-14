package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;


/**
 * Class for the breakout ball
 * 
 * @author Carlie Smits
 *
 */
public class Ball {
	// width of the ball
	public static final float WIDTH = 20f;
	public static final float INITIALSPEED = 400;
	public static float MAX_X_VELOCITY = 800f;
	private float multiplier = 1;
	
	// velocity of the ball
	Vector2 velocity = new Vector2();
	// Circle representing ball
	Circle ballCirc = new Circle();

	// Variables to store the render colour
	private float renderColourRed;
	private float renderColourGreen;
	private float renderColourBlue;
	private float renderColourAlpha;

	/**
	 * Create a ball where the paddle is located
	 */
	public Ball() {
		ballCirc.x = Breakout.SCREENWIDTH / 2 - Ball.WIDTH / 2 + 64;
		ballCirc.y = 35 + ballCirc.radius;
		ballCirc.radius = WIDTH/2;
		setColor(0.7f, 0.7f, 0.7f, 0.5f);
	}
	
	// Getter method for the balls X position
	public float getX() {
//		return bounds.x;
		return ballCirc.x;
	}
	
	// Getter method for the balls Y position
	public float getY() {
//		return bounds.y;
		return ballCirc.y;
	}
	
	public float getRadius() {
		return ballCirc.radius;
	}

	// Setter method for the X Velocity of the ball
	public void setXVelocity(float newVelocity) {
		if (newVelocity > MAX_X_VELOCITY) {
			this.velocity.x = multiplier * MAX_X_VELOCITY;
			return;
		}
		if (newVelocity < -MAX_X_VELOCITY) {
			this.velocity.x = -MAX_X_VELOCITY * multiplier;
			return;
		}
		this.velocity.x = newVelocity;
	}

	// Reset the ball to where the paddle is
	public void reset(Vector2 paddlePos) {
		velocity.x = 0;
		velocity.y = 0;
		multiplier = 1;
		ballCirc.x = paddlePos.x - Ball.WIDTH / 2 + 64;
		ballCirc.y = paddlePos.y + 25 + ballCirc.radius;
	}

	// Getter method for the y velocity
	public float getYVelocity() {
		return this.velocity.y;
	}

	// Getter method for the x velocity
	public float getXVelocity() {
		return this.velocity.x;
	}

	// Set the colour to render the ball
	public void setColor(float r, float g, float b, float a) {
		renderColourRed = r;
		renderColourGreen = g;
		renderColourBlue = b;
		renderColourAlpha = a;
	}

	// Colour the ball the colour given by setColor
	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(renderColourRed, renderColourGreen,
				renderColourBlue, renderColourAlpha);
		shapeRenderer.filledCircle(this.ballCirc.x, this.ballCirc.y, 
				this.ballCirc.radius);
	}

	// determine the new x velocity the ball will bounce at
	public void updateVelocity(float lastHitX, float lastHitY, Paddle paddle) {
		float currentHitX = ballCirc.x;
		float currentHitY = ballCirc.y;
		double angle = Math.atan2(lastHitY - currentHitY, lastHitX
				- currentHitX);
		float cosAngle = (float) Math.cos(angle);
		if ((Gdx.input.isKeyPressed(Keys.RIGHT))
				|| (Gdx.input.isKeyPressed(Keys.LEFT))) {
			cosAngle *= 1.5;
		}
		float pX = paddle.getPaddleX();
		float pWidth = paddle.getWidth();
		float bX = ballCirc.x;
		float newVelocity = 0f;
		// Handles the ball if it hits the left end of the paddle
		if (bX < pX + pWidth/7) {
			newVelocity = - (Math.abs(getXVelocity()) + 100 + 80
					* cosAngle);
		// Handles the ball if it hits between the left end and the middle	
		} else if (bX >= pX + pWidth/7 && bX < pX + 3*pWidth/7) {
			if (getXVelocity() < 0) {
				newVelocity = (getXVelocity() + 40
					* cosAngle);
			} else {
				newVelocity = (getXVelocity() - 40
						* cosAngle);
			}
		// Handles the ball if it hit in the middle of the paddle	
		} else if (bX >= pX + 3*pWidth/7 && bX < pX + 4*pWidth/7) {
			newVelocity = (getXVelocity()
					* Math.abs(cosAngle));
		// Handles the ball if it hits between the right end and the middle
		} else if (bX >= pX + 4*pWidth/7 && bX < pX + 6*pWidth/7) {
			if (getXVelocity() < 0) {
				newVelocity = (getXVelocity() + 40
					* cosAngle);
			} else {
				
				newVelocity = (getXVelocity() - 40
						* cosAngle);
			}
		// Handles the ball if it hits the right end of the paddle
		} else if (bX >= pX + 6*pWidth/7 && bX <= pX + pWidth) {
			newVelocity = 100 + (Math.abs(getXVelocity()) + 80
					* cosAngle);
		}
		setXVelocity(newVelocity);
	}

	// Reverse the X direction
	public void bounceX() {
		velocity.x *= -1;
	}

	// Reverse the Y direction
	public void bounceY() {
		velocity.y *= -1;
	}
	
	/*
	 * ball hits far right side, take abs value and increase x velocity
	 * ball hits right hand side, rebound proportional to theta. so make negative but x velocity --> 0
	 * ball hits left hand side, rebound proportional to theta. so make positive but x velocity --> 0
	 * ball hits far left side, make negative and decrease x velocity
	 * ball hits middle, x velocity --> 0
	 */
	
	/**
	 * Move the ball according to its current velocity over the given time
	 * period.
	 * 
	 * @param time
	 *            the time elapsed in seconds
	 */
	public void move(float time) {
		ballCirc.x += time * velocity.x;
		ballCirc.y += time * velocity.y;
	}
	
	public void randomizeVelocity() {
		int xFactor = (int) (100f + Math.random() * 90f);
		int yFactor = (int) Math.sqrt((200 * 200) - (xFactor * xFactor));
		velocity.x = xFactor;
		velocity.y = yFactor + 200;
	}
	
	/**
	 * Method for powerup to slow the ball
	 */
	public void slowBall () {
		this.multiplier = 0.8f;
		this.velocity.y *= multiplier;
	}

}