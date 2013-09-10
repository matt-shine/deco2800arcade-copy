package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.hunter.model.EntityCollection;
import deco2800.arcade.hunter.model.Map;

public class SpriteLayer extends Map {
	private EntityCollection clouds = new EntityCollection();

	public SpriteLayer(float speedModifier) {
		super(speedModifier);
	}

	@Override
	public void update(float delta, float gameSpeed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

}
