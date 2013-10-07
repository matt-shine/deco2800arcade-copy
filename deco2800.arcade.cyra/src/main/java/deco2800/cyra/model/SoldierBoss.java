package deco2800.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class SoldierBoss extends Enemy {
	
	private static final float SPEED = 4f;
	private static final float WIDTH = 5f;
	private static final float HEIGHT = 5f;
	private static final float GRAVITY = -1.8f;
	public static final float JUMP_TIME = 0.4f;
	public static final float JUMP_VELOCITY = 14f;
	
	boolean facingRight;
	private State state;
	private Boolean performingTell;
	private float stateTime;
	private float jumpTime;
	
	private enum State {
		WALK, WAIT, JUMP, SHOOT, BOMB, SWORD, CHARGE, DEATH
	}
	
	public SoldierBoss () {
		this(new Vector2(-1,-1));
		
	}
	
	public SoldierBoss (Vector2 pos) {
		super(SPEED, 0, pos, WIDTH, HEIGHT);
		facingRight = false;
		state = State.WALK;
		performingTell = false;
		stateTime = 0.33f;
		jumpTime = 0;
		
		
	}

	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {
		super.update(ship);
		Array<Enemy> newEnemies = new Array<Enemy>();
		
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
		}
		
		
		position.add(velocity.mul(delta));
		//System.out.println("Velocity after scl " + velocity.x+","+velocity.y);
		//tmp1.scl(Gdx.graphics.getDeltaTime());
		velocity.mul(1/delta);
		velocity.add(0, GRAVITY);
		
		
		stateTime -= delta;
		if (stateTime < 0) {
			if (performingTell) {
				performingTell = false;
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
				
				case SHOOT:
					int randInt = MathUtils.random(1);
					if (randInt == 0) {
						//newEnemies.add(new HomingBullet);
					} else {
						//newEnemies.add(new SimpleBullet);
					}
					stateTime = 1f;
					break;
				/*case AOE:
					stateTime = 2f;
					break;
				case RAM:
					velocity.x = SPEED * 3f;
					if (!facingRight) {
						velocity.x = -velocity.x;
					}
					stateTime = 1f;
					break;
				case SWORD:
					stateTime = 2f;
					swordTime = 1.5f;
					break;
				case GRENADE:
					newEnemies.add(new Grendae);
					stateTime = 1f;
					break;
					*/
				}
				
				
				
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
		
		return newEnemies;
	}

	public void pickNewState(Ship ship) {
		//will need to make it so the same state doesn't get picked twice
		/*float walkChance = 0.4f - 0.34 * rank;
		float jumpChance = walkChance + 0.3f;
		float waitChance = jumpChance + 0.2f - 0.2f * rank;
		float shootChance = waitChance + 0.4f * rank;
		float aoeChance;
		if (rank > 0.75f) {
			aoeChance = shootChance + 0.3f;
		} else {
			aoeChance = shootChance;	
		}
		float ramChance = aoeChance + 0.1f * rank;
		float swordChance =  ramChance + 0.2f;
		float grenadeChance;
		if (rank > 0.6f) {
			grenadeChance = swordChance + 0.3f
		} else {
			grenadeChance = swordChance;
		float rand = MathUtils.random(grenadeChance);
		
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
		} else if (rand < shootChance) {
			state = State.SHOOT;
			if (ship.position.x > position.x) {
				facingRight = true;
			} else {
				facingRight = false;
			}
			performingTell = true;
			stateTime = 1.5f-1.35f * rank;
		
		} else if (rand < aoeChance) {
			state = State.AOE;
			
			performingTell = true;
			stateTime = 2f-1.5f * rank;
		
		} else if (rand < ramChance) {
			state = State.RAM;
			if (ship.position.x > position.x) {
				facingRight = true;
			} else {
				facingRight = false;
			}
			performingTell = true;
			stateTime = 1.5f-1.35f * rank;
		} else if (rand < swordChance) {
			state = State.RAM;
			if (ship.position.x > position.x) {
				facingRight = true;
			} else {
				facingRight = false;
			}
			performingTell = true;
			stateTime = 1.5f-1.35f * rank;
		
		//} else if (rand < grenadeChance) {
		} else {
			state = State.GRENADE;
			if (ship.position.x > position.x) {
				facingRight = true;
			} else {
				facingRight = false;
			}
			performingTell = true;
			stateTime = 1.5f-1.35f * rank;
		
		}
		
		System.out.println("Picked new state: "+ state);
		*/
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
	
	@Override
	public Array<Rectangle> getPlayerDamageBounds() {
		Array<Rectangle> output = new Array<Rectangle>();
		/*if (state == State.AOE) {
			float size = 9f;
			output.add(new Rectangle(position.x - size, position.y-size, width+2*size, height+2*size));
		} else if (swordTime > 0) {
			swordTime -= Gdx.graphics.getDelta();
			if (facingRight) {
				output.add(new Rectangle(position.x + width/2, position.y + height/4, width *2, height/2));
			} else {
				output.add(new Rectangle(position.x - width*1.5, position.y + height/4, width *2, height/2));
			}
		} else if (state == State.JUMP) {
			output.add(new Rectangle(position.x, position.y, width,height));
		} else if (state == State.RAM) {
			output.add(new Rectangle(position.x, position.y, width,height/2));
		}*/
		return output;	
	}
	
	@Override
	public void handleDamage(boolean fromRight) {
		isDead = true;
		startingNextScene = true;
	}

}
