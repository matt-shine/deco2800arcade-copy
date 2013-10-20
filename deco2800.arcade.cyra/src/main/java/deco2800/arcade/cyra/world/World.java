package deco2800.arcade.cyra.world;



import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.cyra.game.Cyra;
import deco2800.arcade.cyra.game.AchievementsTracker;
import deco2800.arcade.cyra.game.MainMenu;
import deco2800.arcade.cyra.model.Block;
import deco2800.arcade.cyra.model.BlockMaker;
import deco2800.arcade.cyra.model.Bullet;
import deco2800.arcade.cyra.model.BulletHomingDestructible;
import deco2800.arcade.cyra.model.BulletSimple;
import deco2800.arcade.cyra.model.CutsceneObject;
import deco2800.arcade.cyra.model.Enemy;
import deco2800.arcade.cyra.model.EnemySpawner;
import deco2800.arcade.cyra.model.LaserBeam;
import deco2800.arcade.cyra.model.MovableEntity;
import deco2800.arcade.cyra.model.MovablePlatform;
import deco2800.arcade.cyra.model.MovablePlatformAttachment;
import deco2800.arcade.cyra.model.MovablePlatformSpawner;
import deco2800.arcade.cyra.model.Player;
import deco2800.arcade.cyra.model.RandomizedEnemySpawner;
import deco2800.arcade.cyra.model.ResultsScreen;
import deco2800.arcade.cyra.model.SoldierEnemy;
import deco2800.arcade.cyra.model.Sword;
import deco2800.arcade.cyra.model.Zombie;

/** World class describes the Model component of the game's MVC model.
 * It controls the interactions between various game objects - interactions
 * among entities, interactions between entities and the specified level
 * including any collisions. This class also links Object references where
 * needed.
 *
 * @author Game Over
 */
public class World {
	public static final float WORLD_WIDTH = 618f;
	public static final float WORLD_HEIGHT = 60f;
	public static final float PLAYER_INIT_X = 20f;
	public static final float GAME_INIT_X = 602.5f;
	public static final int DEFAULT_LIVES = 3;
	
	private AchievementsTracker at = new AchievementsTracker();
	
	private Boolean firstUpdate;
	private Player ship;
	Rectangle sRec;
	private Sword sword;
	private Array<Enemy> enemies;
	private Array<Bullet> bullets;
	private Array<CutsceneObject> cutsceneObjects; 
	private Array<MovablePlatform> movablePlatforms;
	private Array<BlockMaker> blockMakers;
	private ParallaxCamera cam;
	private ResultsScreen resultsScreen;
	private int score;
	private int lives;
	private Vector2 respawnPosition;
	
	public float rank;
	private Level curLevel;
	private LevelScenes levelScenes;
	private InputHandler inputHandler;
	
	private float time;
	private float count;
	private boolean isPaused;
	private int scenePosition;
	private boolean callingInitAfterReloadLevel;
	private float initCount;
	
	private boolean turnOffScenes = false;
	
	
	private Cyra game;
	//He says this creates circular logic and hence is very bad. It's only really to get touchDown to access camera
	// if not using mouse then remove this
	//WorldRenderer wr;
	
	public World(Cyra game, int level, float difficulty, ParallaxCamera cam) {
		this.rank = difficulty;
		this.cam = cam;
		this.game = game;
		curLevel = new Level(level, rank);
		
		
		if (!Sounds.areSoundsLoadedYet()) {
			Sounds.loadAll();
		}
		callingInitAfterReloadLevel = false;
		initCount = 2.5f;
		init(true);
		//hardcode
				if(level == 1) {
					levelScenes = new Level1Scenes(ship, cam, resultsScreen);
				} else {
					levelScenes = new Level2Scenes(ship, cam, resultsScreen);
					Sounds.playLevelMusic();
				}
		
	}
	
	
	//Used to count number of jumps - for achievements
	public void incrementJumps() {
		game.incrementAchievement("cyra.jumparound");
	}
	
