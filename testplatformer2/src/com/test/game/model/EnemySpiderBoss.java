package com.test.game.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.world.ParallaxCamera;

public class EnemySpiderBoss extends Enemy {
	public enum State {
		IDLE, THROW_ARMS, FIREBALL, RAM, LASER
	}
	
	
	public static final float WIDTH = 10f;
	public static final float HEIGHT = 10f; 
	public static final float ATTACK_RATE = 6f;
	public static final float ATTACK_RANK_RATE = 5f;
	public static final float MOUTH_OFFSET_X = 7f;
	public static final float MOUTH_OFFSET_Y = 5f;
	public static final float FIREBALL_WIDTH = 0.5f;
	public static final float FIREBALL_HEIGHT = 0.5f;
	public static final float INVINCIBLE_TIME = 0.3f;
	
	
	private float rank;
	private ParallaxCamera cam;
	private int phase;
	private float count;
	private int count2;
	private float countEnemies;
	private int movesUntilVulnerable;
	private boolean performingTell;
	private EnemySpiderBossArms arms;
	private int health;
	private float invincibleTime;
	private boolean beingHit;
	private Array<EnemySpiderBossPopcorn> popcorns;
	private float phase2fireballPosition;
	
	//private BlockMakerSpiderBoss blockMaker;
	
	private State state;
	
	//the following array is only added for a quick fix to make bullets with screen not terrain. Actually that's pretty weird. Might remove? now removes bullets after phase change
	//public Array<Enemy> bullets;
	
	public EnemySpiderBoss(Vector2 pos, float rank, ParallaxCamera cam) {
		super(0, 0, pos, WIDTH, HEIGHT);
		this.rank = rank;
		this.cam = cam;
		//this.blockMaker = blockMaker;
		phase = 1;
		count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
		count2 = 0;
		countEnemies = 0f;
		movesUntilVulnerable = 1;
		performingTell = false;
		state = State.IDLE;
		arms = new EnemySpiderBossArms(this);
		//bullets = new Array<Enemy>();
		popcorns = new Array<EnemySpiderBossPopcorn>();
		health = 3;
		invincibleTime = INVINCIBLE_TIME;
		beingHit = false;
		phase2fireballPosition = 0f;
		//state = State.INTRO1;
		
		
	}

	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {
		Array<Enemy> newEnemies = new Array<Enemy>();
		int randInt;
		count -= delta;
		//if (phase == 1) {
			//Choose a new move if it is time to
			if (count < 0) {
				switch(state) {
				case IDLE:
					//choose an attack
					if (--movesUntilVulnerable == 0) {
						//Perform arm throwing move
						System.out.println("Chosen: Throwing arms");
						state = State.THROW_ARMS;
						performingTell = true;
						count = 1.1f - rank;
						movesUntilVulnerable = 2;
						
					} else {
						randInt = MathUtils.random(1);
						if (randInt == 0 || (randInt == 1 && phase == 3)) {
							//do fireball attack
							System.out.println("Chosen: Shooting fireballs");
							performingTell = true;
							state = State.FIREBALL;
							count = 2.1f - 2*rank;
							phase2fireballPosition = MathUtils.random(-14f, 14f);
						} else if (randInt == 1 || (randInt == 2 && phase == 2)) {
							System.out.println("Chosen: Ram attack");
							performingTell = true;
							state = State.RAM;
							count = 2.1f - 2* rank;
							phase2fireballPosition = MathUtils.random(-14f, 14f);
						} else if (randInt == 2) {
							System.out.println("Chosen: Ram attack");
							performingTell = true;
							state = State.RAM;
							count = 2.1f - 2* rank;
						}
					}
					break;
					
				case FIREBALL:
					if (performingTell) {
						performingTell =false;
						count = 0.2f;
						count2 = 0;
					} else {
						float xToShip = (ship.position.x +ship.getWidth()/2)- (position.x+MOUTH_OFFSET_X);
						float yToShip = (ship.position.y + ship.getHeight()/2) - (position.y+MOUTH_OFFSET_Y);
						Vector2 dirToShip = new Vector2(xToShip, yToShip);
						Vector2 dirPerp0 = new Vector2(-yToShip, xToShip);
						Array<Vector2> spawnDirections = new Array<Vector2>();
						switch(count2) {
						
						case 0:
						case 1:
						case 3:
							System.out.println("Firing fireballs");
							/*Vector2 directionToShip = new Vector2(ship.position.x - position.x, ship.position.y - position.y);
							Vector2 direction0 = new Vector2(directionToShip);
							direction0.add(new Vector2())*/

							//Vector2 dirPerp1 = new Vector2(xToShip, -yToShip);
							spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(5f)));
							spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-5f)));
							//Shoot fireballs around player position but not at
							/*System.out.println("dirToShip="+dirToShip+"  dirPerp0="+dirPerp0+"  dir="+ (new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(5f)) +
									"  dir2=" + (new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-5f)));
							System.out.println("widestDir1=" + (new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(16f)) + "  widestDir2=" +
									(new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-16f)));*/
							
