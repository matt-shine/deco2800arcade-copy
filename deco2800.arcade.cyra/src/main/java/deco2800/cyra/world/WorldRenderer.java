package deco2800.cyra.world;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
//import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.Array;
import deco2800.cyra.model.*;

/**World Renderer takes the object from the World class and draws them to the screen
 * 
 * @author Game Over
 *
 */
public class WorldRenderer {

	private static final float FOLLOWER_FRAME_DURATION = 0.06f;
	
	
	
	World world;
	SpriteBatch batch;
	SpriteBatch textBatch;
	Ship ship;
	//Follower follower;
	Walker walker;
	private ParallaxCamera cam;
	private BitmapFont font;
	private Texture shipTexture, followerTexture, bulletTexture, walkerTexture, example, bg, heartsTexture;
	private TextureRegion followerFrame;
	private TextureRegion walkerRegion;
	private TextureAtlas groundTextureAtlas, laserTextures, explosionTextures;
	private Array<AtlasRegion> walkerRegions;
	private Animation followerAnimation; 
	float width, height;
	private Array<Bullet> bullets;
	private Array<Enemy> enemies;
	Iterator<Bullet> bItr;
	Iterator<Enemy> eItr;
	private Bullet b;
	private Enemy e;
	private Array<CutsceneObject> csObjects;
	private Array<MovablePlatform> mvPlatforms;
	private Array<BlockMaker> blockMakers;
	private float rotationArms;
	
	//attempting to use maps
	TileMapRenderer tileMapRenderer;
	//OrthogonalTiledMapRenderer oRenderer;
	//TiledMap map;
	//TileAtlas mapAtlas;
	
	private boolean blinkShip;
	
	private static final int[] layersList = { 0 };
	
	
	//debug
	private boolean debugMode = true;
	ShapeRenderer sr;
	TextureRegion testRegion;
	private FPSLogger fpsLogger;
	
	public WorldRenderer(World world, ParallaxCamera cam) {
		this.world = world;
		this.cam = cam;
		cam.update();
		
		
		//not good
		//world.setRenderer(this);
		
		//make the map NOT SURE IF GOOD
		/*map = TiledLoader.createMap(Gdx.files.internal("data/level.tmx"));
		mapAtlas = new TileAtlas(map, Gdx.files.internal("data/level1"));
		
		tileMapRenderer = new TileMapRenderer(map, mapAtlas, 32, 32, 1, 1);*/
		
		
		
		
		
		//was using number of tiles to show each direction
		
		/*width = 24;
		height = 15;*/
		
		//this is important for different phone sizes. about cutting off stuff on smaller screens etc
		//cam.setToOrtho(false, width, height);
		
		
		init();
	}
	
	public void render() {
		ship = world.getShip();
		//follower = world.getFollower();
		enemies = world.getEnemies();
		bullets = world.getBullets();
		csObjects = world.getCutsceneObjects();
		mvPlatforms = world.getMovablePlatforms();
		blockMakers = world.getBlockMakers();
		
		//System.out.println("Sxy: " + ship.getPosition().x+","+ship.getPosition().y+" Cwh: "+cam.viewportWidth+","+cam.viewportHeight);
		/*if(ship.getPosition().x > cam.viewportWidth/2 && ship.getPosition().y > cam.viewportHeight/2) {
			cam.position.set(ship.getPosition().x, ship.getPosition().y, 0);
		}*/
		//cam.position.x = ship.getPosition().x;
		//cam.position.y = ship.getPosition().y;
		
		/* Camera code needs to be fixed later on. Currently it's set to always
		 * follow the sprite so that camera's updated properly when reset*/
		
		
		
		cam.update();
		
		//batch.setProjectionMatrix(cam.combined);
		
		//ANIMATIONS
		//followerFrame = followerAnimation.getKeyFrame(e.getStateTime(), true);
		/*System.out.println(followerAnimation);
		System.out.println(follower.getStateTime());
		System.out.println(followerAnimation.getKeyFrame(follower.getStateTime(), true));
		System.out.println(followerFrame);
		System.out.println(testRegion);*/
		
		//tileMapRenderer.render(cam);
		
		drawLevel(batch);
		drawGameObjects(batch);
		drawHUD(batch);
		if(debugMode) drawDebugAids();
		
		return;
	}
	
