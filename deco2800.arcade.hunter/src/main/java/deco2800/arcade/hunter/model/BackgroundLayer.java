package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector3;
import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.screens.GameScreen;

public class BackgroundLayer extends Map {

	TextureRegion background = new TextureRegion(new Texture("textures/background.png"));
	private GameScreen gamescreen;
	
	
	public BackgroundLayer(float speedModifier, GameScreen gameScreen) {
		super(speedModifier);
		this.gamescreen = gameScreen;
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(background, 0, 0, Config.screenWidth, Config.screenHeight);
	}

	@Override
	public void update(float delta, Vector3 cameraPos) {
		// TODO Auto-generated method stub
		
	}
}
