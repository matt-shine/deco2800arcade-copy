package com.test.game.world;



import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.TestGame2;
import com.test.game.model.Bullet;
import com.test.game.model.CutsceneObject;
import com.test.game.model.Enemy;
import com.test.game.model.EnemySpawner;
import com.test.game.model.Follower;
import com.test.game.model.MovableEntity;
import com.test.game.model.MovablePlatform;
import com.test.game.model.MovablePlatformSpawner;
import com.test.game.model.Ship;
import com.test.game.model.Ship.State;
import com.test.game.model.Sword;
import com.test.game.model.Walker;

/** World class controls all objects in the specified level including any collisions
 * and links Object references where needed
 * @author Game Over
 *
 */
public class World {
	public static final float WORLD_WIDTH = 220f;
	public static final float WORLD_HEIGHT = 59f;
	
	
	private Boolean firstUpdate;
	private Ship ship;
	Rectangle sRec;
	private Sword sword;
	private Array<Enemy> enemies;
	private Array<Bullet> bullets;
	private Array<EnemySpawner> spawners;
	private Array<MovablePlatformSpawner> movablePlatformSpawners;
	private Array<CutsceneObject> cutsceneObjects; 
	private Array<MovablePlatform> movablePlatforms;
	private OrthographicCamera cam;
	
	private int curLevel;
	private LevelLayout levelLayout;
	private LevelScenes levelScenes;
	private InputHandler inputHandler;
	
	//He says this creates circular logic and hence is very bad. It's only really to get touchDown to access camera
	// if not using mouse then remove this
	//WorldRenderer wr;
	
	public World(TestGame2 game, int level, OrthographicCamera cam) {
		curLevel = level;
		this.cam = cam;
		
		init();
		loadLevel(curLevel);
		
		
		
	}
	
	
	
	public void update() {
		//System.out.println("Delta = "+Gdx.graphics.getDeltaTime());
		//System.out.println("State before ship = "+ship.getState());
		ship.update(ship);		
		//if (sword.inProgress()) sword.update(ship);
		sword.update(ship);
		//System.out.println("State after ship = "+ship.getState());
		spawnEnemies();
		spawnMovablePlatforms();

		for (int i = -1; i < enemies.size + bullets.size; i++) {
			MovableEntity mve;
			if (i == -1) {
				mve = ship;
			} else if (i > -1 && i < enemies.size){
				mve = enemies.get(i);
			} else {
				mve = bullets.get(i - enemies.size);
			}
			if (mve.isSolid()) {
				checkTileCollision(mve);
			}
		}
		
		//System.out.println("State after tiles = "+ship.getState());
		checkDamage();

		handleEnemies();

		if (firstUpdate) {
			resetCamera();
			firstUpdate = false;
		} else {
			updateCamera();
		}
		
		//System.out.println("End of World update " + ship.getVelocity().x);

		// Check if sprite has gone out of level bounds to the bottom.
		if( (int)ship.getPosition().y < -1 ) {
			resetLevel(curLevel);
		}
		// Reset if health = 0
		if( ship.getHearts() == 0 ) {
			resetLevel(curLevel);
		}

		if (levelScenes.isPlaying()) {
			levelScenes.update(Gdx.graphics.getDeltaTime());
		} else {
			float[] scenePos = levelScenes.getStartValues();
			if (ship.getPosition().x > scenePos[0]) {
				inputHandler.cancelInput();
				ship.getVelocity().x = 0;
				Array<MovableEntity> temp = levelScenes.start();
				for (MovableEntity ment: temp) {
					if (ment.getClass() == CutsceneObject.class) {
						cutsceneObjects.add( (CutsceneObject) ment );
					} else if (ment.getClass() == MovablePlatform.class) {
						movablePlatforms.add( (MovablePlatform) ment );
					}
				}
			}
		}
		return;
	}
	
	public void addBullet(Bullet b) {
		bullets.add(b);
	}