							/*System.out.println("(new Vector2(dirPerp0))=" + (new Vector2(dirPerp0)) + "  (new Vector2(dirPerp0)).nor()=" + (new Vector2(dirPerp0)).nor());
							System.out.println("(new Vector2(dirPerp0)).nor().scl(-16f))="+(new Vector2(dirPerp0)).nor().scl(-16f));
							System.out.println("new Vector2(dirToShip)=" + new Vector2(dirToShip));
							System.out.println("(new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-16f)))="+(new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-16f)));
							*/
							if (rank > 0.4f) {
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(4.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-4.5f)));
								
							}
							if (rank > 0.6f) {
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(3.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-3.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(5.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-5.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(7.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-7.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(9.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-9.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(13.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-13.5f)));
							}
							if (rank > 0.8f) {
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(3f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-3f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(6f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-6f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(12f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-12f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(14f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-14f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(16f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-16f)));
								
							}
							count = 0.2f;
							
							break;
						case 4:
						case 5:
						
							spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(0.2f)));
							spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-0.2f)));
							if (rank > 0.4f) {
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(0.4f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-0.4f)));
							}
							if (rank > 0.6f) {
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(0.6f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-0.6f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(0.8f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-0.8f)));
							}
							if (rank > 0.8f) {
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(1f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(1f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(0.1f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-0.1f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(0.3f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-0.3f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(0.5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().scl(-0.5f)));
							}
							count = 0.025f;
							break;
						case 6:
							
							count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
							state=State.IDLE;
						}
						for (Vector2 vec: spawnDirections) {
							newEnemies.add(spawnBullet(vec));
						}
						count2++;
					}
					break;
								
				case THROW_ARMS:
					if (performingTell) {
						System.out.println("Telling arms to start");
						performingTell = false;
						count = 0.5f;
						//choose whether to attack up or down
						boolean throwUp = MathUtils.randomBoolean();
						arms.beginAttack(throwUp);
					} else {
						count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
						state = State.IDLE;
					}
					
				case RAM:
					if (performingTell) {
						performingTell = false;
						if (phase == 1) {
							velocity = new Vector2(0, 10f);
						} else if (phase ==2) {
							velocity = new Vector2(0, -1f-50*rank);
						}
						count2= 0;
					} else {
						
					}
				}
					
				
				
			}
			
			//Make the move
			//System.out.println("state="+ state+" phase="+phase+" health=" +health);
			if (state == State.IDLE) {
				if (phase == 1) {
					float lerp = 0.8f;
					position.y -= delta * (position.y - (5f - 4 * MathUtils.cos(count))) * lerp;
					position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 1f)) * lerp;
				} else if (phase == 2) {
					//System.out.println("yeah, yeah. I'm moving!");
					float lerp = 0.8f;
					position.y -= delta * (position.y - (cam.position.y + cam.viewportHeight/2 - 5f)) * lerp;
					position.x -= delta * (position.x - (cam.position.x -WIDTH/2-2 * MathUtils.cos(4f*count))) * lerp;
				}
			} else if (state == State.THROW_ARMS){
				if (performingTell) {
					float lerp = 0.7f;
					if (phase == 1) {
						
						position.y -= delta * (position.y - 5f) * lerp;
						position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 0.5f)) * lerp;
					} else if (phase ==2) {
						position.y -= delta * (position.y - (cam.position.y + cam.viewportHeight/2 - 4f)) * lerp;
						position.x -= delta * (position.x - (cam.position.x -WIDTH/2)) * lerp;
					}
				}
			} else if (state == State.FIREBALL) {
				if (performingTell) {
					float lerp = 0.9f;
					if (phase == 1) {
						position.y -= delta * (position.y - 9.5f) * lerp;
						position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 2.5f)) * lerp;
					} else if (phase == 2) {
						position.y -= delta * (position.y - (cam.position.y + cam.viewportHeight/2 - 6f)) * lerp;
						position.x -= delta * (position.x - (cam.position.x -WIDTH/2 + phase2fireballPosition)) * 1.4;
					}
				}
			} else if (state == State.RAM){
				if (performingTell) {
					float lerp=0.9f;
					if (phase == 1) {
						position.y -= delta * (position.y - (-4f)) * lerp;
						position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 2.5f)) * lerp;
					}
				} else {
					if (phase == 1) {
						if (velocity.angle() < 89f) {
							velocity.rotate(delta * 2f);
							
						} else {
							velocity.scl(1f+delta*2*rank);
						}
						if (position.y <= 2f) {
							velocity = new Vector2(-BlockMakerSpiderBoss.SPEED, 0);
						}
						if (position.x <= 1f) {
							count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
							state = State.IDLE;
						}
					} else {
						if (position.y <= 1f) {
							count2 += delta;
							if (count2 <= 3f) {
								velocity = new Vector2(0,0);
							} else if (count2 <= 3f-rank){
								velocity = new Vector2(0, -1f-rank*3f);
							} else {
								count2 = 0;
								count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
								state = State.IDLE;
							}
						}
					}
					position.add(new Vector2(velocity).scl(delta));
				}
			}
		//}
		
			//Spawn any new enemies 
			if (phase == 2) {
				countEnemies+= delta;
				if (countEnemies >= 3f) {
					countEnemies = 0;
					Vector2 startPos = new Vector2(MathUtils.random(cam.position.x-cam.viewportWidth/2+
							2f, cam.position.x+cam.viewportWidth/2-2f-EnemySpiderBossPopcorn.WIDTH),
							-EnemySpiderBossPopcorn.HEIGHT);
					float startVeloX = MathUtils.random(-20f, 20f);
					EnemySpiderBossPopcorn newEnemy = new EnemySpiderBossPopcorn(startPos, startVeloX);
					newEnemies.add(newEnemy);
					popcorns.add(newEnemy);
				}
				for (EnemySpiderBossPopcorn p: popcorns) {
					if (p.getPosition().x < 0f) {
						popcorns.removeValue(p, true);
					} else if (p.isProjectile()){
						//WARNING NOT SURE IF THIS WILL BE EFFECTED BY ENEMY ATTACK SWAPOVER
						if (p.getBounds().overlaps(getBounds())) {
							p.getPosition().x = -40;
							handleDamage(true);
						}
					}
				}
				
				//check collisions with popcorn
				
			}
			
			if (beingHit) {
				invincibleTime -= delta;
				if (invincibleTime <= 0f) {
					beingHit = false;
				}
			}
			
		Array<Enemy> armsNewEnemies = arms.advance(delta, ship, rank);
		//for (BulletSimple b: bullets) {
			//b.position.x -= delta * BlockMakerSpiderBoss.SPEED;
		//}
		newEnemies.addAll(armsNewEnemies);
		return newEnemies;
		
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public void handleNoTileUnderneath() {
		
		
	}
	
	@Override
	public void handleDamage(boolean fromRight) {
		if (!beingHit) {
			if (--health == 0) {
				changePhase();
			} else {
				beingHit = true;
				invincibleTime = INVINCIBLE_TIME;
			}
		}
	}
	
	public EnemySpiderBossArms getArms() {
		return arms;
	}
	
	private BulletSimple spawnBullet(Vector2 direction) {
		
		//not sure which rank this is pulling from or if it matters
		BulletSimple bullet = new BulletSimple(5f + 30f * rank, 0f, new Vector2(position.x + MOUTH_OFFSET_X, 
				position.y + MOUTH_OFFSET_Y), FIREBALL_WIDTH, FIREBALL_HEIGHT, direction, BulletSimple.Graphic.FIRE);
		//bullets.add(bullet);
		return bullet;
	}
	
	private void changePhase() {
		switch(phase){
		case 1:
		case 2:
			startingNextScene = true;
			state = State.IDLE;
			health = 3;
			break;
		}
		phase++;
	}
	
	public int getPhase() {
		return phase;
	}
	
	@Override
	public Array<Rectangle> getPlayerDamageBounds() {
		Array<Rectangle> rects = new Array<Rectangle>();
		rects.add(arms.getBounds());
		rects.add(new Rectangle(position.x, position.y, WIDTH, 3f));
		return rects;
	}
}
