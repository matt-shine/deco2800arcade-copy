package com.test.game.world;

import com.test.game.model.EnemySpawner;
import com.test.game.model.RandomizedEnemySpawner;
import com.test.game.model.MovablePlatformSpawner;
import com.test.game.model.Follower;
import com.test.game.model.MovablePlatform;
import com.test.game.model.SoldierEnemy;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Level {
	private int levelNum;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	private TiledMapTileLayer collisionLayer;
	
	private Array<EnemySpawner> enemySpawners;
	private Array<RandomizedEnemySpawner> randomEnemySpawners;
	private Array<MovablePlatformSpawner> movablePlatformSpawners;
	
	public Level(int levelNum) {
		this.levelNum = levelNum;
		init();
	}
	
	private void init() {
		enemySpawners = new Array<EnemySpawner>();
		randomEnemySpawners = new Array<RandomizedEnemySpawner>();
		movablePlatformSpawners = new Array<MovablePlatformSpawner>();
		
		map = new TmxMapLoader().load("data/level"+"2_new"+".tmx");
		collisionLayer = (TiledMapTileLayer) ( map.getLayers().get("Collision") );

		renderer = new OrthogonalTiledMapRenderer(map, 1/32f);
		
		//Load objects. Need to standardize this. Talk to Robert.
		Array<Object> objects = new Array<Object>();

		if(levelNum == 1) {
			for( MapObject object : map.getLayers().get("spawners").getObjects() ) {
				if(object instanceof RectangleMapObject) {
					Rectangle rect = ((RectangleMapObject)object).getRectangle();
					Class<Follower> c = Follower.class;
					EnemySpawner e = new EnemySpawner(c, new Vector2(rect.x/32f, rect.y/32f), 4, 100, 1); //the 32f comes from the LevelLayout
					objects.add(e);
				}
			}

		} else {
			// Add the log parts for the waterfall
			Texture logTex = new Texture("data/log.png");
			logTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			MovablePlatform log =  new MovablePlatform(logTex, new Vector2(-1, -1), 2, 4, 2.5f);
			MovablePlatformSpawner logS0 = new MovablePlatformSpawner(log, new Vector2(200, 59), new Vector2(200, -9), 0f, 3.5f, 0, 10);
			MovablePlatformSpawner logS1 = new MovablePlatformSpawner(log, new Vector2(210, 59), new Vector2(210, -9), 1.75f, 3.5f, 0, 10);
			
			//Add popcorn enemy spawners
			Class<SoldierEnemy> c = SoldierEnemy.class;
			
			Array<EnemySpawner> spawners = new Array<EnemySpawner>();
			spawners.add(new EnemySpawner(c, new Vector2(0f, 3f), 4, 100, 2)); 
			spawners.add(new EnemySpawner(c, new Vector2(0f, 8f), 4, 100, 2)); 
			spawners.add(new EnemySpawner(c, new Vector2(0f, 3f), 4, 100, 2)); 
			spawners.add(new EnemySpawner(c, new Vector2(0f, 8f), 4, 100, 2)); 
			boolean[] sides = {true, true, false, false};
			
			RandomizedEnemySpawner res = new RandomizedEnemySpawner(spawners, sides, 1.5f, 0f, 200f);
			res.setActive(true);
			
			objects.add(logS0);
			objects.add(logS1);
			objects.add(res);
		}
		
		//Filter objects
		for (Object o: objects) {
			if (o instanceof EnemySpawner) {
				enemySpawners.add((EnemySpawner) o);
			} else if (o instanceof MovablePlatformSpawner) {
				movablePlatformSpawners.add((MovablePlatformSpawner) o);
			} else if (o instanceof RandomizedEnemySpawner) {
				randomEnemySpawners.add((RandomizedEnemySpawner) o);
			}
		}
		System.out.println("Found " + enemySpawners.size + " enemy spawners");
		System.out.println("Found " + movablePlatformSpawners.size + " platform spawners");
		System.out.println("Found " + randomEnemySpawners.size + " randomizd enemy spawners");
	}
	
	public void reloadLevel() {
		renderer = null;
		map = null;
		collisionLayer = null;
		
		enemySpawners = null;
		randomEnemySpawners = null;
		movablePlatformSpawners = null;
		
		init();
	}
	
	/* Getter methods */
	public Array<EnemySpawner> getEnemySpawners() {
		return enemySpawners;
	}
	
	public Array<RandomizedEnemySpawner> getRandomEnemySpawners() {
		return randomEnemySpawners;
	}
	
	public Array<MovablePlatformSpawner> getMovablePlatformSpawners() {
		return movablePlatformSpawners;
	} 
	
	public TiledMap getMap() {
		return map;
	}
	
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}
	
	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}
	
	private void loadTextures() {
		
		return;
	}
	
	
}
