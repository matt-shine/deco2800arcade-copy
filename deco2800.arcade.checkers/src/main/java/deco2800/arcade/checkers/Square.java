package deco2800.arcade.checkers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A basic implementation of a Square for the checkers game.
 * @author shewwiii
 *
 */
public class Square {
	
	static final float WIDTH = 50f; // The width of the square
	static final float HEIGHT = 50f; //The initial height of the square
	
	private float renderColourRed;
	private float renderColourGreen;
	private float renderColourBlue;
	private float renderColourAlpha;
	
	Rectangle bounds = new Rectangle(); // The position (x,y) and dimensions (width,height) of the square
	
	/**
	 * Basic constructor for squares
	 * @param position the initial position of the square
	 */
	public Square(Vector2 position) {
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.width = WIDTH;
		this.bounds.height = HEIGHT;
	}
	
	/**
	 * Set the colour of the rendered square.
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
	 * Render the square.
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
	 * Set the position of the square
	 * @param newPosition the new position of the square
	 */
	public void setPosition(Vector2 newPosition) {
		bounds.x = newPosition.x;
		bounds.y = newPosition.y;
	}

}
