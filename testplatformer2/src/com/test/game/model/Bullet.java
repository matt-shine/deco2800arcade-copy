package com.test.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends MovableEntity {

	public static float BULLET_SPEED = 18f;
	public static float BULLET_SIZE = 0.4f;
	
	public Bullet(float speed, float rotation, Vector2 pos, float width,
			float height, Vector2 velocity) {
		super(speed, rotation, pos, width, height);
		this.velocity = velocity;
	}
	
	@Override
	public void update(Ship ship) {
		//position.add(velocity.tmp().mul(Gdx.graphics.getDeltaTime() * speed));
		position.add(velocity.scl(Gdx.graphics.getDeltaTime() * speed));
		velocity.scl(1/(Gdx.graphics.getDeltaTime()*speed));
		super.update(ship);
	}

}
