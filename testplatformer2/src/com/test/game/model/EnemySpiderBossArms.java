package com.test.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class EnemySpiderBossArms extends Enemy {
	
	private static final float BASE_SPEED = 4f;
	private static final float RANK_SPEED_MULT = 20f;
	private static final float WIDTH = 4f;
	private static final float HEIGHT = 4f;
	private static final float PARENT_OFFSET_X = 9f;
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
				if (position.x > parent.getPosition().x +29f) {
					
					moveRight = false;
					attackUp = !attackUp;
					//System.out.println("Arms: turn reached end. Changing dir. now attackUp = " + attackUp);
				}
			} else {
				position.x -= thisMoveDelta;
				if (position.x < parent.getPosition().x + PARENT_OFFSET_X + 4f) {
					//System.out.println("Arms: End in sight. Returning");
					isAttacking = false;
					isReturning = true;
				}
			}
			if (rank > 0.5f) {
				bulletCount -= delta;
				if (bulletCount <= 0f) {
					//System.out.println("Arms: Spawning bullet!");
					bulletCount = 0.5f - 0.49f* rank;
					BulletSimple bullet = new BulletSimple(10f + 2*rank, 0f, new Vector2(position.x+WIDTH/2,
							position.y+HEIGHT/2), parent.FIREBALL_WIDTH, parent.FIREBALL_HEIGHT, new Vector2(ship.position.x + ship.getWidth()/2-
							position.x, ship.position.y+ship.getHeight()/2-position.y),BulletSimple.Graphic.FIRE);
					parent.bullets.add(bullet);
					newEnemies.add(bullet);
				}
			}
			//System.out.println("Arms: y="+position.y);
		} else if (isReturning){
			position.x -= (position.x - parent.position.x) * delta;
			position.y -= (position.y - parent.position.y) * delta * 1.2;
			if (position.x <= parent.position.x + PARENT_OFFSET_X) {
				System.out.println("Arms: I'm back!");
				isReturning = false;
				isAttacking = false;
			}
		} else {
			position.x = parent.position.x + PARENT_OFFSET_X;
			position.y = parent.position.y + PARENT_OFFSET_Y;
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
		this.attackUp=attackUp;
	}
	
	public boolean isAttacking() {
		return isAttacking;
	}

	
}
