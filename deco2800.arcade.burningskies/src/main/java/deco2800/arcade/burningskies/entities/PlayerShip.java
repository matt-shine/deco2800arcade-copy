package deco2800.arcade.burningskies.entities;

import deco2800.arcade.burningskies.*;
import deco2800.arcade.burningskies.entities.bullets.BombPattern;
import deco2800.arcade.burningskies.entities.bullets.BulletPattern;
import deco2800.arcade.burningskies.entities.bullets.PlayerPattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlayerShip extends Ship {
	
	private BulletPattern playerBullets;
	
	private float maxHealth; //For health powerups.
	private int lives = 3;
	private PlayScreen screen;
	
	private float speedTimer = 0f;
	private boolean speedUp = false;
	private float patternTimer = 0f;
	private boolean patternUp = false;
	
	//direction handling
	private boolean left = false, right = false, up = false, down = false;
	private boolean shooting = false;
	
	//mouse pointer
	private Vector2 mousePos = new Vector2();
	
	// movement 
	private float acceleration = 2000f;
	private float deceleration = 400f;
	private float maxVelocity = 300; //Changed from static to dynamic.

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
		if (health > maxHealth) {
			this.health = maxHealth;
		}
	}
	
	@Override
	public void damage(float damage) {
		super.damage(damage);
		if(!isAlive()) {
			new BombPattern(this, screen).fire(0, getCenterX(), getCenterY());
			lives -= 1;
			screen.killPlayer();
		}
	}
	
	@Override
	public boolean remove() {
		if(playerBullets != null) playerBullets.stop();
		if(getStage() != null) {
			getStage().addActor(new Explosion(getX() + getWidth()/2,getY() + getHeight()/2, 0));
		}
		return super.remove();
	}
	
	
	/**
	 * Keeps the player within the screen bounds.(hopefully)
	 */
	public void onRender(float delta) {
		super.onRender(delta);
		//resets any powerups if needed. Initial testing on speedup only.
		//easily applies to a bullet pattern.
		if (speedUp) {	
			speedTimer -= delta;
			if (speedTimer <= 0) {
				maxVelocity = 300;
				speedUp = false;
			}
		}
		if (patternUp) {	
			patternTimer -= delta;
			if (patternTimer <= 0) {
				setBulletPattern(null);
				patternUp = false;
			}
		}

		// reset
//		velocity.set(0, 0);
    	if(up) {
    		velocity.add(0, (acceleration*delta) );
    		if(velocity.y > maxVelocity)
    			velocity.y = maxVelocity;
    	}
    	if(down) {
    		velocity.add(0, -(acceleration*delta) );
    		if (velocity.y < -maxVelocity)
    			velocity.y = -maxVelocity;
    	}
    	if(left) {
    		velocity.add(-(acceleration*delta), 0);
    		if (velocity.x < -maxVelocity)
    			velocity.x = -maxVelocity;
    	}
    	if(right) {
    		velocity.add( (acceleration*delta), 0);
    		if (velocity.x > maxVelocity)
    			velocity.x = maxVelocity;
    	}
    	
    	decelShip(velocity, delta);
    	
    	//normalise our velocity
//    	velocity.nor();
//    	velocity.mul(maxVelocity);
		
		// Set the position of the ship
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
		
		// Get user mouse input and set ship rotation		
		mousePos.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()); // reversed y
		mousePos.sub(getCenterX(), getCenterY()); // gotta have it centered
		setRotation(mousePos.angle()-90);
		setZIndex(getStage().getActors().size); // this is silly, but no better way
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
		maxVelocity = velocity;
		speedTimer = 10f;
	}
	
	/**
	 * Changes the players current bullet type.
	 */
	public void setBulletPattern(BulletPattern pattern, boolean timeLimited) {
		if(playerBullets != null) {
			playerBullets.remove();
		}
		playerBullets = pattern;
		if(playerBullets != null) {
			getStage().addActor(playerBullets);
		}
		if(timeLimited) {
			patternUp = true;
			patternTimer = 10f;
		}
	}
	
	public void setBulletPattern(BulletPattern pattern) {
		setBulletPattern(pattern, false);
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
	
	public void respawn() {
		this.health = this.maxHealth;
		setBulletPattern(null);
		this.maxVelocity = 300;
		this.speedUp = false;
		this.flash = 0f;
		this.velocity.set(0f, 0f);
		position.set(getStage().getWidth()/2 - this.getOriginX(),getStage().getHeight()/2 - this.getOriginY());
	}

	public void upgradeBullets() {
		if(playerBullets instanceof PlayerPattern) {
			((PlayerPattern)playerBullets).upgrade();
		}
	}
	
	public int getLives() {
		return lives;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public BulletPattern getPattern() {
		return this.playerBullets;
	}
	
	private void decelShip(Vector2 v, float delta) {
		if (v.y > 0) {
			v.sub(0, deceleration*delta);
		} 
		else {
			v.add(0, deceleration*delta);
		}
		if( v.x > 0 ) {
			v.sub( (deceleration*delta), 0);
		}
		else {
			v.add( (deceleration*delta), 0);
		}
		
		
	}
}