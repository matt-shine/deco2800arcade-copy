package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.cyra.world.Sounds;


/** Player class describes the behaviours, actions and traits of the  
 * character sprite controlled by user.
 * 
 * @author Game Over
 */
public class Player extends MovableEntity{
	
	public enum State {
		IDLE, WALK, JUMP, FALL, DEATH, DASH_JUMP, WALL
	}
	public static final float SPEED = 9f;
	public static final float DASH_SPEED = 13.5f;
	public static final float JUMP_VELOCITY = 20f;
	public static final float WIDTH = 1f;
	public static final float HEIGHT = 1.8f;
	//public static final float WIDTH = 1f;
	//public static final float HEIGHT = 1f;
	public static final float MAX_BOUNCEBACK_TIME = 0.06f;
	public static final float JUMP_TIME = 0.2f;
	public static final float GRAVITY = -1.8f;
	public static final float MAX_FALL_VELOCITY = 30f;
	public static final float MAX_WALL_VELOCITY = 6.2f; //note that any movable platform falling at speed greater than this will have bugged moving
	public static final float WALL_ATTACH_TIME = 0.13f;
	public static final float MAX_INVINCIBLE_TIME = 2.5f;
	public static final int DEFAULT_HEARTS = 4;
	
	private State state = State.IDLE;
	private int hearts = DEFAULT_HEARTS;
	private boolean facingRight = false;
	private boolean canMove = true;
	private boolean onMovable = false;
	private boolean invincible = false;
	private float invincibleTime = 0;
	private boolean bouncingBack = false;
	private float bounceBackTime = 0;
	float jumpTime = 0;
	float wallTime = WALL_ATTACH_TIME;
	private boolean wallClimbEnabled = true;
	private float doubleTapTime = 0;
	private boolean isOverForeground = false;
	private boolean hasDied = false;
	
	/**
	 * Intanciate player.
	 * @param pos
	 */
	
	public Player(Vector2 pos) {
		super (SPEED, 0, pos, WIDTH, HEIGHT);
	}
	
	/**
	 * Set jumpTime back to original value
	 */

	public void resetJumpTime() {
		jumpTime = JUMP_TIME;
		//System.out.println("jumptime RESET to " + JUMP_TIME);
	}
	
	/**
	 * Make jumpTime equal to zero.
	 */
	
	public void clearJumpTime() {
		jumpTime = 0f;
		//System.out.println("jumptime CLEARED to " + 0f);
	}
	
	/**
	 * Set wallTime back to original value
	 */
	
	public void resetWallTime() {
		wallTime = WALL_ATTACH_TIME;
	}
	
	/**
	 * Make wallTime equal to zero
	 */
	
	public void clearWallTime() {
		wallTime = 0f;
	}
	
	
	
