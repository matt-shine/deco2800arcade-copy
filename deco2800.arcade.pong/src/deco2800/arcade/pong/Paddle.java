package deco2800.arcade.pong;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * A basic implementation of a Paddle for the pong game.
 * @author uqjstee8
 *
 */
public abstract class Paddle {
	
	static final float WIDTH = 10f; // The width of the paddle
	static final float INITHEIGHT = 64f; //The initial height of the paddle
	
	Rectangle bounds = new Rectangle(); // The position (x,y) and dimensions (width,height) of the paddle
	
	/**
	 * Basic constructor for paddle
	 * @param position the initial position of the paddle
	 */
	public Paddle(Vector2 position) {
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.width = WIDTH;
		this.bounds.height = INITHEIGHT;
	}
	
	/**
	 * Move the paddle up or down.
	 * @param y distance to move the paddle up (y<0 for down)
	 */
	public void move(float y) {
		bounds.y += y;
	}
	
	/**
	 * Set the position of the paddle
	 * @param newPosition the new position of the paddle
	 */
	public void setPosition(Vector2 newPosition) {
		bounds.x = newPosition.x;
		bounds.y = newPosition.y;
	}
	
	/**
	 * Handle in-point updating of the paddle
	 * @param ball 
	 */
	public abstract void udpate(Ball ball);
	
}
