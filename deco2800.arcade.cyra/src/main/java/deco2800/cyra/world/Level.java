package deco2800.cyra.world;

import deco2800.cyra.model.EnemySpawner;
import deco2800.cyra.model.RandomizedEnemySpawner;
import deco2800.cyra.model.MovablePlatformSpawner;
import deco2800.cyra.model.Follower;
import deco2800.cyra.model.MovablePlatform;
import deco2800.cyra.model.SoldierEnemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
//import com.badlogic.gdx.maps.MapObject;
//import com.badlogic.gdx.maps.objects.RectangleMapObject;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
//import com.badlogic.gdx.maps.tiled.TmxMapLoader;
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Level {
	private int levelNum;
	private com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer renderer;
	//private TiledMap map;
	//private TiledMapTileLayer collisionLayer;
	private TiledLayer collisionLayer;
	private com.badlogic.gdx.graphics.g2d.tiled.TiledMap map;
	private TileAtlas atlas;
	
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
		
		map = TiledLoader.createMap(Gdx.files.internal("data/tiles/level"+"2_new"+".tmx"));
		//map = TiledLoader.createMap(Gdx.files.internal("data/level"+levelNum+".tmx"));
		//map = TiledLoader.createMap(Gdx.files.internal("data/levelOld.tmx"));
		
		collisionLayer = (TiledLayer) ( map.layers.get(3) );
		

		atlas = new TileAtlas(map, Gdx.files.internal("data/tiles/"));
		//atlas = new TileAtlas(map, Gdx.files.internal("data/tiles/"));
		//renderer = new com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer(map, 1/32f);
		
		renderer = new com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer(map, atlas, 16,16, 1, 1);
		
		//Debugging downgrade stuff
		for (int x =0; x<map.layers.size(); x++) {
			//for (int i =0; i<map.layers.get(x).getWidth(); i++) {
			for (int i =0; i<250; i++) {
				for (int j = 0; j< 5; j++) {
					//System.out.println("Lyar= "+x+ " ("+i+","+(map.height-j-1)+") = "+map.layers.get(x).tiles[j][i]);
				}
			}
		}
		
		
		
		//Load objects. Need to standardize this. Talk to Robert.
		Array<Object> objects = new Array<Object>();
		
		//The following code will need to be worked out. Was removed in downgrade to Libgdx 0.9.7
		/*
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
		*/
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
			spawners.add(new EnemySpawner(c, new Vector2(0f, 8f), 4, 100, 2)); 
			boolean[] sides = {true, true, true, false};
			
			RandomizedEnemySpawner res = new RandomizedEnemySpawner(spawners, sides, collisionLayer, 1.5f, 0f, 200f);
			res.setActive(true);
			
			objects.add(logS0);
			objects.add(logS1);
			objects.add(res);
		//}
		
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
	
	public com.badlogic.gdx.graphics.g2d.tiled.TiledMap getMap() {
		return map;
	}
	
	public TiledLayer getCollisionLayer() {
		return collisionLayer;
	}
	
	public com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer getRenderer() {
		return renderer;
	}
	
	private void loadTextures() {
		
		return;
	}
	
	
}