	/* ----- Getter methods ----- */
	/**
	 * Get the current state of the player (e.g. jump, walk etc.)
	 * @return state
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Find if state is walk
	 * @return boolean
	 */
	public boolean isWalking(){
		if(state == State.WALK){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Is player invincible
	 * @return boolean
	 */
	public boolean isInvincible() {
		return invincible;
	}
	
	/**
	 * Is player bouncing back
	 * @return boolean
	 */
	public boolean isBouncingBack() {
		return bouncingBack;
	}
	
	/**
	 * Is player facing right
	 * @return boolean
	 */
	public boolean isFacingRight() {
		return facingRight;
	}
	
	/**
	 * Is player on moveable
	 * @return boolean
	 */
	public boolean isOnMovable() {
		return onMovable;
	}
	
	/**
	 * Get current health of player
	 * @return hearts
	 */
	
	public int getHearts() {
		return hearts;
	}
	
	/**
	 * Update position
	 * @return position
	 */
	public Vector2 getNextPos() {
		Vector2 tmp = new Vector2(position); 
		tmp.add(velocity.mul(Gdx.graphics.getDeltaTime()));
		velocity.mul(1/(Gdx.graphics.getDeltaTime()));
		return tmp;
	}
	
	/* ----- Setter methods ----- */
	/**
	 * Make player's position move to simulate a jump
	 */
	public void jump() {
		if (wallClimbEnabled) {
			if (getState() == State.WALL) {
				if (getVelocity().x > 0) {
					setFacingRight(true);
				} else if (getVelocity().x < 0) {
					setFacingRight(false);
				} else {
					setFacingRight(!isFacingRight());
				}
			}
			getVelocity().y = Player.JUMP_VELOCITY;
			setState(Player.State.JUMP);
			resetJumpTime();
			Sounds.playJumpSound(0.5f);
		}
	}
	
	/**
	 * Make player move right
	 */
	public void moveRight() {
		getVelocity().x = SPEED;
		if (getState() != State.WALL) {
			setState(State.WALK);
			setFacingRight(true);
		}
		if (doubleTapTime > 0 && facingRight && state != State.WALL && state != State.JUMP) {
			getVelocity().x = DASH_SPEED;
		} else {
			doubleTapTime = 0.2f;
		}
		bounceBack(false);
		System.out.println("telling to move right");
	}
	/**
	 * Make player move left
	 */
	
	public void moveLeft() {
		getVelocity().x = -1 * SPEED;
		if (getState() != State.WALL) {
			setState(State.WALK);
			setFacingRight(false);
		}
		if (doubleTapTime > 0 && !facingRight && state != State.WALL && state != State.JUMP) {
			getVelocity().x = -DASH_SPEED;
		} else {
			doubleTapTime = 0.2f;
		}
		doubleTapTime = 0.2f;
		bounceBack(false);
	}
	
	/**
	 * Make player bounce back
	 * @param bounce
	 */
	public void bounceBack(boolean bounce) {
		bouncingBack = bounce;
		if(bounce) {
			if(isFacingRight()) {
				getVelocity().x = -2 * SPEED;
			} else {
				getVelocity().x = 2 * SPEED;
			}
		}
	}
	
	/**
	 * Change the value of CanMove.
	 */
	public void setCanMove(boolean canMv) {
		canMove = canMv;
	}
	
	/**
	 * Change the state of the player
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	/**
	 * Make player face right.
	 * @param right
	 */
	public void setFacingRight(boolean right) {
		facingRight = right;
	}
	
	/**
	 * Make player on moveable.
	 * @param onMovable
	 */
	public void setOnMovable(boolean onMovable) {
		this.onMovable = onMovable;
	}
	
	/**
	 * Decrease health of player
	 */
	
	public void decrementHearts() {
		hearts--;
		if (hearts <= 0 ){
			state = State.DEATH;
			hasDied = true;
		}
		return;
	}
	
	/**
	 * Reset player health to original state.
	 */
	public void resetHearts() {
		hearts = DEFAULT_HEARTS;
	}
	
	/**
	 * Make player invincible
	 * @param invisibility
	 */
	public void setInvincibility(boolean invisibility) {
		invincible = invisibility;
	}
	
	/**
	 * Make player solid.
	 */
	@Override
	public boolean isSolid() {
		return true;
	}
	
	/**
	 * Make players position move with a moving platform
	 */
	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		getPosition().y = movablePlatform.getPosition().y + 
				movablePlatform.getCollisionRectangle().height+1/32f;
	}
	
	/**
	 * Collision handling
	 */
	@Override
	public void handleXCollision(Rectangle tile) {
		if (velocity.x > 0.01f) {
			position.x = tile.getX() - getWidth() - 0.001f;
				
		} else if (velocity.x < -0.01f){
			position.x = tile.getX() + tile.getWidth() + 0.001f;
		}
		
		/* Wall slide */
		if (velocity.y < 0) {
		//if (tileUnderShip || ship.getVelocity().y < 0) {
			
			//NOTE: WILL PROBABLY NEED TO INCLUDE SWORD AS A PART OF THE SHIP CLASS NOT IN THE WORLD
			
			//if (getState() != State.WALL) sword.cancel(); //BUG: sword is getting cancelled while on ground against wall
			
			//inputHandler.resetWallTime();
			setState(State.WALL);
			resetWallTime();
			
		}
	}
	
	/**
	 * Collision handling.
	 */
	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform, MovablePlatform movablePlatform) {
		if (velocity.y < 0 ) {
			//System.out.println("Velocity: "+ship.getVelocity().y);
			//System.out.println("DownCollision: " + sRec +" " + tile);
			//ship.getPosition().y = tile.y + ship.getHeight();
			if (onMovablePlatform) {
				velocity.y = -movablePlatform.getSpeed();
			}
			if (!onMovablePlatform) {
				position.y = tile.y + tile.height + 0.001f;
				velocity.y = 0;
			}
			if (velocity.x == 0) {
				setState(State.IDLE);
			} else {
				setState(State.WALK);
			}
			//ship.getVelocity().y = 0;
			//System.out.println("after y state="+ship.getState());
			
			
		} else if (velocity.y > 0) {
			if (onMovablePlatform) {
				velocity.y = -movablePlatform.getSpeed();
			} else {
				velocity.y = 0;
			}
			position.y = tile.y - getHeight() - 0.001f;
		}
		
	}
	
