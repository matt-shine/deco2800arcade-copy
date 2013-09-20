package com.test.game.world;



import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.TestGame2;
import com.test.game.model.Block;
import com.test.game.model.BlockMaker;
import com.test.game.model.Bullet;
import com.test.game.model.BulletHomingDestructible;
import com.test.game.model.BulletSimple;
import com.test.game.model.CutsceneObject;
import com.test.game.model.Enemy;
import com.test.game.model.EnemySpawner;
import com.test.game.model.Follower;
import com.test.game.model.LaserBeam;
import com.test.game.model.MovableEntity;
import com.test.game.model.MovablePlatform;
import com.test.game.model.MovablePlatformAttachment;
import com.test.game.model.MovablePlatformSpawner;
import com.test.game.model.RandomizedEnemySpawner;
import com.test.game.model.Ship;
import com.test.game.model.Ship.State;
import com.test.game.model.SoldierEnemy;
import com.test.game.model.Sword;
import com.test.game.model.Walker;
import com.test.game.model.Zombie;

/** World class controls all objects in the specified level including any collisions
 * and links Object references where needed
 * @author Game Over
 *
 */
public class World {
	public static final float WORLD_WIDTH = 400f;
	public static final float WORLD_HEIGHT = 59f;
	
	
	private Boolean firstUpdate;
	private Ship ship;
	Rectangle sRec;
	private Sword sword;
	private Array<Enemy> enemies;
	private Array<Bullet> bullets;
	private Array<CutsceneObject> cutsceneObjects; 
	private Array<MovablePlatform> movablePlatforms;
	private Array<BlockMaker> blockMakers;
	private ParallaxCamera cam;
	
	private float rank;
	private Level curLevel;
	private LevelScenes levelScenes;
	private InputHandler inputHandler;
	
	private float time;
	private boolean isPaused;
	private int scenePosition;
	
	//He says this creates circular logic and hence is very bad. It's only really to get touchDown to access camera
	// if not using mouse then remove this
	//WorldRenderer wr;
	
	public World(TestGame2 game, int level, ParallaxCamera cam) {
		curLevel = new Level(level);
		this.cam = cam;
		
		init();
		
		//hardcode
		if(level == 1) {
			levelScenes = new Level1Scenes(ship, cam);
		} else {
			levelScenes = new Level2Scenes(ship, cam);
		}
	}
	
	
	
	public void update() {
		time += Gdx.graphics.getDeltaTime();

		//If game is in paused state immediately return
		if (isPaused) return;
		//System.out.println("Delta = "+Gdx.graphics.getDeltaTime());
		//System.out.println("State before ship = "+ship.getState());
		ship.update(ship);		
		//if (sword.inProgress()) sword.update(ship);
		sword.update(ship);
		//System.out.println("State after ship = "+ship.getState());
		spawnEnemies();
		spawnMovablePlatforms();

		for (MovablePlatform mp: movablePlatforms) {
			mp.update(ship);
		}
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
		

		handleEnemies();
		
		if (!levelScenes.isPlaying()) {
			checkDamage();
		}

		if (firstUpdate) {
			resetCamera();
			firstUpdate = false;
		} else {
			updateCamera();
		}
		
		//System.out.println("End of World update " + ship.getVelocity().x);

		
		// Check if sprite has reached left level boundary.
		// if( (int)(ship.getPosition().x) < 1 ) ship.getVelocity().x = 0;
		
		
		// Check if sprite has gone out of level bounds to the bottom.
		if( (int)(ship.getPosition().y) < -1 ) {
			resetLevel();
		}
		// Reset if health = 0
		if( ship.getHearts() == 0 ) {
			resetLevel();
		}

		if (levelScenes.isPlaying()) {
			if (levelScenes.update(Gdx.graphics.getDeltaTime())) {
				// The scene is complete; accept input again
				inputHandler.acceptInput();
			}
		} else {
			float[] sceneStartValues = levelScenes.getStartValues();
			if (ship.getPosition().x > sceneStartValues[scenePosition]) {
				sceneStart();
				
			}
		}
		
		updateBlockMakers();
		
		return;
	}
	
	public void addBullet(Bullet b) {
		bullets.add(b);
	}
	
	/* ----- Object handlers ----- */	
	private void checkTileCollision(MovableEntity mve) {
		
		//System.out.println("Checking the mve: "+ mve.getClass()+ " at ("+mve.getPosition().x+","+mve.getPosition().y+")");
		/* MovablePlatform code */
		boolean onMovable = false;
		MovablePlatform onPlat = null;

		//Check moving platform collisions
		sRec = mve.getProjectionRect();
		for (MovablePlatform mp: movablePlatforms) {
			
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
				for (MovablePlatformSpawner mps: curLevel.getMovablePlatformSpawners() ) {
					mps.removePlatform(mp);
				}
				movablePlatforms.removeValue(mp, true);
			}
			//mp.update(ship);
		}
		
		
		
