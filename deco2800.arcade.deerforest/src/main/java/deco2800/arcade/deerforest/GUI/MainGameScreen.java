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
import deco2800.arcade.deerforest.models.cards.AbstractMonster;

public class MainGameScreen implements Screen {
	//FIXME some big methods that could use whitespace or shrinking to be more readable
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

    //variables for highlighting zones
	private float glowSize;
	private boolean glowDirection;

    //Variables for extra sprites
    private ExtendedSprite p1Health;
    private ExtendedSprite p2Health;
    private ExtendedSprite cardBack;
    private ExtendedSprite p1Deck;
    private ExtendedSprite p2Deck;
    private final static float healthBarX = 0.065625f;
    private final static float healthBarWidth = 0.01875f;
    private final static float P1HealthBarY = 0.65833336f;
    private final static float P2HealthBarY = 0.093055554f;
    private final static float healthBarHeight = 0.247222246f;
    private final static float lifePointsX = 0.103f;
    private final static float lifePointsP1Y = 0.65055555f;
    private final static float lifePointsP2Y = 0.089277776f;
    private final static float deckX = 0.21471875f;
    private final static float deckWidth = 0.07265625f;
    private final static float deckHeight = 0.1375f;
    private final static float P1DeckY = 0.7361111f;
    private final static float P2DeckY = 0.16527778f;

	//assets
	private Map<String, Set<ExtendedSprite>> spriteMap;
	private Arena arena;
	private List<Rectangle> highlightedZones;

