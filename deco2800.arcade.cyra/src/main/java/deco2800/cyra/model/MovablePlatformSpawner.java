package deco2800.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MovablePlatformSpawner {
	private MovablePlatform platform;
	private Vector2 pos;
	private float rate;
	private float sync;
	private boolean hasSynced;
	private int maxSpawn;
	private int maxSpawnAtOnce;
	private Vector2 targetPos;
	
	private float count;
	private Array<MovablePlatform> platforms;
	//private int enemyCount;
	private int spawnCount;
	
	public MovablePlatformSpawner(MovablePlatform platform, Vector2 pos, Vector2 targetPos, float sync, float rate, int maxSpawn, int maxSpawnAtOnce) {
		this.platform=platform;
		this.pos = pos;
		this.rate=rate;
		this.sync = sync;
		this.maxSpawn=maxSpawn;
		this.maxSpawnAtOnce = maxSpawnAtOnce;
		hasSynced = false;
		count = 0;
		platforms = new Array<MovablePlatform>();
		//enemyCount = 0;
		spawnCount = 0;
		this.targetPos = targetPos;
	}
	
	public boolean increment() {
		count += Gdx.graphics.getDeltaTime();
		if (hasSynced) {
			
			//System.out.println("c" + count + " r" + rate+" es"+platforms.size+" msao"+maxSpawnAtOnce+ " sc"+spawnCount+ " ms"+maxSpawn);
			if (count > rate && platforms.size < maxSpawnAtOnce && (spawnCount < maxSpawn || maxSpawn == 0)) {
				return true;
			} else {
				return false;
			}
		} else {
			//System.out.println("Not synced "+count+" "+sync);
			if (count > sync) {
				//System.out.println("synced "+count+" "+sync);
				count = 0;
				hasSynced = true;
			}
			return false;
		}
	}
	
	public MovablePlatform spawnNew() {
		
		MovablePlatform p = new MovablePlatform(platform);
		p.setPosition(new Vector2(pos.x, pos.y));
		p.setTargetPosition(targetPos);
		platforms.add(p);
		//enemyCount++;
		spawnCount++;
		count = 0;
		System.out.println("Spawned platform at " + pos.x+","+pos.y);
		return p;
		
	}
	
	public void removePlatform(MovablePlatform p) {
		// this might break if two platforms get removed at the exact same time
		//System.out.println("Ready to platform to remove");
		for (int i=0; i< platforms.size; i++) {
			if (platforms.get(i) == p) {
				//System.out.println("Found platform to remove");
				platforms.removeIndex(i);
			}
		}
	}
	
	
	
	/*public Class<? extends Enemy> getObjectClass() {
		return objClass;
	}*/
	
	public Vector2 getPosition() {
		return pos;
	}
}
