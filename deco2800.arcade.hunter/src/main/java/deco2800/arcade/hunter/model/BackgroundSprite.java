package deco2800.arcade.hunter.model;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.platformergame.model.Entity;

public class BackgroundSprite extends Entity {

	public BackgroundSprite(Vector2 pos, float width, float height) {
		super(pos, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void update(float delta) {
		setX(getX() - 10);
	}
}
