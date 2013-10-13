package deco2800.cyra.world;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import deco2800.cyra.model.BlockMakerSpiderBoss;
import deco2800.cyra.model.EnemySpiderBoss;
import deco2800.cyra.model.Explosion;
import deco2800.cyra.model.MovableEntity;
import deco2800.cyra.model.MovablePlatform;
import deco2800.cyra.model.MovablePlatformAttachment;
import deco2800.cyra.model.PartTween;
import deco2800.cyra.model.ResultsScreen;
import deco2800.cyra.model.Player;
import deco2800.cyra.model.SoldierBoss;
import deco2800.cyra.model.WalkerPart;

public class Level2Scenes extends LevelScenes {

	public static final float SPIDER_BOSS_START = 602f;
	public static final float SOLDIER_BOSS_START = 235f;
	//public static final float SOLDIER_BOSS_START = 600f;
	
	
	//private ParallaxCamera cam;
	private TweenManager manager;
	BlockMakerSpiderBoss blockMaker;
	private EnemySpiderBoss boss;
	private SoldierBoss soldierBoss;
	private MovablePlatform destructLog;
	
	private boolean closeNextUpdate;
	
	private float targetPos;
	
	private float count;
	private int scenePosition;
	private boolean doneSomethingOnce;
	
	
	
	
	
	public Level2Scenes(Player ship, ParallaxCamera cam, ResultsScreen resultsScreen) {
		super(ship, cam, resultsScreen);
		doneSomethingOnce = false;
		blockMaker = new BlockMakerSpiderBoss();
		
	}

