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
	// minimum velocity
	public static final float INITIALSPEED = 400;
	// maximum velocity
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
	
	/**
	 * 
	 * @return the balls x position
	 */
	public float getX() {
		return ballCirc.x;
	}
	
	/**
	 * 
	 * @return the balls y position
	 */
	public float getY() {
		return ballCirc.y;
	}
	
	/**
	 * 
	 * @return the balls radius
	 */
	public float getRadius() {
		return ballCirc.radius;
	}
	
	/**
	 * Checks whether the maximum velocity has been reached
	 * so that we can ensure the maximum velocity is never
	 * exceeded
	 * @param newVelocity - sets the x velocity of the ball
	 */
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
	
	/**
	 * Resets the ball to where the paddle is
	 * @param paddlePos - the x and y coordinates of the paddle
	 */
	public void reset(Vector2 paddlePos) {
		velocity.x = 0;
		velocity.y = 0;
		multiplier = 1;
		ballCirc.x = paddlePos.x - Ball.WIDTH / 2 + 64;
		ballCirc.y = paddlePos.y + 25 + ballCirc.radius;
	}

	/**
	 * 
	 * @return the y velocity of the ball
	 */
	public float getYVelocity() {
		return this.velocity.y;
	}

	/**
	 * 
	 * @return the x velocity of the ball
	 */
	public float getXVelocity() {
		return this.velocity.x;
	}

	/**
	 * 
	 * @param r - sets the colour red to render the ball
	 * @param g - sets the colour green to render the ball
	 * @param b - sets the colour blue to render the ball
	 * @param a - sets alpha to render the ball
	 */
	public void setColor(float r, float g, float b, float a) {
		renderColourRed = r;
		renderColourGreen = g;
		renderColourBlue = b;
		renderColourAlpha = a;
	}

	/**
	 * 
	 * @param shapeRenderer - colours the ball the colour given by
	 * the setColour i.e. colours the ball based on the red, green,
	 * blue and aplha values
	 */
	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(renderColourRed, renderColourGreen,
				renderColourBlue, renderColourAlpha);
		shapeRenderer.filledCircle(this.ballCirc.x, this.ballCirc.y, 
				this.ballCirc.radius);
	}

	/**
	 * Determines the new x velocity the ball will bounce at based
	 * on the x and y coordinates which are used in conjunction 
	 * with basic trigonometry
	 * @param lastHitX - the x coordinate of the last point at which the
	 * ball bounced off something
	 * @param lastHitY - the y coordinate of the last point at which the 
	 * ball bounced off something
	 * @param paddle - the current paddle
	 */
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
		float pWidth = paddle.getPaddleShapeWidth();
		float bX = ballCirc.x;
		float newVelocity = 0f;
		// Handles the ball if it hits the left end of the paddle
		if (bX < pX + 2*pWidth/9) {
			newVelocity = - (Math.abs(getXVelocity()) + 100 + 80
					* cosAngle);
		// Handles the ball if it hits between the left end and the middle	
		} else if (bX >= pX + 2*pWidth/9 && bX < pX + 4*pWidth/9) {
			if (getXVelocity() < 0) {
				newVelocity = (getXVelocity() + 40
					* cosAngle);
			} else {
				newVelocity = (getXVelocity() - 40
						* cosAngle);
			}
		// Handles the ball if it hit in the middle of the paddle	
		} else if (bX >= pX + 4*pWidth/9 && bX < pX + 5*pWidth/9) {
			newVelocity = (getXVelocity()
					* Math.abs(cosAngle));
		// Handles the ball if it hits between the right end and the middle
		} else if (bX >= pX + 5*pWidth/9 && bX < pX + 7*pWidth/9) {
			if (getXVelocity() < 0) {
				newVelocity = (getXVelocity() + 40
					* cosAngle);
			} else {
				
				newVelocity = (getXVelocity() - 40
						* cosAngle);
			}
		// Handles the ball if it hits the right end of the paddle
		} else if (bX >= pX + 7*pWidth/9 && bX <= pX + pWidth) {
			newVelocity = 100 + (Math.abs(getXVelocity()) + 80
					* cosAngle);
		}
		setXVelocity(newVelocity);
	}
	
	/**
	 * Reverses the x direction of the ball
	 * @param offset - the value to move ball back (to avoid ball
	 * glitching along edges)
	 */
	public void bounceX(int offset) {
		this.ballCirc.x += offset;
		velocity.x *= -1;
	}
	
	/**
	 * Reverses the y direction of the ball
	 * @param offset - the value to move the ball back (to avoid
	 * ball glitching along the top edge of the screen)
	 */
	public void bounceY(int offset) {
		this.ballCirc.y += offset;
		velocity.y *= -1;
	}
		
	/**
	 * Move the ball according to its current velocity over the given time
	 * period
	 * @param time - the time elapsed in seconds
	 */
	public void move(float time) {
		ballCirc.x += time * velocity.x;
		ballCirc.y += time * velocity.y;
	}
	
	/**
	 * Method for randomising the ball given some x and y factor
	 */
	public void randomizeVelocity() {
		int xFactor = (int) (100f + Math.random() * 90f);
		int yFactor = (int) Math.sqrt((200 * 200) - (xFactor * xFactor));
		velocity.x = xFactor;
		velocity.y = yFactor + 200;
	}
	
	/**
	 * Method for the slow ball powerup which slows the ball
	 */
	public void slowBall () {
		this.multiplier = 0.7f;
		this.velocity.y *= multiplier;
	}	
}