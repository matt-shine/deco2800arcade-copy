package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * The superclass for all powerups
 * @author Carlie Smits
 *
 */
public class Powerup {

	private float x,y;
	private SpriteBatch sBatch;
	private Sprite sprite;
	private int numActive = 0;
	
	public Powerup() {
		
	}
	
	/**
	 * Move the powerup down
	 */  
	public void move() {
		this.y -= 2;
	}
	
	/**
	 * 
	 * @return - the number of active powerups
	 */
	public int getNumActive() {
		return this.numActive;
	}
	
	/**
	 * 
	 * @return - getter method for sprite
	 */
	public Sprite getSprite() {
		return this.sprite;
	}
	
	/**
	 * 
	 * @param b - contains rendering details
	 * @param s - sprite to be rendered
	 */
	public void render(SpriteBatch b, Sprite s) {
		sBatch = b;
		sBatch.begin();
		sBatch.draw(s, x, y);
		sBatch.end();
	}
	
	/**
	 * 
	 * @return - a rectangular shape associated with a powerup
	 */
	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, 50, 50);
	}
	
	/**
	 * 
	 * @param x - set the x coordinate of the powerup
	 * @param y - set the y position of the powerup
	 */
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void applyPowerup() {
		// see sub classes
		
	}
	
	
}
