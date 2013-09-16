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

public class MainMenuScreen implements Screen {
	
	private final MainMenu menu;
	private OrthographicCamera camera;
	AssetManager manager;

	//assets
	private Map<String, Set<ExtendedSprite>> spriteMap;
	private Arena arena;

	public MainMenuScreen(final MainMenu men) {
		this.menu = men;
		
		//create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
		
		//load some assets
		//load some assets
		manager = new AssetManager();
		loadAssets();
		manager.finishLoading();
		
		arena = new Arena(manager.get("DeerForestAssets/MenuScreen.png", Texture.class));
		
		
	}

	private void loadAssets() {
		manager.load("DeerForestAssets/MenuScreen.png", Texture.class);
		
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
		menu.batch.setProjectionMatrix(camera.combined);
	 
		//Begin drawing the sprites
		menu.batch.begin();
		
		arena.draw(menu.batch);

	    // Print menu text
	    menu.font.draw(menu.batch, "We have a main menu!", 200, 200);
	    menu.font.draw(menu.batch, "0 - Play Game", 200, 250);
	    menu.font.draw(menu.batch, "1 - Build Deck", 200, 300);

	    menu.batch.end();

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

	public Map<String, Set<ExtendedSprite>> getSpriteMap() {
		return spriteMap;
	}
	
	public Arena getArena() {
		return arena;
	}
	
	public int getWidth() {
		return Gdx.graphics.getWidth();
	}
	
	public int getHeight() {
		return Gdx.graphics.getHeight();
	}
}
