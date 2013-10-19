package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.cyra.world.Sounds;


public class SoldierBoss extends Enemy {
	
	private static final float SPEED = 4f;
	private static final float WIDTH = 5f;
	private static final float HEIGHT = 5f;
	//private static final float GRAVITY = -1.8f;
	//public static final float JUMP_TIME = 0.4f;
	//public static final float JUMP_VELOCITY = 14f;
	public static final float INVINCIBLE_TIME = 0.3f;
	public static final int INITIAL_HEALTH = 10;
	public static final float ANIMATION_IDLE_RATE = 0.1f;
	public static final float ANIMATION_ARMS_RATE = 0.1f;
	public static final float ANIMATION_SPIN_RATE = 0.1f;
	public static final float ANIMATION_DRILL_RATE = 0.1f;
	public static final float ANIMATION_CIRCLE_RATE = 0.1f;
	public static final float LOWEST_HEIGHT= 47.4f;
	
	
	
	boolean facingRight;
	private State state;
	private Boolean performingTell;
	private float stateTime;
	private int stateFrame;
	private float shootRotation;
	private boolean shootRotationDirectionUp;
	//private float jumpTime;
	private int health;
	private boolean beingHit;
	private float invincibleTime;
	
	private int animationFrame;
	private float animationTime;
	private int otherAnimationFrame;
	private float otherAnimationTime;
	
	public enum State {
		WALK, SHOOT, AOE, LASER, RAM, DEATH
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
		stateFrame = 0;
		//jumpTime = 0;
		health = INITIAL_HEALTH;
		beingHit =false;
		healthName = "Drewbert The Dark Mage";
		animationFrame = 0;
		shootRotation = 0f;
		shootRotationDirectionUp = false;
		animationTime = 0;
		otherAnimationFrame = 0;
		otherAnimationTime = 0f;
		
		
	}

	
		
