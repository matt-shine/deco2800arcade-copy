package deco2800.arcade.cyra.world;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
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
import com.badlogic.gdx.math.MathUtils;
//import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.Array;
import deco2800.arcade.cyra.model.*;
import deco2800.arcade.cyra.model.SoldierBoss.State;

/** WorldRenderer describes the View component of the game's MVC model.
 * This class draws the game based on the game's state described in World
 * class. It will draw specific region of the level depending on the
 * position of camera.
 * 
 * @author Game Over
 */
public class WorldRenderer {

	private static final float FOLLOWER_FRAME_DURATION = 0.06f;
	private static final float SCENE_BAR_MAX_HEIGHT = 1.5f;
	
	
	
	World world;
	SpriteBatch batch;
	SpriteBatch textBatch;
	Player ship;
	//Follower follower;
	Walker walker;
	private ParallaxCamera cam;
	Integer rightCyraCount, leftCyraCount, rightFrameCounter, leftFrameCounter;
	private BitmapFont font, fontBig;
	private Texture shipTexture, followerTexture, bulletTexture, walkerTexture, bg, heartsTexture, 
	jumperBodyTexture, jumperFrontArmTexture, jumperFrontLegTexture, jumperFrontArmJumpingTexture,jumperFrontLegJumpingTexture;
	private TextureRegion followerFrame, cyraFrame;
	private TextureRegion walkerRegion;
	private TextureAtlas groundTextureAtlas, laserTextures, explosionTextures, boss1Atlas, bossRam, firestarter, myEnemy;
	private TextureRegion boss1body, boss1head0, boss1head1, boss1arms;
	private Array<AtlasRegion> walkerRegions;
	private Animation followerAnimation, cyraRightAnimation, cyraLeftAnimation; 
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
	//private float rotationArms;
	private float sceneBarHeight;
	private boolean enemyWithHealthOnScreen = false;
	private float healthPercentage;
	private String healthName;
	private float redScreenAlpha;
	
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
		rightCyraCount = 0;
		leftCyraCount = 0;
		rightFrameCounter = 0;
		redScreenAlpha = 0;
		cam.update();
		
		
		//not good
		//world.setRenderer(this);
		
