package com.test.game.world;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.SpriteTween;
import com.test.game.model.CutsceneObject;
import com.test.game.model.MovableEntity;
import com.test.game.model.MovablePlatform;
import com.test.game.model.PartTween;
import com.test.game.model.Ship;

public class Level1Scenes extends LevelScenes{
	private Texture copterTex;
	private MovablePlatform copter;
	
	private float count = 0f;
	private int keyframe = 0;
	//private float[] startPositions;
	//private boolean isPlaying = false;
	//private TweenManager manager;
	
	private int playState;
	
	public Level1Scenes (Ship ship, ParallaxCamera cam) {
		super(ship, cam);
		copterTex = new Texture("data/copter.png");
		copterTex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		
		
		
		
	}
	
	public Array<Object> start(float rank) {
		playState=0;
		copter = new MovablePlatform(copterTex, new Vector2(50, 1), 4f, 2f, new Vector2(52f,10f), 2f, false, 0f);
		//copter.setCollisionRectangle(0,0,4f,1f);
		isPlaying = true;
		//1st keyframe
		/*Tween.registerAccessor(MovablePlatform.class, new PartTween());
		manager = new TweenManager();
		TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				playState=1;
				ship.getVelocity().x = Ship.SPEED;
			}

			
		};
		
		Tween.to(copter, PartTween.POSITION,1.5f).target(52f,10f).ease(
				TweenEquations.easeNone).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
		*/
		Array<Object> out = new Array<Object>();
		out.add(copter);
		return out;
	}
	public boolean update(float delta) {
		//manager.update(delta);
		count += delta;
		if (count > 2.0f) {
			keyframe++;
			count = 0;
		}
		if (playState == 0) {
			if (!copter.isMoving()) {
				playState = 1;
				ship.getVelocity().x = Ship.SPEED;
			}
		}
		if (playState == 1 && ship.getPosition().x > 48.5f) {
			playState =2;
			ship.getVelocity().y = Ship.JUMP_VELOCITY;
			ship.setState(Ship.State.JUMP);
			ship.resetJumpTime();
		}
		
		if (playState == 2 && ship.getPosition().x > 52.5f) {
			playState = 3;
			ship.getVelocity().x = 0;
			//Tween.to(copter, PartTween.POSITION,1.5f).target(53f,15f).ease(
			//		TweenEquations.easeNone).start(manager);
			copter.setTargetPosition(new Vector2(56f, 15f));
		}
		
		copter.update(ship);
		return false;
	}

	@Override
	public float[] getStartValues() {
		float[] out = {48f};
		return out;
	}

	@Override
	public boolean isPlaying() {
		return isPlaying;
	}
}
