package com.test.game.world;



import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.TestGame2;
import com.test.game.model.Bullet;
import com.test.game.model.Enemy;
import com.test.game.model.EnemySpawner;
import com.test.game.model.Follower;
import com.test.game.model.Ship;
import com.test.game.model.Ship.State;
import com.test.game.model.Sword;

public class World {

	Ship ship;
	Sword sword;
	Follower follower;
	Array<Enemy> enemies = new Array<Enemy>();
	Array<Bullet> bullets = new Array<Bullet>();
	Array<EnemySpawner> spawners = new Array<EnemySpawner>();
	Iterator<Bullet> bItr;
	Iterator<Enemy> eItr;
	Iterator<EnemySpawner> sItr;
	Enemy e;
	Bullet b;
	EnemySpawner s;
	
	public Rectangle sRec;
	
	private LevelLayout levelLayout;
	//private LevelObjects levelObjects;
	//private InputHandler inputHandler;
	
	//He says this creates circular logic and hence is very bad. It's only really to get touchDown to access camera
	// if not using mouse then remove this
	WorldRenderer wr;
	
	public World(TestGame2 game, int level) {
		//ship = new Ship(new Vector2(3, 3), 1, 1, 0, 6);
		ship = new Ship(new Vector2(2.8f,16));
		//enemies.add(new Follower(5f, 0, new Vector2(1,1), 1, 1));
		sword = new Sword(new Vector2(-1, -1));
		
		//inputHandler = new InputHandler(this);
		Gdx.input.setInputProcessor(new InputHandler(this));
		
		levelLayout = new LevelLayout(level);
		
		Array<Object> objects = new Array<Object>();
		if (level ==1) {
			objects = Level1Objects.loadObjects(levelLayout.getMap());
			
		}
		for (Object o: objects) {
			if (o instanceof EnemySpawner) {
				spawners.add((EnemySpawner) o);
			}
		}
		System.out.println("Found " + spawners.size+" enemy spawners");
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
		//follower.update(ship);
		
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
		
		sRec = ship.getXProjectionRect();
		for (Rectangle tile: tiles) {
			if (sRec.overlaps(tile)) {
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
					if (ship.getState() != State.WALL) sword.cancel();
					//inputHandler.resetWallTime();
					ship.setState(State.WALL);
					ship.resetWallTime();
					
				}
				
				break;
			}
		}
		
		sRec = ship.getYProjectionRect();
		boolean tileUnderShip = false;
		for (Rectangle tile:tiles) {
			if (sRec.overlaps(tile)) {
				
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
		}
		
		
		if (!tileUnderShip && (ship.getState() == State.IDLE || ship.getState() == State.WALK)) {
			ship.setState(State.JUMP);
		}
		
		eItr = enemies.iterator();
		while(eItr.hasNext()) {
			e = eItr.next();
			e.advance(Gdx.graphics.getDeltaTime(), ship);
			
			//SWORD COLLISIONS
			if (e.getBounds().overlaps(sword.getBounds())) {
				System.out.println("C");
				eItr.remove();
				System.out.println("removed enemy");
				for (EnemySpawner spns: spawners) {
					spns.removeEnemy(e);
				}
				System.out.println("cleaned arrays");
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
					System.out.println("C");
					eItr.remove();
					System.out.println("removed enemy");
					bItr.remove();
					System.out.println("removed bullet");
					for (EnemySpawner spns: spawners) {
						spns.removeEnemy(e);
					}
					System.out.println("cleaned arrays");
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
		
	}
	
	public void addBullet(Bullet b) {
		bullets.add(b);
	}
	public Array<Bullet> getBullets() {
		return bullets;
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