		//make the map NOT SURE IF GOOD
		/*map = TiledLoader.createMap(Gdx.files.internal("level.tmx"));
		mapAtlas = new TileAtlas(map, Gdx.files.internal("level1"));
		
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
		if (ship.isOverForeground()) {
			//tileMapRenderer.render(cam, new int[]{3,4,5}); // Don't know how to make this transparent
			
		}
		drawHUD(batch);
		if(debugMode) drawDebugAids();
		
		return;
	}
	
	private void drawShip() {
		
		
		if (ship.isFacingRight()) {
			leftCyraCount = 0;
			leftFrameCounter = 0;
			rightFrameCounter = (rightFrameCounter+1) %8;
			if((rightFrameCounter == 0) && (ship.isWalking() == true)){
				cyraFrame = cyraRightAnimation.getKeyFrame(rightCyraCount, true);
					rightCyraCount = (rightCyraCount+1) % 5;
					System.out.println("Cyra Frame count is " + rightCyraCount);
			}
			
			cyraFrame = cyraRightAnimation.getKeyFrame(rightCyraCount+1, true);
			batch.draw(cyraFrame, ship.getPosition().x, ship.getPosition().y, ship.getWidth()/2,
					ship.getHeight()/2, ship.getWidth(), ship.getHeight(), 1.8f, 1f, ship.getRotation());
			
			/*batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y+ship.getHeight()/2, ship.getWidth() /2, ship.getHeight()/2,
					1f, 1f, 2, 2, ship.getRotation(), 0, 0, shipTexture.getWidth(),
					shipTexture.getHeight(), false, false);*/
		/*batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, ship.getWidth() /2, ship.getHeight()/2,
				ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 0, 0, shipTexture.getWidth(),
				shipTexture.getHeight(), false, false);*/
		} else {
			/*rightFrameCounter = 0;
			rightCyraCount = 0;
			System.out.println("woop derit is");
			leftFrameCounter = (leftFrameCounter+1) %10;
			//cyraFrame = cyraAnimation.getKeyFrame(e.getStateTime(), true);
			/*batch.draw(cyraFrame, ship.getPosition().x, ship.getPosition().y, ship.getWidth()/2,
					ship.getHeight()/2, ship.getWidth(), ship.getHeight(), 1, 1, 0);
			if((leftFrameCounter == 0) && (ship.isWalking() == true)){
				cyraFrame = cyraLeftAnimation.getKeyFrame(leftCyraCount, true);
					leftCyraCount = (leftCyraCount+1) % 5;
					System.out.println("Cyra Frame count is " + leftCyraCount+1);
					System.out.println("woop derit is");
			}
			System.out.println("woop derit is");
			cyraFrame = cyraLeftAnimation.getKeyFrame(leftCyraCount+1, true);
			batch.draw(cyraFrame, ship.getPosition().x, ship.getPosition().y, ship.getWidth()/2,
					ship.getHeight()/2, ship.getWidth(), ship.getHeight(), 1.5f, 1f, 0);
			/*batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y+ship.getHeight()/2, ship.getWidth() /2, ship.getHeight()/2,
					1f, 1f, 2, 2, ship.getRotation(), 0, 0, shipTexture.getWidth(),
					shipTexture.getHeight(), true, false);*/
			leftCyraCount = 0;
			leftFrameCounter = 0;
			rightFrameCounter = (rightFrameCounter+1) %8;
			if((rightFrameCounter == 0) && (ship.isWalking() == true)){
				cyraFrame = cyraRightAnimation.getKeyFrame(rightCyraCount, true);
					rightCyraCount = (rightCyraCount+1) % 5;
					System.out.println("Cyra Frame count is " + rightCyraCount);
			}
			
			cyraFrame = cyraRightAnimation.getKeyFrame(rightCyraCount+1, true);
			batch.draw(cyraFrame, ship.getPosition().x, ship.getPosition().y, ship.getWidth()/2,
					ship.getHeight()/2, ship.getWidth(), ship.getHeight(), 1.8f, 1f, ship.getRotation());
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
		/*tileMapRenderer.getProjectionMatrix().set(cam.calculateParallaxMatrix(0.25f, 1));
		tileMapRenderer.render(cam, new int[]{0});
		tileMapRenderer.getProjectionMatrix().set(cam.calculateParallaxMatrix(0.5f, 1));
		tileMapRenderer.render(cam, new int[]{1});*/
		//tileMapRenderer.render(cam.);
		tileMapRenderer.getProjectionMatrix().set(cam.calculateParallaxMatrix(1, 1));
		//tileMapRenderer.getProjectionMatrix().set(cam.combined);
		
		/*Vector3 tmp = new Vector3();
		tmp.set(0,0,0);
		cam.unproject(tmp);
		tileMapRenderer.render((int) tmp.x, (int) tmp.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());*/
		tileMapRenderer.render(cam, new int[]{3}); // THIS IS ONLY UNTIL layer 2's graphics are complete
		tileMapRenderer.render(cam, new int[]{2});
		tileMapRenderer.render(cam, new int[]{0,1,2});
		if (!ship.isOverForeground()) {
			tileMapRenderer.render(cam, new int[]{3,4,5});
		}
		
		
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
		
		if (ship.getState() == Player.State.DEATH) {
			
			redScreenAlpha += Gdx.graphics.getDeltaTime() / 3;
			if (redScreenAlpha > 1.0f) {
				redScreenAlpha = 1.0f;
			}
			batch.end();
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.FilledRectangle);
			
			sr.setColor(new Color(255f, 0f, 0f,redScreenAlpha));
			sr.filledRect(cam.position.x-cam.viewportWidth/2, cam.position.y-cam.viewportHeight/2, cam.viewportWidth, cam.viewportHeight);
			
			sr.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			
			batch.begin();
		} else {
			redScreenAlpha = 0f;
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
		
		enemyWithHealthOnScreen = false;
		eItr = enemies.iterator();
		while (eItr.hasNext()) {
			e = eItr.next();
			if (e.getClass() == Follower.class) {
				followerFrame = followerAnimation.getKeyFrame(e.getStateTime(), true);
				batch.draw(followerFrame, e.getPosition().x, e.getPosition().y, e.getWidth()/2,
						e.getHeight()/2, e.getWidth(), e.getHeight(), 1, 1, 0);
			} else if (e.getClass() == SoldierEnemy.class){
				
				if(e.isJumping() == true){
				batch.draw(jumperFrontArmJumpingTexture, (e.getPosition().x)-.3f, (e.getPosition().y) +.55f, e.getWidth() /2, e.getHeight()/2,
						e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperFrontArmJumpingTexture.getWidth(),
						jumperFrontArmJumpingTexture.getHeight(), false, false);
				
				batch.draw(jumperFrontLegJumpingTexture, (e.getPosition().x)-.1f, (e.getPosition().y), e.getWidth() /2, e.getHeight()/2,
						e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperFrontLegJumpingTexture.getWidth(),
						jumperFrontLegJumpingTexture.getHeight(), false, false);
				
					batch.draw(jumperBodyTexture, e.getPosition().x, e.getPosition().y+.70f, e.getWidth() /2, e.getHeight()/2,
							e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperBodyTexture.getWidth(),
							jumperBodyTexture.getHeight(), false, false);
					
					batch.draw(jumperFrontArmJumpingTexture, (e.getPosition().x)-.3f, (e.getPosition().y) +.45f, e.getWidth() /2, e.getHeight()/2,
							e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperFrontArmJumpingTexture.getWidth(),
							jumperFrontArmJumpingTexture.getHeight(), false, false);
					
					batch.draw(jumperFrontLegJumpingTexture, (e.getPosition().x)+.2f, (e.getPosition().y), e.getWidth() /2, e.getHeight()/2,
							e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperFrontLegJumpingTexture.getWidth(),
							jumperFrontLegJumpingTexture.getHeight(), false, false);
				}
				else{
					batch.draw(jumperFrontLegTexture, (e.getPosition().x)-.2f, (e.getPosition().y), e.getWidth() /2, e.getHeight()/2,
							e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperFrontLegTexture.getWidth(),
							jumperFrontLegTexture.getHeight(), true, false);
					
					batch.draw(jumperFrontArmTexture, (e.getPosition().x)+.1f, (e.getPosition().y) +.25f, e.getWidth() /2, e.getHeight()/2,
							e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperFrontArmTexture.getWidth(),
							jumperFrontArmTexture.getHeight(), false, false);
					
					batch.draw(jumperBodyTexture, e.getPosition().x, e.getPosition().y+.55f, e.getWidth() /2, e.getHeight()/2,
							e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperBodyTexture.getWidth(),
							jumperBodyTexture.getHeight(), false, false);
				
					batch.draw(jumperFrontArmTexture, (e.getPosition().x)+.1f, (e.getPosition().y) +.35f, e.getWidth() /2, e.getHeight()/2,
							e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperFrontArmTexture.getWidth(),
							jumperFrontArmTexture.getHeight(), false, false);
					
					batch.draw(jumperFrontLegTexture, (e.getPosition().x)+.2f, (e.getPosition().y), e.getWidth() /2, e.getHeight()/2,
							e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, jumperFrontLegTexture.getWidth(),
							jumperFrontLegTexture.getHeight(), false, false);
				}
			}
			else if (e.getClass() == Zombie.class){
				batch.draw(shipTexture, e.getPosition().x, e.getPosition().y, e.getWidth() /2, e.getHeight()/2,
						e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, shipTexture.getWidth(),
						shipTexture.getHeight(), false, false);				
			}
			else if ((e.getClass() == EnemySpiderBossPopcorn.class)){
				batch.draw(shipTexture, e.getPosition().x, e.getPosition().y, e.getWidth() /2, e.getHeight()/2,
						e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, shipTexture.getWidth(),
						shipTexture.getHeight(), false, false);
			} else if (e.getClass() == Zombie.class){
				batch.draw(shipTexture, e.getPosition().x, e.getPosition().y, e.getWidth() /2, e.getHeight()/2,
						e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, shipTexture.getWidth(),
						shipTexture.getHeight(), false, false);
			} else if (e.getClass() == EnemySpiderBoss.class) {
				EnemySpiderBoss esb = (EnemySpiderBoss)e;
				float scale = 1.3f;
				if (esb.getState() == EnemySpiderBoss.State.RAM){
					float rotation;
					if (esb.getPhase() ==1) {
						rotation = 0;
					} else {
						rotation = -90f;
					}
					//rotation = MathUtils.random(359f);
					TextureRegion bossRamFrame = bossRam.findRegion("stephenram", esb.getRamFrame());
					//bossRamFrame.flip(true, false);
					//batch.draw(bossRamFrame, e.getPosition().x-15f, e.getPosition().y-2f, 0,
						//	0, bossRamFrame.getRegionWidth()/32f, bossRamFrame.getRegionHeight()/32f, scale, scale, rotation);
					//batch.draw(bossRamFrame, e.getPosition().x+e.getWidth()/2, e.getPosition().y+e.getHeight()/2, bossRamFrame.getRegionWidth()/32f-e.getWidth()/2,
					batch.draw(bossRamFrame, e.getPosition().x-bossRamFrame.getRegionWidth()/32f/2f-3f, e.getPosition().y+e.getHeight()/2-bossRamFrame.getRegionHeight()/2f/32f, bossRamFrame.getRegionWidth()/32f-e.getWidth()/2,
								bossRamFrame.getRegionHeight()/32f/2f, bossRamFrame.getRegionWidth()/32f, bossRamFrame.getRegionHeight()/32f, scale, scale, rotation);
					
					//region, las.getOriginPosition().x-las.getCurrentWidth()/2-bufferWidth, las.getOriginPosition().y, las.getCurrentWidth()/2+2*bufferWidth, 0, las.getCurrentWidth()+2*bufferWidth, 
					//LaserBeam.DEFAULT_LENGTH, 1f,1f, e.getRotation()-90f
				}
				if (!(esb.isInvincible() && esb.toggleFlash())) { 
					
					batch.draw(boss1body, e.getPosition().x, e.getPosition().y, 0,
							0, boss1body.getRegionWidth()/32f, boss1body.getRegionHeight()/32f, scale, scale, e.getRotation());
					int headFrame = esb.getHeadFrame();
					TextureRegion b1head;
					if (headFrame == 0) {
						b1head = boss1head0;
					} else {
						b1head = boss1head1;
					}
					Vector2 posForOriginZeroZero = new Vector2(e.getPosition().x+(45f/32f)*scale, e.getPosition().y+(188f/32f)*scale);
					//Vector2 origin = new Vector2(b1head.getRegionWidth()/32f, -5f);
					Vector2 origin = new Vector2(0,0);
					batch.draw(b1head, posForOriginZeroZero.x, posForOriginZeroZero.y, origin.x,
							origin.y, b1head.getRegionWidth()/32f, b1head.getRegionHeight()/32f, scale, scale, e.getRotation());
					
				}
				
				EnemySpiderBossArms arms = ((EnemySpiderBoss)e).getArms();
				float rotationArms = arms.getRotation();
				System.out.println("scale*arms.getScale() = "+scale*arms.getScale());
				//batch.draw(boss1arms, arms.getPosition().x, arms.getPosition().y+arms.getHeight()-2f, arms.getWidth()/2, arms.getHeight()/2, 
				batch.draw(boss1arms, arms.getPosition().x, arms.getPosition().y+arms.getHeight()-1.5f, arms.getWidth()/2, arms.getHeight()/2,
						boss1arms.getRegionWidth()/32f, boss1arms.getRegionHeight()/32f, scale*arms.getScale(), scale*arms.getScale(), e.getRotation() + rotationArms);
				
				if (((EnemySpiderBoss)e).getState() == EnemySpiderBoss.State.LASER) {
					Array<Rectangle> charges = ((EnemySpiderBoss)e).getLaserChargePositions();
					TextureRegion region = laserTextures.findRegion("laser_charge");
					for (Rectangle r: charges) {
						
						batch.draw(region, r.x, r.y, r.width/2, r.height/2); 
					}
				} else if (esb.getState() == EnemySpiderBoss.State.FIREBALL){
					int frameNo = esb.getRamFrame();
					if (frameNo >= 0 && frameNo <= 4) {
						TextureRegion fireRegion = firestarter.findRegion("firestarter", frameNo);
						batch.draw(fireRegion, esb.getPosition().x+EnemySpiderBoss.MOUTH_OFFSET_X-fireRegion.getRegionWidth()/32f/2f, esb.getPosition().y+EnemySpiderBoss.MOUTH_OFFSET_Y-fireRegion.getRegionHeight()/32f/2f,
								0,0, fireRegion.getRegionWidth()/32f, fireRegion.getRegionHeight()/32f, 1,1,0);
					}
				}
			} else if (e.getClass() == SoldierBoss.class) {
				SoldierBoss sb = (SoldierBoss)e;
				//if (sb.getState() == 
				
				TextureRegion bossRegion = myEnemy.findRegion("myEnemy",sb.getAnimationFrame());
				if ( (!sb.isFacingRight() && !bossRegion.isFlipX()) || (sb.isFacingRight() && bossRegion.isFlipX())) {
					bossRegion.flip(true, false);
				}
				batch.draw(bossRegion, e.getPosition().x+e.getWidth()/2-bossRegion.getRegionWidth()/32f/2f, e.getPosition().y+e.getHeight()/2-bossRegion.getRegionHeight()/32f/2f,
						0,0, bossRegion.getRegionWidth()/32f, bossRegion.getRegionHeight()/32f, 2,2,0);
				if (sb.getState() == State.RAM && !sb.isPerformingTell()) {
					bossRegion = myEnemy.findRegion("drill", sb.getOtherAnimationFrame());
					batch.draw(bossRegion, e.getPosition().x+e.getWidth()/2-bossRegion.getRegionWidth()/32f/2f, e.getPosition().y+e.getHeight()/2-bossRegion.getRegionHeight()/32f/2f,
							0,0, bossRegion.getRegionWidth()/32f, bossRegion.getRegionHeight()/32f, 2,2,90);
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
				float bufferWidth = 0.2f;
				batch.draw(region, las.getOriginPosition().x-las.getCurrentWidth()/2-bufferWidth, las.getOriginPosition().y, las.getCurrentWidth()/2+2*bufferWidth, 0, las.getCurrentWidth()+2*bufferWidth, 
						LaserBeam.DEFAULT_LENGTH, 1f,1f, e.getRotation()-90f);
			} else if (e.getClass() == WarningOverlay.class){
				batch.end();
				textBatch.begin();
				WarningOverlay overlay = (WarningOverlay)e;
				Vector2 position2;
				float pixels = 32f;
				float distanceApart = 200f;
				if (overlay.isLeftRepeat()) {
					
					position2 = new Vector2(overlay.getPositionOnScreen(pixels).x-distanceApart, overlay.getPositionOnScreen(pixels).y);
				} else {
					position2 = new Vector2(overlay.getPositionOnScreen(pixels).x+distanceApart, overlay.getPositionOnScreen(pixels).y);
				}
				
				//font.scale(overlay.getScale());
				fontBig.draw(textBatch, "WARNING", overlay.getPositionOnScreen(pixels).x, overlay.getPositionOnScreen(pixels).y);
				fontBig.draw(textBatch, "WARNING", position2.x, position2.y);
				
				textBatch.end();
				//font.scale(1/overlay.getScale());
				/*batch.draw(shipTexture, e.getPosition().x, e.getPosition().y, 0, 0,
						e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, shipTexture.getWidth(),
						shipTexture.getHeight(), false, false);*/
				batch.begin();
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
			
			//draw the enemies health
			if (e.displayHealth()) {
				enemyWithHealthOnScreen = true;
				healthPercentage = e.getHealthPercentage();
				healthName = e.getHealthName();
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
			//System.out.println("Texture: "+csObj.getTexture());
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
		float heartY = cam.position.y + 6.2f;
		
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
		sr.setProjectionMatrix(cam.combined);
		if (enemyWithHealthOnScreen) {
			float barWidth = 10f;
			float barHeight = 1.5f;
			sr.begin(ShapeType.Rectangle);
			sr.setColor(0f,0f,0f,1f);
			sr.rect(cam.position.x - barWidth/2, cam.position.y-barHeight/2-8f, barWidth, barHeight);
			sr.end();
			sr.begin(ShapeType.FilledRectangle);
			sr.setColor(1f, 0f,0f,1f);
			sr.filledRect(cam.position.x-barWidth/2+0.05f, cam.position.y-barHeight /2+0.05f-8f, barWidth * healthPercentage, barHeight-0.1f);
			sr.end();
			textBatch.begin();
			//System.out.println(cam);
			//System.out.println(font);
			//System.out.println(textBatch);
			//System.out.println(healthName);
			//font.draw(textBatch, healthName, cam.position.x-cam.viewportWidth/2-4f, cam.position.y-cam.viewportHeight/2+4f);
			font.draw(textBatch, healthName, 16f, 64f);
			textBatch.end();
		}
		
		
		textBatch.begin();
		CharSequence str = "Time: " + (int)world.getTime(); //(int)ship.getPosition().x;
		int strXPos = Gdx.graphics.getWidth() - 223; //Offset from right
		int strYPos = Gdx.graphics.getHeight() - 4; //Offset from top
		font.draw(textBatch, str, strXPos, strYPos);
		
		str = "Score: " + (int)world.getScore();
		strXPos = Gdx.graphics.getWidth() - 2*223;
		strYPos = Gdx.graphics.getHeight() - 4; //Offset from top
		font.draw(textBatch, str, strXPos, strYPos);
		
		str = "Lives: " + (int)world.getLives();
		strXPos = Gdx.graphics.getWidth() - 850;
		strYPos = Gdx.graphics.getHeight() - 4; //Offset from top
		font.draw(textBatch, str, strXPos, strYPos);
		
		
		
		textBatch.end();
		if (world.isScenePlaying()) {
			if (sceneBarHeight < SCENE_BAR_MAX_HEIGHT) {
				sceneBarHeight += 0.04f;
			}
			
		} else if (sceneBarHeight > 0) {
			sceneBarHeight -= 0.04f;
		}
		if (sceneBarHeight > 0) {
			sr.begin(ShapeType.FilledRectangle);
			sr.setColor(0f,0f,0f,1f);
			//float rectHeight = 2.5f;
			sr.filledRect(cam.position.x-cam.viewportWidth/2, cam.position.y-cam.viewportHeight/2, cam.viewportWidth, sceneBarHeight);
			sr.filledRect(cam.position.x-cam.viewportWidth/2, cam.position.y+cam.viewportHeight/2-sceneBarHeight, cam.viewportWidth, sceneBarHeight);
			sr.end();
		}
		
		ResultsScreen resultsScreen = world.getResultsScreen();
		if (resultsScreen.isShowing()) {
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.FilledRectangle);
			
			sr.setColor(new Color(0f, 0f, 0f,0.55f));
			sr.filledRect(cam.position.x-cam.viewportWidth/2, cam.position.y-cam.viewportHeight/2, cam.viewportWidth, cam.viewportHeight);
			
			sr.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			
			textBatch.begin();
			CharSequence timeText= "TIME BONUS: " + resultsScreen.getTimeBonus();
			CharSequence healthText = "HEALTH BONUS: "+resultsScreen.getHealthBonus();
			CharSequence scoreText = "TOTAL SCORE: "+ world.getScore();
			font.draw(textBatch, timeText, cam.position.x-14f, Gdx.graphics.getHeight()-122);
			font.draw(textBatch, healthText, cam.position.x-14f, Gdx.graphics.getHeight()-172);
			font.draw(textBatch, scoreText, cam.position.x-14f, Gdx.graphics.getHeight()-272);
			//System.out.println("Drawing TotalScore at "+ (cam.position.x-14f) + ","+ (Gdx.graphics.getHeight()-272));
			textBatch.end();
			
			
		}
		

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
			sr.rect(e.getVulnerableBounds().x, e.getVulnerableBounds().y, e.getVulnerableBounds().width, e.getVulnerableBounds().height);
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
		font = new BitmapFont(Gdx.files.internal("font/fredericka_the_great/fredericka_the_great.fnt"), false);
		fontBig = new BitmapFont(Gdx.files.internal("font/fredericka_the_great/fredericka_the_great.fnt"), false);
		fontBig.setScale(3f);
		
		sceneBarHeight = 0f;
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
		//manager.load("enemysprite1-small.png", Texture.class, linearFilteringParam);
		manager.load("stephen.txt", TextureAtlas.class);
		manager.load("stephenram.txt", TextureAtlas.class);
		manager.load("firestarter.txt", TextureAtlas.class);
		manager.load("myEnemy.txt", TextureAtlas.class);
		manager.load("bg2.png", Texture.class, linearFilteringParam);
		manager.load("ship.png", Texture.class, linearFilteringParam);
		manager.load("cyra.png", Texture.class, linearFilteringParam);
		manager.load("body-jumper.png", Texture.class, linearFilteringParam);
		manager.load("frontarm-normal-jumper.png", Texture.class, linearFilteringParam);
		manager.load("frontleg-normal1-jumper.png", Texture.class, linearFilteringParam);
		manager.load("frontleg-jumping-jumper.png", Texture.class, linearFilteringParam);
		manager.load("frontarm-jumping-jumper.png", Texture.class, linearFilteringParam);
		manager.load("heart.png", Texture.class, linearFilteringParam);
		manager.load("projectiles/lightningball.png", Texture.class, linearFilteringParam);
		manager.load("projectiles/lasers.txt", TextureAtlas.class);
		manager.load("projectiles/explosion.txt", TextureAtlas.class);
		manager.load("follower.txt", TextureAtlas.class);
		manager.load("cyraRightMovement.txt", TextureAtlas.class);
		manager.load("cyraLeftMovement.txt", TextureAtlas.class);
		manager.load("modular3.txt", TextureAtlas.class);
		manager.load("level packfile", TextureAtlas.class);
		manager.finishLoading();
		
		boss1Atlas = manager.get("stephen.txt", TextureAtlas.class);
		Array<AtlasRegion> exRegions = boss1Atlas.findRegions("a");
		for (int i=0; i< exRegions.size; i++) {
			exRegions.get(i).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			//exRegions.get(i).flip(true, false);
					
		}
		boss1body = boss1Atlas.findRegion("steaphenbody");
		boss1body.flip(true, false);
		boss1arms = boss1Atlas.findRegion("stephenarms");
		boss1arms.flip(true, false);
		boss1head0 = boss1Atlas.findRegion("steaphenhead0");
		boss1head0.flip(true, false);
		boss1head1 = boss1Atlas.findRegion("steaphenhead1");
		boss1head1.flip(true, false);
		bossRam = manager.get("stephenram.txt", TextureAtlas.class);
		TextureRegion frame0 = bossRam.findRegion("stephenram",0);
		frame0.flip(true, false);
		TextureRegion frame1 = bossRam.findRegion("stephenram", 1);
		frame1.flip(true, false);
		TextureRegion frame2 = bossRam.findRegion("stephenram", 2);
		frame2.flip(true, false);
		TextureRegion frame3 = bossRam.findRegion("stephenram", 3);
		frame3.flip(true, false);
		
		firestarter = manager.get("firestarter.txt", TextureAtlas.class);
		//bossRamAnimation = new Animation(FOLLOWER_FRAME_DURATION, bossRamFrames);
		myEnemy = manager.get("myEnemy.txt", TextureAtlas.class);
		
		bg = manager.get("bg2.png", Texture.class);
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		shipTexture = manager.get("ship.png", Texture.class);
		shipTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		heartsTexture = manager.get("heart.png", Texture.class);
		heartsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bulletTexture = manager.get("projectiles/lightningball.png");
		bulletTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		laserTextures = manager.get("projectiles/lasers.txt");
		explosionTextures = manager.get("projectiles/explosion.txt");
		//laserTextures.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
			/* Load follower texture */
		//followerTexture = new Texture("follower.png");
		followerTexture = manager.get("ship.png");
		followerTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		jumperBodyTexture = manager.get("body-jumper.png");
		jumperBodyTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		jumperFrontArmTexture = manager.get("frontarm-normal-jumper.png");
		jumperFrontArmTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		jumperFrontLegTexture = manager.get("frontleg-normal1-jumper.png");
		jumperFrontLegTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		jumperFrontLegJumpingTexture = manager.get("frontleg-jumping-jumper.png");
		jumperFrontLegJumpingTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		jumperFrontArmJumpingTexture = manager.get("frontarm-jumping-jumper.png");
		jumperFrontArmJumpingTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		TextureAtlas cyraRightAtlas = manager.get("cyraRightMovement.txt", TextureAtlas.class);
		TextureAtlas cyraLeftAtlas = manager.get("cyraRightMovement.txt", TextureAtlas.class);
		
		
		
		TextureAtlas atlas = manager.get("follower.txt", TextureAtlas.class);
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
		
		
		Array<AtlasRegion> cyraRightFrames = cyraRightAtlas.findRegions("cyra");
		System.out.println("Found " + cyraRightFrames.size + " cyra frames");
		for (int i=0; i<cyraRightFrames.size; i++) 
			cyraRightFrames.get(i).getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		cyraRightAnimation = new Animation(FOLLOWER_FRAME_DURATION, cyraRightFrames);
		
		Array<AtlasRegion> cyraLeftFrames = cyraLeftAtlas.findRegions("cyra");
		System.out.println("Found " + cyraLeftFrames.size + " cyra frames");
		for (int i=0; i<cyraLeftFrames.size; i++) 
			cyraLeftFrames.get(i).getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		cyraLeftAnimation = new Animation(FOLLOWER_FRAME_DURATION, cyraLeftFrames);
		
		
			/* Load walker texture */
		//walkerTexture = new Texture("walker.png");
		TextureAtlas walkerAtlas = manager.get("modular3.txt", TextureAtlas.class);
		walkerRegions = walkerAtlas.findRegions("a");
				
		for (int i=0; i<walkerRegions.size; i++) {
			walkerRegions.get(i).getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
					
		}
		System.out.println("Found " + walkerRegions.size + " walker parts");
		
		groundTextureAtlas = manager.get("level packfile", TextureAtlas.class);
			/* Load walker texture - END */
		return;
		
		
		
		
		
		
	}
	
	public void dispose() {
		batch.dispose();
		shipTexture.dispose();
		sr.dispose();
	}
}
