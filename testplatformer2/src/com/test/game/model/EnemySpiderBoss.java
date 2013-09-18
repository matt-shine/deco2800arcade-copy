package com.test.game.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.world.ParallaxCamera;

public class EnemySpiderBoss extends Enemy {
	public enum State {
		IDLE, THROW_ARMS, FIREBALL
	}
	
	
	public static final float WIDTH = 10f;
	public static final float HEIGHT = 10f; 
	public static final float ATTACK_RATE = 3f;
	public static final float ATTACK_RANK_RATE = 4f;
	public static final float MOUTH_OFFSET_X = 7f;
	public static final float MOUTH_OFFSET_Y = 5f;
	public static final float FIREBALL_WIDTH = 0.5f;
	public static final float FIREBALL_HEIGHT = 0.5f;
	
	
	private float rank;
	private ParallaxCamera cam;
	private int phase;
	private float count;
	private int count2;
	private int movesUntilVulnerable;
	private boolean performingTell;
	private EnemySpiderBossArms arms;
	
	private State state;
	
	public EnemySpiderBoss(Vector2 pos, float rank, ParallaxCamera cam) {
		super(0, 0, pos, WIDTH, HEIGHT);
		this.rank = rank;
		this.cam = cam;
		phase = 1;
		count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
		count2 = 0;
		movesUntilVulnerable = 1;
		performingTell = false;
		state = State.IDLE;
		arms = new EnemySpiderBossArms(this);
		//state = State.INTRO1;
		
	}

	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {
		Array<Enemy> newEnemies = new Array<Enemy>();
		int randInt;
		count -= delta;
		if (phase == 1) {
			//Choose a new move if it is time to
			if (count < 0) {
				switch(state) {
				case IDLE:
					//choose an attack
					if (--movesUntilVulnerable == 0) {
						//Perform arm throwing move
						state = State.THROW_ARMS;
						performingTell = true;
						count = 1.1f - rank;
						movesUntilVulnerable = 2;
						
					} else {
						randInt = MathUtils.random(0);
						if (randInt == 0) {
							//do fireball attack
							performingTell = true;
							state = State.FIREBALL;
							count = 1.1f - rank;
						}
					}
					break;
					
				case FIREBALL:
					if (performingTell) {
						performingTell =false;
						count = 0.2f;
						count2 = 0;
					} else {
						switch(count2) {
						case 0:
							/*Vector2 directionToShip = new Vector2(ship.position.x - position.x, ship.position.y - position.y);
							Vector2 direction0 = new Vector2(directionToShip);
							direction0.add(new Vector2())*/
							float xToShip = ship.position.x - position.y;
							float yToShip = ship.position.y - position.y;
							float slope = yToShip/xToShip;
							Vector2 dirToShip = new Vector2(xToShip, yToShip);
							Vector2 dirPerp0 = new Vector2(-xToShip, yToShip);
							//Vector2 dirPerp1 = new Vector2(xToShip, -yToShip);
							Vector2 dir0 = new Vector2(dirToShip).add(new Vector2(dirPerp0).nor().scl(2f));
							Vector2 dir1 = new Vector2(dirToShip).add(new Vector2(dirPerp0).nor().scl(-2f));
							//Shoot fireballs around player position but not at
							newEnemies.add(new BulletSimple(30f * rank, 0f, new Vector2(position.x + MOUTH_OFFSET_X, 
									position.y + MOUTH_OFFSET_Y), FIREBALL_WIDTH, FIREBALL_HEIGHT, dir0, BulletSimple.Graphic.FIRE));
							newEnemies.add(new BulletSimple(30f * rank, 0f, new Vector2(position.x + MOUTH_OFFSET_X, 
									position.y + MOUTH_OFFSET_Y), FIREBALL_WIDTH, FIREBALL_HEIGHT, dir1, BulletSimple.Graphic.FIRE));
							state= State.IDLE;
							break;
						}
						count2++;
					}
					break;
								
				case THROW_ARMS:
					if (performingTell) {
						performingTell = false;
						count = 0.5f;
						//choose whether to attack up or down
						boolean throwUp = MathUtils.randomBoolean();
						arms.beginAttack(throwUp);
					} else {
						count = ATTACK_RATE - rank * ATTACK_RANK_RATE;
						state = State.IDLE;
					}
				}
					
				
				
			}
			
			//Make the move
			if (state == State.IDLE) {
				float lerp = 0.8f;
				position.y -= delta * (position.y - (5f + 4 * MathUtils.cos(count))) * lerp;
				position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 1f)) * lerp;
			} else if (state == State.THROW_ARMS){
				if (performingTell) {
					float lerp = 0.7f;
					position.y -= delta * (position.y - 5f) * lerp;
					position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 0.5f)) * lerp;
				}
			} else if (state == State.FIREBALL) {
				if (performingTell) {
					float lerp = 0.9f;
					position.y -= delta * (position.y - 15f) * lerp;
					position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 0.5f)) * lerp;
				}
			}
		}
		Array<Enemy> armsNewEnemies = arms.advance(delta, ship, rank);
		newEnemies.addAll(armsNewEnemies);
		return newEnemies;
		
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}
	
	public EnemySpiderBossArms getArms() {
		return arms;
	}
	
}
