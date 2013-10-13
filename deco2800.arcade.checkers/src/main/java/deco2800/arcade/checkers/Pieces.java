package deco2800.arcade.checkers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * A basic implementation of a playing piece for checkers
 * @author shewwiii
 *
 */
public abstract class Pieces {
	
	static final float WIDTH = 30f; // The width of the pieces
	static final float INITHEIGHT = 30f; //The initial height of the pieces
	
	private float renderColourRed;
	private float renderColourGreen;
	private float renderColourBlue;
	private float renderColourAlpha;
	
	Rectangle bounds = new Rectangle(); // The position (x,y) and dimensions (width,height) of the pieces
	
	/**
	 * Basic constructor for pieces
	 * @param position the initial position of the pieces
	 */
	public Pieces(Vector2 position) {
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.width = WIDTH;
		this.bounds.height = INITHEIGHT;
	}
	
	/**
	 * Move the pieces
	 * @param y distance to move the pieces up (y<0 for down)
	 */
	public void move(float x, float y) {
		bounds.x += x;
		bounds.y += y;
	}
	
	/**
	 * Set the colour of the rendered pieces.
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
	 * Render the pieces.
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
	 * Set the position of the pieces
	 * @param newPosition the new position of the pieces
	 */
	public void setPosition(Vector2 newPosition) {
		bounds.x = newPosition.x;
		bounds.y = newPosition.y;
	}

	
}
