package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * 
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
	
	public void move() {
		this.y -= 2;
	}
	
	public int getNumActive() {
		return this.numActive;
	}
	
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public void render(SpriteBatch b, Sprite s) {
		sBatch = b;
		sBatch.begin();
		sBatch.draw(s, x, y);
		sBatch.end();
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, 128, 40);
	}
	
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void applyPowerup() {
		// see sub classes
		
	}
	
	
}
