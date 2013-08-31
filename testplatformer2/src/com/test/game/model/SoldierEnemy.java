package com.test.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.test.game.model.Ship.State;

public class SoldierEnemy extends Enemy {
	
	private static final float SPEED = 6f;
	private static final float WIDTH = 2f;
	private static final float HEIGHT = 2f;
	private static final float GRAVITY = -1.8f;
	
	boolean facingRight;
	boolean startOnRight;
	boolean notYetDeterminedStartOnRight;
	private State state;
	private Boolean performingTell;
	private float stateTime;
	
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
		stateTime = 4f;
		
		if (startOnRight) {
			velocity.x = -SPEED;
		} else {
			velocity.x = SPEED;
		}
	}

	@Override
	public void advance(float delta, Ship ship, float rank) {
		
		if (notYetDeterminedStartOnRight) {
			notYetDeterminedStartOnRight = false;
			if (ship.getPosition().x > position.x) {
				startOnRight = true;
				velocity.x = -SPEED;
			} else {
				startOnRight = false;
				velocity.x = SPEED;
			}
		}
		
		position.add(velocity.scl(delta));
		//System.out.println("Velocity after scl " + velocity.x+","+velocity.y);
		//tmp1.scl(Gdx.graphics.getDeltaTime());
		velocity.scl(1/delta);
		velocity.add(0, GRAVITY);
		
		
		stateTime -= delta;
		if (stateTime == 0) {
			if (performingTell) {
				switch (state) {
				//Will be used for states JUMP, SHOOT, BOMB, SWORD and CHARGE
				case JUMP:
					velocity.y = 14f;
					if (facingRight) {
						velocity.x = SPEED * 2;
					} else {
						velocity.x = -SPEED * 2;
					}
					break;
				}
				
				performingTell = false;
				
			} else {
				//pick a new state
				pickNewState(ship);
			}
		}
		System.out.println("Velocity after soldier advance = "+velocity+ "    Position after soldier advance ="+position);
		
	}

	public void pickNewState(Ship ship) {
		float rand = MathUtils.random(1f);
		float walkChance = 0.4f;
		float jumpChance = 0.5f;
		float waitChance = 1f;
		if (rand < walkChance) {
			state = State.WALK;
			stateTime = 4f;
			performingTell = false;
			float directionChance;
			if (ship.getPosition().x > position.x) {
				directionChance = walkChance * 0.77f;  
			} else {
				directionChance = walkChance * 23f;
			}
			if (rand < directionChance) {
				facingRight = true;
				velocity.x = SPEED;
			} else {
				facingRight = false;
				velocity.x = -SPEED;
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
		} else if (rand < waitChance) {
			state = State.WAIT;
			stateTime = 4f;
			performingTell = false;
		}
	}
	
	
	@Override
	public boolean isSolid() {
		return true;
	}

	

	

	

	@Override
	public void handleNoTileUnderneath() {
		if (state != State.JUMP) {
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
		}
		
	}

}
