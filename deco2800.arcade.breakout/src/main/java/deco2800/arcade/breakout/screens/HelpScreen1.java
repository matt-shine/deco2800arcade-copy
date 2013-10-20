package deco2800.arcade.breakout.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import deco2800.arcade.breakout.Breakout;

/**
 * Class for the SplashScreen 
 * @author Tony Wu and ZhuLun Liang
 * 
 */
 
public class HelpScreen1 implements Screen  {
	/*
	 * creates instance variables for each image.
	 */
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	private Stage stage;
	  
	 /**
	  * Constructor.
	  * Load the image from the resource
	  * @param game-The Breakout game
	  */    
	public HelpScreen1(final Breakout game) {
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/HelpScreen1.png"));
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}
	
	/**
	 * get into the HelpScreen2
	 */
	@Override
	public void render(float arg0) {
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			game.setScreen(game.getHelpscreen2());
		}
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		 
		batch.begin();
		batch.draw(texture, 0, 0);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	
}
