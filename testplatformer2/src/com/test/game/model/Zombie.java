package com.test.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.lang.Math;

public class Zombie extends Enemy {
	public final static float WIDTH = 2f;
	public final static float HEIGHT = 2f;

	public Zombie(Vector2 pos) {
		super(1, 0, pos, WIDTH, HEIGHT);
	}

	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {
		velocity.x = (float)(Math.sin(4*position.x) + 1.5);
		
		position.add(velocity.scl(delta));
		velocity.scl(1/delta);
		return null;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}

}
