package deco2800.arcade.deerforest.GUI;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * This class functions as the view for the main tutorial
 */
public class TutorialScreen implements Screen {
	
	private final Tutorial tutorial;
	private OrthographicCamera camera;
	AssetManager manager;

	//assets
	private Map<String, Set<ExtendedSprite>> spriteMap;
	private Arena arena;

	/**
	 * Initialises the main tutorial screen
	 */
	public TutorialScreen(final Tutorial tut) {
		this.tutorial = tut;
		
		//create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
		
		//load some assets
		//load some assets
		manager = new AssetManager();
		loadAssets();
		manager.finishLoading();
		
		arena = new Arena(manager.get("DeerForestAssets/tutorialScreen.png", Texture.class));
		
		
	}

	/**
	 * Loads the assets for the main tutorial
	 */
	private void loadAssets() {
		manager.load("DeerForestAssets/tutorialScreen.png", Texture.class);
		
	}

	@Override
	public void render(float delta) {
		
		//clear the screen with a dark blue color
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//tell the camera to update its matrices
		camera.update();
		
		//tell the SpriteBatch to render in the
		//coordinate system specified by the camera
		tutorial.batch.setProjectionMatrix(camera.combined);
	 
		//Begin drawing the sprites
		tutorial.batch.begin();
		
		arena.draw(tutorial.batch);

	    // Print tutorial text
	    tutorial.font.draw(tutorial.batch, "We have a main tutorial!", 200, 200);
	    tutorial.font.draw(tutorial.batch, "0 - Play Game", 200, 250);
	    tutorial.font.draw(tutorial.batch, "1 - Build Deck", 200, 300);

	    tutorial.batch.end();

	}

	@Override
	public void dispose() {
		manager.dispose();
        manager.dispose();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resize(int x, int y) {
		arena.resize(x, y);
		camera.setToOrtho(true, getWidth(), getHeight());
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {

	}

	/**
	 * Returns the sprite map for the main tutorial screen
	 */
	public Map<String, Set<ExtendedSprite>> getSpriteMap() {
		return spriteMap;
	}
	
	/**
	 * Returns the arena for the main tutorial screen
	 */
	public Arena getArena() {
		return arena;
	}
	
	/**
	 * Returns the width of the main tutorial
	 */
	public int getWidth() {
		return Gdx.graphics.getWidth();
	}
	
	/**
	 * Returns the height of the main tutorial
	 */
	public int getHeight() {
		return Gdx.graphics.getHeight();
	}
}
