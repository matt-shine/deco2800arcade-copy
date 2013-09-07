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

	// Sets the size of the paddle
	public static float width = 128f;
	public static float height = 20f;

	Rectangle paddleShape = new Rectangle();

	/**
	 * Stores the details of the paddle's size and current position
	 */
	public Paddle(Vector2 position) {
		this.paddleShape.x = position.x;
		this.paddleShape.y = position.y;
		this.paddleShape.width = width;
		this.paddleShape.height = height;
	}

	/**
	 * Moves the paddle horizontally
	 */
	public void movement(float horizontal) {
		paddleShape.x += horizontal;
	}

	/**
	 * 
	 * @param render
	 */
	public void render(ShapeRenderer render) {
		render.setColor(Color.RED);
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
	public void update(PongBall ball) {
		if (paddleShape.x > Breakout.SCREENWIDTH - paddleShape.width)
			paddleShape.x = Breakout.SCREENWIDTH - paddleShape.width;
		else if (paddleShape.x < 0)
			paddleShape.x = 0;
	}
}