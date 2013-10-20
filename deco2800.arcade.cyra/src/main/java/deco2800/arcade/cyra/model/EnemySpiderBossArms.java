package deco2800.arcade.cyra.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.cyra.model.EnemySpiderBoss.State;

public class EnemySpiderBossArms extends Enemy {
	
	private static final float BASE_SPEED = 3f;
	private static final float RANK_SPEED_MULT = 10f;
	
	public static final float WIDTH = 2.2f;
	private static final float HEIGHT = 2.2f;
	private static final float PARENT_OFFSET_X = 5.4f;
	//private static final float PARENT_OFFSET_Y = 5f;
	private static final float PARENT_OFFSET_Y = 4.35f;
	private static final float PHASE2_OFFSET_X = 5.4f;
	private static final float PHASE2_OFFSET_Y = 4.35f;
	private static final float PHASE3_OFFSET_X = 5f;
	private static final float PHASE3_OFFSET_Y = 5f;
	public static final float PHASE3_SCALE = 1.8f;
	
	
	private boolean isAttacking;
	private boolean attackUp;
	private boolean moveRight;
	private boolean isReturning; 
	private EnemySpiderBoss parent;
	//private int phase2pos;
	//private float circlePos;
	private Vector2 targetPosition;
	private float count;
	private float scale;
	
	
	
	
	private float bulletCount;
	

