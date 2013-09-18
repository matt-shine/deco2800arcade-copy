package com.test.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BulletSimple extends BulletEnemy {

	private int graphic;
	
	public BulletSimple(float speed, float rotation, Vector2 pos, float width,
			float height, Vector2 velocity, int graphic) {
		super(speed, rotation, pos, width, height, velocity);
		this.graphic = graphic;
	}

	@Override
	public void update(Ship ship) {
		position.add(velocity.scl(Gdx.graphics.getDeltaTime() * speed));
		velocity.scl(1/(Gdx.graphics.getDeltaTime()*speed));
		super.update(ship);
		
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleXCollision(Rectangle tile) {
		// remove the bullet
		position.x = -40;
		velocity = new Vector2(0,0);
		
	}

	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		//remove the bullet
		position.x = -40;
		velocity = new Vector2(0,0);
		
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}
	
	public int getGraphic() {
		return graphic;
	}

}
