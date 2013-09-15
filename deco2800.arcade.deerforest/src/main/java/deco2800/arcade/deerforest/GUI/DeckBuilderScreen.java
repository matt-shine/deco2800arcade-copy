package deco2800.arcade.deerforest.GUI;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;


public class DeckBuilderScreen implements Screen {
	
	private ShapeRenderer shapeRenderer;
	
	private final DeckBuilder deckBuilder;
	private OrthographicCamera camera;
	private Map<String, Map<Rectangle, ExtendedSprite>> all;
	AssetManager manager;

	//assets
	private Map<String, Set<ExtendedSprite>> spriteMap;
	private BuilderArena arena;

	public DeckBuilderScreen(final DeckBuilder dec) {
		this.deckBuilder = dec;
	    
		//initialise shapeRender / glowSize
		shapeRenderer = new ShapeRenderer();
		
		//create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
		
		//load some assets
		manager = new AssetManager();
		loadAssets();
		manager.finishLoading();
		
		arena = new BuilderArena(manager.get("DeerForestAssets/builderBackground.png", Texture.class));
		
	}

	private void loadAssets() {
		manager.load("DeerForestAssets/builderBackground.png", Texture.class);
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
		arena.draw(deckBuilder.batch);

	    deckBuilder.batch.end();
	    
	    Map<String, Map<Rectangle, ExtendedSprite>> map = BuilderArena.getMap();
	    
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    for (String key : map.keySet()) {
	    	for(Rectangle r : map.get(key).keySet()) {
	    		if(key == "cardZone"){
	    			shapeRenderer.filledRect(r.getX(), r.getY(), r.getWidth(), r.getHeight(), Color.GREEN, Color.CLEAR, Color.CLEAR, Color.CLEAR);
	    		} else if (key =="zoomZone") {
	    			shapeRenderer.filledRect(r.getX(), r.getY(), r.getWidth(), r.getHeight(), Color.RED, Color.CLEAR, Color.CLEAR, Color.CLEAR);
	    		} else {
	    			shapeRenderer.filledRect(r.getX(), r.getY(), r.getWidth(), r.getHeight(), Color.BLUE, Color.CLEAR, Color.CLEAR, Color.CLEAR);
	    		}
	    	}
	    }
	    shapeRenderer.end();
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
	
	public BuilderArena getArena() {
		return arena;
	}
	
	public int getWidth() {
		return Gdx.graphics.getWidth();
	}
	
	public int getHeight() {
		return Gdx.graphics.getHeight();
	}
}