	private void loadLevel(int level) {
		levelLayout = new LevelLayout(level);

		//Load level objects
		Array<Object> objects = new Array<Object>();
		if (level == 1) {
			objects = Level1Objects.loadObjects(levelLayout.getMap());
			levelScenes = new Level1Scenes(ship);
			
		} else {
			objects = Level2Objects.loadObjects(levelLayout.getMap());
			levelScenes = new Level2Scenes(ship);
		}
		
		//Load objects to World
		for (Object o: objects) {
			if (o instanceof EnemySpawner) {
				spawners.add((EnemySpawner) o);
			} else if (o instanceof MovablePlatformSpawner) {
				movablePlatformSpawners.add((MovablePlatformSpawner) o);
			}
		}
		//System.out.println("Found " + spawners.size + " enemy spawners");
		//System.out.println("Found " + movablePlatformSpawners.size + " platform spawners");
		enemies.add( new Walker(new Vector2 (10f, 9f)) );

		Texture copterTex = new Texture("data/copter.png");
		copterTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		movablePlatforms.add(new MovablePlatform(copterTex, new Vector2(0, 1), 4f, 2f, new Vector2(0,11), 5f, true, 1.5f));
		movablePlatforms.add(new MovablePlatform(copterTex, new Vector2(17, 10), 4f, 2f, new Vector2(20,8), 5f, true, 3.5f));
		movablePlatforms.add(new MovablePlatform(copterTex, new Vector2(25, 8), 4f, 2f, new Vector2(28,10), 4.5f, true, 3.5f));
	}
	
