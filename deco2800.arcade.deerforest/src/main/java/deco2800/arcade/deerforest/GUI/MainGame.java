package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.deerforest.models.gameControl.GameSystem;

public class MainGame extends Game {

	SpriteBatch batch;
	BitmapFont font;
	final private GameSystem model;
	
	public MainGame(GameSystem model) {
		this.model = model;
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font
		font = new BitmapFont();
		this.setScreen(new MainGameScreen(this));	
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
	
	public GameSystem getModel() {
		return this.model;
	}
}
