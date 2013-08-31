package com.test.game.model;

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
	//private int enemyCount;
	private int spawnCount;
	
	public EnemySpawner(Class<? extends Enemy> objClass, Vector2 pos, int rate, int maxSpawn, int maxSpawnAtOnce) {
		this.objClass=objClass;
		this.pos = pos;
		this.rate=rate;
		this.maxSpawn=maxSpawn;
		this.maxSpawnAtOnce = maxSpawnAtOnce;
		count = rate;
		enemies = new Array<Enemy>();
		//enemyCount = 0;
		spawnCount = 0;
	}
	
	public boolean increment() {
		count += Gdx.graphics.getDeltaTime();
		//System.out.println("c" + count + " r" + rate+" es"+enemies.size+" msao"+maxSpawnAtOnce+ " sc"+spawnCount+ " ms"+maxSpawn);
		if (count > rate && enemies.size < maxSpawnAtOnce && spawnCount < maxSpawn) {
			return true;
		} else {
			return false;
		}
	}
	
	public Enemy spawnNewIfPossible() {
		if (enemies.size < maxSpawnAtOnce && spawnCount < maxSpawn) {
			return spawnNew();
		} else {
			return null;
		}
	}
	
	public Enemy spawnNew() {
		try {
			Enemy e = (Enemy) objClass.newInstance();
			e.setPosition(new Vector2(pos.x, pos.y));
			enemies.add(e);
			//enemyCount++;
			spawnCount++;
			count = 0;
			System.out.println("Spawned enemy at " + pos.x+","+pos.y);
			return e;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void removeEnemy(Enemy e) {
		
		for (int i=0; i< enemies.size; i++) {
			if (enemies.get(i) == e) {
				enemies.removeIndex(i);
			}
		}
	}
	
	
	
	public Class<? extends Enemy> getObjectClass() {
		return objClass;
	}
	
	public Vector2 getPosition() {
		return pos;
	}
	
	public void setPosition(Vector2 pos) {
		this.pos = pos;
	}
}
