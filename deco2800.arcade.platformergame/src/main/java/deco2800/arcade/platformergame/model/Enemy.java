package deco2800.arcade.platformergame.model;

import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends MovableEntity{
	
	protected float stateTime = 0;
	
	public Enemy(float speed, float rotation, Vector2 pos, float width, float height) {
		super(speed, rotation, pos, width, height);
	}

	public float getStateTime() {
		return stateTime;
	}
	public abstract void advance(float delta, Player player);

}
