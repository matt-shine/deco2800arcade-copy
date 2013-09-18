package com.test.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.model.Ship.State;

public class SoldierEnemy extends Enemy {
	
	private static final float SPEED = 11f;
	private static final float WIDTH = 2f;
	private static final float HEIGHT = 2f;
	private static final float GRAVITY = -1.8f;
	public static final float JUMP_TIME = 0.4f;
	public static final float JUMP_VELOCITY = 14f;
	
	boolean facingRight;
	boolean startOnRight;
	boolean notYetDeterminedStartOnRight;
	private State state;
	private Boolean performingTell;
	private float stateTime;
	private float jumpTime;
	
	private enum State {
		INIT, WALK, WAIT, JUMP, SHOOT, BOMB, SWORD, CHARGE, DEATH
	}
	
	public SoldierEnemy () {
		this(new Vector2(-1,-1), true);
		notYetDeterminedStartOnRight = true;
	}
	
	public SoldierEnemy (Vector2 pos, boolean startOnRight) {
		super(SPEED, 0, pos, WIDTH, HEIGHT);
		this.startOnRight= startOnRight;
		notYetDeterminedStartOnRight = false;
		facingRight = !startOnRight;
		state = State.INIT;
		performingTell = false;
		stateTime = 0.33f;
		jumpTime = 0;
		
		if (startOnRight) {
			velocity.x = -SPEED;
		} else {
			velocity.x = SPEED;
		}
	}

	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {
		super.update(ship);
		
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
		}
		
		if (notYetDeterminedStartOnRight) {
			notYetDeterminedStartOnRight = false;
			if (ship.getPosition().x < position.x) {
				startOnRight = true;
				if (ship.getVelocity().x >= 0) {
					velocity.x = -SPEED;
				} else {
					velocity.x = -SPEED * 1.8f;
				}
				//System.out.println("Determined to start on right");
			} else {
				startOnRight = false;
				if (ship.getVelocity().x <= 0) {
					velocity.x = SPEED;
				} else {
					velocity.x = SPEED * 1.8f;
				}
				//System.out.println("Determined to NOT start on right");
			}
		}
		
		position.add(velocity.scl(delta));
		//System.out.println("Velocity after scl " + velocity.x+","+velocity.y);
		//tmp1.scl(Gdx.graphics.getDeltaTime());
		velocity.scl(1/delta);
		velocity.add(0, GRAVITY);
		
		
		stateTime -= delta;
		if (stateTime < 0) {
			if (performingTell) {
				switch (state) {
				//Will be used for states JUMP, SHOOT, BOMB, SWORD and CHARGE
				case JUMP:
					velocity.y = JUMP_VELOCITY;
					if (facingRight) {
						velocity.x = SPEED * 1.5f;
					} else {
						velocity.x = -SPEED * 1.5f;
					}
					jumpTime = JUMP_TIME;
					stateTime = 3f;
					break;
				}
				
				performingTell = false;
				
			} else {
				//pick a new state
				pickNewState(ship);
			}
		}
		//System.out.println("Velocity after soldier advance = "+velocity+ "    Position after soldier advance ="+position);
		
		if (state == State.JUMP && !performingTell) {
			jumpTime -= delta;
			//System.out.println("jumpTime= "+jumpTime);
			if (jumpTime < 0) {
				//state = State.WAIT;
				//velocity.x = 0;
			} else {
				velocity.y = JUMP_VELOCITY;
			}
		}
		
		return null;
	}

	public void pickNewState(Ship ship) {
		float rand = MathUtils.random(1f);
		//will need to make it so the same state doesn't get picked twice
		float walkChance = 0.4f;
		float jumpChance = 0.9f;
		float waitChance = 1f;
		if (rand < walkChance) {
			state = State.WALK;
			stateTime = 2f;
			performingTell = false;
			float directionChance;
			if (ship.getPosition().x > position.x) {
				directionChance = walkChance * 0.85f;  
			} else {
				directionChance = walkChance * 0.15f;
			}
			if (rand < directionChance) {
				facingRight = true;
				velocity.x = SPEED *0.6f;
			} else {
				facingRight = false;
				velocity.x = -SPEED *0.6f;
			}
		} else if (rand < jumpChance) {
			state = State.JUMP;
			stateTime = 1f;
			performingTell = true;
			if (ship.getPosition().x > position.x) {
				facingRight = true;
				
			} else {
				facingRight = false;
				
			}
			velocity.x = 0;
		} else if (rand < waitChance) {
			state = State.WAIT;
			stateTime = 1f;
			velocity.x = 0;
			performingTell = false;
		}
		System.out.println("Picked new state: "+ state);
	}
	
	
	@Override
	public boolean isSolid() {
		return true;
	}

	

	

	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		
		if (velocity.y < 0 && state == State.JUMP && !performingTell) {
			state = State.WAIT;
			velocity.x = 0;
			System.out.println("hit ground");
		}
		super.handleYCollision(tile, onMovablePlatform, movablePlatform);
		
	}

	@Override
	public void handleNoTileUnderneath() {
		/*if (state != State.JUMP) {
			//velocity.y = 14f;
			state = State.JUMP;
			performingTell = true;
			if (velocity.x > 0) {
				position.x -= 0.2f;
			} else {
				position.x += 0.2f;
			}
			velocity.x = 0;
			stateTime = 1f;
		}*/
		
	}

}
