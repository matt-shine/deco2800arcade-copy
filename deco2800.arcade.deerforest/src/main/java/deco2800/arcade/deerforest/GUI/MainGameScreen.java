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
	AssetManager manager;
	private ShapeRenderer shapeRenderer;

	//Variables for Card locations and what they contain
//	private int p1DeckSize;
//	private int p2DeckSize;
//	
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
		
		//create P1Hand
		List<ExtendedSprite> p1Hand = new ArrayList<ExtendedSprite>();
		
		//create P1Monster
		List<ExtendedSprite> p1Monster = new ArrayList<ExtendedSprite>();

		//create P2Hand
		List<ExtendedSprite> p2Hand = new ArrayList<ExtendedSprite>();
		
		//create P2Monster
		List<ExtendedSprite> p2Monster = new ArrayList<ExtendedSprite>();
	    
	    spriteMap.put("P1HandZone", p1Hand);
	    spriteMap.put("P1MonsterZone", p1Monster);
	    spriteMap.put("P1SpellZone", new ArrayList<ExtendedSprite>());
	    spriteMap.put("P2HandZone", p2Hand);
	    spriteMap.put("P2MonsterZone", p2Monster);
	    spriteMap.put("P2SpellZone", new ArrayList<ExtendedSprite>());
	    
	    //list of highlighted zones
	    highlightedZones = new ArrayList<Rectangle>();
	}

	private void loadAssets() {
		manager.load("DeerForestAssets/BLARG!.png", Texture.class);
		manager.load("DeerForestAssets/LightMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/DarkMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/FireMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/WaterMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/NatureMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/GeneralSpellShell.png", Texture.class);
		manager.load("DeerForestAssets/FieldSpellShell.png", Texture.class);
		manager.load("DeerForestAssets/background.png", Texture.class);
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
		game.batch.setProjectionMatrix(camera.combined);
	 
		//Begin drawing the sprites
		game.batch.begin();
		
		//Draw game board
	    arena.draw(game.batch);
	    
	    //draw the sprites currently in map 
	    for(String key : spriteMap.keySet()) {
	    	for(ExtendedSprite s : spriteMap.get(key)) {
		    	s.draw(game.batch);
		    }
	    }
	    
	    //Print the model / game data (for debugging)
	    game.font.draw(game.batch, "Press SPACE for next phase", 0.80f*getWidth(), 0.2f*getHeight());
	    game.font.draw(game.batch, "Press RIGHT_ALT for debug info", 0.80f*getWidth(), 0.25f*getHeight());
	    game.font.draw(game.batch, "Press LEFT_ALT for next turn", 0.80f*getWidth(), 0.3f*getHeight());
	    game.font.draw(game.batch, "Current Player: " + game.getCurrentPlayer(), 0.80f*getWidth(), 0.35f*getHeight());
	    game.font.draw(game.batch, "Current Phase: " + game.getPhase(), 0.80f*getWidth(), 0.4f*getHeight());
	    game.font.draw(game.batch, "Summoned this turn: " + game.getSummoned(), 0.80f*getWidth(), 0.45f*getHeight());
	    game.font.draw(game.batch, "P1 LP: " + game.getPlayerLP(1), 0.80f*getWidth(), 0.5f*getHeight());
	    game.font.draw(game.batch, "P2 LP: " + game.getPlayerLP(2), 0.80f*getWidth(), 0.55f*getHeight());
	    
	    game.batch.end();
	    
	    //draw highlighted zone
	    highlightZones();
		
	}
	
	private void highlightZones() {
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		if(!highlightedZones.isEmpty()) {
	    	Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		    shapeRenderer.begin(ShapeType.FilledRectangle);
		    
		    for(Rectangle r: highlightedZones) {
		    	shapeRenderer.filledRect(r.getX(), r.getY(), r.getWidth(), r.getHeight()+glowSize, Color.YELLOW, Color.CLEAR, Color.CLEAR, Color.CLEAR);
		    	
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
	
	public boolean setSpriteToArea(ExtendedSprite s, String area) {
		List<ExtendedSprite> listToAddTo = spriteMap.get(area);
		if(listToAddTo != null) {
			return listToAddTo.add(s);
		}
		return false;
	}
	
	public boolean removeSpriteFromArea(ExtendedSprite s, String area) {
		List<ExtendedSprite> listToAddTo = spriteMap.get(area);
		if(listToAddTo != null) {
			return listToAddTo.remove(s);
		}
		return false;
	}
	
	public int getSpritePlayer(ExtendedSprite s) {
		for(String key : spriteMap.keySet()) {
			if(spriteMap.get(key).contains(s)) {
				if(key.startsWith("P1")) {
					return 1;
				} else {
					return 2;
				}
			}
		}
		return 0;
	}
	
	public void printSpriteMap() {
		for(String key : spriteMap.keySet()) {
			System.out.println("Key: " + key + " list: " + spriteMap.get(key));
		}
	}
}
