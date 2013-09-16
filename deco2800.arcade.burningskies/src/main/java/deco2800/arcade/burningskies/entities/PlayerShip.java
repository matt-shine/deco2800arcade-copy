package deco2800.arcade.burningskies.entities;

import deco2800.arcade.burningskies.*;
import deco2800.arcade.burningskies.entities.bullets.BulletPattern;
import deco2800.arcade.burningskies.entities.bullets.PlayerPattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlayerShip extends Ship {
	
	private BulletPattern playerBullets;
	private final float MAXVELOCITY = 600;
	private PlayScreen screen;
	
	//direction handling
	private boolean left = false, right = false, up = false, down = false;
	private boolean shooting = false;

	/**
	 * Construct a playable ship for the user(s).
	 * @ensure health && hitbox1 && hitbox2 > 0
	 */
	public PlayerShip(int health, Texture image, Vector2 position, PlayScreen screen) {
		super(health, image, position);
		this.screen = screen;
		hitboxScale = 0.25f; // lets the player 'just miss' bullets
	}
	
	/**
	 * Keeps the player within the screen bounds.(hopefully)
	 */
	public void onRender(float delta) {
		// reset
		velocity.set(0, 0);
    	if(up) {
    		velocity.add(0, MAXVELOCITY);
    	}
    	if(down) {
    		velocity.add(0, -MAXVELOCITY);
    	}
    	if(left) {
    		velocity.add(-MAXVELOCITY, 0);
    	}
    	if(right) {
    		velocity.add(MAXVELOCITY, 0);
    	}
    	position.add( velocity.x * delta, velocity.y * delta );
		if (position.x + getWidth() > BurningSkies.SCREENWIDTH) {
			position.x = BurningSkies.SCREENWIDTH - getImageWidth();
		}
    	if (position.x < 0) position.x = 0;
    	
		if (position.y + getHeight() > BurningSkies.SCREENHEIGHT) {
			position.y = BurningSkies.SCREENHEIGHT - getImageHeight();
		}
    	if (position.y < 0) position.y = 0;
		setX(position.x);
		setY(position.y);
		this.setZIndex(getStage().getActors().size); // this is silly, but no better way
		shoot(delta);
		
	}
	
	public void setUp(boolean dir) {
		down = false;
		up = dir;
	}
	
	public void setDown(boolean dir) {
		up = false;
		down = dir;
	}
	
	public void setLeft(boolean dir) {
		right = false;
		left = dir;
	}
	
	public void setRight(boolean dir) {
		left = false;
		right = dir;
	}
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	/**
	 * Fire a shot.
	 */
	public void shoot(float delta) {
		if(playerBullets == null) {
			playerBullets = new PlayerPattern(this,screen);
			getStage().addActor(playerBullets);
		}
		if (shooting) {
			if(!playerBullets.isFiring()) {
				playerBullets.start();
			}
		} else {
			if(playerBullets.isFiring()) playerBullets.stop();
		}
	}
}