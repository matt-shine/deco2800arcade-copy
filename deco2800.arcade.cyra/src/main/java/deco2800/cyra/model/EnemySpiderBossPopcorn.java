package deco2800.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import deco2800.cyra.world.Sounds;

public class EnemySpiderBossPopcorn extends Enemy {

	public static final float WIDTH = 2f;
	public static final float HEIGHT = 2f;
	private static final float RISE_RATE = 4f;
	
	private boolean isProjectile;
	private boolean releasedMissiles;
	private float count;
	
	public EnemySpiderBossPopcorn(Vector2 pos, float velocityX) {
		super(0, 0, pos, WIDTH, HEIGHT);
		velocity = new Vector2(velocityX, RISE_RATE);
		isProjectile = false;
		releasedMissiles = false;
		count = 0f;
		
		
	}

	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {
		Array<Enemy> newEnemies = new Array<Enemy>();
		if (isProjectile && position.y <= 0) {
			velocity.y = -velocity.y;
		}
		
		if (!releasedMissiles && position.y > 8.5f && !isProjectile) {
			//release Missiles
			float xToShip = (ship.position.x +ship.getWidth()/2)- (position.x+width/2);
			float yToShip = (ship.position.y + ship.getHeight()/2) - (position.y+height/2);
			Vector2 dirAwayShip = new Vector2(-xToShip, -yToShip);
			Vector2 dirPerp0 = new Vector2(-yToShip, xToShip);
			Array<Vector2> spawnDirections = new Array<Vector2>();
			spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(0f)));
			if (rank > 0.3f) {
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(1f)));
			}
			if (rank > 0.5f) {
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(-1f)));
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(5f)));
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(-5f)));
			}
			if (rank > 0.8f) {
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(2.5f)));
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(-2.5f)));
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(10f)));
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(-10f)));
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(15f)));
				spawnDirections.add((new Vector2(dirAwayShip)).add((new Vector2(dirPerp0)).nor().mul(-15f)));
			}
			for (Vector2 v: spawnDirections) {
				BulletHomingDestructible bullet = new BulletHomingDestructible(5f + 10f * rank, 0f, new Vector2(position.x + width/2, 
						position.y + height/2), 1f, 1f, v, BulletSimple.Graphic.FIRE);
				newEnemies.add(bullet);
			}
			releasedMissiles = true;
		}
		
		if (isProjectile) {
			count += delta;
			if (count > 0.2f) {
				newEnemies.add(new Explosion(new Vector2(position.x+width/2, position.y+height/2)));
				count = 0f;
				Sounds.playExplosionShort(0.5f);
			}
		}
		
		//do the move
		position.add(velocity.mul(delta));
		velocity.mul(1/delta);
		
		return newEnemies;
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void handleXCollision(Rectangle tile) {
		//bounce with current velocity
		velocity.x = velocity.x * (-1);
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleDamage(boolean fromRight) {
		if (!isProjectile) {
			isProjectile = true;
			velocity.y = -RISE_RATE * 4;
			if (fromRight) {
				velocity.x = -24f;
			} else {
				velocity.x = 24f;
			}
			
		}
	}
	
	public boolean isProjectile() {
		return isProjectile;
	}
	
	@Override
	public Array<Rectangle> getPlayerDamageBounds() {
		Array<Rectangle> playerDamageRectangle = new Array<Rectangle>();
		return playerDamageRectangle;
	}

}