	/* ----- Object handlers ----- */	
	private void checkTileCollision(MovableEntity mve) {
		
		
		/* MovablePlatform code */
		boolean onMovable = false;
		MovablePlatform onPlat = null;

		//Check moving platform collisions
		sRec = mve.getProjectionRect();
		for (MovablePlatform mp: movablePlatforms) {
			mp.update(ship);
			//System.out.println("sRec: "+sRec.x+","+sRec.y+","+sRec.width+","+sRec.height+" mp: "+mp.getCollisionRectangle().x+","+mp.getCollisionRectangle().y+","+mp.getCollisionRectangle().width+","+mp.getCollisionRectangle().height);
			if (sRec.overlaps(mp.getCollisionRectangle())) {
				onMovable = true;
				onPlat = mp;
				//System.out.println("moving ship");
				mve.getPosition().add(mp.getPositionDelta());
				//ship.getVelocity().add(mp.getPositionDelta());
				//ship.getPosition().y -= Ship.GRAVITY * Gdx.graphics.getDeltaTime();
				
				// Stop falling through the floor when going up if on the top of platform
				float top = mp.getPosition().y + mp.getCollisionRectangle().height;
				if (mve.getPosition().y < top + 24/32f && mve.getPosition().y > top - 24/32f) {
					//System.out.println("****Fixing position on platform*****. Top: "+top+" Ship ypos: "+ship.getPosition().y);
					mve.handleTopOfMovingPlatform(mp);
					
					//onMovable = true;
					//onPlat = mp;
				}
				
				//If in wall state and hit bottom of platform, fall off
				//if (ship.getState() == State.WALL && ship.getPosition().y <  
			}
			
			//Remove moving platforms that leave the bottom of the map
			if (mp.getPosition().y + mp.getHeight()<0) {
				for (MovablePlatformSpawner mps: movablePlatformSpawners) {
					mps.removePlatform(mp);
				}
				movablePlatforms.removeValue(mp, true);
			}
			//mp.update(ship);
		}
		
		
		
		/* Tile collisions code */
		//Check player to tile collisions
		//get tiles near player
		TiledMapTileLayer collisionLayer = levelLayout.getCollisionLayer();
		
		Array<Rectangle> tiles = new Array<Rectangle>();
		tiles.clear();

		int checkX = 0, checkY = 0;
		if (mve.getVelocity().x != 0) checkX = 1;
		if (mve.getVelocity().y != 0) checkY = 2;

		for (float i = mve.getPosition().x - checkX; i < mve.getPosition().x + 1+ mve.getWidth() + checkX; i++) {
			for (float j = mve.getPosition().y - checkY; j < mve.getPosition().y + mve.getHeight() + checkY; j++) {
				//System.out.println("chcking cell at " + (int)i + "," + (int)j + " and collisionlayer is " + collisionLayer);
		
				Cell cell = collisionLayer.getCell((int) i, (int) j);
				if (cell != null) {
					//System.out.println("found cell at " + (int)i + "," + (int)j);
					//System.out.println("floats were " + i +"," + j);
					//System.out.println("i range: " + (ship.getPosition().x-checkX) +" to "+(ship.getPosition().x+ship.getWidth()+checkX));
					//System.out.println("j range: " + (ship.getPosition().y-checkY)+" to "+(ship.getPosition().y+ship.getHeight()+checkY));
					Rectangle rect = new Rectangle((int)i, (int)j, 1, 1);
					tiles.add(rect);
				}
			}
		}
		
		//add the cutsceneobjects or movable platforms to the collisions
		for (MovablePlatform mvPlat: movablePlatforms) {
			tiles.add(mvPlat.getCollisionRectangle());
		}

		sRec = mve.getXProjectionRect();
		//System.out.println("Number of tiles checking: "+tiles.size);
		for (Rectangle tile: tiles) {
			if (sRec.overlaps(tile)) {
			//if (ship.getBounds().overlaps(tile)) {
				//ship.getVelocity().x = 0;
				//System.out.println("Get knocked back from "+ship.getPosition().x + " by " + ship.getVelocity().x);
				/*ship.getPosition().x -= ship.getVelocity().scl(Gdx.graphics.getDeltaTime()).x;
				ship.getVelocity().scl(1/Gdx.graphics.getDeltaTime());*/
				System.out.println("@@@@@@@@@@@@@@@@X-Collision with tile at "+tile.x+", "+tile.y+ "   (w,h): "+tile.width+","+tile.height+"    @@@@@@@@@@@@@@");
				mve.handleXCollision(tile);
				
				
				break;
			}
		}
		
		sRec = ship.getYProjectionRect();
		boolean tileUnderMve = false;
		for (Rectangle tile:tiles) {
			if (sRec.overlaps(tile)) {
				//System.out.println("Y-Collision with tile at "+tile.x+", "+tile.y+ "   (w,h): "+tile.width+","+tile.height);
				//to the bottom of player
				mve.handleYCollision(tile, onMovable, onPlat);
				
					//ship.getVelocity().y = 0;
					//System.out.println("after y state="+ship.getState());
					tileUnderMve = true;
					
				
			}
		}
		if (!tileUnderMve) {
			mve.handleNoTileUnderneath();
		}
		//System.out.println("after both state="+ship.getState());
		//ship.update(ship);
		
		return;
	}
	
	private void checkDamage() {
		Iterator<Enemy> eItr;
		Enemy e;
		
		eItr = enemies.iterator();
		while(eItr.hasNext()) {
			e = eItr.next();
			
			/* Collision with enemy */
			if(!ship.isInvincible()) {
				if ( e.getBounds().overlaps(ship.getBounds()) ) {
					ship.decrementHearts();
					
					if(e.getPosition().x >= ship.getPosition().x) {
						ship.getPosition().x -= 1;
					} else {
						ship.getPosition().x += 1;
					}
					
					ship.setInvincibility(true);
				}
			}
		}
		
		return;
	}
	
