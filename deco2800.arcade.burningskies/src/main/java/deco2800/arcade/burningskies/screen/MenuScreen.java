package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

public class MenuScreen implements Screen {

	private BurningSkies game;
    private Stage stage;
    private BitmapFont black;
    private BitmapFont white;
    private Skin skin;
    private SpriteBatch batch;
    private TextButton startButton;
    private TextButton optionsButton;
    private TextButton scoresButton;
    private TextButton helpButton;
    private TextButton exitButton;
    private Image background;
    // Used to check whether to display dotted selection box around buttons
	private static Boolean keyboardSelection;
	// Input processor for keyboard navigation in menus
	private MenuInputProcessor processor;
	private int width = BurningSkies.SCREENWIDTH;
    private int height = BurningSkies.SCREENHEIGHT;
    private Texture selectionBox;
    private float selectionX;
    private float selectionY;
    
	public MenuScreen( BurningSkies game){
		this.game = game;		
	}

	@Override
	public void dispose() {
		batch.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
        stage.dispose();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
		ArcadeInputMux.getInstance().removeProcessor(processor);
		this.dispose();
	}

	@Override
	public void pause() {
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // Math that gets the selection box where it needs to be. Thank god we don't have to worry about scaling
        selectionX = ((optionsButton.getX() - (2 * (startButton.getWidth() + 30))) - 9) + (MenuInputProcessor.getButtonSelection() * 230);
        selectionY = (height / 9) - 69;
        
        keyboardSelection = MenuInputProcessor.getKeyboardSelection();
        
        stage.act(delta);
        stage.draw();
        
        if (keyboardSelection == true) {
        	batch.begin();
        	batch.draw(selectionBox, selectionX, selectionY);
        	batch.end();
        }
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("images/menu/uiskin32.json"));
        white = new BitmapFont(Gdx.files.internal("images/menu/whitefont.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("images/menu/font.fnt"), false);
        background = new Image(new Texture(Gdx.files.internal("images/menu/menu_background.png")));
        selectionBox = new Texture(Gdx.files.internal("images/menu/selected_outline.png"));
        
        stage = new Stage(width, height, true);
        ArcadeInputMux.getInstance().addProcessor(stage);
        
        processor = new MenuInputProcessor(game);
    	ArcadeInputMux.getInstance().addProcessor(processor);
		
	    startButton = new TextButton("Start", skin);
	    optionsButton = new TextButton("Options", skin);
	    scoresButton = new TextButton("Scores", skin);
	    helpButton = new TextButton("Help", skin);
	    exitButton = new TextButton("Exit", skin);
	   
	    buttonDimensions();
	    buttonPosition(width, height);
	    listeners();
	    	   	
	    stage.addActor(startButton);
	    stage.addActor(optionsButton);
	    stage.addActor(scoresButton);
	    stage.addActor(helpButton);
	    stage.addActor(exitButton);
	    stage.addActor(background);
	    background.toBack();
	}
	
	public void buttonDimensions() {
		startButton.setWidth(200);
	    startButton.setHeight(50);
	    optionsButton.setWidth(200);
	    optionsButton.setHeight(50);
	    scoresButton.setWidth(200);
	    scoresButton.setHeight(50);
	    helpButton.setWidth(200);
	    helpButton.setHeight(50);
	    exitButton.setWidth(200);
	    exitButton.setHeight(50);
	}
	
	public void buttonPosition(int width, int height) {		
		optionsButton.setY(height / 9);
	    startButton.setY(height / 9);
	    scoresButton.setY(height / 9);
	    helpButton.setY(height / 9);
	    exitButton.setY(height / 9);
	    
	    optionsButton.setX(width / 2 - optionsButton.getWidth() / 2);
	    startButton.setX(optionsButton.getX() - (2 * (startButton.getWidth() + 30)));
	    scoresButton.setX(optionsButton.getX() - (startButton.getWidth() + 30));
	    helpButton.setX(optionsButton.getX() + (startButton.getWidth() + 30));
	    exitButton.setX(optionsButton.getX() + (2 * (startButton.getWidth() + 30)));
	}
	
	public void listeners() {
		startButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        		game.stopSong();
            	game.setScreen(new PlayScreen(game));
            }
		});
    
		optionsButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.optionsScreen);
			}
		});
    
	    scoresButton.addListener(new InputListener() {
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	                return true;
	        }
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	        	game.setScreen(game.scoreScreen);
	        	//game.setScreen(game.gameOverScreen);
	        }
	    });
    
	    helpButton.addListener(new InputListener() {
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	        	return true;
	        }
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	        	game.setScreen(game.helpScreen);
	        }
	    });
	    
	    exitButton.addListener(new InputListener() {
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	        	return true;
	        }
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	        	game.stopSong();
	        	ArcadeSystem.goToGame(ArcadeSystem.UI);
	        }
	    });
	}
}
