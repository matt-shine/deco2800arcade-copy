package deco2800.arcade.hunter.model;

import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.platformergame.Entity;

public class BackgroundSprite extends Entity {

	public BackgroundSprite(Vector2 pos, float width, float height) {
		super(pos, width, height);
	}
	
	@Override
	public void update(float delta) {
		setX(getX() - 128 * delta);
	}
}