    //Battling animation variables
    private boolean showBattle;
    private ExtendedSprite cardAttacking;
    private ExtendedSprite cardDefending;
    private int attackAmount;
    private final int battleDisplayTime = 150;
    private int currentBattleDisplayTime;
    private final int battleFadeRatio = 5;

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
        p1Deck = new ExtendedSprite(manager.get("DeerForestAssets/Deck.png", Texture.class));
        p2Deck = new ExtendedSprite(manager.get("DeerForestAssets/Deck.png", Texture.class));

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
        manager.load("DeerForestAssets/Player1Victory.png", Texture.class);
        manager.load("DeerForestAssets/Player2Victory.png", Texture.class);
        manager.load("DeerForestAssets/Deck.png", Texture.class);
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
                    drawCardBack(s);
                } else {
                    s.draw(game.batch);
                }
		    }
	    }

	    //Print the model / game data (for debugging)
	    game.font.draw(game.batch, "Press SPACE for next phase", 0.80f*getWidth(), 0.2f*getHeight());
	    game.font.draw(game.batch, "Press RIGHT_ALT for debug info", 0.80f*getWidth(), 0.25f*getHeight());
	    game.font.draw(game.batch, "Press LEFT_ALT for next turn", 0.80f*getWidth(), 0.3f*getHeight());
        game.font.draw(game.batch, "Press LEFT_SHIFT for zoom", 0.80f*getWidth(), 0.35f*getHeight());
        game.font.draw(game.batch, "Press M for mute", 0.80f*getWidth(), 0.4f*getHeight());
        game.font.draw(game.batch, "Press N for new game", 0.80f*getWidth(), 0.45f*getHeight());
        game.font.draw(game.batch, "Current Player: " + game.getCurrentPlayer(), 0.80f*getWidth(), 0.5f*getHeight());
	    game.font.draw(game.batch, "Current Phase: " + game.getPhase(), 0.80f*getWidth(), 0.55f*getHeight());
	    game.font.draw(game.batch, "Summoned this turn: " + game.getSummoned(), 0.80f*getWidth(), 0.6f*getHeight());

        // draw health bars / print LP
        drawHealthBars();
        game.font.draw(game.batch,String.valueOf(game.getPlayerLP(1)), lifePointsX*getWidth(), lifePointsP1Y*getHeight());
        game.font.draw(game.batch, String.valueOf(game.getPlayerLP(2)), lifePointsX * getWidth(), lifePointsP2Y * getHeight());

        //Draw deck images
        drawDeckImages();

        game.batch.flush();

        //draw highlighted zone
        highlightZones();

        game.batch.flush();

        //Draw the phase message if needed
        drawPhaseMessage();

	    game.batch.end();

        //Draw zoomed sprite
        ExtendedSprite zoomed = DeerForestSingletonGetter.getDeerForest().inputProcessor.getCurrentZoomed();
        if(zoomed != null) {
            game.batch.begin();
            //Check if zoomed is in opponents hand
            if(game.getCurrentPlayer() != zoomed.getPlayer() && !zoomed.isField()) {
                zoomed = cardBack;
                drawCardBack(zoomed);
            } else {
                zoomed.setSelected(false);
                zoomed.draw(game.batch);
                zoomed.setSelected(true);
            }
            game.batch.end();
        }

        //Draw battling cards
        if(showBattle) {
            drawBattle();
            if(battleDisplayTime < currentBattleDisplayTime) {
                showBattle = false;
                currentBattleDisplayTime = 0;
            } else {
                currentBattleDisplayTime++;
            }
        }

        //Draw victorious players
        drawVictory();

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

    private void drawDeckImages() {

        float x = Gdx.graphics.getWidth();
        float y = Gdx.graphics.getHeight();

        p1Deck.setPosition(x*deckX, y*P1DeckY);
        p1Deck.setScale(x*deckWidth/ p1Deck.getWidth(), y*deckHeight/p1Deck.getHeight());

        p2Deck.setPosition(x*deckX, y*P2DeckY);
        p2Deck.setScale(x*deckWidth/ p2Deck.getWidth(), y*deckHeight/p2Deck.getHeight());

        //If draw phase make deck glow if haven't drawn
        if(game.getPhase().equals("DrawPhase") && !DeerForestSingletonGetter.getDeerForest().inputProcessor.hasDrawn()) {
            if(game.getCurrentPlayer()==1) {
                p1Deck.setColor(1.0f - glowSize/20, 1.0f - glowSize/20, 1.0f - glowSize/20, 1.0f);
            } else {
                p2Deck.setColor(1.0f - glowSize/20, 1.0f - glowSize/20, 1.0f - glowSize/20, 1.0f);
            }
        } else {
            p1Deck.setColor(Color.WHITE);
            p2Deck.setColor(Color.WHITE);
        }

        p1Deck.draw(game.batch);
        p2Deck.draw(game.batch);
    }

    private void drawCardBack(ExtendedSprite s) {

        cardBack.setPosition(s.getX(), s.getY());

        float xScale = s.getBoundingRectangle().getWidth() / cardBack.getWidth();
        float yScale = s.getBoundingRectangle().getHeight() / cardBack.getHeight();

        cardBack.setScale(xScale, yScale);
        cardBack.draw(game.batch);
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

		    }
		    
		    shapeRenderer.end();
		    Gdx.gl.glDisable(GL10.GL_BLEND);
	    }

        //Adjust glow
        if(glowSize > 10 && glowDirection == true) {
            glowDirection = false;
        } else if(glowSize <= 0 && glowDirection == false) {
            glowDirection = true;
        }

        if(glowDirection) glowSize += 0.07;
        else glowSize -= 0.07;
	}

    private void drawPhaseMessage() {
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
    }

    private void drawBattle() {

        game.batch.begin();

        //Draw attacking card
        if(cardAttacking != null) {

            //Set fade in / out
            if(currentBattleDisplayTime < battleDisplayTime/battleFadeRatio) {
                cardAttacking.setColor(1.0f, 1.0f, 1.0f, ((float)currentBattleDisplayTime*battleFadeRatio)/battleDisplayTime);
            } else if(currentBattleDisplayTime > (battleFadeRatio-1)*battleDisplayTime/battleFadeRatio) {
                if(battleFadeRatio*(1-(float)currentBattleDisplayTime/battleDisplayTime) > 0) {
                    cardAttacking.setColor(1.0f, 1.0f, 1.0f, battleFadeRatio*(1-(float)currentBattleDisplayTime/battleDisplayTime));
                } else {
                    cardAttacking.setColor(1.0f, 1.0f, 1.0f, 0);
                }
            }

            cardAttacking.draw(game.batch);
        }

        //Draw defending card
        if(cardDefending != null) {

            //Set fade in / out
            if(currentBattleDisplayTime < battleDisplayTime/battleFadeRatio) {
                cardDefending.setColor(1.0f, 1.0f, 1.0f, ((float)currentBattleDisplayTime*battleFadeRatio)/battleDisplayTime);
            } else if(currentBattleDisplayTime > (battleFadeRatio-1)*battleDisplayTime/battleFadeRatio) {
                if(battleFadeRatio*(1-(float)currentBattleDisplayTime/battleDisplayTime) > 0) {
                    cardDefending.setColor(1.0f, 1.0f, 1.0f, battleFadeRatio*(1-(float)currentBattleDisplayTime/battleDisplayTime));
                } else {
                    cardDefending.setColor(1.0f, 1.0f, 1.0f, 0);
                }
            }
            cardDefending.draw(game.batch);
        }

        //Draw attack damage
        if(currentBattleDisplayTime > battleDisplayTime/6 && currentBattleDisplayTime < 5*battleDisplayTime/6) {
            game.font.setColor(Color.RED);
            game.font.setScale(2.0f);
            game.font.draw(game.batch, "-" + String.valueOf(attackAmount), 15*Gdx.graphics.getWidth()/24, 2*Gdx.graphics.getHeight()/5);
            game.font.setScale(0.5f);
            game.font.setColor(Color.WHITE);
        }

        game.batch.end();
    }

    private void drawVictory() {
        //Show victorious player if exists
        if(game.getPlayerLP(1) <= 0) {
            game.batch.begin();
            ExtendedSprite s = new ExtendedSprite(manager.get("DeerForestAssets/Player2Victory.png", Texture.class));
            s.setScale(Gdx.graphics.getWidth()/(2*s.getWidth()), Gdx.graphics.getHeight() / (2*s.getHeight()));
            s.setPosition(Gdx.graphics.getWidth()/2 - s.getBoundingRectangle().getWidth()/2,
                    Gdx.graphics.getHeight()/2 - s.getBoundingRectangle().getHeight()/2);
            s.draw(game.batch);
            DeerForestSingletonGetter.getDeerForest().inputProcessor.setGameFinished(true);
            game.batch.end();
        } else if(game.getPlayerLP(2) <= 0) {
            game.batch.begin();
            ExtendedSprite s = new ExtendedSprite(manager.get("DeerForestAssets/Player1Victory.png", Texture.class));
            s.setScale(Gdx.graphics.getWidth()/(2*s.getWidth()), Gdx.graphics.getHeight() / (2*s.getHeight()));
            s.setPosition(Gdx.graphics.getWidth()/2 - s.getBoundingRectangle().getWidth()/2,
                    Gdx.graphics.getHeight()/2 - s.getBoundingRectangle().getHeight()/2);
            s.draw(game.batch);
            DeerForestSingletonGetter.getDeerForest().inputProcessor.setGameFinished(true);
            game.batch.end();
        }
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

    public void setBattleSprites(ExtendedSprite attackingCard, ExtendedSprite defendingCard, int damage) {

        //Set up attacking card
        if(attackingCard != null) {
            this.cardAttacking = new ExtendedSprite(attackingCard);
            this.cardAttacking.setScale(Gdx.graphics.getHeight()/(2*this.cardAttacking.getHeight()));
            float x = Gdx.graphics.getWidth()/3 - this.cardAttacking.getBoundingRectangle().getWidth()/2;
            float y = Gdx.graphics.getHeight()/2 - this.cardAttacking.getBoundingRectangle().getHeight()/2;
            this.cardAttacking.setPosition(x, y);
            this.cardAttacking.setCard(null);
        }

        //Set up defending card
        if(defendingCard != null) {
            this.cardDefending = new ExtendedSprite(defendingCard);
            this.cardDefending.setScale(Gdx.graphics.getHeight()/(2*this.cardDefending.getHeight()));
            float x = 2*Gdx.graphics.getWidth()/3 - this.cardDefending.getBoundingRectangle().getWidth()/2;
            float y = Gdx.graphics.getHeight()/2 - this.cardDefending.getBoundingRectangle().getHeight()/2;
            this.cardDefending.setPosition(x, y);
            this.cardDefending.setCard(null);
        } else {
            this.cardDefending = null;
        }

        //Calculate correct damage to show
        if(defendingCard != null) {
            this.attackAmount = ((AbstractMonster)defendingCard.getCard()).modifiedDamage(damage, ((AbstractMonster)attackingCard.getCard()).getType());
        } else {
            this.attackAmount = damage;
        }

        this.showBattle = true;
    }

    //Checks if there is a deck at the given point
    public boolean deckAtPoint(int x, int y) {
        if(p1Deck.containsPoint(x, y) || p2Deck.containsPoint(x, y)) return true;
        return false;
    }

    public void setSpriteToDeck(int player, ExtendedSprite s) {
        if(player == 1) {
            Rectangle r = p1Deck.getBoundingRectangle();
            s.setPosition(r.getX(), r.getY());
            s.setScale(r.getWidth()/s.getWidth(), r.getHeight()/s.getHeight());
        } else {
            Rectangle r = p2Deck.getBoundingRectangle();
            s.setPosition(r.getX(), r.getY());
            s.setScale(r.getWidth()/s.getWidth(), r.getHeight()/s.getHeight());
        }
    }
}
