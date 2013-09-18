package com.test.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class EnemySpiderBossArms extends Enemy {
	
	private static final float BASE_SPEED = 4f;
	private static final float RANK_SPEED_MULT = 20f;
	private static final float WIDTH = 2f;
	private static final float HEIGHT = 2f;
	private static final float PARENT_OFFSET_X = 4f;
	private static final float PARENT_OFFSET_Y = 4f;
	
	private boolean isAttacking;
	private boolean attackUp;
	private boolean moveRight;
	private boolean isReturning; 
	private EnemySpiderBoss parent;
	
	private float bulletCount;
	

	public EnemySpiderBossArms(EnemySpiderBoss parent) {
		super(0, 0, new Vector2(parent.position.x + PARENT_OFFSET_X, 
				parent.position.y + PARENT_OFFSET_Y), WIDTH, HEIGHT);
		isAttacking = false;
		attackUp= true;
		isReturning = false;
		moveRight = true;
		this.parent = parent;
		
		bulletCount = 0f;
	}

	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {
		Array<Enemy> newEnemies = new Array<Enemy>();
		if (isAttacking) {
			float thisMoveDelta = delta * (BASE_SPEED + rank * RANK_SPEED_MULT);
		
			if (attackUp) {
				if (position.y < 4f) {
					position.y += thisMoveDelta;
				} 
			} else {
				if (position.y > 0.5f) {
					position.y -= thisMoveDelta;
				}
				
			}
			if (moveRight) {
				position.x += thisMoveDelta;
				if (position.x > parent.getPosition().x +14f) {
					moveRight = false;
					attackUp = !attackUp;
				}
			} else {
				position.x -= thisMoveDelta;
				if (position.x < parent.getPosition().x + PARENT_OFFSET_X + 2f) {
					isAttacking = false;
					isReturning = true;
				}
			}
		} else if (isReturning){
			position.x -= (position.x - parent.position.x) * delta;
			position.y -= (position.y - parent.position.y) * delta;
			if (position.x <= parent.position.x + PARENT_OFFSET_X) {
				isReturning = false;
			}
		} else {
			position.x = parent.position.x + PARENT_OFFSET_X;
			position.y = parent.position.y + PARENT_OFFSET_Y;
		}
		if (rank > 0.5f) {
			if (bulletCount <= 0f) {
				bulletCount = 1f - rank;
				newEnemies.add(new BulletSimple(2f + 1/rank, 0f, new Vector2(position.x+WIDTH/2,
						position.y+HEIGHT/2), parent.FIREBALL_WIDTH, parent.FIREBALL_HEIGHT, new Vector2(ship.position.x-
						position.x, ship.position.y-position.y),BulletSimple.Graphic.FIRE));
			}
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
		this.attackUp=attackUp;
	}

	
}
