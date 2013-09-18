package com.test.game.world;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.model.BlockMakerSpiderBoss;
import com.test.game.model.EnemySpiderBoss;
import com.test.game.model.MovableEntity;
import com.test.game.model.PartTween;
import com.test.game.model.Ship;
import com.test.game.model.WalkerPart;

public class Level2Scenes extends LevelScenes {

	//private ParallaxCamera cam;
	private TweenManager manager;
	BlockMakerSpiderBoss blockMaker;
	private EnemySpiderBoss boss;
	
	private boolean closeNextUpdate;
	
	private float targetPos;
	
	private float count;
	private int scenePosition;
	
	public Level2Scenes(Ship ship, ParallaxCamera cam) {
		super(ship, cam);
		
	}

	@Override
	public Array<Object> start(int scenePosition, float rank) {
		this.scenePosition = scenePosition;
		isPlaying = true;
		closeNextUpdate = false;
		Array<Object> output = new Array<Object>();
		cam.setFollowShip(false);
		System.out.println("Starting scene " + scenePosition);
		
		if (scenePosition == 0) {
			Tween.registerAccessor(ParallaxCamera.class, new CameraTween());
			manager = new TweenManager();
			TweenCallback cb = new TweenCallback() {
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					tweenCompleted();
				}
	
				
			};
			Tween.to(cam, CameraTween.POSITION,(343f-319f)/BlockMakerSpiderBoss.SPEED).target(343f + cam.viewportWidth/2, cam.viewportHeight/2).ease(
					TweenEquations.easeNone).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
			
			
			
			Array<MovableEntity> movingEntities = new Array<MovableEntity>();
			movingEntities.add(ship);
			//movingEntites.add(spiderBoss)
			blockMaker = new BlockMakerSpiderBoss(rank, movingEntities);
			output.add(blockMaker);
			blockMaker.setActive(true);
			boss = new EnemySpiderBoss(new Vector2(319f - cam.viewportWidth/2 - EnemySpiderBoss.WIDTH, 5f), rank, cam);
			output.add(boss);
			
			targetPos = 0f;
		} else if (scenePosition == 1) {
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
			manager.update(delta);
			ship.getVelocity().x = Ship.SPEED / 1.5f;
			
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
		} else if (scenePosition == 1) {
			count += delta;
			if (count <= 2f) {
				ship.getPosition().x += delta * (cam.position.x - ship.getPosition().x) * 0.8f;
			} else {
				blockMaker.startDownward();
				isPlaying = false;
				ship.getVelocity().x = 0;
				return true;
			}
			return false;
		} else {
			return false;
		}
		
	}

	@Override
	public float[] getStartValues() {
		float[] starts = {319, 999, 999};
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