	public EnemySpiderBossArms(EnemySpiderBoss parent) {
		super(0, 0, new Vector2(parent.position.x + PARENT_OFFSET_X, 
				parent.position.y + PARENT_OFFSET_Y), WIDTH, HEIGHT);
		isAttacking = false;
		attackUp= true;
		isReturning = false;
		moveRight = true;
		this.parent = parent;
		//phase2pos = 4;
		
		scale = 1;
		bulletCount = 0f;
	}

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam) {
		Array<Enemy> newEnemies = new Array<Enemy>();
		int phase = parent.getPhase();
		float thisMoveDelta = delta * (BASE_SPEED + rank * RANK_SPEED_MULT);
		if (phase == 1) {
			if (isAttacking) {
				
			
				if (attackUp) {
					/*if (position.y < 4f) {
						System.out.println("Arms: Raising position");
						position.y += thisMoveDelta;
					} */
					position.y += (10f - position.y) * thisMoveDelta * 0.4f;
				} else {
					/*
					if (position.y > 2.5f) {
						System.out.println("Arms: Lowering position");
						position.y -= thisMoveDelta;
					}*/
					position.y += (2.5f - position.y) * thisMoveDelta * 0.4f;
				}
				if (moveRight) {
					position.x += thisMoveDelta;
					if (position.x > parent.getPosition().x +32f) {
						
						moveRight = false;
						attackUp = !attackUp;
						//System.out.println("Arms: turn reached end. Changing dir. now attackUp = " + attackUp);
					}
					if (rank > 0.9f && position.x < parent.getPosition().x + 26f) {
						bulletCount -= delta;
						if (bulletCount <= 0f) {
							//System.out.println("Arms: Spawning bullet!");
							bulletCount = 0.75f - 0.39f* rank;
							BulletSimple bullet = new BulletSimple(10f + 2*rank, 0f, new Vector2(position.x+WIDTH/2,
									position.y+HEIGHT/2), parent.FIREBALL_WIDTH, parent.FIREBALL_HEIGHT, new Vector2(ship.position.x + ship.getWidth()/2-
									(position.x+WIDTH/2), ship.position.y+ship.getHeight()/2-(position.y+HEIGHT/2)),BulletSimple.Graphic.FIRE);
							//parent.bullets.add(bullet);
							newEnemies.add(bullet);
						}
					}
				} else {
					position.x -= thisMoveDelta;
					if (position.x < parent.getPosition().x + PARENT_OFFSET_X + 5f) {
						//System.out.println("Arms: End in sight. Returning");
						isAttacking = false;
						isReturning = true;
					}
					if (rank > 0.7f && position.x < parent.getPosition().x + 26f) {
						bulletCount -= delta;
						if (bulletCount <= 0f) {
							//System.out.println("Arms: Spawning bullet!");
							bulletCount = 0.75f - 0.39f* rank;
							BulletSimple bullet = new BulletSimple(10f + 2*rank, 0f, new Vector2(position.x+WIDTH/2,
									position.y+HEIGHT/2), parent.FIREBALL_WIDTH, parent.FIREBALL_HEIGHT, new Vector2(ship.position.x + ship.getWidth()/2-
									(position.x+WIDTH/2), ship.position.y+ship.getHeight()/2-(position.y+HEIGHT/2)),BulletSimple.Graphic.FIRE);
							//parent.bullets.add(bullet);
							newEnemies.add(bullet);
						}
					}
				}
				
				//System.out.println("Arms: y="+position.y);
			} else if (isReturning){
				position.x -= (position.x - parent.position.x+PARENT_OFFSET_X) * delta;
				position.y -= (position.y - parent.position.y+PARENT_OFFSET_Y) * delta * 1.5f;
				if (position.x <= parent.position.x + PARENT_OFFSET_X) {
					System.out.println("Arms: I'm back!");
					isReturning = false;
					isAttacking = false;
				}
				
			} else {
				position.x = parent.position.x + PARENT_OFFSET_X;
				position.y = parent.position.y + PARENT_OFFSET_Y;
			}
			
			
			
			
			//Phase 2 actions
		} else if (phase == 2) {
			/*
			float aimX, aimY;
			if (phase2pos == 0) {
				//Move to bottom of screen
				velocity = new Vector2(0, -thisMoveDelta/delta);
				position.add(velocity.mul(delta)); 
				if (position.y < 1.0f) {
					phase2pos = 1;
					circlePos = 0;
					velocity = new Vector2(0, -thisMoveDelta/delta);
					isAttacking = false;
					isReturning = false;
				}
			} else if (phase2pos == 1) {
				//make a circle in one of two directions
				/*float centerX;
				float centerY = 1f;
				if (attackUp) {
					centerX = parent.getPosition().x+PHASE2_OFFSET_X-5f;
				} else {
					centerX = parent.getPosition().x+PHASE2_OFFSET_X+5f; 
				}
				float radius = 5f;
				float xCoord = centerX + MathUtils.sin(thisMoveDelta) * radius;
				float yCoord = centerY + MathUtils.cos(thisMoveDelta) * radius;
				position = new Vector2(xCoord, yCoord);
				System.out.println("Arms: Circle position ="+position);
				if (position.y >= centerY + radius - 0.2f) {
					System.out.println("Arms: Circle Next phase");
					phase2pos = 2;
				}*/
			/*
				if (attackUp) {
					velocity.rotate(-150*delta*rank);
				} else {
					velocity.rotate(150*delta*rank);
				}
				System.out.println("Velocity="+velocity);
				position.add(new Vector2(velocity).mul(delta));
				
				if (position.y >= 5.9f) {
					System.out.println("Arms: Circle Next phase");
					phase2pos = 2;
					
					//aim at the right location
					float range = 10f;
					if (attackUp) {
						aimX =  (-position.x + (parent.getPosition().x+PHASE2_OFFSET_X+range));
						
					} else {
						aimX =  (-position.x + (parent.getPosition().x+PHASE2_OFFSET_X-range));
						
					}
					aimY =  (-position.y + 6f);
					velocity = new Vector2(aimX,aimY).nor().mul(thisMoveDelta/delta);
				}
			} else if (phase2pos == 2) {
				float range = 10f;
				if (attackUp) {
					
					if (position.x >= parent.getPosition().x+range-0.1f) {
						System.out.println("attackUp so is to right of " + (parent.getPosition().x+range-0.1f));
						phase2pos = 3;
					}
				} else {
					if (position.x >= parent.getPosition().x-range+0.1f) {
						System.out.println("!attackUp so is to right of " + (parent.getPosition().x-range+0.1f));
						phase2pos = 3;
					}
				}
				
				
				//position.y -= (position.y - 6f) * thisMoveDelta;
				position.add(new Vector2(velocity).mul(delta));
			} else if (phase2pos == 3) {
				float pickUp = 12f;
				if (attackUp) {
					position.x -= (position.x - (parent.getPosition().x+PHASE2_OFFSET_X+pickUp))*thisMoveDelta;
				} else {
					position.x -= (position.x - (parent.getPosition().x+PHASE2_OFFSET_X-pickUp))*thisMoveDelta;
				}
				position.y -= (position.y - (parent.getPosition().y + PHASE2_OFFSET_Y)) * thisMoveDelta;
				if (position.y >= parent.getPosition().y+PHASE2_OFFSET_Y-0.1f) {
					phase2pos=4;
				}
			} else if (phase2pos == 4) {
				//also the idle state
				position.x = parent.position.x + PHASE2_OFFSET_X;
				position.y = parent.position.y + PHASE2_OFFSET_Y;
			}
			System.out.println("phase2pos="+phase2pos+"  position="+position);
			
			*/
			if (targetPosition == null) {
				targetPosition = new Vector2(ship.getPosition().x + ship.getWidth()/2-width/2, ship.getPosition().y+ship.getHeight()/2-height/2-1f);
			}
			if (isAttacking) {
				velocity = new Vector2(targetPosition.x - position.x, targetPosition.y - position.y).nor().mul(thisMoveDelta/delta);
				position.add(new Vector2(velocity).mul(delta));
				if (position.y <= targetPosition.y + 1f) {
					position.y = targetPosition.y+1f;
					count += delta;
					if (count > 0.2f+2.5f*rank) {
						isAttacking = false;
						isReturning = true;
					}
				}
			} else if (isReturning) {
				position.x += (parent.getPosition().x + PHASE2_OFFSET_X - position.x) * delta * 0.8f;
				position.y += (parent.getPosition().y + PHASE2_OFFSET_Y - position.y) * delta * 0.8f;
			} else {
				position.x = parent.position.x + PHASE2_OFFSET_X;
				position.y = parent.position.y + PHASE2_OFFSET_Y;
			}
		} else if (phase == 3) {
			//System.out.println(isAttacking + " "+moveRight + "  isReturning?="+isReturning);
			//float phase3SpeedIncrease = 3f+6f*rank;
			float phase3SpeedIncrease = 0f;
			if (isAttacking) {
				if (moveRight) {
					velocity = new Vector2(thisMoveDelta / delta + phase3SpeedIncrease, 0);
					if (position.x >= parent.getPosition().x + 24f) {
						moveRight = false;
						velocity = new Vector2(0, thisMoveDelta/delta+phase3SpeedIncrease);
					}
				} else {
					if (position.y >= 14f) {
						velocity = new Vector2(parent.position.x + PHASE3_OFFSET_X - position.x, parent.position.y+PHASE3_OFFSET_Y-position.y).nor().mul(thisMoveDelta/delta+phase3SpeedIncrease/(10f-2f*rank));
						isAttacking = false;
						isReturning = true;
					}
				}
			} else if (isReturning) {
				if (position.x <= parent.position.x+PHASE3_OFFSET_X) {
					isReturning = false;
					velocity = new Vector2(0,0);
				}
			} else {
				position.x = parent.position.x + PHASE3_OFFSET_X;
				position.y = parent.position.y + PHASE3_OFFSET_Y;
			}
			position.add(new Vector2(velocity).mul(delta));
		}
		
		if (isAttacking || isReturning) {
			rotation += 600*delta;
		} else if (phase == 3 && parent.getState() == State.THROW_ARMS) {
			rotation -= 100*delta;
		} else {
			rotation = 0;
		}
		return newEnemies;
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}
	
	public void beginAttack(boolean attackUp) {
		isAttacking = true;
		moveRight = true;
		isReturning = false;
		this.attackUp=attackUp;
		if (parent.getPhase() == 2) {
			targetPosition = null;
		}
		count = 0f;
	}
	
	public boolean isAttacking() {
		return isAttacking;
	}
	
	public boolean isReturning() {
		return isReturning;
	}
	
	public void resetPosition() {
		
		position = new Vector2(parent.position.x + PARENT_OFFSET_X, 
				parent.position.y + PARENT_OFFSET_Y);
		rotation = 0f;
		isAttacking = false;
		isReturning = false;
	}
	
	public void increaseSize(float scaleIncrease) {
		if (width <= PHASE3_SCALE * WIDTH) {
			scale += scaleIncrease;
			width = WIDTH * scale;
			height = HEIGHT * scale;
		}
		
	}
	
	public float getScale() {
		return scale;
	}
	
	

	
}