		@Override
		public Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam) {
			super.update(ship);
			System.out.println(state);
			if (beingHit) {
				invincibleTime -= delta;
				if (invincibleTime < 0) {
					beingHit = false;
				}
			}
			Array<Enemy> newEnemies = new Array<Enemy>();
			
			if (Math.abs(velocity.x) < 1) {
				velocity.x = 0;
			}
			
			
			
			position.add(velocity.mul(delta));
			//System.out.println("Velocity after scl " + velocity.x+","+velocity.y);
			//tmp1.scl(Gdx.graphics.getDeltaTime());
			velocity.mul(1/delta);
			//velocity.add(0, GRAVITY);
			
			
			
			stateTime -= delta;
			if (stateTime < 0) {
				
				switch (state) {
				//Will be used for states JUMP, SHOOT, BOMB, SWORD and CHARGE
				
				
				case SHOOT:
					if (performingTell) {
						performingTell = false;
					} else {
						int randInt = MathUtils.random(1);
						/*if (randInt == 0) {
							newEnemies.add(new BulletHomingDestructible(5f + 10f * rank, new Vector2(position.x + width/2, 
									position.y + height/2), 1f, 1f, new Vector2(0,1f), BulletSimple.Graphic.FIRE));
							
						} else {
							Vector2 direction;
							if (facingRight) { 
								direction = new Vector2(1f,0);
							} else {
								direction = new Vector2(-1f,0);
							}
							newEnemies.add(new BulletSimple(5f + 30f * rank, 0f, new Vector2(position.x + width/2, 
									position.y + height/2), 1f, 1f, direction, BulletSimple.Graphic.FIRE));
							
						}*/
						if (--stateFrame == 0) {
							startWalk(ship, rank);
						} else {
							Sounds.playShootSound(0.5f);
							if (rank < 0.7f) {
								stateTime = 1f-rank;
							} else if (rank < 0.9f) {
								stateTime = 0.91f-rank;
							} else {
								stateTime = 0.0001f;
							}
							float rotationDel;
							if (shootRotationDirectionUp) {
								rotationDel = MathUtils.random(10f, 20f);
								if (shootRotation > 30f) {
									shootRotationDirectionUp = false;
								}
							} else {
								rotationDel = -MathUtils.random(10f,20f);
								if (shootRotation < -30f) {
									shootRotationDirectionUp = true;
								}
							}
							
							Vector2 direction;
							if (facingRight) { 
								direction = new Vector2(1f,0);
							} else {
								direction = new Vector2(-1f,0);
							}
							shootRotation += rotationDel;
							direction.rotate(shootRotation);
							float randSpeedChange = MathUtils.random(-10*rank, 30*rank);
							
							newEnemies.add(new BulletSimple(5f+ 10f*rank + randSpeedChange, 0f, new Vector2(position.x + width/2,
									position.y + height/2), 0.5f, 0.5f, direction, BulletSimple.Graphic.FIRE));
							if (rank > 0.7f && ship.getPosition().x > position.x-1f && ship.getPosition().x< position.x+width+1f) {
								direction = new Vector2(-1,-1);
								for (int i=0; i<50; i++) {
									direction.rotate(90f/50f);
									newEnemies.add(new BulletSimple(40f, 0f, new Vector2(position.x + width/2,
											position.y + height/2), 0.5f, 0.5f, new Vector2(direction), BulletSimple.Graphic.FIRE));
								}
							}
						}
						
					}
					break;
				case AOE:
					stateTime = 2f;
					velocity.x = 0f;
					break;
				case RAM:
					if (performingTell) {
						velocity.x = SPEED * 2.5f * rank;
						if (!facingRight) {
							velocity.x = -velocity.x;
						}
						stateTime = 1f+rank;
						otherAnimationFrame = 0;
						otherAnimationTime = ANIMATION_DRILL_RATE;
						performingTell = false;
					} else {
						startWalk(ship, rank);
					}
					break;
				case LASER:
					stateTime = 2f;
					//swordTime = 1.5f;
					break;
				
				case DEATH:
					//isDead = true;
					stateFrame--;
					float buffer = 2f;
					float randX = MathUtils.random(-WIDTH/2-buffer, WIDTH/2+buffer);
					float randY = MathUtils.random(-HEIGHT/2-buffer, HEIGHT/2+buffer);
					newEnemies.add(new Explosion(new Vector2(position.x+width/2-Explosion.WIDTH/2+randX, position.y+height/2-Explosion.HEIGHT/2+randY)));
					stateTime = 0.05f;
					if (stateFrame == 0) {
						isDead = true;
						startingNextScene=true;
					}
					break;
					
				case WALK:
					pickNewState(ship, rank);
					break;
				}
				
					
					
				 
			}
			//System.out.println("Velocity after soldier advance = "+velocity+ "    Position after soldier advance ="+position);
			
			//Every-update actions
			if (state == State.SHOOT) {
				float lerp = 0.9f;
				if (Math.abs(ship.getPosition().y - position.y) > 3f) {
					lerp = 1+ 15*rank;
				} else if (!performingTell) {
					lerp = 0.3f;
				}
				position.y += (ship.getPosition().y- 3f - position.y)* delta * lerp;
				if (position.y < LOWEST_HEIGHT) {
					position.y = LOWEST_HEIGHT;
				}
				if (ship.getPosition().x+ship.getWidth()/2 > position.x+width/2) {
					facingRight = true;
				} else {
					facingRight = false;
				}
			}
			
			//Animation frames
			animationTime-=delta;
			
			if (animationTime <0) {
				if (state == State.WALK) {
					animationFrame++;
					if (animationFrame == 3) {
						animationFrame = 0;
					}
					animationTime = ANIMATION_IDLE_RATE;
				}
			}
			otherAnimationTime -= delta;
			if (otherAnimationTime < 0) {
				if (state == State.RAM) {
					otherAnimationTime = ANIMATION_DRILL_RATE;
					if (++otherAnimationFrame == 3) {
						otherAnimationFrame = 0;
					}
				} //else if (state == State.)
			}
			
			return newEnemies;
		}

		public void pickNewState(Player ship, float rank) {
			velocity.x = 0f;
			
			//will need to make it so the same state doesn't get picked twice
			float walkChance = 0.4f - 0.34f * rank;
			
			
			float shootChance = walkChance + 0.4f * rank;
			//float shootChance = waitChance + 50f+0.4f * rank; // doing this to test the shooting
			float aoeChance;
			if (rank > 0.75f) {
				aoeChance = shootChance + 0.3f;
			} else {
				aoeChance = shootChance;	
			}
			float ramChance = aoeChance + 0.1f * rank;
			//float laserChance =  ramChance + 0.2f;
			float laserChance =  ramChance;
			
			float rand = MathUtils.random(laserChance);
			
			if (rand < walkChance) {
				startWalk(ship, rank);
			
			} else if (rand < shootChance) {
				state = State.SHOOT;
				if (ship.position.x > position.x) {
					facingRight = true;
				} else {
					facingRight = false;
				}
				performingTell = true;
				velocity.x = 0;
				velocity.y = 0;
				stateTime = 1.5f-1.35f * rank;
				shootRotation = 0f;
				stateFrame = (int)(3f + 30*rank);
				shootRotationDirectionUp = MathUtils.randomBoolean();
			
			} else if (rand < aoeChance) {
				state = State.AOE;
				
				performingTell = true;
				velocity.x = 0;
				stateTime = 2.5f-1.5f * rank;
			
			} else if (rand < ramChance) {
				state = State.RAM;
				if (ship.position.x > position.x) {
					facingRight = true;
				} else {
					facingRight = false;
				}
				performingTell = true;
				velocity.x = 0;
				stateTime = 1.5f-1.35f * rank;
			} else if (rand < laserChance) {
				state = State.LASER;
				if (ship.position.x > position.x) {
					facingRight = true;
				} else {
					facingRight = false;
				}
				performingTell = true;
				velocity.x = 0;
				stateTime = 1.5f-1.35f * rank;
			}
			//} else if (rand < grenadeChance) {
			
			
			System.out.println("Picked new state: "+ state);
		}
	
	
	@Override
	public boolean isSolid() {
		return true;
	}

	

	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		System.out.println("BEFORE COLLISION pos="+position+" vel="+velocity+"***********************************");
		Vector2 savedVelocity = new Vector2(velocity);
		super.handleYCollision(tile,  onMovablePlatform, movablePlatform);
		/*if (state==State.WALK) {
			velocity = savedVelocity;
			velocity.y = velocity.y * (-1);
		}
		position.y -= savedVelocity.y;*/
		System.out.println("AFTER COLLISION pos="+position+" vel="+velocity+"***********************************");
		
	}

	
	
	@Override
	public void handleXCollision(Rectangle tile) {
		super.handleXCollision(tile);
		if (state == State.WALK) {
			velocity.x = velocity.x * (-1);
		}
		
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
		if (state == State.AOE) {
			float size = 9f;
			output.add(new Rectangle(position.x - size, position.y-size, width+2*size, height+2*size));
		} else if (state == State.RAM) {
			output.add(new Rectangle(position.x-4f, position.y, width+8f,height));
		}
		return output;	
	}
	
	@Override
	public boolean displayHealth() {
		return true;
	}
	
	@Override
	public float getHealthPercentage() {
		return ((float)health)/((float)INITIAL_HEALTH);
	}
	
	@Override
	public void handleDamage(boolean fromRight) {
		if (!beingHit) {
			if (--health == 0) {
				state = State.DEATH;
				stateFrame = 20;
				stateTime = 0.1f;
				
			} else {
				beingHit = true;
				invincibleTime = INVINCIBLE_TIME;
			}
		
		}	
		
	}
	
	public void startWalk(Player ship, float rank) {
		state = State.WALK;
		stateTime = 2f;
		performingTell = false;
		float directionChance;
		if (ship.getPosition().x > position.x) {
			directionChance = 0.85f;  
		} else {
			directionChance = 0.15f;
		}
		if (MathUtils.random(1f) < directionChance) {
			facingRight = true;
			velocity.x = SPEED+ 30*rank;
		} else {
			facingRight = false;
			velocity.x = -SPEED + 30 * rank;
		}
		float randomY = MathUtils.random(-4f, 4f);
		velocity.y = randomY;
		animationFrame = 0;
		animationTime = ANIMATION_IDLE_RATE;
	}
	
	public int getAnimationFrame() {
		return animationFrame;
	}
	
	public State getState() {
		return state;
	}
	
	public boolean isPerformingTell() {
		return performingTell;
	}
	
	public int getOtherAnimationFrame() {
		return otherAnimationFrame;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}

}
