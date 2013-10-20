package deco2800.arcade.cyra.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import deco2800.arcade.cyra.world.ParallaxCamera;
import deco2800.arcade.cyra.world.Sounds;

public class EnemySpiderBoss extends Enemy {
	public enum State {
		IDLE, THROW_ARMS, FIREBALL, RAM, LASER, DEATH
	}
	
	
	public static final float WIDTH = 6f;
	public static final float HEIGHT = 10f; 
	public static final float ATTACK_RATE = 6f;
	public static final float ATTACK_RANK_RATE = 5f;
	public static final float MOUTH_OFFSET_X = 5f;
	public static final float MOUTH_OFFSET_Y = 8.5f;
	public static final float FIREBALL_WIDTH = 0.5f;
	public static final float FIREBALL_HEIGHT = 0.5f;
	public static final float INVINCIBLE_TIME = 0.3f;
	public static final float RAM_ANGLE = -50f;
	
	public static final int PHASE1_HEALTH = 5;
	public static final int PHASE2_HEALTH = 4;
	public static final int PHASE3_HEALTH = 8;
	
	
	private float rank;
	private ParallaxCamera cam;
	private int phase;
	private float count;
	private int count2;
	private float count3;
	private float countEnemies;
	private int movesUntilVulnerable;
	private boolean performingTell;
	private EnemySpiderBossArms arms;
	private int health;
	//private float invincibleTime;
	//private boolean flash;
	private boolean beingHit;
	private Array<EnemySpiderBossPopcorn> popcorns;
	private float phase2fireballPosition;
	private Array<MovablePlatformAttachment> solidParts;
	private Array<Rectangle> charges;
	private int headFrame;
	private int ramFrame;
	private float fireballRotation;
	private LaserBeam laserBeam;
	//private LaserBeam cutsceneLaser;
	
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
		movesUntilVulnerable = 0;
		performingTell = false;
		state = State.IDLE;
		arms = new EnemySpiderBossArms(this);
		//bullets = new Array<Enemy>();
		popcorns = new Array<EnemySpiderBossPopcorn>();
		health = PHASE1_HEALTH;
		invincibleTime = 0;
		beingHit = false;
		phase2fireballPosition = 0f;
		headFrame = 0;
		flash = true;
		ramFrame = 0;
		//state = State.INTRO1;
		healthName = "Steven The Mutant Spider";
		
		
	}

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam) {
		Array<Enemy> newEnemies = new Array<Enemy>();
		int randInt;
		count -= delta;
		//if (phase == 1) {
			//Choose a new move if it is time to
			if (count < 0) {
				switch(state) {
				case IDLE:
					
					//choose an attack
					if (movesUntilVulnerable-- == 0) {
						//Perform arm throwing move
						System.out.println("Chosen: Throwing arms");
						state = State.THROW_ARMS;
						performingTell = true;
						count = 1.1f - rank;
						movesUntilVulnerable = 2;
						
					} else {
						randInt = MathUtils.random(2);
						//randInt = 0; //make it do a certain attack every time for debugging
						if (randInt == 0 || (randInt == 1 && phase == 3)) {
							//do fireball attack
							System.out.println("Chosen: Shooting fireballs");
							performingTell = true;
							state = State.FIREBALL;
							count = 2.1f - 2*rank;
							phase2fireballPosition = MathUtils.random(-14f, 14f);
							headFrame = 1;
							ramFrame = 0;
							count3 = 0f;
						} else if (randInt == 1 || (randInt == 2 && phase == 2)) {
							System.out.println("Chosen: Ram attack");
							performingTell = true;
							state = State.RAM;
							count = 2.8f - 2* rank;
							phase2fireballPosition = MathUtils.random(-14f, 14f);
						} else if (randInt == 2) {
							System.out.println("Chosen: Laser attack");
							performingTell = true;
							state = State.LASER;
							count = 2.1f - 2* rank;
							count3=0f;
							charges = new Array<Rectangle>();
							charges.add(new Rectangle(position.x+MOUTH_OFFSET_X-1, position.y+MOUTH_OFFSET_Y-1, 2,2));
							charges.add(new Rectangle(position.x+MOUTH_OFFSET_X + 1.6f, position.y+MOUTH_OFFSET_Y, 1, 1));
							charges.add(new Rectangle(position.x+MOUTH_OFFSET_X + 0.1f, position.y+MOUTH_OFFSET_Y-0.25f, 1, 1));
							charges.add(new Rectangle(position.x+MOUTH_OFFSET_X + 0.5f, position.y+MOUTH_OFFSET_Y+0.44f, 1, 1));
							headFrame = 1;
							Sounds.playLaserChargeSound(0.5f);
						}
					}
					break;
					
				case FIREBALL:
					if (performingTell) {
						performingTell =false;
						count = 0.2f;
						count2 = 0;
						fireballRotation = 0f;
					} else {
						float xToShip = (ship.position.x +ship.getWidth()/2-FIREBALL_WIDTH/2)- (position.x+MOUTH_OFFSET_X);
						float yToShip = (ship.position.y + ship.getHeight()/2-FIREBALL_HEIGHT/2) - (position.y+MOUTH_OFFSET_Y);
						Vector2 dirToShip = new Vector2(xToShip, yToShip);
						Vector2 dirPerp0 = new Vector2(-yToShip, xToShip);
						Array<Vector2> spawnDirections = new Array<Vector2>();
						if (phase == 2) {
							if (count2 == 10 + (int)(rank * 30f)) {
								count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
								state=State.IDLE;
								headFrame = 0;
							} else {
								fireballRotation+= 360/(10+rank*30f);
								spawnDirections.add(new Vector2(1,0).rotate(fireballRotation));
								count = 0.2f- rank*0.2f;
							}
							
						} else {
							switch(count2) {
							
							case 0:
							case 1:
							case 3:
								System.out.println("Firing fireballs");
								/*Vector2 directionToShip = new Vector2(ship.position.x - position.x, ship.position.y - position.y);
								Vector2 direction0 = new Vector2(directionToShip);
								direction0.add(new Vector2())*/
	
								//Vector2 dirPerp1 = new Vector2(xToShip, -yToShip);
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(5f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-5f)));
								//Shoot fireballs around player position but not at
								/*System.out.println("dirToShip="+dirToShip+"  dirPerp0="+dirPerp0+"  dir="+ (new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(5f)) +
										"  dir2=" + (new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-5f)));
								System.out.println("widestDir1=" + (new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(16f)) + "  widestDir2=" +
										(new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-16f)));*/
								
								/*System.out.println("(new Vector2(dirPerp0))=" + (new Vector2(dirPerp0)) + "  (new Vector2(dirPerp0)).nor()=" + (new Vector2(dirPerp0)).nor());
								System.out.println("(new Vector2(dirPerp0)).nor().mul(-16f))="+(new Vector2(dirPerp0)).nor().mul(-16f));
								System.out.println("new Vector2(dirToShip)=" + new Vector2(dirToShip));
								System.out.println("(new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-16f)))="+(new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-16f)));
								*/
								if (rank > 0.4f) {
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(4.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-4.5f)));
									
								}
								if (rank > 0.6f) {
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(3.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-3.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(5.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-5.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(7.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-7.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(9.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-9.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(13.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-13.5f)));
								}
								if (rank > 0.8f) {
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(3f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-3f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(6f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-6f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(12f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-12f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(14f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-14f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(16f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-16f)));
									
								}
								count = 0.2f;
								
								break;
							case 4:
							case 5:
							
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(0.2f)));
								spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-0.2f)));
								if (rank > 0.4f) {
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(0.4f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-0.4f)));
								}
								if (rank > 0.6f) {
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(0.6f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-0.6f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(0.8f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-0.8f)));
								}
								if (rank > 0.8f) {
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(1f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(1f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(0.1f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-0.1f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(0.3f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-0.3f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(0.5f)));
									spawnDirections.add((new Vector2(dirToShip)).add((new Vector2(dirPerp0)).nor().mul(-0.5f)));
								}
								count = 0.025f;
								break;
							case 6:
								
								count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
								state=State.IDLE;
								headFrame = 0;
							}
						}
						float bulletSpeed;
						if (phase == 2) {
							bulletSpeed = 5f;
						} else {
							bulletSpeed = 5f + 30f * rank;
						}
						for (Vector2 vec: spawnDirections) {
							
							BulletSimple bullet = new BulletSimple(bulletSpeed, 0f, new Vector2(position.x + MOUTH_OFFSET_X, 
									position.y + MOUTH_OFFSET_Y), FIREBALL_WIDTH, FIREBALL_HEIGHT, vec, BulletSimple.Graphic.FIRE);
							//bullets.add(bullet);
							newEnemies.add(bullet);
						}
						count2++;
					}
					break;
								
				case THROW_ARMS:
					if (performingTell) {
						System.out.println("Telling arms to start");
						performingTell = false;
						count = 1.5f-rank;
						//choose whether to attack up or down
						boolean throwUp = MathUtils.randomBoolean();
						arms.beginAttack(throwUp);
					} else {
						if ((phase ==1 && !arms.isAttacking()) || phase != 1) {
							count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
							state = State.IDLE;
						}
					}
					break;
				case RAM:
					if (performingTell) {
						performingTell = false;
						if (phase == 1) {
							velocity = new Vector2(1f, 2.5f);
							velocity.nor().mul(1f+rank*30f);
						} else if (phase ==2) {
							velocity = new Vector2(0, -1f-50*rank);
						}
						System.out.println("Ram velocity starts at"+velocity);
						count3 = 0f;
						count2 = 0;
						count = 0f;
					} else {
						
					}
				
					break;
				case LASER:
					if (performingTell) {
						Vector2 intendedDirection = new Vector2((ship.getPosition().x+ ship.getWidth()/2) - (position.x+MOUTH_OFFSET_X),
								(ship.getPosition().y +ship.getHeight()/2)- (position.y+MOUTH_OFFSET_Y));
						float intendedAngle = intendedDirection.angle();
						laserBeam = new LaserBeam(intendedAngle, new Vector2(position.x+MOUTH_OFFSET_X, position.y+MOUTH_OFFSET_Y), 2f+ rank*5f, false, 1.5f-rank);
						newEnemies.add(laserBeam);
						performingTell = false;
						count = 6f-2*rank;
						charges.clear();
						Sounds.stopLaserChargeSound();
					} else {
						count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
						state = State.IDLE;
						headFrame = 0;
					}
					break;
				case DEATH:
					count3--;
					float buffer = 2f;
					float randX = MathUtils.random(-WIDTH/2-buffer, WIDTH/2+buffer);
					float randY = MathUtils.random(-HEIGHT/2-buffer, HEIGHT/2+buffer);
					newEnemies.add(new Explosion(new Vector2(position.x+width/2-Explosion.WIDTH/2+randX, position.y+height/2-Explosion.HEIGHT/2+randY)));
					Sounds.playExplosionLong(0.5f);
					count = 0.08f;
					if (count3 <= 0) {
						isDead = true;
						startingNextScene=true;
					}
					break;
				}
				
			}
			
			//Make the move
			//System.out.println("state="+ state+" phase="+phase+" health=" +health);
			if (state == State.IDLE) {
				if (phase == 1) {
					float lerp = 0.8f;
					position.y -= delta * (position.y - (4f - 3 * MathUtils.cos(count))) * lerp;
					position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 1f)) * lerp;
				} else if (phase == 2) {
					//System.out.println("yeah, yeah. I'm moving!");
					float lerp = 0.8f;
					position.y -= delta * (position.y - (cam.position.y + cam.viewportHeight/2 - 9.5f)) * lerp;
					position.x -= delta * (position.x - (cam.position.x -WIDTH/2-2 * MathUtils.cos(4f*count))) * lerp;
				}
				//rotation += (0f - rotation) * 0.8f;
			} else if (state == State.THROW_ARMS){
				if (performingTell) {
					float lerp = 0.7f;
					if (phase == 1) {
						
						position.y -= delta * (position.y - 5f) * lerp;
						position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 0.5f)) * lerp;
					} else if (phase ==2) {
						position.y -= delta * (position.y - (cam.position.y + cam.viewportHeight/2 - 8f)) * lerp;
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
						position.y -= delta * (position.y - (cam.position.y + cam.viewportHeight/2 - 9.5f)) * lerp;
						position.x -= delta * (position.x - (cam.position.x -WIDTH/2 + phase2fireballPosition)) * 1.4;
					}
					count3 += delta;
					if (count3 > 0.1f) {
						ramFrame++;
						count3 = 0f;
					}
					
				}
			} else if (state == State.RAM){
				
				if (performingTell) {
					ramFrame = 0;
					float lerp=0.9f;
					if (phase == 1) {
						position.y -= delta * (position.y - (0f)) * lerp;
						position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 6.5f)) * lerp;
						//rotation += (-RAM_ANGLE - rotation) *3f* 0.8f * delta;
					} else if (phase == 2) {
						position.y -= delta * (position.y - (10f)) * lerp;
						position.x -= delta * (position.x - (ship.getPosition().x+ship.getWidth()/2-width/2)) * lerp*rank;
					}
				} else {
					count -= delta;
					if (count <= 0) {
						if (++ramFrame ==4) {
							ramFrame = 0;
						}
						count = 0.01f;
					}
					if (phase == 1) {/*
						System.out.println("velocity="+velocity+"   angle="+velocity.angle()+ "   position="+position+"   count2="+count2);
						if (count2 == 0 || count2 == 1) {
							if (velocity.angle() <= 4f) {
								//allow next part of attack to start
								count2 = 1;
							}
							if (velocity.angle() >= 270f && velocity.angle() <= 272f) {
								//found downward angle
								velocity.mul(1f+delta*2*rank);
								
								
								
							} else {
								//adjust towards downward angle
								velocity.rotate(-delta * 145f * rank);
							}
						} 
						if (position.y <= 2f && count2 == 1) {
							velocity = new Vector2(-BlockMakerSpiderBoss.SPEED, 0);
							count2 = 2;
						} else if (count2==2) {
							if (position.x <= cam.position.x+2f) {
								velocity = new Vector2(-BlockMakerSpiderBoss.SPEED * 3, 0);
							}
							if (position.x <= cam.position.x-cam.viewportWidth+1f) {
					
								count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
								state = State.IDLE;
							}
						}
						*/
						if (count2 == 0) {
							velocity = new Vector2(1,0).nor().mul(10f + rank*45f);
							if (position.x > cam.position.x + cam.viewportWidth/2 - width - (10f-5f*rank)) {
								count2 = 1;
								velocity = new Vector2(-BlockMakerSpiderBoss.SPEED,0);
							}
						} else if (count2 == 1) {
							if (position.x < cam.position.x + cam.viewportWidth/2 - width - (11f-5f*rank)) {
								count2 = 2;
								velocity = new Vector2(-BlockMakerSpiderBoss.SPEED * (3+3*rank),0);
							}
 						} else if (count2 == 2) {
 							if (position.x <= cam.position.x + cam.viewportWidth/2 - width - (12f-5f*rank)) {
	 							count = ATTACK_RATE - rank*ATTACK_RANK_RATE;
	 							state = State.IDLE;
 							}
 						}
						///rotation += (-RAM_ANGLE - rotation) * 0.8f * delta;
					} else if (phase == 2){
						if (position.y <= 1f) {
							count3 += delta;
							System.out.println("count3="+count3);
							if (count3 <= 1f) {
								velocity = new Vector2(0,0);
							} else if (count3 <= 3f-rank){
								velocity = new Vector2(0, -1f-rank*3f);
							} else {
								count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
								state = State.IDLE;
							}
						}
					} else if (phase ==3) {
						count3 -= delta;
						if (count3<0) {
							count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
							state = State.IDLE;
						}
					}
					if (phase != 3) {
						position.add(new Vector2(velocity).mul(delta));
					}
					
				}
			} else if (state == State.LASER) {
				if (performingTell) {
					count3+= delta;
					if (count3 > 0.2f) {
						charges.add(new Rectangle(position.x+MOUTH_OFFSET_X+ MathUtils.random(-0.5f,1.5f), position.y+MOUTH_OFFSET_Y+MathUtils.random(-1.7f, 0.7f), 1,1));
					}
					float chargeSpeed = 2f;
					for (int i=1; i<charges.size; i++) {
						Rectangle r = charges.get(i);
						Vector2 rVel = new Vector2(charges.get(0).x - r.x, charges.get(0).y - r.y).nor().mul(chargeSpeed);
						r.x += rVel.x * delta;
						r.y += rVel.y * delta;
						if (r.x < charges.get(0).x) {
							charges.removeIndex(i);
							i--;
						}
					}
				} else if (phase ==3) {
					Vector2 intendedDirection = new Vector2(ship.getPosition().x + ship.getWidth()/2 - (position.x+MOUTH_OFFSET_X), 
							ship.getPosition().y + ship.getHeight()/2 - (position.y+MOUTH_OFFSET_Y));
					float intendedAngle = intendedDirection.angle();
					
					
					
					float currentAngle = laserBeam.getRotation();
					
					/*float angleDifference = Math.abs(intendedAngle - currentAngle);
					System.out.println("Beforeangle: "+angleDifference);
					if (angleDifference < 180 && intendedAngle > currentAngle) {
						;
					} else if (angleDifference < 180 && intendedAngle < currentAngle) {
						angleDifference = -angleDifference;
					} else if (angleDifference > 180 && intendedAngle > currentAngle) {
						angleDifference = -angleDifference;
					} else if (angleDifference > 180 && intendedAngle < currentAngle) {
						;
						//angleDifference = -angleDifference/Math.abs(angleDifference) * Math.abs(180-angleDifference);
					}*/
					
					float angleDifference;
					if (Math.abs(intendedAngle-currentAngle) < 180) {
						angleDifference = intendedAngle-currentAngle;
					} else if (intendedAngle>currentAngle) {
						angleDifference = intendedAngle-currentAngle-360;
					} else {
						angleDifference = intendedAngle-currentAngle+360;
					}
					
					
					laserBeam.setRotation(laserBeam.getRotation() + (angleDifference) *delta * (0.2f+rank/2));
					System.out.println("Angle Difference" + angleDifference+" laserRotation:" + laserBeam.getRotation());
				}
			}
		//}
			if ((phase == 3 || (phase == 1 && rank > 0.7)) && ship.getPosition().x < position.x + width/2) {
				state = State.RAM;
				count = 1f;
				count3 = 1f;
				if (ramFrame >= 4) {
					ramFrame = 0;
				}
				performingTell = false;
			}
		
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
			
		Array<Enemy> armsNewEnemies = arms.advance(delta, ship, rank, cam);
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
	/*
	private BulletSimple spawnBullet(Vector2 direction) {
		
		//not sure which rank this is pulling from or if it matters
		BulletSimple bullet = new BulletSimple(5f + 30f * rank, 0f, new Vector2(position.x + MOUTH_OFFSET_X, 
				position.y + MOUTH_OFFSET_Y), FIREBALL_WIDTH, FIREBALL_HEIGHT, direction, BulletSimple.Graphic.FIRE);
		//bullets.add(bullet);
		return bullet;
	}*/
	
	private void changePhase() {
		//boolean setSolidParts = true;
		boolean setSolidParts = false;
		switch(phase){
		case 1:
			setSolidParts = false;
			startingNextScene = true;
			state = State.IDLE;
			health = PHASE2_HEALTH;
			count = ATTACK_RATE;
			break;
		case 2:
			startingNextScene = true;
			state = State.IDLE;
			health = PHASE3_HEALTH;
			count = ATTACK_RATE;
			break;
		case 3:
			state = State.DEATH;
			count = 0.1f;
			count3=7f;
			break;
		}
		//for (MovablePlatformAttachment apa: solidParts) {
		for (int i=0; i < solidParts.size; i++) {
			MovablePlatformAttachment mpa = solidParts.get(i);
			if (setSolidParts) {
				if (i == 0) {
					mpa.setTargetOffset(new Vector2(2f,5f));
				} else {
					mpa.setTargetOffset(new Vector2(5f,2f));
				}
			} else {
				mpa.setTargetOffset(new Vector2(-100,0));
			}
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
		if (state == State.RAM && !performingTell) {
			if (phase == 1) {
				rects.add(new Rectangle(position.x-10f, position.y-0.5f, 10f + WIDTH+1f, HEIGHT+1f));
				
			} else {
				rects.add(new Rectangle(position.x - 0.5f, position.y - 0.5f, WIDTH+1f, HEIGHT+20f));
			}
		} else {
			rects.add(new Rectangle(position.x+0.5f, position.y+1.5f, WIDTH, 2.5f));
		}
		return rects;
	}
	
	@Override
	public Rectangle getVulnerableBounds() {
		return new Rectangle(position.x + width/2-0.2f, position.y + height-6f, 1.6f,4.8f);
	}
	
	public void setSolidParts(Array<MovablePlatformAttachment> solidParts) {
		this.solidParts = solidParts;
		for (MovablePlatformAttachment mpa: solidParts) {
			mpa.setTargetOffset(new Vector2(-100,0));
		}
	}
	
	public Array<Rectangle> getLaserChargePositions() {
		if (state == State.LASER && performingTell) {
			count3++;
			
		}
		return charges;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public int getHeadFrame() {
		return headFrame;
	}
	
	public void startCutsceneLaser() {
		state = State.LASER;
		count3=0;
		charges = new Array<Rectangle>();
		charges.add(new Rectangle(position.x+MOUTH_OFFSET_X-1, position.y+MOUTH_OFFSET_Y-1, 2,2));
		charges.add(new Rectangle(position.x+MOUTH_OFFSET_X + 1.6f, position.y+MOUTH_OFFSET_Y, 1, 1));
		charges.add(new Rectangle(position.x+MOUTH_OFFSET_X + 0.1f, position.y+MOUTH_OFFSET_Y-0.25f, 1, 1));
		charges.add(new Rectangle(position.x+MOUTH_OFFSET_X + 0.5f, position.y+MOUTH_OFFSET_Y+0.44f, 1, 1));
		Sounds.playLaserChargeSound(0.5f);
	}
	
	public void advanceCutsceneLaser(float delta) {
		
		count3+= delta;
		if (count3 > 0.2f) {
			charges.add(new Rectangle(position.x+MOUTH_OFFSET_X+ MathUtils.random(-0.5f,1.5f), position.y+MOUTH_OFFSET_Y+MathUtils.random(-1.7f, 0.7f), 1,1));
		}
		float chargeSpeed = 2f;
		for (int i=1; i<charges.size; i++) {
			Rectangle r = charges.get(i);
			Vector2 rVel = new Vector2(charges.get(0).x - r.x, charges.get(0).y - r.y).nor().mul(chargeSpeed);
			r.x += rVel.x * delta;
			r.y += rVel.y * delta;
			if (r.x < charges.get(0).x) {
				charges.removeIndex(i);
				i--;
			}
		}
		
		
	}
	
	public int getRamFrame() {
		return ramFrame;
	}
	

	
	@Override
	public boolean displayHealth() {
		return true;
	}
	
	@Override
	public float getHealthPercentage() {
		if (phase == 1) {
			return ((float)health)/PHASE1_HEALTH; 
		} else if (phase == 2) {
			return ((float)health)/PHASE2_HEALTH;
		} else {
			return ((float)health)/PHASE3_HEALTH;
		}
		
	}
}
