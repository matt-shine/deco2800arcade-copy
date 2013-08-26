package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class MainGameScreen implements Screen {
	
	private final MainGame game;
	private OrthographicCamera camera;
	private AssetManager manager;
	private ShapeRenderer shapeRenderer;

	//Variables for Card locations and what they contain
	private int p1DeckSize;
	private int p2DeckSize;
	
	private float glowSize;
	private boolean glowDirection;
	
	//assets
	private Map<String, List<ExtendedSprite>> spriteMap;
	private Arena arena;
	private List<Rectangle> highlightedZones;
	
	public MainGameScreen(final MainGame gam) {
		this.game = gam;
		
		//create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
		
		//initialise shapeRender / glowSize
		shapeRenderer = new ShapeRenderer();
		glowSize = 0f;
		glowDirection = true;
		
		//load some assets
		manager = new AssetManager();
		loadAssets();
		manager.finishLoading();
		
		//create game arena
		arena = new Arena(manager.get("DeerForestAssets/background.png", Texture.class));
		
		//create map of sprites
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
	    
	    //list of highlighted zones
	    highlightedZones = arena.getAvailableZones(1, true, true);
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
		shapeRenderer.setProjectionMatrix(camera.combined);
		 
		game.batch.begin();
		
		//Draw game board
	    arena.draw(game.batch);

	    //draw the sprites currently in map 
	    for(String key : spriteMap.keySet()) {
	    	for(ExtendedSprite s : spriteMap.get(key)) {
		    	s.draw(game.batch);
		    }
	    }
	    
	    //draw highlighted zone
	    if(!highlightedZones.isEmpty()) {
	    	Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		    shapeRenderer.begin(ShapeType.FilledRectangle);
		    
		    for(Rectangle r: highlightedZones) {
		    	shapeRenderer.filledRect(r.getX(), r.getY(), r.getWidth(), r.getHeight()+glowSize, Color.BLUE, Color.CLEAR, Color.CLEAR, Color.CLEAR);
		    	
		    	if(glowSize > 10 && glowDirection == true) {
		    		glowDirection = false;
		    	} else if(glowSize < 0 && glowDirection == false) {
		    		glowDirection = true;
		    	}
		    	
		    	if(glowDirection) glowSize += 0.05;
		    	else glowSize -= 0.05;
		    }
		    
		    shapeRenderer.end();
		    Gdx.gl.glDisable(GL10.GL_BLEND);
	    }
	    
	    game.batch.end();
		
	}
	
	@Override
	public void dispose() {
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
	
	public void setHighlightedZones(List<Rectangle> highlight) {
//		highlightedZones.clear();
//		for(Rectangle r: highlight) {
//			highlightedZones.add(new Rectangle(r));
//		}
		highlightedZones = highlight;
	}
}
