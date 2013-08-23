package com.test.game.world;



import java.util.Iterator;

import com.badlogic.gdx.Gdx;
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

	Ship ship;
	Sword sword;
	Follower follower;
	Array<Enemy> enemies = new Array<Enemy>();
	Array<Bullet> bullets = new Array<Bullet>();
	Array<EnemySpawner> spawners = new Array<EnemySpawner>();
	private Array<CutsceneObject> cutsceneObjects = new Array<CutsceneObject>();
	private Array<MovablePlatform> movablePlatforms = new Array<MovablePlatform>();
	Iterator<Bullet> bItr;
	Iterator<Enemy> eItr;
	Iterator<EnemySpawner> sItr;
	Enemy e;
	Bullet b;
	EnemySpawner s;
	
	
	
	public Rectangle sRec;
	
	private LevelLayout levelLayout;
	//private LevelObjects levelObjects;
	private LevelScenes levelScenes;
	private InputHandler inputHandler;
	
	//He says this creates circular logic and hence is very bad. It's only really to get touchDown to access camera
	// if not using mouse then remove this
	WorldRenderer wr;
	
	public World(TestGame2 game, int level) {
		//ship = new Ship(new Vector2(3, 3), 1, 1, 0, 6);
		ship = new Ship(new Vector2(2.8f,16));
		
		//enemies.add(new Follower(5f, 0, new Vector2(1,1), 1, 1));
		sword = new Sword(new Vector2(-1, -1));
		
		inputHandler = new InputHandler(this);
		Gdx.input.setInputProcessor(inputHandler);
		
		levelLayout = new LevelLayout(level);
		
		Array<Object> objects = new Array<Object>();
		if (level == 1) {
			objects = Level1Objects.loadObjects(levelLayout.getMap());
			levelScenes = new Level1Scenes(ship);
			
		}
		for (Object o: objects) {
			if (o instanceof EnemySpawner) {
				spawners.add((EnemySpawner) o);
			}
		}
		
		enemies.add(new Walker(new Vector2 (5f, 9f)));
		System.out.println("Found " + spawners.size+" enemy spawners");
		Texture copterTex = new Texture("data/copter.png");
		copterTex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		movablePlatforms.add(new MovablePlatform(copterTex, new Vector2(0, 1), 4f, 2f, new Vector2(0,11), 5f, true, 1.5f));
		movablePlatforms.add(new MovablePlatform(copterTex, new Vector2(17, 10), 4f, 2f, new Vector2(20,8), 5f, true, 3.5f));
		movablePlatforms.add(new MovablePlatform(copterTex, new Vector2(25, 8), 4f, 2f, new Vector2(28,10), 4.5f, true, 3.5f));
		
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public Sword getSword() {
		return sword;
	}
	
	
	
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	public void update() {

		ship.update(ship);		
		//if (sword.inProgress()) sword.update(ship);
		sword.update(ship);

		/* MovablePlatform code */
		boolean onMovable = false;
		MovablePlatform onPlat = null;
		sRec = ship.getProjectionRect();
		//Check moving platform collisions
		for (MovablePlatform mp: movablePlatforms) {
			mp.update(ship);
			//System.out.println("sRec: "+sRec.x+","+sRec.y+","+sRec.width+","+sRec.height+" mp: "+mp.getCollisionRectangle().x+","+mp.getCollisionRectangle().y+","+mp.getCollisionRectangle().width+","+mp.getCollisionRectangle().height);
			if (sRec.overlaps(mp.getCollisionRectangle())) {
				//System.out.println("moving ship");
				ship.getPosition().add(mp.getPositionDelta());
				//ship.getVelocity().add(mp.getPositionDelta());
				//ship.getPosition().y -= Ship.GRAVITY * Gdx.graphics.getDeltaTime();
				
				//stop falling through the floor when going up if on the top of platform
				float top = mp.getPosition().y +mp.getCollisionRectangle().height;
				//System.out.println("****Poo*****. Top: "+top+" Ship ypos: "+ship.getPosition().y);
				//if (mp.getPositionDelta().y > 0 && ship.getPosition().y < top + 2/32f && ship.getPosition().y > top-2/32f) {
				if (ship.getPosition().y < top + 12/32f && ship.getPosition().y > top-12/32f) {
					//System.out.println("****Fixing position on platform*****. Top: "+top+" Ship ypos: "+ship.getPosition().y);
					ship.getPosition().y = mp.getPosition().y+mp.getCollisionRectangle().height;
					onMovable = true;
					onPlat= mp;
				}
			}
			//mp.update(ship);
		}
		




		/* Tile collisions code */
		//Check player to tile collisions
		//get tiles near player
		TiledMapTileLayer collisionLayer = levelLayout.getCollisionLayer();
		Array<Rectangle> tiles = new Array<Rectangle>();
		tiles.clear();
		int checkX, checkY;
		if (ship.getVelocity().x != 0) {
			checkX = 1; 
		} else {
			checkX=0;
		}
		if (ship.getVelocity().y != 0) {
			checkY=2;
		} else {
			checkY=0;
		}
		for (float i=ship.getPosition().x -checkX; i < ship.getPosition().x + ship.getWidth() +1+ checkX; i++) {
			for (float j=ship.getPosition().y - checkY; j < ship.getPosition().y + ship.getHeight() + checkY; j++) {
				//System.out.println("chcking cell at " + (int)i + "," + (int)j + " and collisionlayer is " + collisionLayer);
		
				Cell cell = collisionLayer.getCell((int) i, (int)j);
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
		/*sRec = ship.getProjectionRect();
		boolean tileUnderShip = false;
		for (Rectangle tile:tiles) {
			if (sRec.overlaps(tile)) {
				//System.out.println("TileCollision: " + sRec +" " + tile);
				//to right of player
				if (ship.getVelocity().x > 0) {
					
				}
				//to the bottom of player
				if (ship.getVelocity().y <0 ) {
					//System.out.println("Velocity: "+ship.getVelocity().y);
					//System.out.println("DownCollision: " + sRec +" " + tile);
					//ship.getPosition().y = tile.y + ship.getHeight();
					ship.getPosition().y = tile.y +tile.height;
					ship.getVelocity().y = 0;
					if (ship.getVelocity().x == 0) {
						ship.setState(State.IDLE);
					} else {
						ship.setState(State.WALK);
					}
					tileUnderShip = true;
				}
			}
		}*/
		//add the cutsceneobjects or movable platforms to the collisions
		for (MovablePlatform mvPlat: movablePlatforms) {
			tiles.add(mvPlat.getCollisionRectangle());
		}
		
		
		/*sRec = ship.getYProjectionRect();
		boolean tileUnderShip = false;
		for (Rectangle tile:tiles) {
			if (sRec.overlaps(tile)) {
			//if (ship.getBounds().overlaps(tile)) {
				
				//to the bottom of player
				if (ship.getVelocity().y <0 ) {
					//System.out.println("Velocity: "+ship.getVelocity().y);
					//System.out.println("DownCollision: " + sRec +" " + tile);
					//ship.getPosition().y = tile.y + ship.getHeight();
					//BUG: this collision is getting detected at the height of a jump against a wall when it shouldn't
					ship.getPosition().y = tile.y +tile.height;
					ship.getVelocity().y = 0;
					if (ship.getVelocity().x == 0) {
						ship.setState(State.IDLE);
						
					} else {
						ship.setState(State.WALK);
					}
					System.out.println("after y state="+ship.getState());
					tileUnderShip = true;
				}
			}
		}*/
		
		sRec = ship.getXProjectionRect();
		for (Rectangle tile: tiles) {
			if (sRec.overlaps(tile)) {
			//if (ship.getBounds().overlaps(tile)) {
				//ship.getVelocity().x = 0;
				//System.out.println("Get knocked back from "+ship.getPosition().x + " by " + ship.getVelocity().x);
				/*ship.getPosition().x -= ship.getVelocity().scl(Gdx.graphics.getDeltaTime()).x;
				ship.getVelocity().scl(1/Gdx.graphics.getDeltaTime());*/
				
				if (ship.getVelocity().x > 0) {
					ship.getPosition().x = tile.getX()-ship.getWidth();
						
				} else {
					ship.getPosition().x = tile.getX() + tile.getWidth();
				}
				
				if (ship.getVelocity().y < 0) {
				//if (tileUnderShip || ship.getVelocity().y < 0) {
					if (ship.getState() != State.WALL) sword.cancel(); //BUG: sword is getting cancelled while on ground against wall
					//inputHandler.resetWallTime();
					ship.setState(State.WALL);
					ship.resetWallTime();
					
				}
				//System.out.println("after x state="+ship.getState());
				
				break;
			}
		}
		
		sRec = ship.getYProjectionRect();
		boolean tileUnderShip = false;
		for (Rectangle tile:tiles) {
			if (sRec.overlaps(tile)) {
				
				//to the bottom of player
				if (ship.getVelocity().y < 0 ) {
					//System.out.println("Velocity: "+ship.getVelocity().y);
					//System.out.println("DownCollision: " + sRec +" " + tile);
					//ship.getPosition().y = tile.y + ship.getHeight();
					if (!onMovable) {
					ship.getPosition().y = tile.y +tile.height;
					ship.getVelocity().y = 0;
					}
					if (ship.getVelocity().x == 0) {
						ship.setState(State.IDLE);
					} else {
						ship.setState(State.WALK);
					}
					//System.out.println("after y state="+ship.getState());
					tileUnderShip = true;
				}
				if (ship.getVelocity().y > 0) {
					ship.getPosition().y = tile.y -Ship.HEIGHT;
					ship.getVelocity().y = 0;
				}
			}
		}
		
		if (!tileUnderShip && (ship.getState() == State.IDLE || ship.getState() == State.WALK)) {
			ship.setState(State.JUMP);
		}
		//System.out.println("after both state="+ship.getState());
		//ship.update(ship);
		//follower.update(ship);
		eItr = enemies.iterator();
		while(eItr.hasNext()) {
			e = eItr.next();
			if (!levelScenes.isPlaying())
			e.advance(Gdx.graphics.getDeltaTime(), ship);
			
			//SWORD COLLISIONS
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
		//System.out.println("End of World update " + ship.getVelocity().x);
		
		
		
		
		
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
						cutsceneObjects.add((CutsceneObject)ment);
					} else if (ment.getClass() == MovablePlatform.class) {
						movablePlatforms.add((MovablePlatform)ment);
					}
				}
			}
		}
	}
	
	public void addBullet(Bullet b) {
		bullets.add(b);
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
	
	public void setRenderer(WorldRenderer wr) {
		this.wr = wr;
	}
	
	public WorldRenderer getRenderer() {
		return wr;
	}
	
	public LevelLayout getLevelLayout() {
		return levelLayout;
	}
	public void dispose() {
		
	}
}
