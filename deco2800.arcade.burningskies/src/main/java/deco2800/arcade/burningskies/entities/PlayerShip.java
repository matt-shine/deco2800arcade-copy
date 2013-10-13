package deco2800.arcade.burningskies.entities;

import deco2800.arcade.burningskies.*;
import deco2800.arcade.burningskies.entities.bullets.BulletPattern;
import deco2800.arcade.burningskies.entities.bullets.PlayerPattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlayerShip extends Ship {
	
	private BulletPattern playerBullets;
	private float maxVelocity = 300; //Changed from static to dynamic.
	private int maxHealth; //For health powerups.
	private PlayScreen screen;
	
	//brute force way to handle timer
	private long initialTime;
	private boolean speedUp = false;
	
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
		this.maxHealth = health;
		hitboxScale = 0.25f; // lets the player 'just miss' bullets
	}
	
	@Override
	public void heal(int healthchange) {
		this.health += healthchange;
		//Just for the sake of the health bar being consistent.
		//This probably will have to be changed if we plan to be able to heal over the maxHealth
		//though.
		if (health > maxHealth) {
			this.health = maxHealth;
		}
	}
	
	/**
	 * Keeps the player within the screen bounds.(hopefully)
	 */
	public void onRender(float delta) {
		super.onRender(delta);
		//resets any powerups if needed. Initial testing on speedup only.
		//easily applies to a bullet pattern.
		if (speedUp) {	
			long currentTime = System.currentTimeMillis();
			if ((currentTime - initialTime) >= 10000) {
				maxVelocity = 300;
				speedUp = false;
			}
		}
		
		// reset
		velocity.set(0, 0);
    	if(up) {
    		velocity.add(0, maxVelocity);
    	}
    	if(down) {
    		velocity.add(0, -maxVelocity);
    	}
    	if(left) {
    		velocity.add(-maxVelocity, 0);
    	}
    	if(right) {
    		velocity.add(maxVelocity, 0);
    	}
    	//normalise our velocity
    	velocity.nor();
    	velocity.scl(maxVelocity);
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
	 * Changes the max velocity of the ship.
	 */
	public void setMaxSpeed(float velocity) {
		speedUp = true;
		initialTime = System.currentTimeMillis();
		maxVelocity = velocity;
	}
	
	/**
	 * Changes the players current bullet type.
	 * @ensure pattern != NULL
	 */
	public void setBulletPattern(BulletPattern pattern) {
		playerBullets.remove();
		playerBullets = pattern;
		getStage().addActor(playerBullets);
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