	@Override
	public Array<Object> start(int scenePosition, float rank, int time) {
		this.scenePosition = scenePosition;
		//scenePosition++; //DEBUG line!
		isPlaying = true;
		closeNextUpdate = false;
		Array<Object> output = new Array<Object>();
		
		System.out.println("Starting scene " + scenePosition);
		
		if (scenePosition == 0) {
			ship.getVelocity().x = 0;
			Texture logTex = new Texture("data/log.png"); //need to move this to WorldRenderer
			logTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			destructLog =  new MovablePlatform(logTex, new Vector2(267, 60), 2, 14, new Vector2(267,46), 10f, false, 0f);
			MovablePlatform log1 =  new MovablePlatform(logTex, new Vector2(235, 60), 2, 14, new Vector2(235,46), 10f, false, 0f);
			output.add(destructLog);
			output.add(log1);
			soldierBoss = new SoldierBoss(new Vector2(239, 60));
			output.add(soldierBoss);
			count = 0;
			//make it drop down, screen shakes, health charges up, then battle starts
		} else if (scenePosition == 1) {
			count = 0;
			resultsScreen.showResults(time, ship.getHearts());
			destructLog.setTargetPosition(new Vector2(270, 61));
			for (int i=0; i< 5; i++) {
				output.add(new Explosion(new Vector2(267, 46+2*i)));
			}
			
		} else if (scenePosition == 2) {
			cam.setFollowShip(false);
			Tween.registerAccessor(ParallaxCamera.class, new CameraTween());
			manager = new TweenManager();
			TweenCallback cb = new TweenCallback() {
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					tweenCompleted();
				}
	
				
			};
			Sounds.playBossMusic();
			Tween.to(cam, CameraTween.POSITION,(24f)/BlockMakerSpiderBoss.SPEED).target(SPIDER_BOSS_START + 24f + cam.viewportWidth/2, cam.viewportHeight/2).ease(
					TweenEquations.easeNone).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
			
			
			
			Array<MovableEntity> movingEntities = new Array<MovableEntity>();
			movingEntities.add(ship);
			//movingEntites.add(spiderBoss)
			//blockMaker = new BlockMakerSpiderBoss(rank, movingEntities);
			blockMaker.setMoveWithEntites(movingEntities);
			output.add(blockMaker);
			blockMaker.setActive(true);
			boss = new EnemySpiderBoss(new Vector2(SPIDER_BOSS_START - cam.viewportWidth/2 - EnemySpiderBoss.WIDTH, 5f), rank, cam);
			output.add(boss);
			MovablePlatformAttachment bossSolid1 = new MovablePlatformAttachment(null, 2f, 5f, boss, new Vector2(4f, 5f));
			MovablePlatformAttachment bossSolid2 = new MovablePlatformAttachment(null, 5f, 2f, boss, new Vector2(1f, 2f));
			output.add(bossSolid1);
			output.add(bossSolid2);
			Array<MovablePlatformAttachment> solidParts = new Array<MovablePlatformAttachment>();
			solidParts.add(bossSolid1);
			solidParts.add(bossSolid2);
			boss.setSolidParts(solidParts);
			targetPos = 0f;
		} else if (scenePosition == 3) {
			count = 0;
			//blockMaker.startDownward();
			//blockMaker.setActive(false);
			//ship.getVelocity().x = Ship.SPEED / 1.5f;
		}
		return output;
	}

	@Override
	public boolean update(float delta) {
		if (scenePosition == 0) {
			if (ship.getPosition().x < 248f) {
				ship.getPosition().x += delta * Player.SPEED;
			} else {
				isPlaying = false;
				return true;
			}
			return false;
		} else if (scenePosition == 1) {
			count += delta;
			if (count >= 0.1f) {
				Sounds.playExplosionLong(0.5f);
				isPlaying = false;
				return true;
			} else {
				return false;
			}
		} else if (scenePosition == 2) {
			//Scene to introduce the boss
			manager.update(delta);
			//ship.getVelocity().x = Ship.SPEED / 1.5f;
			ship.getVelocity().x = 12f / 1.5f; //after changed ship's default speed
			
			float lerp = 0.8f;
			targetPos -= delta * 1.5;
			boss.getPosition().x += delta* (ship.getPosition().x - 0f - boss.getPosition().x + targetPos)* lerp;
			if (closeNextUpdate) {
				isPlaying = false;
				ship.getVelocity().x = 0;
				return true;
			} else {
				return false;
			}
		} else if (scenePosition == 3) {
			//scene to move the boss into phase 2
			count += delta;
			if (count <= 2f) {
				ship.getPosition().x += delta * (cam.position.x - ship.getPosition().x) * 0.8f;
				if (ship.getPosition().y < 4f) {
					ship.getPosition().y = 4f;
				}
			} else if (count <= 4f){
				if (count <3f) blockMaker.startDownward();
				ship.getVelocity().x = 0;
				ship.getPosition().y -= (ship.getPosition().y - (4f)) * delta;
				ship.getPosition().x -= (ship.getPosition().x - (cam.position.x)) * delta * 0.4f;
				ship.setWallClimbEnabled(false);
			} else {
				isPlaying = false;
				ship.getVelocity().x = 0;
				count = 0;
				return true;
			}
			return false;
		} else if (scenePosition == 4) {
			//boss into phase 3 scene
			count += delta;
			if (count <= 2f) {
				boss.getPosition().x -= (boss.getPosition().x - (cam.position.x -cam.viewportWidth/2))*delta*0.6f;
				boss.getPosition().y -= (boss.getPosition().y - 14f)*delta*0.2f;
			} else if (count <=5f) {
				if (!doneSomethingOnce) {
					doneSomethingOnce = true;
					blockMaker.startStatic();
				}
				boss.getPosition().x -= (boss.getPosition().x - (cam.position.x -cam.viewportWidth/2))*delta*0.5f;
				boss.getPosition().y -= (boss.getPosition().y - (-13f))*delta*12.2f;
				ship.getPosition().x -= (ship.getPosition().x - (cam.position.x + cam.viewportWidth/2-3f))*delta*0.8f;
				
			} else if (count <= 5.5f) {
				boss.getPosition().x = (cam.position.x -cam.viewportWidth/2);
				boss.getPosition().y = 0f;
				blockMaker.staticExplosion(cam.position.x-cam.viewportWidth/2);
			} else {
				isPlaying=false;
				ship.getVelocity().x = 0;
				count = 0;
				doneSomethingOnce = false;
				ship.setWallClimbEnabled(true);
				return true;
			}
			return false;
		} else {
			return false;
		}
		
	}

	@Override
	public float[] getStartValues() {
		float[] starts = {SOLDIER_BOSS_START, 999, SPIDER_BOSS_START, 999, 999, 999};
		//float[] starts = {SPIDER_BOSS_START, 999, 999, 999};
		return starts;
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return isPlaying;
	}
	
	public void tweenCompleted() {
		closeNextUpdate = true;
		blockMaker.setActive(true);
		blockMaker.camHasReachedStartPosition();
		ship.getVelocity().x = 0;
	}

}