		/* Tile collisions code */
		//Check player to tile collisions
		//get tiles near player
		TiledMapTileLayer collisionLayer = curLevel.getCollisionLayer();
		
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
		
		//add the movable platforms and BlockMaker blocks to the collisions
		for (MovablePlatform mvPlat: movablePlatforms) {
			tiles.add(mvPlat.getCollisionRectangle());
		}
		for (BlockMaker blockMaker: blockMakers) {
			Array<Block> bmb = blockMaker.getBlocks();
			for (Block b: bmb) {
				if (b.isSolid()) {
					tiles.add(b.getBounds());
				}
			}
		}

		sRec = mve.getXProjectionRect();
		//System.out.println("XProjectionRec="+sRec);
		//System.out.println("Number of tiles checking: "+tiles.size);
		for (Rectangle tile: tiles) {
			if (sRec.overlaps(tile)) {
			//if (ship.getBounds().overlaps(tile)) {
				//ship.getVelocity().x = 0;
				//System.out.println("Get knocked back from "+ship.getPosition().x + " by " + ship.getVelocity().x);
				/*ship.getPosition().x -= ship.getVelocity().scl(Gdx.graphics.getDeltaTime()).x;
				ship.getVelocity().scl(1/Gdx.graphics.getDeltaTime());*/
				//System.out.println("@@@@@@@@@@@@@@@@X-Collision with tile at "+tile.x+", "+tile.y+ "   (w,h): "+tile.width+","+tile.height+"    @@@@@@@@@@@@@@");
				mve.handleXCollision(tile);
				
				
				break;
			}
		}
		
