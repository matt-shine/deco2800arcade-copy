package deco2800.arcade.breakout.screens;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.breakout.Breakout;

/**
 * Class for the SplashScreen 
 * @author Tony Wu and ZhuLun Liang
 * 
 */
 
public class SplashScreen implements Screen  {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	

	/**
	 * Constructor.
	 * Load the image from the resource
	 * @param game
	 * 				-The Breakout game
	 */    
	public SplashScreen(final Breakout game) {
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/splashscreen.png"));
	}
		
	/**
	 * get into the game menu screen
	 */
	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			game.setScreen(game.getMenuScreen());
		}

		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(texture, 0, 0);
		batch.end();
	
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {
		texture.dispose();
		batch.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void show() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
