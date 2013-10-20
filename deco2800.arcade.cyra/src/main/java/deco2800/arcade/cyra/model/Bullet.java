package deco2800.arcade.cyra.model;

import java.lang.Math;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends MovableEntity {

	private static float BULLET_SPEED = 10f;
	private static float BULLET_SIZE = 1f;
	private static float MAX_EXIST_TIME = 5f;

	private float existTime = 0;
	private boolean verticalInverse;
	
	public Bullet(float speed, float rotation, Vector2 pos, float width,
			float height, Vector2 velocity, boolean verticalInverse) {
		super(speed, rotation, pos, width, height);
		this.velocity = velocity;
		this.verticalInverse = verticalInverse;
	}
	
	@Override
	public void update(Player ship) {
		if(verticalInverse) {
			velocity.y = -1 * (float)Math.cos(2*position.x);
		} else {
			velocity.y = (float)Math.cos(2*position.x);
		}
		//position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime() * speed));
		position.add(velocity.mul(Gdx.graphics.getDeltaTime() * speed));
		velocity.mul(1/(Gdx.graphics.getDeltaTime()*speed));
		existTime += Gdx.graphics.getDeltaTime();
		super.update(ship);
	}
	
	public float getExistTime() {
		return existTime;
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

	public static float getBULLET_SPEED() {
		return BULLET_SPEED;
	}

	public static void setBULLET_SPEED(float bULLET_SPEED) {
		BULLET_SPEED = bULLET_SPEED;
	}

	public static float getBULLET_SIZE() {
		return BULLET_SIZE;
	}

	public static void setBULLET_SIZE(float bULLET_SIZE) {
		BULLET_SIZE = bULLET_SIZE;
	}

	public static float getMAX_EXIST_TIME() {
		return MAX_EXIST_TIME;
	}

	public static void setMAX_EXIST_TIME(float mAX_EXIST_TIME) {
		MAX_EXIST_TIME = mAX_EXIST_TIME;
	}

}
