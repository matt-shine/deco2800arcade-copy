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
	public static final float MAX_X_VELOCITY = 800f;
	// rectangular bounds of the ball
	Rectangle bounds = new Rectangle();
	// velocity of the ball
	Vector2 velocity = new Vector2();

	// Variables to store the render colour
	private float renderColourRed;
	private float renderColourGreen;
	private float renderColourBlue;
	private float renderColourAlpha;

	/**
	 * Create a ball where the paddle is located
	 */
	public Ball() {
		bounds.x = Breakout.SCREENWIDTH / 2 - Ball.WIDTH / 2 + 64;
		bounds.y = 35;
		bounds.height = WIDTH;
		bounds.width = WIDTH;
	}
	
	// Getter method for the balls X position
	public float getX() {
		return bounds.x;
	}
	
	// Getter method for the balls Y position
	public float getY() {
		return bounds.y;
	}

	// Setter method for the X Velocity of the ball
	public void setXVelocity(float newVelocity) {
		if (newVelocity > MAX_X_VELOCITY) {
			this.velocity.x = MAX_X_VELOCITY;
			return;
		}
		if (newVelocity < -MAX_X_VELOCITY) {
			this.velocity.x = -MAX_X_VELOCITY;
			return;
		}
		this.velocity.x = newVelocity;
	}

	// Reset the ball to where the paddle is
	public void reset(Vector2 paddlePos) {
		velocity.x = 0;
		velocity.y = 0;
		bounds.x = paddlePos.x - Ball.WIDTH / 2 + 64;
		bounds.y = paddlePos.y + 25;
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
		shapeRenderer.filledRect(this.bounds.x, this.bounds.y,
				this.bounds.width, this.bounds.height);
	}

	// determine the new x velocity the ball will bounce at
	public void updateVelocity(float lastHitX, float lastHitY, Paddle paddle) {
		float currentHitX = bounds.x;
		float currentHitY = bounds.y;
		double angle = Math.atan2(lastHitY - currentHitY, lastHitX
				- currentHitX);
		if ((Gdx.input.isKeyPressed(Keys.RIGHT))
				|| (Gdx.input.isKeyPressed(Keys.LEFT))) {
			angle = angle + 1/2 * angle;
		}
		float pX = paddle.getPaddleX();
		float pWidth = paddle.getWidth();
		float bX = this.bounds.x;
		float newVelocity = 0f;
		// Handles the ball if it hits the left end of the paddle
		if (bX < pX + pWidth/7) {
			newVelocity = - (Math.abs(getXVelocity()) + 100 + 80
					* (float) Math.cos(angle));
		// Handles the ball if it hits between the left end and the middle	
		} else if (bX >= pX + pWidth/7 && bX < pX + 3*pWidth/7) {
			if (getXVelocity() < 0) {
				newVelocity = (getXVelocity() + 40
					* (float) Math.cos(angle));
			} else {
				newVelocity = (getXVelocity() - 40
						* (float) Math.cos(angle));
			}
		// Handles the ball if it hit in the middle of the paddle	
		} else if (bX >= pX + 3*pWidth/7 && bX < pX + 4*pWidth/7) {
			newVelocity = (getXVelocity()
					* (float) Math.cos(angle));
		// Handles the ball if it hits between the right end and the middle
		} else if (bX >= pX + 4*pWidth/7 && bX < pX + 6*pWidth/7) {
			if (getXVelocity() < 0) {
				newVelocity = (getXVelocity() + 40
					* (float) Math.cos(angle));
			} else {
				newVelocity = (getXVelocity() - 40
						* (float) Math.cos(angle));
			}
		// Handles the ball if it hits the right end of the paddle
		} else if (bX >= pX + 6*pWidth/7 && bX <= pX + pWidth) {
			newVelocity = 100 + (Math.abs(getXVelocity()) + 80
					* (float) Math.cos(angle));
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
		bounds.x += time * velocity.x;
		bounds.y += time * velocity.y;
	}
	
	public void randomizeVelocity() {
		// TODO This is a bit of a hack. A better way would be to generate an
		// angle then use sin/cos/tan to work out the X,Y components
		int xFactor = (int) (100f + Math.random() * 90f);
		int yFactor = (int) Math.sqrt((200 * 200) - (xFactor * xFactor));
		velocity.x = xFactor;
		velocity.y = yFactor + 200;
	}

}