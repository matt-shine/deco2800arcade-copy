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
	
	private float targetPos;
	
	public Level2Scenes(Ship ship, ParallaxCamera cam) {
		super(ship, cam);
		
	}

	@Override
	public Array<Object> start(float rank) {
		isPlaying = true;
		
		cam.setFollowShip(false);
		
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
		
		Array<Object> output = new Array<Object>();
		
		Array<MovableEntity> movingEntities = new Array<MovableEntity>();
		movingEntities.add(ship);
		//movingEntites.add(spiderBoss)
		blockMaker = new BlockMakerSpiderBoss(rank, movingEntities);
		output.add(blockMaker);
		blockMaker.setActive(true);
		boss = new EnemySpiderBoss(new Vector2(319f - cam.viewportWidth/2 - EnemySpiderBoss.WIDTH, 5f), rank, cam);
		output.add(boss);
		
		targetPos = 0f;
		return output;
	}

	@Override
	public void update(float delta) {
		manager.update(delta);
		ship.getVelocity().x = Ship.SPEED / 1.5f;
		
		float lerp = 0.8f;
		targetPos -= delta * 1.5;
		boss.getPosition().x += delta* (ship.getPosition().x - 0f - boss.getPosition().x + targetPos)* lerp;
		
	}

	@Override
	public float[] getStartValues() {
		float[] starts = {319, 999};
		return starts;
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return isPlaying;
	}
	
	public void tweenCompleted() {
		isPlaying = false;
		blockMaker.setActive(true);
		ship.getVelocity().x = 0;
	}

}
