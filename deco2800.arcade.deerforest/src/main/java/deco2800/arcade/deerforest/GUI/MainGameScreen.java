package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainGameScreen implements Screen {
	
	private final MainGame game;
	private OrthographicCamera camera;
	private AssetManager manager;
	
	//Variables for Card locations and what they contain
	private int p1DeckSize;
	private int p2DeckSize;
	
	//assets
	private Map<String, List<ExtendedSprite>> spriteMap;
	private Arena arena;
	
	public MainGameScreen(final MainGame gam) {
		this.game = gam;
		
		//create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
		
		//load some assets
		manager = new AssetManager();
		loadAssets();
		manager.finishLoading();
		
		arena = new Arena(manager.get("DeerForestAssets/background.png", Texture.class));
		
		spriteMap = new HashMap<String, List<ExtendedSprite>>();
		List<ExtendedSprite> p1Hand = new ArrayList<ExtendedSprite>();
		ExtendedSprite s1 = new ExtendedSprite(manager.get("DeerForestAssets/generalCard.png", Texture.class));
		s1.setPosition(100, 100);
		p1Hand.add(s1);
		s1.setScale(0.25f);
		ExtendedSprite s2 = new ExtendedSprite(manager.get("DeerForestAssets/2.png", Texture.class));
		s2.setPosition(200, 200);
	    p1Hand.add(s2);
	    spriteMap.put("P1Hand", p1Hand);
	}

	private void loadAssets() {
		manager.load("DeerForestAssets/generalCard.png", Texture.class);
		manager.load("DeerForestAssets/2.png", Texture.class);
		manager.load("DeerForestAssets/background.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		//clear the screen with a dark blue colour
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//tell the camera to update its matrices
		camera.update();
		
		//tell the SpriteBatch to render in the
		//coordinate system specified by the camera
		game.batch.setProjectionMatrix(camera.combined);
		
		//draw some random text  
	    game.batch.begin();

	    arena.draw(game.batch);
	    
	    for(String key : spriteMap.keySet()) {
	    	for(ExtendedSprite s : spriteMap.get(key)) {
		    	s.draw(game.batch);
		    }
	    }

	    game.batch.end();
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int x, int y) {
		arena.resize(x, y);
		camera.setToOrtho(true, getWidth(), getHeight());
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	public Map<String, List<ExtendedSprite>> getSpriteMap() {
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
