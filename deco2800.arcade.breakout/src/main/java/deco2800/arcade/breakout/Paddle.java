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

	private static final float WIDTH = 128f;
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

	// Move the paddle by a given amount
	public void movement(float horizontal) {
		paddleShape.x += horizontal;
	}

	// Render the paddle red
	public void render(ShapeRenderer render) {
		render.setColor(Color.RED);
		render.filledRect(paddleShape.x, paddleShape.y, paddleShape.width,
				paddleShape.height);
	}

	// Set the position of the paddle to a given vector position
	public void setPosition(Vector2 iniPosition) {
		paddleShape.x = iniPosition.x;
		paddleShape.y = iniPosition.y;
	}

	// Ensure the paddle stays in bounds
	public void update(Ball ball) {
		if (paddleShape.x > Breakout.SCREENWIDTH - paddleShape.width)
			paddleShape.x = Breakout.SCREENWIDTH - paddleShape.width;
		else if (paddleShape.x < 0)
			paddleShape.x = 0;
	}
}