	private void drawShip() {
		if (ship.isFacingRight()) {
			batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y+ship.getHeight()/2, ship.getWidth() /2, ship.getHeight()/2,
					1f, 1f, 2, 2, ship.getRotation(), 0, 0, shipTexture.getWidth(),
					shipTexture.getHeight(), false, false);
		/*batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, ship.getWidth() /2, ship.getHeight()/2,
				ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 0, 0, shipTexture.getWidth(),
				shipTexture.getHeight(), false, false);*/
		} else {
			batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y+ship.getHeight()/2, ship.getWidth() /2, ship.getHeight()/2,
					1f, 1f, 2, 2, ship.getRotation(), 0, 0, shipTexture.getWidth(),
					shipTexture.getHeight(), true, false);
		}
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	private void drawLevel(SpriteBatch batch) {
		/* Draw background layers */
		batch.setProjectionMatrix(cam.calculateParallaxMatrix(0, 0));
		batch.begin();
		batch.draw(bg, -cam.viewportWidth/2, -cam.viewportHeight/2, cam.viewportWidth, cam.viewportHeight);
		batch.end();
		
		/* Draw tiled layers */
		/*tileMapRenderer.setView(cam.calculateParallaxMatrix(0.25f, 1), 0, 0, World.WORLD_WIDTH, World.WORLD_HEIGHT);
		
		tileMapRenderer.render(new int[]{0});
		tileMapRenderer.setView(cam.calculateParallaxMatrix(0.5f, 1), 0, 0, World.WORLD_WIDTH, World.WORLD_HEIGHT);
		tileMapRenderer.render(new int[]{1});
		tileMapRenderer.setView(cam.calculateParallaxMatrix(1, 1), 0, 0, World.WORLD_WIDTH, World.WORLD_HEIGHT);
		tileMapRenderer.render(new int[]{2});*/
		
		//tileMapRenderer.getProjectionMatrix().set(cam.combined);
		//tileMapRenderer.render(cam, new int[]{0, 1, 2});
		//tileMapRenderer.render(cam.position.x-cam.viewportWidth/2, cam.position.y-cam.viewportHeight/2,
			//	cam.viewportWidth, cam.viewportHeight);
		//tileMapRenderer.render(cam.position.x, cam.position.y, cam.viewportWidth*2, cam.viewportHeight*2);
		tileMapRenderer.getProjectionMatrix().set(cam.calculateParallaxMatrix(0.25f, 1));
		tileMapRenderer.render(cam, new int[]{0});
		tileMapRenderer.getProjectionMatrix().set(cam.calculateParallaxMatrix(0.5f, 1));
		tileMapRenderer.render(cam, new int[]{1});
		//tileMapRenderer.render(cam.);
		tileMapRenderer.getProjectionMatrix().set(cam.calculateParallaxMatrix(1, 1));
		//tileMapRenderer.getProjectionMatrix().set(cam.combined);
		
		/*Vector3 tmp = new Vector3();
		tmp.set(0,0,0);
		cam.unproject(tmp);
		tileMapRenderer.render((int) tmp.x, (int) tmp.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());*/
		tileMapRenderer.render(cam, new int[]{3}); // THIS IS ONLY UNTIL layer 2's graphics are complete
		tileMapRenderer.render(cam, new int[]{2});
		
		
		//tileMapRenderer.render((int) cam.position.x-cam.viewportWidth/2, (int) cam.position.y-cam.viewportHeight/2, 999, 999,
			//	new int[]{2});
		
		
		
		return;
	}
	
	private void drawGameObjects(SpriteBatch batch) {
		/* Draw game objects */
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		//batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y);
		
		//0,0 origin will be offcenter
		
		/* Draw BlockMaker Blocks */
		for (BlockMaker bm: blockMakers) {
			Array<Block> blocks = bm.getBlocks();
			for (Block b: blocks) {
				TextureAtlas at;
				if (b.getAtlas() == Block.TextureAtlasReference.LEVEL) {
					at = groundTextureAtlas;
				} else {
					at = groundTextureAtlas;
				}
				TextureRegion tr = at.findRegion("level", b.getAtlasIndex());
				//batch.draw(tr, b.getPosition().x, b.getPosition().y, 1f, 1f);
				
				batch.draw(tr, b.getPosition().x, b.getPosition().y, 0.5f, 0.5f, 1f, 1f, 1f, 1f, b.getDrawRotation());
			}
		}
		
		/* Draw Moveable Platform */
		for (MovablePlatform mvPlat: mvPlatforms) {
			if (mvPlat.getTexture() != null) {
				batch.draw(mvPlat.getTexture(), mvPlat.getPosition().x, mvPlat.getPosition().y, mvPlat.getWidth() /2, mvPlat.getHeight()/2,
						mvPlat.getWidth(), mvPlat.getHeight(), 1, 1, mvPlat.getRotation(), 0, 0, mvPlat.getTexture().getWidth(),
						mvPlat.getTexture().getHeight(), false, false);
			}
		}
		
		/* Draw ship */
		if(ship.isInvincible()) {
			if(blinkShip) {
				drawShip();
				blinkShip = !blinkShip;
			} else {
				blinkShip = !blinkShip;
			}
		} else {
			drawShip();
		}
		
		eItr = enemies.iterator();
		while (eItr.hasNext()) {
			e = eItr.next();
			if (e.getClass() == Follower.class) {
				followerFrame = followerAnimation.getKeyFrame(e.getStateTime(), true);
				batch.draw(followerFrame, e.getPosition().x, e.getPosition().y, e.getWidth()/2,
						e.getHeight()/2, e.getWidth(), e.getHeight(), 1, 1, 0);
			} else if (e.getClass() == SoldierEnemy.class || (e.getClass() == EnemySpiderBossPopcorn.class)){
				batch.draw(shipTexture, e.getPosition().x, e.getPosition().y, e.getWidth() /2, e.getHeight()/2,
						e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, shipTexture.getWidth(),
						shipTexture.getHeight(), false, false);
			} else if (e.getClass() == Zombie.class){
				batch.draw(shipTexture, e.getPosition().x, e.getPosition().y, e.getWidth() /2, e.getHeight()/2,
						e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, shipTexture.getWidth(),
						shipTexture.getHeight(), false, false);
			} else if (e.getClass() == EnemySpiderBoss.class) {
				batch.draw(example, e.getPosition().x, e.getPosition().y, 0,
						0, e.getWidth(), e.getHeight(), 1, 1, 0, 0, 0,
						256, 256, true, false);
				EnemySpiderBossArms arms = ((EnemySpiderBoss)e).getArms();
				if (arms.isAttacking()) { 
					rotationArms+= 10;
				} else {
					rotationArms = 0;
				}
				batch.draw(shipTexture, arms.getPosition().x, arms.getPosition().y, arms.getWidth()/2, arms.getHeight()/2, 
						arms.getHeight(), arms.getHeight(), 1, 1, rotationArms, 0, 0, shipTexture.getWidth(),
						shipTexture.getHeight(), false, false);
				
				if (((EnemySpiderBoss)e).getState() == EnemySpiderBoss.State.LASER) {
					Array<Rectangle> charges = ((EnemySpiderBoss)e).getLaserChargePositions();
					TextureRegion region = laserTextures.findRegion("laser_charge");
					for (Rectangle r: charges) {
						
						batch.draw(region, r.x, r.y, r.width/2, r.height/2); 
					}
				}
			} else if (e.getClass() == Explosion.class) {
				int frame = ((Explosion)e).getFrame();
				if (frame >= 0 && frame <= 5) {
					TextureRegion region = explosionTextures.findRegion("explosion", frame);
					batch.draw(region, e.getPosition().x, e.getPosition().y, e.getWidth()/2, e.getHeight()/2, e.getWidth(), e.getHeight(),
							1f,1f, e.getRotation());
				}
				
			} else if (e.getClass() == BulletSimple.class || e.getClass() == BulletHomingDestructible.class) {
				Texture bulletTex;
				if (((BulletSimple)e).getGraphic() == BulletSimple.Graphic.FIRE) {
					bulletTex = bulletTexture;
				} else {
					bulletTex = bulletTexture;
				}
				batch.draw(bulletTex, e.getPosition().x, e.getPosition().y, e.getWidth() /2, e.getHeight()/2,
						e.getWidth(), e.getHeight(), 4, 4, e.getRotation(), 0, 0, bulletTexture.getWidth(),
						bulletTexture.getHeight(), false, false);
			} else if (e.getClass() == LaserBeam.class) {
				TextureRegion region = laserTextures.findRegion("laser");
				LaserBeam las = (LaserBeam)e;
				batch.draw(region, las.getOriginPosition().x-las.getMaxWidth()/2, las.getOriginPosition().y, las.getMaxWidth()/2, 0, las.getCurrentWidth(), 
						LaserBeam.DEFAULT_LENGTH, 1f,1f, e.getRotation()-90f);
			} else if (e.getClass() == Walker.class){
				//draw the parts in order
				int i=7; 
				while (i <8) {
					MovableEntity mve = ((Walker)e).getPart(i);
					
					AtlasRegion ar = walkerRegions.get(i);
					Texture tx = ar.getTexture();
					tx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
					batch.draw(tx, mve.getPosition().x, mve.getPosition().y, 0,
							ar.getRegionHeight()/64f, ar.getRegionWidth()/64f, ar.getRegionHeight()/64f, 1, 1, mve.getRotation(), ar.getRegionX(), ar.getRegionY(),
							ar.getRegionWidth(), ar.getRegionHeight(), false, false);
					//batch.draw
					switch(i) {
						case 7:
							i =3;
							break;
						case 3:
							i=6;
							break;
						case 6:
							i=5;
							break;
						case 5:
							i=2;
							break;
						case 2:
							i=1;
							break;
						case 1:
							i=4;
							break;
						case 4:
							i=0;
							break;
						case 0:
						default:
							i=8;
							break;
					}
				}
			}
		}
		bItr = bullets.iterator();
		while(bItr.hasNext()) {
			b = bItr.next();
			batch.draw(bulletTexture, b.getPosition().x, b.getPosition().y, b.getWidth() /2, b.getHeight()/2,
					b.getWidth(), b.getHeight(), 1, 1, b.getRotation(), 0, 0, bulletTexture.getWidth(),
					bulletTexture.getHeight(), false, false);
		}
		
		for (CutsceneObject csObj: csObjects) {
			System.out.println("Texture: "+csObj.getTexture());
			//batch.draw(csObj.getTexture(), csObj.getPosition().x, csObj.getPosition().y);
			batch.draw(csObj.getTexture(), csObj.getPosition().x, csObj.getPosition().y, csObj.getWidth() /2, csObj.getHeight()/2,
					csObj.getWidth(),csObj.getHeight(), 1, 1, csObj.getRotation(), 0, 0, csObj.getTexture().getWidth(),
					csObj.getTexture().getHeight(), false, false);
		}
		
		
		

		batch.end();
		return;
	}
	
	private void drawHUD(SpriteBatch batch) {
		batch.begin();
		
		float heartX = cam.position.x - 13;
		float heartY = cam.position.y + 6.5f;
		
		for(int i = 0; i<ship.getHearts(); i++) {
			heartX += 0.5f;
			batch.draw(heartsTexture,		//good
					heartX, heartY,			//good
					0, 0,				//doesn't matter
					1f, 1f,			//not sure
					1, 1,			//not sure
					0, 						//good
					0, 0,			//not sure
					heartsTexture.getWidth(), heartsTexture.getHeight(), //good
					false, false); 			//good
		}

		batch.end();
		
		
		
		
		textBatch.begin();
		
		CharSequence str = "Time: " + (int)world.getTime(); //(int)ship.getPosition().x;
		int strXPos = Gdx.graphics.getWidth() - 223; //Offset from right
		int strYPos = Gdx.graphics.getHeight() - 4; //Offset from top
		font.draw(textBatch, str, strXPos, strYPos);
		
		textBatch.end();
		
		//If game is paused render pause overlay
		if (world.isPaused()) {
			
		}
	}
	
	private void drawDebugAids() {
		/* ----- Debug stuff. Will slow game down!! ----- */
		sr.setProjectionMatrix(cam.combined);
		//sr.begin(ShapeType.Line);
		sr.begin(ShapeType.Rectangle);
		
		sr.setColor(Color.CYAN);
		sr.rect(ship.getBounds().x, ship.getBounds().y, ship.getBounds().width, ship.getBounds().height);
		sr.end();
		
		eItr = enemies.iterator();
		while (eItr.hasNext()) {
			e = eItr.next();
			sr.begin(ShapeType.Rectangle);
			sr.setColor(Color.RED);
			sr.rect(e.getBounds().x, e.getBounds().y, e.getBounds().width, e.getBounds().height);
			sr.setColor(Color.YELLOW);
			for (Rectangle r: e.getPlayerDamageBounds()) {
				sr.rect(r.x, r.y, r.width, r.height);
			}
			if (e.getClass() == Walker.class) {
				for (int i=0; i<8; i++) {
					WalkerPart wp = ((Walker)e).getPart(i);
					//System.out.println("Id: " + wp.getId() + ", (x,y): " + wp.getPosition().x + ","+wp.getPosition().y+")");
					//System.out.println("Bounds: "+wp.getBounds().x+","+wp.getBounds().y+","+wp.getBounds().width+","+wp.getBounds().height);
					
					sr.rect(wp.getBounds().x, wp.getBounds().y, wp.getBounds().width, wp.getBounds().height);
					
					
				}
			} 
			sr.end();
			sr.begin(ShapeType.Line);
			if (e.getClass() == LaserBeam.class) {
				Polygon p = ((LaserBeam)e).getLaserBounds();
				if (p != null) {
					float[] vertices = p.getTransformedVertices();
					for (int i = 0; i < vertices.length-3; i+=2) {
						sr.line(vertices[i], vertices[i+1], vertices[i+2], vertices[i+3]);
					//sr.
					}
				}
			}
			sr.end();
			
		}
		sr.begin(ShapeType.Rectangle);
		for (CutsceneObject csObj: csObjects) {
			sr.rect(csObj.getPosition().x, csObj.getPosition().y, csObj.getWidth(), csObj.getHeight());
		}
		bItr = bullets.iterator();
		while(bItr.hasNext()) {
			b = bItr.next();
			sr.rect(b.getBounds().x, b.getBounds().y, b.getBounds().width, b.getBounds().height);
		}
		sr.rect(world.sRec.x, world.sRec.y, world.sRec.width, world.sRec.height);
		
		
		Iterator<MovablePlatform> mvPlatItr;
		mvPlatItr = mvPlatforms.iterator();
		sr.setColor(Color.GREEN);
		while(mvPlatItr.hasNext()) {
			MovablePlatform mvPlat = mvPlatItr.next();
			sr.rect(mvPlat.getBounds().x, mvPlat.getBounds().y, mvPlat.getBounds().width, mvPlat.getBounds().height);
		}
		
		Sword sword = world.getSword();
		sr.setColor(Color.YELLOW);
		sr.rect(sword.getBounds().x, sword.getBounds().y, sword.getBounds().width, sword.getBounds().height);
		sr.setColor(Color.WHITE);
		
		sr.end();
		fpsLogger.log();
		return;
	}
	
	private void init() {
		csObjects = new Array<CutsceneObject>();

		textBatch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("data/font/fredericka_the_great/fredericka_the_great.fnt"), false);
		
		
		sr = new ShapeRenderer();
		tileMapRenderer = world.getLevel().getRenderer();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		if (debugMode) fpsLogger = new FPSLogger();
		
		loadTextures();
		return;
	}
	
	private void loadTextures() {
		AssetManager manager = new AssetManager();
		TextureParameter linearFilteringParam = new TextureParameter();
		linearFilteringParam.minFilter = TextureFilter.Linear;
		manager.load("data/enemysprite1-small.png", Texture.class, linearFilteringParam);
		manager.load("data/bg2.png", Texture.class, linearFilteringParam);
		manager.load("data/ship.png", Texture.class, linearFilteringParam);
		manager.load("data/heart.png", Texture.class, linearFilteringParam);
		manager.load("data/projectiles/lightningball.png", Texture.class, linearFilteringParam);
		manager.load("data/projectiles/lasers.txt", TextureAtlas.class);
		manager.load("data/projectiles/explosion.txt", TextureAtlas.class);
		manager.load("data/follower.txt", TextureAtlas.class);
		manager.load("data/modular3.txt", TextureAtlas.class);
		manager.load("data/level packfile", TextureAtlas.class);
		manager.finishLoading();
		
		example = manager.get("data/enemysprite1-small.png", Texture.class);
		example.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		rotationArms = 0f;
		
		bg = manager.get("data/bg2.png", Texture.class);
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		shipTexture = manager.get("data/ship.png", Texture.class);
		shipTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		heartsTexture = manager.get("data/heart.png", Texture.class);
		heartsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bulletTexture = manager.get("data/projectiles/lightningball.png");
		bulletTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		laserTextures = manager.get("data/projectiles/lasers.txt");
		explosionTextures = manager.get("data/projectiles/explosion.txt");
		//laserTextures.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
			/* Load follower texture */
		//followerTexture = new Texture("data/follower.png");
		TextureAtlas atlas = manager.get("data/follower.txt", TextureAtlas.class);
		/*TextureRegion[] followerFrames = new TextureRegion[3];
		for (int i=0; i<3;i++) {
			followerFrames[i] = atlas.findRegion("follower_"+(i+1));
		}*/
		Array<AtlasRegion> followerFrames = atlas.findRegions("follower");
		System.out.println("Found " + followerFrames.size + " follower frames");
		for (int i=0; i<followerFrames.size; i++) 
			followerFrames.get(i).getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		followerAnimation = new Animation(FOLLOWER_FRAME_DURATION, followerFrames);
			/* Load follower texture - END*/
		
			/* Load walker texture */
		//walkerTexture = new Texture("data/walker.png");
		TextureAtlas walkerAtlas = manager.get("data/modular3.txt", TextureAtlas.class);
		walkerRegions = walkerAtlas.findRegions("a");
				
		for (int i=0; i<walkerRegions.size; i++) {
			walkerRegions.get(i).getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
					
		}
		System.out.println("Found " + walkerRegions.size + " walker parts");
		
		groundTextureAtlas = manager.get("data/level packfile", TextureAtlas.class);
			/* Load walker texture - END */
		return;
		
		
		
		
		
		
	}
	
	public void dispose() {
		batch.dispose();
		shipTexture.dispose();
		sr.dispose();
	}
}