	/**
	 * Handles when player is not on solid ground
	 */
	public void handleNoTileUnderneath() {
		if (getState() == State.IDLE || getState() == State.WALK) {
			setState(State.JUMP);
		}
		
	}
	
	/**
	 * Enable wallClimb
	 * @param wallClimbEnabled
	 */
	public void setWallClimbEnabled (boolean wallClimbEnabled) {
		this.wallClimbEnabled = wallClimbEnabled;
	}
	
	/**
	 * Set overForeground
	 * @param overForeground
	 */
	public void setOverForeground(boolean overForeground) {
		isOverForeground = overForeground;
	}
	
	/**
	 * Check if is overForeground
	 * @return
	 */
	public boolean isOverForeground() {
		return isOverForeground;
	}
	
	/**
	 * Update player character
	 */
	public void update(Player ship) {
		super.update(ship);
		if (state == State.DEATH){
			if (facingRight && rotation < 90) {
				rotation += Gdx.graphics.getDeltaTime() * 10;
			} else if (!facingRight && rotation > -90){
				rotation -= Gdx.graphics.getDeltaTime() * 10;
			}
			return;
		}
		
		Vector2 deltaNextPos = velocity.mul(Gdx.graphics.getDeltaTime());
		if(canMove) {
			position.add(deltaNextPos);
		} else {
			position.y += deltaNextPos.y;
			canMove = true;
		}
		velocity.mul(1/(Gdx.graphics.getDeltaTime()));
		
		if(state == State.JUMP){
			jumpTime -= Gdx.graphics.getDeltaTime() * 1;
			if (jumpTime < 0) {
				state = State.FALL;
			} else {
				velocity.y = JUMP_VELOCITY;
			}
		}
		if (state == State.WALL) {
			wallTime -= Gdx.graphics.getDeltaTime();
			if (wallTime > 0) {
				position.x -= velocity.mul(Gdx.graphics.getDeltaTime()).x;
				velocity.mul(1/Gdx.graphics.getDeltaTime());
			} else {
				if (velocity.x > 0) {
					facingRight = true;
				} else {
					facingRight = false;
				}
				setState(State.JUMP);
			}
		}
		velocity.add(0, GRAVITY);
		
		if (state == State.WALL && velocity.y < -MAX_WALL_VELOCITY && wallClimbEnabled){
			velocity.y = -MAX_WALL_VELOCITY;
		} else if (velocity.y<-MAX_FALL_VELOCITY) {
			velocity.y = -MAX_FALL_VELOCITY;
		}
		
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
		}
		
		
		
		
		/* Handle Bounceback */
		if(bouncingBack) {
			bounceBackTime += Gdx.graphics.getDeltaTime();
		}
		
		if(bounceBackTime > MAX_BOUNCEBACK_TIME) {
			bounceBack(false);
			bounceBackTime = 0;
			getVelocity().x = 0;
		}
		
		/* Handle Invincibility */
		if(invincible) {
			invincibleTime += Gdx.graphics.getDeltaTime();
		}
		
		if(invincibleTime > MAX_INVINCIBLE_TIME) {
			setInvincibility(false);
			invincibleTime = 0;
		}
		
		if (doubleTapTime > 0f) {
			doubleTapTime -= Gdx.graphics.getDeltaTime();
		}
		//System.out.println("AFTER State="+state+" canMove="+canMove+" velocity="+velocity+ "position="+position);
		
	}
	
	/**
	 * Check if player has died.
	 * @return
	 */
	public boolean hasDied() {
		return hasDied;
	}
	
	/**
	 * Set player to dead.
	 */
	public void setHasDied() {
		hasDied = true;
	
	}
}
