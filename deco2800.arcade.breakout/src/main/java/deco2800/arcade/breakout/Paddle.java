package deco2800.arcade.breakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * Manages the values of the paddle. Controls the size and position of the
 * paddle as well as rendering the colour.
 * 
 * @author Naveen
 * 
 */

public abstract class Paddle {

	private float width = 128f;
	private static final float HEIGHT = 20f;
	private static final float STANDARDWIDTH = 128f;

	Rectangle paddleShape = new Rectangle();

	/**
	 * Create a new paddle at a given vector position.
	 * 
	 * @param position
	 */
	public Paddle(Vector2 position) {
		this.paddleShape.x = position.x;
		this.paddleShape.y = position.y;
		this.paddleShape.width = width;
		this.paddleShape.height = HEIGHT;
	}

	/**
	 * Return the Standard width size of the paddle
	 * 
	 * @return float STANDARDWIDTH
	 */
	public float getStandardWidth() {
		return STANDARDWIDTH;
	}

	/**
	 * Decreases the size of the paddle by halving the current size
	 */
	public void decreaseSize() {
		this.paddleShape.width = getPaddleShapeWidth() / 2;
	}

	/**
	 * Increases the size of the paddle by doubling the current size
	 */
	public void increaseSize() {
		this.paddleShape.width = getPaddleShapeWidth() * 2;
	}

	/**
	 * Returns the current size of the width
	 * 
	 * @return float width
	 */
	public float getPaddleShapeWidth() {
		return this.paddleShape.width;
	}

	/**
	 * Resizes the paddle by setting the new width value to the paddle width
	 */
	public void resizePaddle() {
		this.paddleShape.width = getWidth();
	}

	/**
	 * Returns the x position of the paddle
	 * 
	 * @return float x
	 */
	public float getPaddleX() {
		return this.paddleShape.x;
	}

	/**
	 * Returns the y position of the paddle
	 * 
	 * @return float y
	 */
	public float getPaddleY() {
		return this.paddleShape.y;
	}

	/**
	 * Returns the value of the width
	 * 
	 * @return float width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Sets given float to become the width value and calls the resizePaddle
	 * method
	 * 
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
		resizePaddle();
	}

	/**
	 * Moves the paddle horizontally
	 */
	public void movement(float horizontal) {
		paddleShape.x += horizontal;
	}

	/**
	 * Render the paddle to white
	 * 
	 * @param render
	 */
	public void render(ShapeRenderer render) {
		render.setColor(1f, 1f, 1f, 0.5f);
		render.filledRect(paddleShape.x, paddleShape.y, paddleShape.width,
				paddleShape.height);
	}

	/**
	 * Sets the Position of the Paddle
	 * 
	 * @param iniPosition
	 */
	public void setPosition(Vector2 iniPosition) {
		paddleShape.x = iniPosition.x;
		paddleShape.y = iniPosition.y;
	}

	/**
	 * Stops the paddle from going outside the right and left side of the
	 * screens
	 * 
	 * @param ball
	 */
	public void update(Ball ball) {
		if (paddleShape.x > Breakout.SCREENWIDTH - paddleShape.width)
			paddleShape.x = Breakout.SCREENWIDTH - paddleShape.width;
		else if (paddleShape.x < 0)
			paddleShape.x = 0;
	}
}