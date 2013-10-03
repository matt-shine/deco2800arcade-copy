package deco2800.cyra.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BossMelee extends Enemy {
	

	public enum State {
		FOLLOWING, PUNCH
	}
	
	public final static float ELEVATION_1 = 5f;
	public final static float ELEVATION_2 = 10f;
	public final static float ELEVATION_3 = 15f;
	public final static float WIDTH = 2f;
	public final static float HEIGHT = 4f;
	public final static float SPEED = 4f;
	public final static float SPEED_RANK_MOD = 4f;
	public final static float PUNCH_RANGE = 4f;
	
	private float count;
	private State state;
	private boolean changingLevel;
	private float punchTargetPosition;
	private boolean facingRight;
	
	public BossMelee(Vector2 pos) {
		super(0, 0, pos, WIDTH, HEIGHT);
		count = 0.2f;
		state = State.FOLLOWING;
		changingLevel = false;
	}
	
	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {
		count -= delta;
		if (count == 0) {
			//make new decision
		}
		
		if (state == State.FOLLOWING) {
			//get ship's elevation to determine how to get to position
			int shipElevationLevel;
			int bossElevationLevel;	
			if (ship.getPosition().y < ELEVATION_1) {
				shipElevationLevel = 0;
			} else if (ship.getPosition().y < ELEVATION_2) {
				shipElevationLevel = 1;
			} else if (ship.getPosition().y < ELEVATION_3) {
				shipElevationLevel = 2;
			} else {
				// above all ground floors - ie. ship is on a wall
				shipElevationLevel = 3;
			}
			
			//determine Boss' elevation level
			
			if (ship.getPosition().y < ELEVATION_1) {
				bossElevationLevel = 0;
			} else if (ship.getPosition().y < ELEVATION_2) {
				bossElevationLevel = 1;
			} else if (ship.getPosition().y < ELEVATION_3) {
				bossElevationLevel = 2;
			} else {
				// above all ground floors - ie. boss is on a wall
				bossElevationLevel = 3;
			}
			
			if (changingLevel == true) {
				if (shipElevationLevel == bossElevationLevel) {
					velocity.y = 0;
					changingLevel=false;
				} else if (shipElevationLevel > bossElevationLevel) {
					//go up
					velocity = new Vector2(0, 10f);
				} else {
					velocity = new Vector2(0, -10f);
				}
				
			} else {
				
				
				if (bossElevationLevel == shipElevationLevel) {
					//move towards ship
					velocity = new Vector2(ship.getPosition().x - position.x, velocity.y).nor().mul(SPEED * rank * SPEED_RANK_MOD);
					
					//check if ship is close and do punch
					if (Math.abs(ship.getPosition().x - position.x) < PUNCH_RANGE) {
						state = State.PUNCH;
						float direction = (ship.getPosition().x - position.x);
						if (direction > 0 ) {
							punchTargetPosition = position.x + width+ PUNCH_RANGE + rank * 10f;
						} else {
							punchTargetPosition = position.x - PUNCH_RANGE - rank * 10f;
						}
					}
				} else {
					Boolean upward;
					if (bossElevationLevel > shipElevationLevel) {
						upward = true;
					} else {
						upward = false;
					}
					//move towards the closest location to change levels
					Array<Vector2> thisLevelChangePositions = findElevationChangePoints(bossElevationLevel, upward); //true is to say going up elevations
					Vector2 targetPos;
					if (thisLevelChangePositions.get(0).x - position.x > thisLevelChangePositions.get(1).x - position.x) {
						targetPos = thisLevelChangePositions.get(1);
					} else {
						targetPos = thisLevelChangePositions.get(0);
					}
					velocity = new Vector2(targetPos.x - position.x, velocity.y).nor().mul(SPEED + rank * SPEED_RANK_MOD);
					if (position.x > targetPos.x - 0.1f && position.x < targetPos.x + 0.1f) {
						changingLevel=true;
						// going to make this a phoenix-like charge up/down instead of the logical way
						velocity.x=0;
						
					}
				}
			}
		} else if (state == State.PUNCH) {
			position.x -= (position.x - punchTargetPosition) * delta * 0.8f;
		}
		
		return null;
	}
	
	@Override
	public Array<Rectangle> getPlayerDamageBounds() {
		Rectangle rect = null;
		if (state == State.PUNCH) {
			if (facingRight) {
				rect = new Rectangle(position.x+width-3f, position.y, 6f, height);
			} else {
				rect = new Rectangle(position.x-3f, position.y, 6f, height);
			}
		}
			
		
		Array<Rectangle> rects = new Array<Rectangle>();
		if (rect != null) {
			rects.add(rect);
		}
		return rects;
			
	}
	
	private Array<Vector2> findElevationChangePoints(int elevationLevel, boolean upward) {
		return new Array<Vector2>();
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public void handleNoTileUnderneath() {
		
		
	}
}
		