	public void update() {
		

		//If game is in paused state immediately return
		if (game.isPaused()) return;
		//If showing results Screen, update it and do nothing else
		if (resultsScreen.isShowing()) {
			score += resultsScreen.update(Gdx.graphics.getDeltaTime());
			return;
		}
		if (!levelScenes.isPlaying()) {
			time += Gdx.graphics.getDeltaTime();
		}
		
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
		
		if ( !levelScenes.isPlaying() ) {
			checkDamage();
		}
		
		if ( levelScenes.isPlaying() && !turnOffScenes ) {
			if (levelScenes.update(Gdx.graphics.getDeltaTime())) {
				// The scene is complete; accept input again
				inputHandler.acceptInput();
				
				//if this was the final scene, the game was won
				if (scenePosition == levelScenes.getStartValues().length) {
					gameWin();
				}
			}
		
		// Check for scene start
		} else if( !levelScenes.isPlaying() && !turnOffScenes ) {
			float[] sceneStartValues = levelScenes.getStartValues();
			
			if (ship.getPosition().x > sceneStartValues[scenePosition]) {
				sceneStart();
			}
		}

		if (firstUpdate) {
			resetCamera();
			firstUpdate = false;
		} else {
			updateCamera();
			if( ship.getHearts() == 0 || ship.getPosition().y < -3) {
				/*if (--lives == 0) {
					gameOver();
				} else {
					resetLevel();
				}*/
				//ship.setState(Player.State.DEATH);
				ship.setHasDied();
				System.out.println("SHIP IN BAD POSITION!!!! hearts="+ship.getHearts()+" ship.getPosition().y"+ship.getPosition().y);
				inputHandler.cancelInput();
				count -= Gdx.graphics.getDeltaTime();
				if (count <= 0) {
					if (--lives == 0) {
						gameOver();
					} else {
						resetLevel();
					}
				}
				
			}
		}
		
		//System.out.println("End of World update " + ship.getVelocity().x);

		
		// Check if sprite has reached left/right level boundary. Changed to just left boundary
		//if( (int)(ship.getNextPos().x) < 1 || (int)(ship.getNextPos().x) > WORLD_WIDTH )
		if( (int)(ship.getNextPos().x) < 2)
			ship.setCanMove(false);
		// Check if sprite has gone out of level bounds to the bottom.
		/*if( (int)(ship.getPosition().y) < -1 ) {
			resetLevel();
		}*/
		if( (int)(ship.getPosition().x)-1 < -1 ) {
			
		}
		// stop player going off top of screen
		if ( ship.getPosition().y > WORLD_HEIGHT-ship.getHeight()) {
			ship.getPosition().y = WORLD_HEIGHT-ship.getHeight();
			if (ship.getVelocity().y > 0) {
				ship.getVelocity().y = 0;
			}
		}
		// Reset if health = 0
		//check if player is near foreground section
		float[] starts = curLevel.getForegroundStarts();
		float[] ends = curLevel.getForegroundEnds();
		float x = ship.getPosition().x;
		boolean isOverForeground = false;
		for (int i=0; i<starts.length; i++) {
			if (x >starts[i] && x < ends[i]) {
				
				isOverForeground = true;
				break;
			}
		}
		ship.setOverForeground(isOverForeground);
		

		/*if (!callingInitAfterReloadLevel) {
			initCount -= Gdx.graphics.getDeltaTime();
			if (initCount <0) {
				resetLevel();
				Sounds.setSoundEnabled(true);
			}
		}*/
		Sounds.setSoundEnabled(true);
		
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
		TiledLayer collisionLayer = curLevel.getCollisionLayer();
		com.badlogic.gdx.graphics.g2d.tiled.TiledMap map = curLevel.getMap();
		
		Array<Rectangle> tiles = new Array<Rectangle>();
		tiles.clear();

		int checkX = 0, checkY = 0;
		if (mve.getVelocity().x != 0) checkX = 2;
		if (mve.getVelocity().y != 0) checkY = 2;

		for (float i = mve.getPosition().x - checkX; i < mve.getPosition().x + 1+ mve.getWidth() + checkX; i++) {
			for (float j = mve.getPosition().y - checkY; j < mve.getPosition().y + mve.getHeight() + checkY; j++) {
				//System.out.println("chcking cell at " + (int)i + "," + (int)j + " and collisionlayer is " + collisionLayer);
		
				//Cell cell = collisionLayer.getCell((int) i, (int) j);
				
				int xLength = collisionLayer.tiles[0].length;
				int yLength = collisionLayer.tiles.length;
				//int yLength = 59; //obviously this is wrong I just need to get this working
				if (i < xLength && i > 0 && j < yLength && j > 0) { 
					int cell = collisionLayer.tiles[yLength-((int)j)-1][(int)i];
					
					//System.out.println("Cell ("+(int)i+","+(int)j+") test: "+cell + "  Mve = "+mve.getClass());
					/*if (cell != null) {
						//System.out.println("found cell at " + (int)i + "," + (int)j);
						//System.out.println("floats were " + i +"," + j);
						//System.out.println("i range: " + (ship.getPosition().x-checkX) +" to "+(ship.getPosition().x+ship.getWidth()+checkX));
						//System.out.println("j range: " + (ship.getPosition().y-checkY)+" to "+(ship.getPosition().y+ship.getHeight()+checkY));
						Rectangle rect = new Rectangle((int)i, (int)j, 1, 1);
						tiles.add(rect);
					}*/
					//String type = map.getTileProperty(cell, "checkCollision");
					//System.out.println(type);
					//if (type != null && type.equals("solid")) {
					if (cell != 0) {
						//System.out.println("I'm colliding with ("+(int)i+","+(int)j+")");
						Rectangle rect = new Rectangle((int)i, (int)j, 1, 1);
						tiles.add(rect);
					}
				}
			}
		}
		
		//add the movable platforms and BlockMaker blocks to the collisions
		for (MovablePlatform mvPlat: movablePlatforms) {
			tiles.add(mvPlat.getCollisionRectangle());
		}
		for (BlockMaker blockMaker: blockMakers) {
			if (blockMaker.isActive()) {
				Array<Block> bmb = blockMaker.getBlocks();
				for (Block b: bmb) {
					if (b.isSolid()) {
						tiles.add(b.getBounds());
					}
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
						
						float sx = ship.getPosition().x;
						float sy = ship.getPosition().y;
						float[] vertices = {sx, sy+ship.getHeight(), sx+ship.getWidth(), sy+ship.getHeight(), sx+ship.getWidth(), sy};
						Polygon shipPoly = new Polygon(vertices);
						shipPoly.setOrigin(sx, sy);
						
						
						if (Intersector.overlapConvexPolygons(laserPoly, shipPoly)) {
							ship.decrementHearts();
							at.incrementHeartsLost();

							
							ship.bounceBack(true);
							
							ship.setInvincibility(true);
						}
					}
				} else {
					for (Rectangle r: e.getPlayerDamageBounds()) {
						if ( r.overlaps(ship.getBounds()) ) {
							ship.decrementHearts();
							at.incrementHeartsLost();
							
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
				//Vector2 shp = ship.getPosition();
				float camX = cam.position.x;
				float camY = cam.position.y;
				/*if ((spp.x < shp.x + 7f && spp.x > shp.x + 6f)||
						(spp.x > shp.x - 7f && spp.x < shp.x - 6f)||
						(spp.y > shp.y -4f && spp.y < shp.y -3.5f)||
						(spp.y < shp.y+4f && spp.y > shp.y+3.5f)) {
					
					enemies.add(s.spawnNew());
				}*/
				/*if (
						(((spp.x < shp.x + 7f && spp.x > shp.x + 6f && ship.getVelocity().x>0)||
						(spp.x > shp.x - 7f && spp.x < shp.x - 6f &&ship.getVelocity().x<0)) &&
						(spp.y > shp.y -3.5f && spp.y < shp.y + 3.5f)) ||
						
						(((spp.y > shp.y -4f && spp.y < shp.y -3.5f && ship.getVelocity().y<0)||
						(spp.y < shp.y+4f && spp.y > shp.y+3.5f && ship.getVelocity().y>0)) &&
						(spp.x > shp.x - 6f && spp.x <shp.x + 6f)) 
						
						) {*/
				if (
						(((spp.x > camX + cam.viewportWidth/2 && spp.x < camX + cam.viewportWidth/2+1f && ship.getVelocity().x > 0) ||
						(spp.x > camX - cam.viewportWidth/2 - 1f && spp.x < camX - cam.viewportWidth/2 && ship.getVelocity().x < 0)) &&
						(spp.y >camY - cam.viewportHeight/2 && spp.y < camY + cam.viewportHeight/2)) ||
						
						(((spp.y > camY + cam.viewportHeight/2 && spp.y < camY + cam.viewportHeight/2+1f && ship.getVelocity().y > 0) ||
						(spp.y > camY - cam.viewportHeight/2 - 1f && spp.y < camY - cam.viewportHeight/2 && ship.getVelocity().y < 0)) &&
						(spp.x >camX - cam.viewportWidth/2 && spp.x < camX + cam.viewportWidth/2))
						) {
					//System.out.println("Spawn! Sxy:"+s.getPosition().x+","+s.getPosition().y+" Shipxy"+ship.getPosition().x+","+ship.getPosition().y);
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
			if ( !levelScenes.isPlaying() ) {
				Array<Enemy> newEnemies = e.advance(Gdx.graphics.getDeltaTime(), ship, rank, cam);
				
				if (e.isDead()) {			
					//System.out.println("removing " + e.getClass()+ " because dead");
					score += e.getScore();
					eItr.remove();
					//Enemy is dead - achievement
					if (e.getClass() == SoldierEnemy.class) {
						game.incrementAchievement("cyra.slayer");
					}
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
			} else if (e.advanceDuringScenes()){
				//keep the bullets explosions and lasers flying throughout scenes
				e.advance(Gdx.graphics.getDeltaTime(), ship, rank, cam);
			}
			
			
			/* Sword collisions */
			if (e.getVulnerableBounds().overlaps(sword.getBounds())) {
				//System.out.println("C");
				boolean fromRight = false;
				if (e.getPosition().x < sword.getPosition().x+sword.getWidth()/2) {
					fromRight = true;
				}
				e.handleDamage(fromRight);
				
				
				//System.out.println("cleaned arrays");
				
			} 

				
			//Remove enemies too far outside of camera view
			if (e.getPosition().x > cam.position.x + cam.viewportWidth * 1.5 ||
					e.getPosition().x < cam.position.x - cam.viewportWidth * 1.5) {
				System.out.println("removing " + e.getClass()+ " because too far from x position");
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
			
			//float boxX=2.5f;
			float boxX = 1.5f;
			
			if(ship.getPosition().x + boxX> cam.viewportWidth/2 && (cam.position.x < ship.getPosition().x-boxX || cam.position.x > ship.getPosition().x + boxX)) {
				//if (ship.getVelocity().x > 0.5f) {
				//float lerp = 0.03f;
				float lerp; 
				if (ship.getVelocity().x < 10f) {
					lerp = 0.03f;
				} else {
					lerp = 0.065f;
				}
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
			//ensure ship stays within camera bounds
			if (ship.getPosition().x < cam.position.x - cam.viewportWidth/2) {
				ship.getPosition().x = cam.position.x - cam.viewportWidth/2;
			} else if (ship.getPosition().x > cam.position.x + cam.viewportWidth/2 - ship.getWidth()) {
				ship.getPosition().x = cam.position.x + cam.viewportWidth/2 -ship.getWidth();
			}
			
			if (ship.getPosition().y > cam.position.y + cam.viewportHeight/2-ship.getHeight()) {
				ship.getPosition().y = cam.position.y + cam.viewportHeight/2-ship.getHeight();
			}
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
				bm.update(Gdx.graphics.getDeltaTime(), cam, rank);
			}
		}
	}

	private void sceneStart() {
		inputHandler.cancelInput();
		ship.getVelocity().x = 0;
		Array<Object> temp = levelScenes.start(scenePosition, rank, (int)time);
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
	
	private void addStaticEnemies() {
		enemies.add( new Zombie(new Vector2(32f, 3f)) );
		return;
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
	public Player getShip() {
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
	
	public int getScore() {
		return score;
	}
	
	public int getLives() {
		return lives;
	}
	
	public ResultsScreen getResultsScreen() {
		return resultsScreen;
	}
	
	public boolean isScenePlaying() {
		return levelScenes.isPlaying();
	}
	

	
	
	
	/* ----- Setter methods ----- */
	public void init(boolean completeInit) {
		
		count = 3f;
		at.printStats();
		
		//Sounds.playBossMusic();
		
		
		firstUpdate = true;
		//ship = new Ship(new Vector2(220f, 60));
		
		//ship = new Ship(new Vector2(270, 60));
		
		sword = new Sword(new Vector2(-1, -1));
		enemies = new Array<Enemy>();
		bullets = new Array<Bullet>();
		cutsceneObjects = new Array<CutsceneObject>();
		movablePlatforms = new Array<MovablePlatform>();
		blockMakers = new Array<BlockMaker>();
		//resetCamera();
		if (completeInit) {
			time = 0;
			score = 0;
			lives = DEFAULT_LIVES;
		//if (callingInitAfterReloadLevel) {
			scenePosition = 0;
			ship = new Player(new Vector2(PLAYER_INIT_X, 6));
		//} else {
		//	ship = new Player(new Vector2(GAME_INIT_X, 6));
		//	scenePosition = 4;
		//}
		} else {
			ship = new Player(respawnPosition);
		}
		resultsScreen = new ResultsScreen();
		
		isPaused = false;
		
		inputHandler = new InputHandler(this);
		Gdx.input.setInputProcessor(inputHandler);
		
		
		//enemies.add( new SoldierEnemy(new Vector2 (15f, 9f), false));
		Texture copterTex = new Texture(Gdx.files.internal("copter.png"));
		copterTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		movablePlatforms.add(new MovablePlatform(copterTex, new Vector2(361, 8), 4f, 2f, new Vector2(361,42), 4.5f, true, 3.5f));
		
		
		//addStaticEnemies();
		
		
		return;
	}

	public void resetLevel() {
		at.addToTime( (int)time );
		
		respawnPosition = new Vector2(levelScenes.getPlayerReloadPosition(scenePosition));
		scenePosition = levelScenes.getScenePositionAfterReload(scenePosition);
		
		
		ship = null;
		sword = null;
		enemies = null;
		bullets = null;
		cutsceneObjects = null;
		movablePlatforms = null;
		levelScenes = null;
		cam.setFollowShip(true);
		inputHandler.acceptInput();

		curLevel.reloadLevel();
		//callingInitAfterReloadLevel = true;
		init(false);
		levelScenes = new Level2Scenes(ship, cam, resultsScreen);
		return;
	}
	
	public void gameOver() {
		// go back to menu
		System.out.println("GAME OVER!!!");
		game.addHighscore(score);
		game.setScreen(new MainMenu(game));

		
	}
	
	public void gameWin() {
		// show some message/credits then go back to menu
		game.addHighscore(score);
		game.setScreen(new MainMenu(game));
	}
	
	public void dispose() {
		
	}
}
