package deco2800.arcade.cyra.world;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import deco2800.arcade.cyra.model.BlockMakerSpiderBoss;
import deco2800.arcade.cyra.model.EnemySpiderBoss;
import deco2800.arcade.cyra.model.EnemySpiderBossArms;
import deco2800.arcade.cyra.model.Explosion;
import deco2800.arcade.cyra.model.LaserBeam;
import deco2800.arcade.cyra.model.MovableEntity;
import deco2800.arcade.cyra.model.MovablePlatform;
import deco2800.arcade.cyra.model.MovablePlatformAttachment;
import deco2800.arcade.cyra.model.PartTween;
import deco2800.arcade.cyra.model.ResultsScreen;
import deco2800.arcade.cyra.model.Player;
import deco2800.arcade.cyra.model.SoldierBoss;
import deco2800.arcade.cyra.model.WalkerPart;
import deco2800.arcade.cyra.model.WallBoss;
import deco2800.arcade.cyra.model.WarningOverlay;

public class Level2Scenes extends LevelScenes {

	public static final float SPIDER_BOSS_START = 602f;
	public static final float SOLDIER_BOSS_START = 235f;
	public static final float WALL_BOSS_START = 300f;
	
	
	//private ParallaxCamera cam;
	private TweenManager manager;
	BlockMakerSpiderBoss blockMaker;
	private EnemySpiderBoss boss;
	private SoldierBoss soldierBoss;
	private WallBoss wallBoss;
	private MovablePlatform destructLog;
	private LaserBeam cutsceneLaser;
	
	private boolean closeNextUpdate;
	
	private float targetPos;
	
	private float count;
	private float count2;
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
			destructLog =  new MovablePlatform(logTex, new Vector2(267, 60), 2, 14, new Vector2(267,46.1f), 10f, false, 0f);
			MovablePlatform log1 =  new MovablePlatform(logTex, new Vector2(235, 60), 2, 14, new Vector2(235,46.1f), 10f, false, 0f);
			output.add(destructLog);
			output.add(log1);
			soldierBoss = new SoldierBoss(new Vector2(239, 60));
			output.add(soldierBoss);
			output.add(new WarningOverlay(new Vector2(cam.position.x+cam.viewportWidth/2, cam.position.y), cam.viewportWidth, 10f));
			count = 0;
			//make it drop down, screen shakes, health charges up, then battle starts
		} else if (scenePosition == 1) {
			count = 0;
			resultsScreen.showResults(time, ship.getHearts());
			ship.resetHearts();
			destructLog.setTargetPosition(new Vector2(270, 61));
			for (int i=0; i< 5; i++) {
				output.add(new Explosion(new Vector2(267, 46+2*i)));
			}
		} else if (scenePosition == 2) {
			ship.getVelocity().x = Player.SPEED;
			wallBoss = new WallBoss(new Vector2(300f, 4f));
			output.add(wallBoss);
		} else if (scenePosition == 3) {
			//after wall boss defeated
		} else if (scenePosition == 4) {
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
			MovablePlatformAttachment bossSolid1 = new MovablePlatformAttachment(null, 2f, 5f, boss, new Vector2(3f, 5f));
			MovablePlatformAttachment bossSolid2 = new MovablePlatformAttachment(null, 5f, 2f, boss, new Vector2(1f, 3f));
			//MovablePlatformAttachment bossSolid1 = null;
			//MovablePlatformAttachment bossSolid2 = null;
			output.add(bossSolid1);
			output.add(bossSolid2);
			Array<MovablePlatformAttachment> solidParts = new Array<MovablePlatformAttachment>();
			solidParts.add(bossSolid1);
			solidParts.add(bossSolid2);
			boss.setSolidParts(solidParts);
			targetPos = 0f;
		} else if (scenePosition == 5) {
			count = 0;
			//blockMaker.startDownward();
			//blockMaker.setActive(false);
			//ship.getVelocity().x = Ship.SPEED / 1.5f;
			cutsceneLaser = new LaserBeam(-160, new Vector2(boss.getPosition().x-15f, 
					boss.getPosition().y+EnemySpiderBoss.MOUTH_OFFSET_Y), 4f, false, 3f);
			output.add(cutsceneLaser);
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
			if (ship.getPosition().x >= 244f) {
				isPlaying = false;
				return true;
			}
			return false;
		} else if (scenePosition == 4) {
			//Scene to introduce the boss
			manager.update(delta);
			//ship.getVelocity().x = Ship.SPEED / 1.5f;
			ship.getVelocity().x = 12f / 1.5f; //after changed ship's default speed
			boss.getArms().resetPosition();
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
		} else if (scenePosition == 5) {
			boss.getArms().resetPosition();
			//scene to move the boss into phase 2
			count += delta;
			if (count <= 1f) {
				ship.getPosition().x += delta * (cam.position.x + 4f- ship.getPosition().x) * 0.8f;
				if (ship.getPosition().y < 4f) {
					ship.getPosition().y = 4f;
				}
				boss.getPosition().x -= (boss.getPosition().x - (cam.position.x -cam.viewportWidth/2 + 2f));
				doneSomethingOnce = false;
			} else if (count <= 3f){
				//blockMaker.setActive(false);
				if (!doneSomethingOnce) {
					boss.startCutsceneLaser();
					doneSomethingOnce = true;
					count2 = 3f;
					cutsceneLaser.setOriginPosition(new Vector2(boss.getPosition().x+EnemySpiderBoss.MOUTH_OFFSET_X, 
							boss.getPosition().y+EnemySpiderBoss.MOUTH_OFFSET_Y));
				}
				count2 -= delta;
				if (count2 < 0) {
					
				}
				boss.advanceCutsceneLaser(delta);
				cutsceneLaser.setRotation(cutsceneLaser.getRotation()+60f*delta);
				if (ship.getPosition().y < 4f) {
					ship.getPosition().y = 4f;
				}
			} else if (count <= 5f) {
				cutsceneLaser.setOriginPosition(new Vector2(boss.getPosition().x-100f, 
						boss.getPosition().y));
				boss.setState(EnemySpiderBoss.State.IDLE);
				if (count <4.5f) {
					blockMaker.startDownward();
					if (boss.getPosition().y < cam.position.y + cam.viewportHeight/2) {
						boss.getPosition().y += 20f * delta;
					}
				}
				ship.getVelocity().x = 0;
				ship.getPosition().y -= (ship.getPosition().y - (4f)) * delta;
				ship.getPosition().x -= (ship.getPosition().x - (cam.position.x)) * delta * 0.4f;
				ship.setWallClimbEnabled(false);
			} else {
				isPlaying = false;
				ship.getVelocity().x = 0;
				count = 0;
				doneSomethingOnce = false;
				return true;
			}
			return false;
		} else if (scenePosition == 6) {
			boss.getArms().resetPosition();
			//if (boss.getArms().getWidth() < EnemySpiderBossArms.PHASE3_SCALE * EnemySpiderBossArms.WIDTH) {
				boss.getArms().increaseSize(0.5f*delta);
			//}
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
		float[] starts = {SOLDIER_BOSS_START, 999, WALL_BOSS_START, 999, SPIDER_BOSS_START, 999, 999, 999, 999};
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
