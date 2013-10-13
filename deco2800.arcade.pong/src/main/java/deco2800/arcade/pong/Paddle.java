package deco2800.arcade.pong;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
	
	private float renderColourRed;
	private float renderColourGreen;
	private float renderColourBlue;
	private float renderColourAlpha;
	
	Rectangle bounds = new Rectangle(); // The position (x,y) and dimensions (width,height) of the paddle
    
    // we use this to keep track of what way we're moving so that
    // we can award the Slider achievement
    public int direction = 0;

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
	 * Set the colour of the rendered paddle.
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
	 * Render the paddle.
	 * @param shapeRenderer The current {@link ShapeRenderer} instance.
	 */
	public void render(ShapeRenderer shapeRenderer)
	{
	    shapeRenderer.setColor(renderColourRed,
	                           renderColourGreen,
	                           renderColourBlue,
	                           renderColourAlpha);
	    shapeRenderer.rect(this.bounds.x,
	                             this.bounds.y,
	                             this.bounds.width,
	                             this.bounds.height);
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
	public void update(Ball ball) {
		//Clamp paddle within screen boundaries
    	if (bounds.y > Pong.SCREENHEIGHT - bounds.height) {
    		bounds.y = Pong.SCREENHEIGHT - bounds.height;
    	} else if (bounds.y < 0) { 
    		bounds.y = 0;
    	}
	}
	
}
