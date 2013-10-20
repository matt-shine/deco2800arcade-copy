package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import deco2800.arcade.cyra.model.Player.State;
import deco2800.arcade.cyra.world.ParallaxCamera;
import deco2800.arcade.cyra.world.Sounds;

public class SoldierEnemy extends Enemy {
	
	private static final float SPEED = 11f;
	private static final float WIDTH = 1.25f;
	private static final float HEIGHT = 1.25f;
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
	private float swordTime;
	private int deathType;
	private boolean deathFromRight;
	private float deathCount;
	private float deathRotation;
	
	private enum State {
		INIT, WALK, WAIT, JUMP, SHOOT, AOE, RAM, SWORD, GRENADE, DEATH
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
		stateTime = 0.1f;
		jumpTime = 0;
		swordTime = 0;
		score = 100;
		
		if (startOnRight) {
			velocity.x = -SPEED;
		} else {
			velocity.x = SPEED;
		}
	}

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam) {
		super.update(ship);
		Array<Enemy> newEnemies = new Array<Enemy>();
		
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
		
		position.add(velocity.mul(delta));
		//System.out.println("Velocity after scl " + velocity.x+","+velocity.y);
		//tmp1.scl(Gdx.graphics.getDeltaTime());
		velocity.mul(1/delta);
		velocity.add(0, GRAVITY);
		
		if (state == State.INIT && (ship.getPosition().x > position.x - 10f || ship.getPosition().x < position.x +10f)) {
			//System.out.println("cancelled init at ship"+ship.getPosition()+" and enemy"+position);
			stateTime = 0f;
		}
		
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
						Sounds.playShootSound(0.5f);
					}
					stateTime = 1f;
					break;
				case AOE:
					stateTime = 2f;
					velocity.x = 0f;
					break;
				case RAM:
					velocity.x = SPEED * 2.5f * rank;
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
					//newEnemies.add(new Grendae);
					stateTime = 1f;
					break;
				case DEATH:
					isDead = true;
					break;
				}
				
				
				
			} else if (position.x >= cam.position.x - cam.viewportWidth/2 && position.x <= cam.position.x + cam.viewportWidth/2 - width/2 &&
					position.y >= cam.position.y - cam.viewportHeight/2 && position.y <= cam.position.y + cam.viewportHeight/2 - height/2){
				
				//pick a new state as long as is on screen
				pickNewState(ship, rank);
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
		
		if (state == State.DEATH) {
			deathCount += delta;
			if (deathType == 0) {
				if (!deathFromRight) {
					velocity = new Vector2(1,1).mul(20f);
				} else {
					velocity = new Vector2(-1,1).mul(20f);
				}
				if (deathCount > 0.1f) {
					newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2)));
					deathCount = 0f;
					Sounds.playExplosionLong(0.5f);
				}
			} else if (deathType == 1) {
				if (!deathFromRight) {
					rotation = 90f;
					velocity = new Vector2(1,0).mul(10f);
				} else {
					rotation = -90f;
					velocity = new Vector2(-1, 0).mul(10f);
				}
				if (deathCount > 0.1f) {
					newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(0f,4f)));
					deathCount = 0f;
					Sounds.playExplosionShort(0.5f);
				}
			} else if (deathType == 2) {
				float eSpeed = 5f;
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(0f,1f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(0f,-1f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,0f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,-1f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,1f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,0f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,-1f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,1f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,0.5f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,-0.5f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,0.5f).nor().mul(eSpeed)));
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,-0.5f).nor().mul(eSpeed)));
				Sounds.playExplosionLong(0.5f);
				isDead = true;
			} else if (deathType == 3) {
				float eSpeed = 5f;
				if (deathCount > 0.04f) {
					velocity = new Vector2(0,0);
					deathCount = 0;
					deathRotation += 10f;
					Vector2 vel = new Vector2(1,0);
					vel.rotate(deathRotation);
					vel.mul(7f);
					newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(vel)));
					newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(vel).mul(-1)));
					Sounds.playExplosionShort(0.5f);
					if (deathRotation > 180f) {
						isDead = true;
					}
					
				}
			}
		}
		
		return newEnemies;
	}

	public void pickNewState(Player ship, float rank) {
		velocity.x = 0f;
		
		//will need to make it so the same state doesn't get picked twice
		float walkChance = 0.4f - 0.34f * rank;
		float jumpChance = walkChance + 0.3f;
		float waitChance = jumpChance + 0.2f - 0.2f * rank;
		float shootChance = waitChance + 0.4f * rank;
		//float shootChance = waitChance + 50f+0.4f * rank; // doing this to test the shooting
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
			grenadeChance = swordChance + 0.3f;
		} else {
			grenadeChance = swordChance;
		}
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
			velocity.x = 0;
			stateTime = 1.5f-1.35f * rank;
		
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
		} else if (rand < swordChance) {
			state = State.SWORD;
			if (ship.position.x > position.x) {
				facingRight = true;
			} else {
				facingRight = false;
			}
			performingTell = true;
			velocity.x = 0;
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
		
		//System.out.println("Picked new state: "+ state);
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
			//System.out.println("hit ground");
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
	public boolean isJumping(){
		if(state == State.JUMP){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public Array<Rectangle> getPlayerDamageBounds() {
		Array<Rectangle> output = new Array<Rectangle>();
		if (!performingTell) {
			if (state == State.AOE) {
				float size = 1.5f;
				output.add(new Rectangle(position.x - size, position.y-size, width+2*size, height+2*size));
			} else if (swordTime > 0) {
				swordTime -= Gdx.graphics.getDeltaTime();
				if (facingRight) {
					output.add(new Rectangle(position.x + width/2, position.y + height/4, width *2, height/2));
				} else {
					output.add(new Rectangle(position.x - width*1.5f, position.y + height/4, width *2, height/2));
				}
			} else if (state == State.JUMP) {
				output.add(new Rectangle(position.x, position.y, width,height));
			} else if (state == State.RAM) {
				output.add(new Rectangle(position.x, position.y, width,height/2));
			}
		}
		return output;	
	}
	
	@Override
	public void handleDamage(boolean fromRight) {
		if (state != State.DEATH) {
			state = State.DEATH;
			deathType = MathUtils.random(3);
			deathFromRight = fromRight;
			stateTime = 1f;
			performingTell = true;
			deathCount = 0f;
			deathRotation = 0f;
			Sounds.playHurtSound(0.5f);
		}
	}

}
