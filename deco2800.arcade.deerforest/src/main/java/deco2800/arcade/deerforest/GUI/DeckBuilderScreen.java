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

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.AbstractCard;


public class DeckBuilderScreen implements Screen {
	
	private final DeckBuilder deckBuilder;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private Map<String, Map<Rectangle, BuilderSprite>> all;
	
	AssetManager manager;

	//assets
	private Map<String, Set<BuilderSprite>> spriteMap;
	private BuilderArena arena;

	/**
	 * Initialises DeckBuilderScreen
	 * 
	 * @param DeckBuilder builder
	 */
	public DeckBuilderScreen(final DeckBuilder dec) {
		this.deckBuilder = dec;
	    
		//create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
				
		//initialise shapeRender
		shapeRenderer = new ShapeRenderer();
		
		
		
		//load some assets
		manager = new AssetManager();
		loadAssets();
		manager.finishLoading();
		
		//initialises the areana
		arena = new BuilderArena(manager.get("DeerForestAssets/builderBackground.png", Texture.class));
		
		//create map of sprites
		spriteMap = new HashMap<String, Set<BuilderSprite>>();
		
		spriteMap.put("Card", new HashSet<BuilderSprite>());
	    spriteMap.put("Deck", new HashSet<BuilderSprite>());
	    spriteMap.put("Zoom", new HashSet<BuilderSprite>());
		
	}

	/**
	 * Loads images used
	 */
	private void loadAssets() {
		manager.load("DeerForestAssets/builderBackground.png", Texture.class);
		CardCollection deck = deckBuilder.getModel().deck;
        deck.addAll(deckBuilder.getModel().deck);
        ArrayList<AbstractCard> decks = new ArrayList<AbstractCard>(deck);
        for(AbstractCard card : decks) {
            manager.load(card.getPictureFilePath(), Texture.class);
        }
	}
	
	/**
	 * Overrides the render function - draws the screen
	 */
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
		
		for(String key : spriteMap.keySet()) {
	    	for(BuilderSprite s : spriteMap.get(key)) {
                    s.draw(deckBuilder.batch);
		    }
	    }

	    deckBuilder.batch.end();
	    
	    Map<String, Map<Rectangle, BuilderSprite>> map = BuilderArena.getMap();
	    
	   /* shapeRenderer.begin(ShapeType.FilledRectangle);
	    
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
	    shapeRenderer.end();*/
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

	/**
	 * Resizes the screen
	 * 
	 * @param x width
	 * @param y height
	 */
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
	 * Gets a list of all the sprites
	 * 
	 * @return spriteMap
	 */
	public Map<String, Set<BuilderSprite>> getSpriteMap() {
		return spriteMap;
	}
	
	/**
	 * Returns the arena
	 * 
	 * @return arena
	 */
	public BuilderArena getArena() {
		return arena;
	}
	
	
	public int getWidth() {
		return Gdx.graphics.getWidth();
	}
	
	public int getHeight() {
		return Gdx.graphics.getHeight();
	}
	
	/**
	 * Prints the spriteMap for debugging
	 */
	public void printSpriteMap() {
		for(String key : spriteMap.keySet()) {
			DeerForest.logger.info("Key: " + key + " list: " + spriteMap.get(key));
		}
	}
	
	/**
	 * Adds a sprite to an area
	 * 
	 * @param currentSelection sprite to set in area
	 * @param area to set the sprite in
	 * @return boolean
	 */
	public boolean setSpriteToArea(BuilderSprite currentSelection, String area) {
		Set<BuilderSprite> listToAddTo = spriteMap.get(area);
		if(listToAddTo != null) {
			return listToAddTo.add(currentSelection);
		}
		return false;
	}
	
	public boolean removeSprite(BuilderSprite s) {
        for(String key : spriteMap.keySet()) {
            if(spriteMap.get(key).contains(s)) {
                return spriteMap.get(key).remove(s);
            }
        }
        return false;
   }
	
	public boolean removeSpriteFromArea(BuilderSprite s, String area) {
		Set<BuilderSprite> listToAddTo = spriteMap.get(area);
		if(listToAddTo != null) {
			return listToAddTo.remove(s);
		}
		return false;
	}
}
