package deco2800.arcade.burningskies.entities;

import deco2800.arcade.burningskies.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class Player extends Ship {

	/**
	 * Construct a playable ship for the user(s).
	 * @ensure health && hitbox1 && hitbox2 > 0
	 */
	public Player(int health, Vector2 hitbox1, Vector2 hitbox2) {
		super(health, hitbox1, hitbox2);
	}

	/**
	 * Sets the current speed of the ship. 
	 */
	@Override
	void velocity(float speed) {
		this.velocity = speed;		
	}

	/**
	 * Moves the player ship in the X direction.
	 */
	void moveX(float x) {
		bounds.x += x;
	}
	
	/**
	 * Moves the player ship in the Y direction.
	 */
	void moveY(float y) {
		bounds.y += y;
	}
	
	@Override
	/**
	 * Keeps the player within the screen bounds.(hopefully)
	 */
	void outBounds() {
		if (bounds.x > BurningSkies.SCREENWIDTH - bounds.width) {
			bounds.x = BurningSkies.SCREENWIDTH - bounds.width;
		}
    	if (bounds.x < 0) bounds.x = 0;
    	
		if (bounds.y > BurningSkies.SCREENHEIGHT - bounds.height) {
			bounds.y = BurningSkies.SCREENHEIGHT - bounds.height;
		}
    	if (bounds.y < 0) bounds.y = 0;
    	
    	if(Gdx.input.isKeyPressed(Keys.UP)) {
    		moveY(this.velocity * Gdx.graphics.getDeltaTime());
    	}
    	if(Gdx.input.isKeyPressed(Keys.DOWN)) {
    		moveY(-this.velocity * Gdx.graphics.getDeltaTime());
    	}
    	if(Gdx.input.isKeyPressed(Keys.LEFT)) {
    		moveX(this.velocity * Gdx.graphics.getDeltaTime());
    	}
    	if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
    		moveX(-this.velocity * Gdx.graphics.getDeltaTime());
    	}
	}
}