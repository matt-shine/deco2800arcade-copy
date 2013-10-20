package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


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
	public static final float MAX_INVINCIBLE_TIME = 3f;
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
	
	public Player(Vector2 pos) {
		super (SPEED, 0, pos, WIDTH, HEIGHT);
	}

	public void resetJumpTime() {
		jumpTime = JUMP_TIME;
		//System.out.println("jumptime RESET to " + JUMP_TIME);
	}
	
	public void clearJumpTime() {
		jumpTime = 0f;
		//System.out.println("jumptime CLEARED to " + 0f);
	}
	
	public void resetWallTime() {
		wallTime = WALL_ATTACH_TIME;
	}
	
	public void clearWallTime() {
		wallTime = 0f;
	}
	
	
	
	/* ----- Getter methods ----- */
	public State getState() {
		return state;
	}
	
	public boolean isWalking(){
		if(state == State.WALK){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isInvincible() {
		return invincible;
	}
	
	public boolean isBouncingBack() {
		return bouncingBack;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}
	
	public boolean isOnMovable() {
		return onMovable;
	}
	
	public int getHearts() {
		return hearts;
	}
	
	public Vector2 getNextPos() {
		Vector2 tmp = new Vector2(position); 
		tmp.add(velocity.mul(Gdx.graphics.getDeltaTime()));
		velocity.mul(1/(Gdx.graphics.getDeltaTime()));
		return tmp;
	}
	
	/* ----- Setter methods ----- */
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
		}
	}
	
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
	
	public void setCanMove(boolean canMv) {
		canMove = canMv;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void setFacingRight(boolean right) {
		facingRight = right;
	}
	
	public void setOnMovable(boolean onMovable) {
		this.onMovable = onMovable;
	}
	
	public void decrementHearts() {
		hearts--;
		if (hearts <= 0 ){
			state = State.DEATH;
			hasDied = true;
		}
		return;
	}
	
	public void resetHearts() {
		hearts = DEFAULT_HEARTS;
	}
	
	public void setInvincibility(boolean invisibility) {
		invincible = invisibility;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		getPosition().y = movablePlatform.getPosition().y + 
				movablePlatform.getCollisionRectangle().height+1/32f;
	}
	
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
	
	public void handleNoTileUnderneath() {
		if (getState() == State.IDLE || getState() == State.WALK) {
			setState(State.JUMP);
		}
		
	}
	
	public void setWallClimbEnabled (boolean wallClimbEnabled) {
		this.wallClimbEnabled = wallClimbEnabled;
	}
	
	public void setOverForeground(boolean overForeground) {
		isOverForeground = overForeground;
	}
	
	public boolean isOverForeground() {
		return isOverForeground;
	}
	
	
	public void update(Player ship) {
		//System.out.println("Before suepr update " + velocity.x);
		super.update(ship);
		//System.out.println("After super update " + velocity.x);
		
		/*if(velocity.x != 0 || velocity.y != 0)
		rotation = velocity.angle();*/
		//position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime() * speed));
		//position.add(velocity.mul(Gdx.graphics.getDeltaTime()));
		//position.add(velocity.scl(Gdx.graphics.getDeltaTime()));
		//position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime()));
		//Vector2 tmp1 = velocity.cpy();
		//tmp1.set(velocity.x, velocity.y);
		//position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
		//position.add(tmp1.mul(Gdx.graphics.getDeltaTime()));
		//System.out.println(velocity.x);
		//System.out.println("Velocity before scl " + velocity.x+","+velocity.y);
		//System.out.println("BEFORE State="+state+" canMove="+canMove+" velocity="+velocity + "position="+position);
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
		
		//System.out.println("Velocity after scl " + velocity.x+","+velocity.y);
		//tmp1.scl(Gdx.graphics.getDeltaTime());

		//System.out.println("Velocity after invert scl " + velocity.x+","+velocity.y);
		//position.x += velocity.x*Gdx.graphics.getDeltaTime();
		//position.y += velocity.y*Gdx.graphics.getDeltaTime();
		//System.out.println("After position add " + velocity.x);
		if(state == State.JUMP){
			//jumpTime -= Gdx.graphics.getDeltaTime() * JUMP_TIME;
			jumpTime -= Gdx.graphics.getDeltaTime() * 1;
			if (jumpTime < 0) {
				state = State.FALL;
			} else {
				velocity.y = JUMP_VELOCITY;
			}
			//System.out.println("jumptime = " + jumpTime +"; velocity.y = " + velocity.y);
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
		//System.out.println("Velocity before gravity " + velocity.x+","+velocity.y);
		velocity.add(0, GRAVITY);
		
		//System.out.println("Velocity after gravity " + velocity.x+","+velocity.y);
		if (state == State.WALL && velocity.y < -MAX_WALL_VELOCITY && wallClimbEnabled){
			velocity.y = -MAX_WALL_VELOCITY;
		} else if (velocity.y<-MAX_FALL_VELOCITY) {
			velocity.y = -MAX_FALL_VELOCITY;
		}
		
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
		}
		//System.out.println("Endo f Ship.update " + velocity.x);
		
		
		
		
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
	public boolean hasDied() {
		return hasDied;
	}
}
