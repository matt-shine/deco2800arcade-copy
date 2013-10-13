package deco2800.arcade.breakout;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author Naveen
 * 
 */

public abstract class Paddle {

	private float width = 128f;
	private static final float HEIGHT = 20f;

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
	
	public void decreaseSize() {
		this.paddleShape.width = width/2;
	}
	
	public void increaseSize() {
		this.paddleShape.width = width*2;
	}
	
	public void resizePaddle() {
			this.paddleShape.width = getWidth();
	}
	
	// Getter method for the paddles x position
	public float getPaddleX() {
		return this.paddleShape.x;
	}
	
	// Getter method for the paddles y position
	public float getPaddleY() {
		return this.paddleShape.y;
	}
	
	// Getter method for the paddles width
	public float getWidth() {
		return width;
	}
	
	//Sets the width
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
	 * render the paddle red
	 * @param render
	 */
	public void render(ShapeRenderer render) {
		render.setColor(0.7f, 0.7f, 0.7f, 0.5f);
		render.rect(paddleShape.x, paddleShape.y, paddleShape.width,
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