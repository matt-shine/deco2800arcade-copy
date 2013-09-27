package deco2800.cyra.model;

import com.badlogic.gdx.math.Vector2;

public abstract class BulletEnemy extends MovableEntity {
	
	public BulletEnemy (float speed, float rotation, Vector2 pos, float width,
			float height, Vector2 velocity) {
		super(speed, rotation, pos, width, height);
		this.velocity= velocity;
	}
	
	public void update(Ship ship) {
		super.update(ship);
	}

}
