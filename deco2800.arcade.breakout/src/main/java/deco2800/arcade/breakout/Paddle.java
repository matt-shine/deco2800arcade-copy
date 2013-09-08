package deco2800.arcade.breakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author Naveen
 * 
 */

public abstract class Paddle {

<<<<<<< HEAD
	private static final float WIDTH = 128f;
	private static final float HEIGHT = 20f;

	Rectangle paddleShape = new Rectangle();
	
	/**
	 * Create a new paddle at a given vector position.
	 * 
	 * @param position
=======
	// Sets the size of the paddle
	public static float width = 128f;
	public static float height = 20f;

	Rectangle paddleShape = new Rectangle();

	/**
	 * Stores the details of the paddle's size and current position
>>>>>>> 1b74204fa1d74e176c155243f8756694379c2594
	 */
	public Paddle(Vector2 position) {
		this.paddleShape.x = position.x;
		this.paddleShape.y = position.y;
		this.paddleShape.width = WIDTH;
		this.paddleShape.height = HEIGHT;
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
		return WIDTH;
	}

<<<<<<< HEAD
	// Move the paddle by a given amount
=======
	/**
	 * Moves the paddle horizontally
	 */
>>>>>>> 1b74204fa1d74e176c155243f8756694379c2594
	public void movement(float horizontal) {
		paddleShape.x += horizontal;
	}

<<<<<<< HEAD
	// Render the paddle red
=======
	/**
	 * 
	 * @param render
	 */
>>>>>>> 1b74204fa1d74e176c155243f8756694379c2594
	public void render(ShapeRenderer render) {
		render.setColor(Color.RED);
		render.filledRect(paddleShape.x, paddleShape.y, paddleShape.width,
				paddleShape.height);
	}

<<<<<<< HEAD
	// Set the position of the paddle to a given vector position
=======
	/**
	 * Sets the Position of the Paddle
	 * 
	 * @param iniPosition
	 */
>>>>>>> 1b74204fa1d74e176c155243f8756694379c2594
	public void setPosition(Vector2 iniPosition) {
		paddleShape.x = iniPosition.x;
		paddleShape.y = iniPosition.y;
	}

<<<<<<< HEAD
	// Ensure the paddle stays in bounds
	public void update(Ball ball) {
=======
	/**
	 * Stops the paddle from going outside the right and left side of the
	 * screens
	 * 
	 * @param ball
	 */
	public void update(PongBall ball) {
>>>>>>> 1b74204fa1d74e176c155243f8756694379c2594
		if (paddleShape.x > Breakout.SCREENWIDTH - paddleShape.width)
			paddleShape.x = Breakout.SCREENWIDTH - paddleShape.width;
		else if (paddleShape.x < 0)
			paddleShape.x = 0;
	}
}