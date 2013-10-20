package deco2800.arcade.cyra.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class WallBoss extends Enemy{

	public static final float WIDTH = 4f;
	public static final float HEIGHT = 8f;
	public static final int INITIAL_HEALTH = 15;
	public static final float INVINSIBLE_TIME = 0.3f;
	
	private int health;
	private float count;
	
	public WallBoss(Vector2 pos) {
		super(0, 0, pos, WIDTH, HEIGHT);
		health = INITIAL_HEALTH;
		count = 0;
		healthName = "Wall Of Doom";
	}

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank,
			OrthographicCamera cam) {
		count += delta;
		Array<Enemy> newEnemies = new Array<Enemy>();
		if (count > 3f-2.9*rank) {
			newEnemies.add(new SoldierEnemy(new Vector2(cam.position.x-cam.viewportWidth-1.5f, cam.position.y), false));
		}
		return null;
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean displayHealth() {
		return true;
	}
	
	@Override
	public float getHealthPercentage() {
		return ((float)health)/INITIAL_HEALTH;
	}
	
	@Override
	public void handleDamage(boolean fromRight) {
		health--;
		if (health==0) {
			isDead = true;
			startingNextScene = true;
		}
	}

}
