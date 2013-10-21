package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class EnemySpawner {
	private Class<? extends Enemy> objClass;
	private Vector2 pos;
	private int rate;
	private int maxSpawn;
	private int maxSpawnAtOnce;
	
	private float count;
	private Array<Enemy> enemies;
	private int spawnCount;
	
	/**
	 * An object that creates other Enemies at set intervals
	 * @param objClass the class of Enemy to spawn. Must have a constructor with no parameters
	 * @param pos Position to spawn Enemies at
	 * @param rate how fast from one spawn time to the next
	 * @param maxSpawn maximum Enemies to spawn over EnemySpawner lifespan
	 * @param maxSpawnAtOnce maximum Enemies to spawn until some are cleared out
	 */
	public EnemySpawner(Class<? extends Enemy> objClass, Vector2 pos, int rate, int maxSpawn, int maxSpawnAtOnce) {
		this.objClass = objClass;
		this.pos = pos;
		this.rate = rate;
		this.maxSpawn = maxSpawn;
		this.maxSpawnAtOnce = maxSpawnAtOnce;
		count = rate;
		enemies = new Array<Enemy>();
		spawnCount = 0;
	}
	
	/**
	 * The update method to count down from the rate to next enemy spawn
	 * @return is an enemy ready to spawn
	 */
	public boolean increment() {
		count += Gdx.graphics.getDeltaTime();
		if (count > rate && enemies.size < maxSpawnAtOnce && spawnCount < maxSpawn) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * checks if it can spawn within the constructors constraints of maxSpawn and maxSpawnAtOnce
	 * @return the new Enemy to spawn
	 */
	public Enemy spawnNewIfPossible() {
		if (enemies.size < maxSpawnAtOnce && spawnCount < maxSpawn) {
			return spawnNew();
		} else {
			return null;
		}
	}
	
	/**
	 * Spawn the Enemy of the constructor's class
	 * @return Enemy to spawn
	 */
	public Enemy spawnNew() {
		try {
			Enemy e = (Enemy) objClass.newInstance();
			e.setPosition(new Vector2(pos.x, pos.y));
			enemies.add(e);
			spawnCount++;
			count = 0;
			return e;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Remove enemy from spawners list, freeing up another for the MaxSpawnAtOnce limit
	 * @param e the enemy to remove
	 */
	public void removeEnemy(Enemy e) {
		
		for (int i=0; i< enemies.size; i++) {
			if (enemies.get(i) == e) {
				enemies.removeIndex(i);
			}
		}
	}
	
	
	/**
	 * 
	 * @return class of Enemy to spawn
	 */
	public Class<? extends Enemy> getObjectClass() {
		return objClass;
	}
	
	/**
	 * 
	 * @return position that enemies will be spawned at
	 */
	public Vector2 getPosition() {
		return pos;
	}
	
	
	/**
	 * Set the position to spawn enemies
	 * @param pos Vector2 position
	 */
	public void setPosition(Vector2 pos) {
		this.pos = pos;
	}
}
