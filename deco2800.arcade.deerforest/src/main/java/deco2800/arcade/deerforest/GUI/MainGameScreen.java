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
import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class MainGameScreen implements Screen {
	
	private final MainGame game;
	private OrthographicCamera camera;
	AssetManager manager;
	private ShapeRenderer shapeRenderer;

	//Variables for Card locations and what they contain
//	private int p1DeckSize;
//	private int p2DeckSize;

    //boolean for phase displayed
    private boolean displayedPhaseMessage;
    private final int timeToDisplay = 150;
    private int currentDisplayTime;

    //
	private float glowSize;
	private boolean glowDirection;

    //Variables for extra sprites
    private ExtendedSprite p1Health;
    private ExtendedSprite p2Health;
    private ExtendedSprite cardBack;
    private final static float healthBarX = 0.065625f;
    private final static float healthBarWidth = 0.01875f;
    private final static float P1HealthBarY = 0.65833336f;
    private final static float P2HealthBarY = 0.093055554f;
    private final static float healthBarHeight = 0.247222246f;

	//assets
	private Map<String, Set<ExtendedSprite>> spriteMap;
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
		spriteMap = new HashMap<String, Set<ExtendedSprite>>();

	    spriteMap.put("P1HandZone", new HashSet<ExtendedSprite>());
	    spriteMap.put("P1MonsterZone", new HashSet<ExtendedSprite>());
	    spriteMap.put("P1SpellZone", new HashSet<ExtendedSprite>());
	    spriteMap.put("P2HandZone", new HashSet<ExtendedSprite>());
	    spriteMap.put("P2MonsterZone", new HashSet<ExtendedSprite>());
	    spriteMap.put("P2SpellZone", new HashSet<ExtendedSprite>());

        //Load extra stuff
        p1Health = new ExtendedSprite(manager.get("DeerForestAssets/HealthBar.png", Texture.class));
        p2Health = new ExtendedSprite(manager.get("DeerForestAssets/HealthBar.png", Texture.class));
        cardBack = new ExtendedSprite(manager.get("DeerForestAssets/CardBack.png", Texture.class));

        //list of highlighted zones
	    highlightedZones = new ArrayList<Rectangle>();

        //set displayPhase to false
        displayedPhaseMessage = false;
        currentDisplayTime = 0;
	}

	private void loadAssets() {
		manager.load("DeerForestAssets/LightMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/DarkMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/FireMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/WaterMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/NatureMonsterShell.png", Texture.class);
		manager.load("DeerForestAssets/GeneralSpellShell.png", Texture.class);
		manager.load("DeerForestAssets/FieldSpellShell.png", Texture.class);
		manager.load("DeerForestAssets/background.png", Texture.class);
        manager.load("DeerForestAssets/MainPhase.png", Texture.class);
        manager.load("DeerForestAssets/BattlePhase.png", Texture.class);
        manager.load("DeerForestAssets/EndPhase.png", Texture.class);
        manager.load("DeerForestAssets/Player1.png", Texture.class);
        manager.load("DeerForestAssets/Player2.png", Texture.class);
        manager.load("DeerForestAssets/HealthBar.png", Texture.class);
        manager.load("DeerForestAssets/CardBack.png", Texture.class);
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
	    
	    //draw the sprites currently in map (if not players turn their hand is cardBack)
	    for(String key : spriteMap.keySet()) {
	    	for(ExtendedSprite s : spriteMap.get(key)) {
                if((game.getCurrentPlayer() == 1 && key.equals("P2HandZone")) ||
                        (game.getCurrentPlayer() == 2 && key.equals("P1HandZone"))) {
                    cardBack.setPosition(s.getX(), s.getY());
                    cardBack.setScale(s.getScaleX(), s.getScaleY());
                    cardBack.draw(game.batch);
                } else {
                    s.draw(game.batch);
                }
		    }
	    }

        game.batch.flush();

        //Draw the phase image if it hasn't already been drawn
        if(!displayedPhaseMessage) {
            if(timeToDisplay < currentDisplayTime) {
                displayedPhaseMessage = true;
                currentDisplayTime = 0;
            }
            currentDisplayTime++;
            if(manager.isLoaded("DeerForestAssets/" + game.getPhase()+".png") ||
                    game.getPhase().equals("DrawPhase")) {
                Gdx.gl.glEnable(GL10.GL_BLEND);
                Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

                ExtendedSprite s;
                if(game.getPhase().equals("DrawPhase")) {
                    if(game.getCurrentPlayer()==1) {
                        s = new ExtendedSprite(manager.get("DeerForestAssets/Player1.png",Texture.class));
                    } else {
                        s = new ExtendedSprite(manager.get("DeerForestAssets/Player2.png",Texture.class));
                    }
                } else {
                    s = new ExtendedSprite(manager.get("DeerForestAssets/" + game.getPhase()+".png",Texture.class));
                }

                //Set fade in / out
                if(currentDisplayTime < timeToDisplay/4) {
                    s.setColor(1.0f, 1.0f, 1.0f, ((float)currentDisplayTime*4)/timeToDisplay);
                } else if(currentDisplayTime > 3*timeToDisplay/4) {
                    if(4*(1-(float)currentDisplayTime/timeToDisplay) > 0) {
                        s.setColor(1.0f, 1.0f, 1.0f, 4*(1-(float)currentDisplayTime/timeToDisplay));
                    } else {
                        s.setColor(1.0f, 1.0f, 1.0f, 0);
                    }
                }

                s.setScale(Gdx.graphics.getWidth()/(3*s.getWidth()/2), Gdx.graphics.getHeight()/(2*s.getHeight()));
                s.setPosition(Gdx.graphics.getWidth()/2 - s.getBoundingRectangle().getWidth()/2,Gdx.graphics.getHeight()/2 - s.getBoundingRectangle().getHeight()/2);
                s.draw(game.batch);

                Gdx.gl.glDisable(GL10.GL_BLEND);
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

        // draw health bars
        drawHealthBars();

        game.batch.flush();

        //draw highlighted zone
        highlightZones();

	    game.batch.end();

        //Draw zoomed sprite
        ExtendedSprite zoomed = DeerForestSingletonGetter.getDeerForest().inputProcessor.getCurrentZoomed();
        if(zoomed != null) {
            game.batch.begin();
            zoomed.draw(game.batch);
            game.batch.end();
        }

	}

    private void drawHealthBars() {

        float x = Gdx.graphics.getWidth();
        float y = Gdx.graphics.getHeight();

        float p1HealthRatio = (float)game.getPlayerLP(1)/200;
        float p2HealthRatio = (float)game.getPlayerLP(2)/200;

        p1Health.setPosition(x*healthBarX, y*P1HealthBarY);
        p1Health.setScale(x*healthBarWidth/ p1Health.getWidth(), p1HealthRatio*y*healthBarHeight/p1Health.getHeight());

        p2Health.setPosition(x*healthBarX, y*P2HealthBarY);
        p2Health.setScale(x*healthBarWidth/ p2Health.getWidth(), p2HealthRatio*y*healthBarHeight/p2Health.getHeight());

        p1Health.draw(game.batch);
        p2Health.draw(game.batch);
    }

	private void highlightZones() {
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		if(!highlightedZones.isEmpty()) {
	    	Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		    shapeRenderer.begin(ShapeType.FilledRectangle);
		    
		    for(Rectangle r: highlightedZones) {
                if(game.getPhase().equals("BattlePhase")) {
                    shapeRenderer.filledRect(r.getX(), r.getY(), r.getWidth(), r.getHeight()+glowSize, Color.RED, Color.CLEAR, Color.CLEAR, Color.CLEAR);
                } else {
                    shapeRenderer.filledRect(r.getX(), r.getY(), r.getWidth(), r.getHeight()+glowSize, Color.YELLOW, Color.CLEAR, Color.CLEAR, Color.CLEAR);
                }

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
	
	public void setHighlightedZones(List<Rectangle> highlight) {
		highlightedZones = highlight;
	}
	
	public boolean setSpriteToArea(ExtendedSprite s, String area) {
		Set<ExtendedSprite> listToAddTo = spriteMap.get(area);
		if(listToAddTo != null) {
			return listToAddTo.add(s);
		}
		return false;
	}

    public boolean removeSprite(ExtendedSprite s) {
        for(String key : spriteMap.keySet()) {
            if(spriteMap.get(key).contains(s)) {
                return spriteMap.get(key).remove(s);
            }
        }
        return false;
    }
	
	public boolean removeSpriteFromArea(ExtendedSprite s, String area) {
		Set<ExtendedSprite> listToAddTo = spriteMap.get(area);
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

    public void setPhaseDisplayed(boolean b) {
        displayedPhaseMessage = b;
    }
}
