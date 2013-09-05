package deco2800.arcade.burningskies.entities;

import deco2800.arcade.burningskies.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlayerShip extends Ship {

	/**
	 * Construct a playable ship for the user(s).
	 * @ensure health && hitbox1 && hitbox2 > 0
	 */
	public PlayerShip(int health, Texture image, Vector2 pos) {
		super(health, image, pos);
	}
	
	/**
	 * Sets the current speed of the ship. 
	 */
	@Override
	public void velocity(float speed) {
		this.velocity = speed;		
	}

	/**
	 * Moves the player ship in the X direction.
	 */
	void moveX(float x) {
		position.x += x;
	}
	
	/**
	 * Moves the player ship in the Y direction.
	 */
	void moveY(float y) {
		position.y += y;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		outBounds(delta);
	}
	
	@Override
	/**
	 * Keeps the player within the screen bounds.(hopefully)
	 */
	public void outBounds(float delta) {
		if (position.x > BurningSkies.SCREENWIDTH + getImageWidth()) {
			position.x = BurningSkies.SCREENWIDTH - getImageWidth();
		}
    	if (position.x < 0) position.x = 0;
    	
		if (position.y > BurningSkies.SCREENHEIGHT - getImageHeight()) {
			position.y = BurningSkies.SCREENHEIGHT - getImageHeight();
		}
    	if (position.y < 0) position.y = 0;
    	
    	if(Gdx.input.isKeyPressed(Keys.UP)) {
    		moveY(this.velocity * delta);
    	}
    	if(Gdx.input.isKeyPressed(Keys.DOWN)) {
    		moveY(-this.velocity * delta);
    	}
    	if(Gdx.input.isKeyPressed(Keys.LEFT)) {
    		moveX(this.velocity * delta);
    	}
    	if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
    		moveX(-this.velocity * delta);
    	}
	}
	
	/**
	 * Fire a shot.
	 */
	public void shot(float delta) {
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			//BulletPattern PBullet = new PBullet(stage, null)?? 
		}
	}
}