	private void spawnEnemies() {
		Iterator<EnemySpawner> sItr;
		EnemySpawner s;

		sItr = spawners.iterator();
		while (sItr.hasNext()) {
			s = sItr.next();
			//Check if spawner is in the range of 2 blocks out of viewport. NOT USING VIEWPORT ATM> SHOULD PROBS CHAGNE
			
			if (s.increment()) {
				//System.out.println("Ready to spawn. Sxy:"+s.getPosition().x+","+s.getPosition().y+" Shipxy"+ship.getPosition().x+","+ship.getPosition().y);
				Vector2 spp = s.getPosition();
				Vector2 shp = ship.getPosition();
				/*if ((spp.x < shp.x + 7f && spp.x > shp.x + 6f)||
						(spp.x > shp.x - 7f && spp.x < shp.x - 6f)||
						(spp.y > shp.y -4f && spp.y < shp.y -3.5f)||
						(spp.y < shp.y+4f && spp.y > shp.y+3.5f)) {
					
					enemies.add(s.spawnNew());
				}*/
				if (
						(((spp.x < shp.x + 7f && spp.x > shp.x + 6f && ship.getVelocity().x>0)||
						(spp.x > shp.x - 7f && spp.x < shp.x - 6f &&ship.getVelocity().x<0)) &&
						(spp.y > shp.y -3.5f && spp.y < shp.y + 3.5f)) ||
						
						(((spp.y > shp.y -4f && spp.y < shp.y -3.5f && ship.getVelocity().y<0)||
						(spp.y < shp.y+4f && spp.y > shp.y+3.5f && ship.getVelocity().y>0)) &&
						(spp.x > shp.x - 6f && spp.x <shp.x + 6f)) 
						
						) {
					System.out.println("Spawn! Sxy:"+s.getPosition().x+","+s.getPosition().y+" Shipxy"+ship.getPosition().x+","+ship.getPosition().y);
					enemies.add(s.spawnNew());
				}
			}
		}
		return;
	}
	
	private void spawnMovablePlatforms() {
		for (MovablePlatformSpawner mps: movablePlatformSpawners) {
			
			//Check if spawner is in the range of 2 blocks out of viewport. NOT USING VIEWPORT ATM> SHOULD PROBS CHAGNE
			
			if (mps.increment()) {
				movablePlatforms.add(mps.spawnNew());
			}
				
		}
		return;
	}
	
	private void handleEnemies() {
		Iterator<Bullet> bItr;
		Iterator<Enemy> eItr;
		Bullet b;
		Enemy e;
		
		eItr = enemies.iterator();
		while(eItr.hasNext()) {
			e = eItr.next();
			// Get near player if scene is not playing
			if (!levelScenes.isPlaying())
				e.advance(Gdx.graphics.getDeltaTime(), ship);
			
			/* Sword collisions */
			if (e.getBounds().overlaps(sword.getBounds())) {
				//System.out.println("C");
				eItr.remove();
				//System.out.println("removed enemy");
				for (EnemySpawner spns: spawners) {
					spns.removeEnemy(e);
				}
				//System.out.println("cleaned arrays");
			}
		}
		
		bItr = bullets.iterator();
		while(bItr.hasNext()) {
			b = bItr.next();
			b.update(ship);

			eItr = enemies.iterator();
			while(eItr.hasNext()) {
				e = eItr.next();
				//e.advance(Gdx.graphics.getDeltaTime(), ship);
			
				if(e.getBounds().overlaps(b.getBounds())){
					//System.out.println("C");
					eItr.remove();
					//System.out.println("removed enemy");
					bItr.remove();
					//System.out.println("removed bullet");
					for (EnemySpawner spns: spawners) {
						spns.removeEnemy(e);
					}
					//System.out.println("cleaned arrays");
				}
			}
		}
		return;
	}