		sRec = mve.getYProjectionRect();
		//System.out.println("YProjectionRec="+sRec);
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
				if (e.getClass() == LaserBeam.class) {
					Polygon laserPoly = ((LaserBeam)e).getLaserBounds();
					if (laserPoly != null) {
						Polygon shipPoly = new Polygon();
						float sx = ship.getPosition().x;
						float sy = ship.getPosition().y;
						shipPoly.setOrigin(sx, sy);
						float[] vertices = {sx, sy+ship.getHeight(), sx+ship.getWidth(), sy+ship.getHeight(), sx+ship.getWidth(), sy};
						shipPoly.setVertices(vertices);
						if (Intersector.overlapConvexPolygons(laserPoly, shipPoly)) {
							ship.decrementHearts();
							
							ship.bounceBack(true);
							
							ship.setInvincibility(true);
						}
					}
				} else {
					for (Rectangle r: e.getPlayerDamageBounds()) {
						if ( r.overlaps(ship.getBounds()) ) {
							ship.decrementHearts();
							
							ship.bounceBack(true);
							
							ship.setInvincibility(true);
							break;
						}
						
					}
				}
			}
		}
		
		return;
	}
	
	private void spawnEnemies() {
		Iterator<EnemySpawner> sItr;
		EnemySpawner s;

		sItr = curLevel.getEnemySpawners().iterator();
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
		
		for (RandomizedEnemySpawner res: curLevel.getRandomEnemySpawners() ) {
			Enemy newEnemy = res.update(Gdx.graphics.getDeltaTime(), cam, rank);
			
			if (newEnemy != null) {
				enemies.add(newEnemy);
			}
		}
		
		return;
	}
	
	private void spawnMovablePlatforms() {
		for (MovablePlatformSpawner mps: curLevel.getMovablePlatformSpawners() ) {
			
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
			if (!levelScenes.isPlaying()) {
				Array<Enemy> newEnemies = e.advance(Gdx.graphics.getDeltaTime(), ship, rank);
				if (e.isDead()) {
					eItr.remove();
					//System.out.println("removed enemy");
					for (EnemySpawner spns: curLevel.getEnemySpawners() ) {
						spns.removeEnemy(e);
					}
					for (RandomizedEnemySpawner res: curLevel.getRandomEnemySpawners() ) {
						res.removeEnemy(e);
					}
				}
				if (e.startingNextScene()) {
					sceneStart();
				}
				if (newEnemies != null) {
					enemies.addAll(newEnemies);
				}
			} else if (e.getClass() == BulletSimple.class || e.getClass() == BulletHomingDestructible.class){
				//keep the bullets flying throughout scenes
				e.advance(Gdx.graphics.getDeltaTime(), ship, rank);
			}
			/* Sword collisions */
			if (e.getBounds().overlaps(sword.getBounds())) {
				//System.out.println("C");
				boolean fromRight = false;
				if (e.getPosition().x < sword.getPosition().x) {
					fromRight = true;
				}
				e.handleDamage(fromRight);
				
				//System.out.println("cleaned arrays");
				
			} 
			
			//Remove enemies too far outside of camera view
			if (e.getPosition().x > cam.position.x + cam.viewportWidth * 1.5 ||
					e.getPosition().x < cam.position.x - cam.viewportWidth * 1.5) {
				eItr.remove();
				for (EnemySpawner spns: curLevel.getEnemySpawners() ) {
					spns.removeEnemy(e);
				}
				for (RandomizedEnemySpawner res: curLevel.getRandomEnemySpawners() ) {
					res.removeEnemy(e);
				}
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
					for (EnemySpawner spns: curLevel.getEnemySpawners() ) {
						spns.removeEnemy(e);
					}
					//System.out.println("cleaned arrays");
				}
			}
			
			if(b.getExistTime() > b.MAX_EXIST_TIME) {
				bItr.remove();
			}
		}
		return;
	}

	public void updateCamera() {
		//can now move this method and resetCamera() into ParralaxCamera class
		if (cam.isFollowingShip()) {
			
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
		} else {
			
		}
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
	
	public void updateBlockMakers() {
		for (BlockMaker bm: blockMakers) {
			if (bm.isActive()) {
				bm.update(Gdx.graphics.getDeltaTime(), cam);
			}
		}
	}

	private void sceneStart() {
		inputHandler.cancelInput();
		ship.getVelocity().x = 0;
		Array<Object> temp = levelScenes.start(scenePosition, rank);
		for (Object obj: temp) {
			if (obj.getClass() == CutsceneObject.class) {
				cutsceneObjects.add( (CutsceneObject) obj );
			} else if (obj.getClass() == MovablePlatform.class) {
				System.out.println("that was a new movable platform!");
				movablePlatforms.add( (MovablePlatform) obj );
			} else if (obj.getClass() == MovablePlatformAttachment.class) {
				System.out.println("that was a new movable platform attachment!");
				movablePlatforms.add( (MovablePlatformAttachment) obj );
			} else if (obj instanceof BlockMaker) {
				System.out.println("adding blockmaker");
				blockMakers.add( (BlockMaker) obj);
			} else if (obj instanceof Enemy) {
				enemies.add( (Enemy) obj);
			}
		}
		scenePosition++;
	}
	public boolean isPaused() {
		return isPaused;
	}
	
	public void togglePause() {
		if (isPaused) isPaused = false;
		else isPaused = true;
	}
	
	public float getLowestClearPosition(float x) {
		return 0f;
	}
	/* ----- Getter methods ----- */
	public Ship getShip() {
		return ship;
	}
	
	public Sword getSword() {
		return sword;
	}
	
	public float getTime() {
		return time;
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
	
	public Array<BlockMaker> getBlockMakers() {
		return blockMakers;
	}

	
	
	public Level getLevel() {
		return curLevel;
	}
	

	
	
	
	/* ----- Setter methods ----- */
	public void init() {
		time = 0;
		firstUpdate = true;
		//ship = new Ship(new Vector2(220f, 60));
		//ship = new Ship(new Vector2(20f, 6));
		ship = new Ship(new Vector2(270, 60));
		sword = new Sword(new Vector2(-1, -1));
		enemies = new Array<Enemy>();
		bullets = new Array<Bullet>();
		cutsceneObjects = new Array<CutsceneObject>();
		movablePlatforms = new Array<MovablePlatform>();
		blockMakers = new Array<BlockMaker>();
		//resetCamera();
		//rank = 0.91f;
		rank = 0.76f;
		//rank = 0.21f;
		scenePosition = 0;
		
		isPaused = false;
		
		inputHandler = new InputHandler(this);
		Gdx.input.setInputProcessor(inputHandler);
		
		//Test objects
		enemies.add( new Zombie(new Vector2(12f,12f)) );
		enemies.add( new Walker(new Vector2 (10f, 9f)) );
		//enemies.add( new SoldierEnemy(new Vector2 (15f, 9f), false));
		Texture copterTex = new Texture("data/copter.png");
		copterTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		movablePlatforms.add(new MovablePlatform(copterTex, new Vector2(17, 10), 4f, 2f, new Vector2(20,8), 5f, true, 3.5f));
		movablePlatforms.add(new MovablePlatform(copterTex, new Vector2(25, 8), 4f, 2f, new Vector2(28,10), 4.5f, true, 3.5f));
		return;
	}
	
	

	public void resetLevel() {
		ship = null;
		sword = null;
		enemies = null;
		bullets = null;
		cutsceneObjects = null;
		movablePlatforms = null;
		levelScenes = null;
		cam.setFollowShip(true);

		curLevel.reloadLevel();
		init();
		levelScenes = new Level2Scenes(ship, cam);
		return;
	}
	
	
	
	public void dispose() {
		
	}
}
