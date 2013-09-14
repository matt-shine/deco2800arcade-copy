package deco2800.arcade.deerforest.GUI;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class DeckBuilderScreen implements Screen {
	
	private final DeckBuilder deckBuilder;
	private OrthographicCamera camera;
	AssetManager manager;

	//assets
	private Map<String, Set<ExtendedSprite>> spriteMap;
	private Arena arena;

	public DeckBuilderScreen(final DeckBuilder dec) {
		this.deckBuilder = dec;
		
		//create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
		
		//load some assets
		manager = new AssetManager();
		manager.finishLoading();
		
	}

	@Override
	public void render(float delta) {
		
		//clear the screen with a dark red color
		Gdx.gl.glClearColor(0.2f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//tell the camera to update its matrices
		camera.update();
		
		//tell the SpriteBatch to render in the
		//coordinate system specified by the camera
		deckBuilder.batch.setProjectionMatrix(camera.combined);
	 
		//Begin drawing the sprites
		deckBuilder.batch.begin();

	    // Print menu text
	    deckBuilder.font.draw(deckBuilder.batch, "This is the deck builder!", 200, 200);

	    deckBuilder.batch.end();

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
