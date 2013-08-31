package com.test.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ship extends MovableEntity{
	
	public enum State {
		IDLE, WALK, DASH, JUMP, FALL, DEATH, DASH_JUMP, WALL
	}
	public static final float SPEED = 12f;
	public static final float JUMP_VELOCITY = 20f;
	public static final float WIDTH = 1f;
	public static final float HEIGHT = 1.7f;
	//public static final float WIDTH = 1f;
	//public static final float HEIGHT = 1f;
	public static final float JUMP_TIME = 0.2f;
	public static final float GRAVITY = -1.8f;
	public static final float MAX_FALL_VELOCITY = 30f;
	public static final float MAX_WALL_VELOCITY = 6.2f; //note that any movable platform falling at speed greater than this will have bugged moving
	public static final float WALL_ATTACH_TIME = 0.13f;
	public static final float MAX_INVINCIBLE_TIME = 3f;
	
	private State state = State.IDLE;
	private int hearts = 3;
	private boolean facingRight = false;
	private boolean onMovable = false;
	private boolean invincible = false;
	private float invincibleTime = 0;
	float jumpTime = 0;
	float wallTime = WALL_ATTACH_TIME;
	
	public Ship(Vector2 pos) {
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
	
	public boolean isInvincible() {
		return invincible;
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
	
	/* ----- Setter methods ----- */
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
		return;
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
			position.x = tile.getX() - getWidth();
				
		} else if (velocity.x < -0.01f){
			position.x = tile.getX() + tile.getWidth();
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
				position.y = tile.y + tile.height;
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
			position.y = tile.y - getHeight();
		}
	}
	
	public void handleNoTileUnderneath() {
		if (getState() == State.IDLE || getState() == State.WALK) {
			setState(State.JUMP);
		}
	}
	public void update(Ship ship) {
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
		
		position.add(velocity.scl(Gdx.graphics.getDeltaTime()));
		velocity.scl(1/(Gdx.graphics.getDeltaTime()));

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
				position.x -= velocity.scl(Gdx.graphics.getDeltaTime()).x;
				velocity.scl(1/Gdx.graphics.getDeltaTime());
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
		if (state == State.WALL && velocity.y < -MAX_WALL_VELOCITY){
			velocity.y = -MAX_WALL_VELOCITY;
		} else if (velocity.y<-MAX_FALL_VELOCITY) {
			velocity.y = -MAX_FALL_VELOCITY;
		}
		
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
		}
		//System.out.println("Endo f Ship.update " + velocity.x);
		
		
		
		
		
		
		/* Handle Invincibility */
		if(invincible) {
			invincibleTime += Gdx.graphics.getDeltaTime();
		}
		
		if(invincibleTime > MAX_INVINCIBLE_TIME) {
			setInvincibility(false);
			invincibleTime = 0;
		}
	}
}
