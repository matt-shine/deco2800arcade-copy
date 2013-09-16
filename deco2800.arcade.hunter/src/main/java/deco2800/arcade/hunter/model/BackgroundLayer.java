package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import deco2800.arcade.hunter.Hunter.Config;

public class BackgroundLayer extends Map {
	
	TextureRegion background = new TextureRegion(new Texture("textures/background.png"));

	public BackgroundLayer(float speedModifier) {
		super(speedModifier);
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(background, 0, 0, Config.screenWidth, Config.screenHeight);
	}

	@Override
	public void update(float delta, float gameSpeed) {
		// TODO Auto-generated method stub
		
	}
}
