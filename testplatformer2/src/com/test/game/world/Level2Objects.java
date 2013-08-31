package com.test.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.model.EnemySpawner;
import com.test.game.model.Follower;
import com.test.game.model.MovablePlatform;
import com.test.game.model.MovablePlatformSpawner;
import com.test.game.model.RandomizedEnemySpawner;
import com.test.game.model.SoldierEnemy;

public final class Level2Objects {

	public static Array<Object> loadObjects(TiledMap map) {
		Array<Object> objects = new Array<Object>();
		
		// Add the log parts for the waterfall
		Texture logTex = new Texture("data/log.png");
		logTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		MovablePlatform log =  new MovablePlatform(logTex, new Vector2(-1, -1), 2, 4, 2.5f);
		MovablePlatformSpawner logS0 = new MovablePlatformSpawner(log, new Vector2(200, 59), new Vector2(200, -9), 0f, 3.5f, 0, 10);
		MovablePlatformSpawner logS1 = new MovablePlatformSpawner(log, new Vector2(210, 59), new Vector2(210, -9), 1.75f, 3.5f, 0, 10);
		
		//Add popcorn enemy spawners
		Class<SoldierEnemy> c = SoldierEnemy.class;
		Array<EnemySpawner> spawners = new Array<EnemySpawner>();
		spawners.add(new EnemySpawner(c, new Vector2(0f, 3f), 4, 100, 1)); 
		spawners.add(new EnemySpawner(c, new Vector2(0f, 8f), 4, 100, 1)); 
		spawners.add(new EnemySpawner(c, new Vector2(0f, 3f), 4, 100, 1)); 
		spawners.add(new EnemySpawner(c, new Vector2(0f, 8f), 4, 100, 1)); 
		boolean[] sides = {true, true, false, false};
		
		RandomizedEnemySpawner res = new RandomizedEnemySpawner(spawners, sides, 4f, 0f, 200f);
		res.setActive(true);
		
		objects.add(logS0);
		objects.add(logS1);
		objects.add(res);
		
		return objects;
	}

}
