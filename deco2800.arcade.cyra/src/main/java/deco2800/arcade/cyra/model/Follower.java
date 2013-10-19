package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Follower extends Enemy {
	
	//private float stateTime = 0;
	//private static final float ROTATION_SPEED = 500;
	public Follower() {
		super(0, 0, new Vector2 (-4, -4), 1, 1);
	}
	
	
	public Follower(float speed, float rotation, Vector2 pos, float width,
			float height) {
		super(speed, rotation, pos, width, height);
		// TODO Auto-generated constructor stub
	}

	/*
	@Override 
	public void update(Ship ship) {
		position.lerp(ship.getPosition(), Gdx.graphics.getDeltaTime());
		//rotation += Gdx.graphics.getDeltaTime() * ROTATION_SPEED;
		
		super.update(ship);
		stateTime += Gdx.graphics.getDeltaTime();
		
		
	}*/
	
	/*public float getStateTime() {
		return stateTime;
	}*/

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam) {
		//position.lerp(ship.getPosition(), delta);
		Vector2 dir = new Vector2(new Vector2(ship.getPosition().x,ship.getPosition().y).sub(
				new Vector2(position.x, position.y)));
		dir.nor().mul(delta * 2);
		position.add(dir);
		//rotation += Gdx.graphics.getDeltaTime() * ROTATION_SPEED;
		
		super.update(ship);
		stateTime += delta;
		return null;
	}


	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void handleXCollision(Rectangle tile) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}

}
