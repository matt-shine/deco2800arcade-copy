package deco2800.arcade.cyra.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.cyra.world.Sounds;

public class WallBoss extends Enemy{

	public static final float WIDTH = 4f;
	public static final float HEIGHT = 8f;
	public static final int INITIAL_HEALTH = 15;
	public static final float INVINCIBLE_TIME = 0.3f;
	
	private int health;
	private float count;
	private boolean dying;
	
	public WallBoss(Vector2 pos) {
		super(0, 0, pos, WIDTH, HEIGHT);
		health = INITIAL_HEALTH;
		count = 0;
		healthName = "Wall Of Doom";
		dying= false;
	}

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank,
			OrthographicCamera cam) {
		
		count += delta;
		Array<Enemy> newEnemies = new Array<Enemy>();
		if (count > 7f-6.7*rank) {
			newEnemies.add(new SoldierEnemy(new Vector2(cam.position.x-cam.viewportWidth/2-1.5f, cam.position.y), false));
			count =0;
		}
		if (invincibleTime >0) {
			invincibleTime-=delta;
		}
		
		if (dying) {
			float eSpeed = 5f;
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(0f,1f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(0f,-1f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,0f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,-1f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,1f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,0f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,-1f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,1f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,0.5f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(1f,-0.5f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,0.5f).nor().mul(eSpeed)));
			newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2), new Vector2(-1f,-0.5f).nor().mul(eSpeed)));
			Sounds.playExplosionLong(0.5f);
			isDead = true;
			startingNextScene = true;
		}
		return newEnemies;
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
		if (invincibleTime <=0) {
			Sounds.playHurtSound(0.5f);
			health--;
			if (health==0) {
				dying = true;
				
			}
			invincibleTime = INVINCIBLE_TIME;
		}
	}

}