	public void updateCamera() {
		float boxX=2.5f;
		//float boxY=6f;
		//if(ship.getPosition().x - boxX> cam.viewportWidth/2 && (cam.position.x < ship.getPosition().x-boxX || cam.position.x > ship.getPosition().x + boxX)) {
		//System.out.println("shipX="+ship.getPosition().x+" camviewwidth/2="+cam.viewportWidth/2+" camX="+cam.position.x);
		if(ship.getPosition().x + boxX> cam.viewportWidth/2 && (cam.position.x < ship.getPosition().x-boxX || cam.position.x > ship.getPosition().x + boxX)) {
			//if (ship.getVelocity().x > 0.5f) {
			float lerp = 0.03f;
			if (ship.isFacingRight()) {
			//cam.position.x = ship.getPosition().x - boxX;
				cam.position.x += (ship.getPosition().x + boxX- cam.position.x) * lerp;
				
			//} else if (ship.getVelocity().x < -0.5f){
			} else if (!ship.isFacingRight()) {
				cam.position.x += (ship.getPosition().x - boxX - cam.position.x) * lerp;
			} else {
				cam.position.x += (ship.getPosition().x - cam.position.x) * lerp;
			}
		}
		if((ship.getPosition().y > cam.viewportHeight/2 && ship.getPosition().y + cam.viewportHeight/2< WORLD_HEIGHT) || 
				(cam.position.y > cam.viewportHeight/2 && cam.position.y + cam.viewportHeight/2< WORLD_HEIGHT)) {
		//if(cam.position.y > cam.viewportHeight/2 && cam.position.y + cam.viewportHeight/2< WORLD_HEIGHT) {
			float lerp = 0.035f;
			cam.position.y += (ship.getPosition().y - cam.position.y) * lerp;
		} /*else {
			cam.position.y = (cam.viewportHeight/2);
			System.out.println("posY "+cam.position.y+" viewportHeight="+cam.viewportHeight);
		}*/
	}
	
	public void resetCamera() {
		if(ship.getPosition().x > cam.viewportWidth/2 ) {
			cam.position.x = ship.getPosition().x;
			
		} else {
			cam.position.x = cam.viewportWidth/2;
		}
		if(ship.getPosition().y > cam.viewportHeight/2 && ship.getPosition().y + cam.viewportHeight/2< WORLD_HEIGHT) {
			cam.position.y = ship.getPosition().y;
		} else if (ship.getPosition().y <= cam.viewportHeight/2){
			cam.position.y = cam.viewportHeight/2;
		} else {
			cam.position.y = WORLD_HEIGHT - cam.viewportHeight/2;
		}
			
		
	}
	
	
	/* ----- Getter methods ----- */
	public Ship getShip() {
		return ship;
	}
	
	public Sword getSword() {
		return sword;
	}
	
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	public Array<Bullet> getBullets() {
		return bullets;
	}

	public Array<CutsceneObject> getCutsceneObjects() {
		return cutsceneObjects;
	}

	public Array<MovablePlatform> getMovablePlatforms() {
		return movablePlatforms;
	}

	
	
	public LevelLayout getLevelLayout() {
		return levelLayout;
	}
	
	public int getCurLevel() {
		return curLevel;
	}
	

	
	
	
	/* ----- Setter methods ----- */
	public void init() {
		firstUpdate = true;
		//ship = new Ship(new Vector2(220f, 60));
		ship = new Ship(new Vector2(3f, 6));
		sword = new Sword(new Vector2(-1, -1));
		enemies = new Array<Enemy>();
		bullets = new Array<Bullet>();
		spawners = new Array<EnemySpawner>();
		movablePlatformSpawners = new Array<MovablePlatformSpawner>();
		cutsceneObjects = new Array<CutsceneObject>();
		movablePlatforms = new Array<MovablePlatform>();
		//resetCamera();
		
		
		inputHandler = new InputHandler(this);
		Gdx.input.setInputProcessor(inputHandler);
		return;
	}
	
	

	public void resetLevel(int level) {
		ship = null;
		sword = null;
		enemies = null;
		bullets = null;
		spawners = null;
		cutsceneObjects = null;
		movablePlatforms = null;
		levelLayout = null;
		levelScenes = null;
		
		
		init();
		loadLevel(level);

		return;
	}
	
	
	
	public void dispose() {
		
	}
}
