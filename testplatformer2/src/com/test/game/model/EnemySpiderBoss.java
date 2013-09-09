package com.test.game.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.test.game.world.ParallaxCamera;

public class EnemySpiderBoss extends Enemy {

	public static final float WIDTH = 10f;
	public static final float HEIGHT = 10f; 
	
	private float rank;
	private ParallaxCamera cam;
	private int phase;
	private float count;
	
	public EnemySpiderBoss(Vector2 pos, float rank, ParallaxCamera cam) {
		super(0, 0, pos, WIDTH, HEIGHT);
		this.rank = rank;
		this.cam = cam;
		phase = 1;
		count = 0;
	}

	@Override
	public void advance(float delta, Ship ship, float rank) {
		System.out.println("Test");
		count += delta;
		if (phase == 1) {
			float lerp = 0.8f;
			position.y -= delta * (position.y - (5f + 4 * MathUtils.cos(count))) * lerp;
			position.x -= delta * (position.x - (cam.position.x -cam.viewportWidth/2 + 1f)) * lerp;
		}
		
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}
	
}
