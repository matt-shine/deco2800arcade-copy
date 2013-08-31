package com.test.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.test.game.model.Ship.State;

public abstract class Enemy extends MovableEntity{
	
	protected float stateTime = 0;
	
	public Enemy(float speed, float rotation, Vector2 pos, float width, float height) {
		super(speed, rotation, pos, width, height);
	}
	
	
	public float getStateTime() {
		return stateTime;
	}
	
	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		System.out.println("enemy had top of moving platform collision");
		getPosition().y = movablePlatform.getPosition().y + 
				movablePlatform.getCollisionRectangle().height+1/32f;
		
	}
	
	@Override
	public void handleXCollision(Rectangle tile) {
		//System.out.println("enemy had x collision");
		if (velocity.x > 0.01f) {
			position.x = tile.getX() - getWidth();
				
		} else if (velocity.x < -0.01f){
			position.x = tile.getX() + tile.getWidth();
		}
		
		
		
	}
	
	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		//System.out.println("enemy had y collision");
		if (velocity.y < 0 ) {
			if (onMovablePlatform) {
				velocity.y = -movablePlatform.getSpeed();
			}
			if (!onMovablePlatform) {
				position.y = tile.y + tile.height;
				velocity.y = 0;
			}
				
			
		} else if (velocity.y > 0) {
			if (onMovablePlatform) {
				velocity.y = -movablePlatform.getSpeed();
			} else {
				velocity.y = 0;
			}
			position.y = tile.y - getHeight();
		}
		
	}
	
	public abstract void advance(float delta, Ship ship, float rank);

}
