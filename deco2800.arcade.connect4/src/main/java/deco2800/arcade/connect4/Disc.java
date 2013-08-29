package deco2800.arcade.connect4;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Disc {

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
	public Disc() {
		bounds.x = Connect4.SCREENWIDTH/2 - Disc.WIDTH/2;
		bounds.y = Connect4.SCREENHEIGHT/2 - Disc.WIDTH/2;
		bounds.height = WIDTH;
		bounds.width = WIDTH;
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
	 * Move the ball according to its current velocity over the given time period.
	 * @param time the time elapsed in seconds
	 */
	public void moveLeft(float time) {
		bounds.x -= 1;
		//bounds.y += time*velocity.y;
	}
	
	public void moveRight(float time){
		bounds.x += 1;
	}
	
	public void moveDown(float time){
		bounds.y -= 1;
	}
	
	/**
	 * Reset the ball to its initial position and velocity
	 */
	public void reset() {
		bounds.x = Connect4.SCREENWIDTH/2 - Disc.WIDTH/2;
		bounds.y = Connect4.SCREENHEIGHT/2 - Disc.WIDTH/2;
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
        shapeRenderer.filledCircle(this.bounds.x, this.bounds.y, this.bounds.width);
    }
	
}
