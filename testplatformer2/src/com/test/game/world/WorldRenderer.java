package com.test.game.world;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.Array;
import com.test.game.model.Bullet;
import com.test.game.model.Enemy;
import com.test.game.model.Ship;
import com.test.game.model.Sword;



public class WorldRenderer {

	private static final float FOLLOWER_FRAME_DURATION = 0.06f;
	
	private static final float WORLD_WIDTH = 90f;
	private static final float WORLD_HEIGHT = 15f;
	
	World world;
	SpriteBatch batch;
	Ship ship;
	//Follower follower;
	OrthographicCamera cam;
	Texture shipTexture, followerTexture, bulletTexture;
	private TextureRegion followerFrame;
	private Animation followerAnimation; 
	float width, height;
	private Array<Bullet> bullets;
	private Array<Enemy> enemies;
	Iterator<Bullet> bItr;
	Iterator<Enemy> eItr;
	private Bullet b;
	private Enemy e;
	
	//attempting to use maps
	TiledMapRenderer tileMapRenderer;
	//OrthogonalTiledMapRenderer oRenderer;
	//TiledMap map;
	//TileAtlas mapAtlas;
	
	private static final int[] layersList = { 0 };
	
	
	//debug
	ShapeRenderer sr;
	TextureRegion testRegion;
	
	public WorldRenderer(World world) {
		this.world = world;
		
		//not good
		world.setRenderer(this);
		
		//make the map NOT SURE IF GOOD
		/*map = TiledLoader.createMap(Gdx.files.internal("data/level.tmx"));
		mapAtlas = new TileAtlas(map, Gdx.files.internal("data/level1"));
		
		tileMapRenderer = new TileMapRenderer(map, mapAtlas, 32, 32, 1, 1);*/
		tileMapRenderer = world.getLevelLayout().getRenderer();
		
		
		cam = new OrthographicCamera();
		
		//was using 100 here originally
		width = Gdx.graphics.getWidth()/100;
		height = Gdx.graphics.getHeight()/100;
		
		//this is important for different phone sizes. about cutting off stuff on smaller screens etc
		cam.setToOrtho(false, width, height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		
		
		shipTexture = new Texture("data/ship.png");
		shipTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		//followerTexture = new Texture("data/follower.png");
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/follower.txt"));
		/*TextureRegion[] followerFrames = new TextureRegion[3];
		for (int i=0; i<3;i++) {
			followerFrames[i] = atlas.findRegion("follower_"+(i+1));
		}*/
		Array<AtlasRegion> followerFrames = atlas.findRegions("follower");
		System.out.println("Found " + followerFrames.size + " follower frames");
		for (int i=0; i<followerFrames.size; i++) 
			followerFrames.get(i).getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		followerAnimation = new Animation(FOLLOWER_FRAME_DURATION, followerFrames);
		
		bulletTexture = new Texture("data/bullet.png");
		bulletTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		sr= new ShapeRenderer();
		//testRegion = followerFrames[0];
		
	}
	
	public void render() {
		ship = world.getShip();
		//follower = world.getFollower();
		enemies = world.getEnemies();
		bullets = world.getBullets();
		
		//System.out.println("Sxy: " + ship.getPosition().x+","+ship.getPosition().y+" Cwh: "+cam.viewportWidth+","+cam.viewportHeight);
		/*if(ship.getPosition().x > cam.viewportWidth/2 && ship.getPosition().y > cam.viewportHeight/2) {
			cam.position.set(ship.getPosition().x, ship.getPosition().y, 0);
		}*/
		if(ship.getPosition().x > cam.viewportWidth/2 ) {
			cam.position.x = ship.getPosition().x;
		}
		if(ship.getPosition().y > cam.viewportHeight/2 && ship.getPosition().y + cam.viewportHeight/2< WORLD_HEIGHT) {
			cam.position.y = ship.getPosition().y;
		}
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		//ANIMATIONS
		//followerFrame = followerAnimation.getKeyFrame(e.getStateTime(), true);
		/*System.out.println(followerAnimation);
		System.out.println(follower.getStateTime());
		System.out.println(followerAnimation.getKeyFrame(follower.getStateTime(), true));
		System.out.println(followerFrame);
		System.out.println(testRegion);*/
		
		//tileMapRenderer.render(cam);
		tileMapRenderer.setView(cam);
		tileMapRenderer.render(new int[]{0});
		/*Vector3 tmp = new Vector3();
		tmp.set(0, 0, 0);
		cam.unproject(tmp);
		tileMapRenderer.render((int) tmp.x, (int) tmp.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), layersList);
		*/
		
		batch.begin();
		//batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y);
		
		//0,0 origin will be offcenter
		if (ship.isFacingRight()) {
			batch.draw(shipTexture, ship.getPosition().x-ship.getWidth()/2, ship.getPosition().y, ship.getWidth() /2, ship.getHeight()/2,
					1f, 1f, 1, 1, ship.getRotation(), 0, 0, shipTexture.getWidth(),
					shipTexture.getHeight(), false, false);
		/*batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, ship.getWidth() /2, ship.getHeight()/2,
				ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 0, 0, shipTexture.getWidth(),
				shipTexture.getHeight(), false, false);*/
		} else {
			batch.draw(shipTexture, ship.getPosition().x-ship.getWidth()/2, ship.getPosition().y, ship.getWidth() /2, ship.getHeight()/2,
					1f, 1f, 1, 1, ship.getRotation(), 0, 0, shipTexture.getWidth(),
					shipTexture.getHeight(), true, false);
		}
		
		
		eItr = enemies.iterator();
		while (eItr.hasNext()) {
			e = eItr.next();
			followerFrame = followerAnimation.getKeyFrame(e.getStateTime(), true);
			batch.draw(followerFrame, e.getPosition().x, e.getPosition().y, e.getWidth()/2,
					e.getHeight()/2, e.getWidth(), e.getHeight(), 1, 1, 0);
			
		}
		bItr = bullets.iterator();
		while(bItr.hasNext()) {
			b = bItr.next();
			batch.draw(bulletTexture, b.getPosition().x, b.getPosition().y, b.getWidth() /2, b.getHeight()/2,
					b.getWidth(), b.getHeight(), 1, 1, b.getRotation(), 0, 0, bulletTexture.getWidth(),
					bulletTexture.getHeight(), false, false);
		}
		
		
		
		batch.end();
		
		
		//Debug stuff. Will slow game down!!
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Line);
		sr.setColor(Color.CYAN);
		sr.rect(ship.getBounds().x, ship.getBounds().y, ship.getBounds().width, ship.getBounds().height);
		sr.setColor(Color.RED);
		eItr = enemies.iterator();
		while (eItr.hasNext()) {
			e = eItr.next();
			sr.rect(e.getBounds().x, e.getBounds().y, e.getBounds().width, e.getBounds().height);
			
		}
		bItr = bullets.iterator();
		while(bItr.hasNext()) {
			b = bItr.next();
			sr.rect(b.getBounds().x, b.getBounds().y, b.getBounds().width, b.getBounds().height);
		}
		sr.rect(world.sRec.x, world.sRec.y, world.sRec.width, world.sRec.height);
		sr.setColor(Color.YELLOW);
		Sword sword = world.getSword();
		sr.rect(sword.getBounds().x, sword.getBounds().y, sword.getBounds().width, sword.getBounds().height);
		sr.end();
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	public void dispose() {
		batch.dispose();
		shipTexture.dispose();
		sr.dispose();
	}
}
