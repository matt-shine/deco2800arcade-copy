package deco2800.arcade.pong;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * A basic implementation of the ball class, responsible for the behaviour of the ball
 * including movement, bouncing, dimensions and positioning
 * @author uqjstee8
 *
 */
public class Ball {

	public static final float WIDTH = 20f; //How big is the ball (its a square)
	public static final float INITIALSPEED = 200; // How fast is the ball going at the start of a point
	public static final float BOUNCEINCREMENT = 1.1f; // How much does the ball speed up each time it gets hit
	
	Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the ball
	Vector2 velocity = new Vector2(); // The current velocity of the ball as x,y
	
	private float renderColourRed;
    private float renderColourGreen;
    private float renderColourBlue;
    private float renderColourAlpha;
	
	/**
	 * Basic constructor for Ball. Set position and dimensions to the default
	 */
	public Ball() {
		bounds.x = Pong.SCREENWIDTH/2 - Ball.WIDTH/2;
		bounds.y = Pong.SCREENHEIGHT/2 - Ball.WIDTH/2;
		bounds.height = WIDTH;
		bounds.width = WIDTH;
	}

	/**
	 * Modify the velocity of the ball
	 * @param newVelocity the new velocity of ball as x,y
	 */
	public void setVelocity(Vector2 newVelocity) {
		this.velocity.x = newVelocity.x;
		this.velocity.y = newVelocity.y;
	}
	
	/**
	 * Modify the position of the ball
	 * @param newPosition the new position of the ball as x,y
	 */
	public void setPosition(Vector2 newPosition) {
		bounds.x = newPosition.x;
		bounds.y = newPosition.y;
	}
	
	/**
	 * Reverse the X direction of the ball when bouncing off the left or right paddle.
	 * Each bounce off a paddle changes the speed of the ball.
	 * @see deco2800.arcade.pong.Ball.BOUNCEINCREMENT
	 */
	public void bounceX() {
		//This is naive
		velocity.x *= -1;
		// velocity = velocity.mul(BOUNCEINCREMENT);
	}

	/**
	 * Move the ball according to its current velocity over the given time period.
	 * @param time the time elapsed in seconds
	 */
	public void move(float time) {
		bounds.x += time*velocity.x;
		bounds.y += time*velocity.y;
	}

	/**
	 * Reverse the Y component of the ball's direction due to bouncing off the top or bottom wall.
	 * Note that since this is not because of a paddle, it does not affect the speed of the ball.
	 */
	public void bounceY() {
		velocity.y *= -1;
	}
	
	/**
	 * Reset the ball to its initial position and velocity
	 */
	public void reset() {
		velocity.x = 0;
		velocity.y = 0;
		bounds.x = Pong.SCREENWIDTH/2 - Ball.WIDTH/2;
		bounds.y = Pong.SCREENHEIGHT/2 - Ball.WIDTH/2;
	}
	
	/**
     * Set the colour of the rendered ball.
     * @param r Red (0-1)
     * @param g Green (0-1)
     * @param b Blue (0-1)
     * @param a Alpha (0-1)
     */
    public void setColor(float r, float g, float b, float a)
    {
        renderColourRed = r;
        renderColourGreen = g;
        renderColourBlue = b;
        renderColourAlpha = a;
    }
    
    /**
     * Render the ball.
     * @param shapeRenderer The current {@link ShapeRenderer} instance.
     */
    public void render(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setColor(renderColourRed,
                               renderColourGreen,
                               renderColourBlue,
                               renderColourAlpha);
        shapeRenderer.filledRect(this.bounds.x,
                                 this.bounds.y,
                                 this.bounds.width,
                                 this.bounds.height);
    }
	
	/**
	 * Generate and assign a random velocity to the ball. The straight-line speed is constant
	 * but the X/Y components are randomly determined.
	 */
	public void randomizeVelocity() {
		//TODO This is a bit of a hack. A better way would be to generate an angle then use sin/cos/tan to work out the X,Y components
		int xFactor = (int) (100f + Math.random()*90f);
		int yFactor = (int) Math.sqrt((200*200) - (xFactor*xFactor));
		velocity.x = xFactor;
		velocity.y = yFactor;
	}
}
