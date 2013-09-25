package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

public class MenuScreen implements Screen {
	
	//@SuppressWarnings("unused")
	private BurningSkies game;
    private Stage stage;
    private BitmapFont black;
    private BitmapFont white;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch batch;
    private TextButton startButton;
    private TextButton optionsButton;
    private TextButton scoresButton;
    private TextButton helpButton;
    private TextButton exitButton;
    private Label label;
	
	public MenuScreen( BurningSkies game){
		this.game = game;		
	}

	@Override
	public void dispose() {
		batch.dispose();
        skin.dispose();
        atlas.dispose();
        white.dispose();
        black.dispose();
        stage.dispose();
	}

	@Override
	public void hide() {
		game.stopSong();
		ArcadeInputMux.getInstance().removeProcessor(stage);
		this.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        batch.begin();
        stage.draw();
        batch.end();
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
        atlas = new TextureAtlas("images/menu/button.pack");
        skin = new Skin();
        skin.addRegions(atlas);
        white = new BitmapFont(Gdx.files.internal("images/menu/whitefont.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("images/menu/font.fnt"), false);
        
        int width = BurningSkies.SCREENWIDTH;
        int height = BurningSkies.SCREENHEIGHT;
        
        stage = new Stage(width, height, true);
	
        ArcadeInputMux.getInstance().addProcessor(stage);
	
	    TextButtonStyle style = new TextButtonStyle();
	    style.up = skin.getDrawable("buttonnormal");
	    style.down = skin.getDrawable("buttonpressed");
	    style.font = black;
	
	    startButton = new TextButton("Start", style);
	    optionsButton = new TextButton("Options", style);
	    scoresButton = new TextButton("Scores", style);
	    helpButton = new TextButton("Help", style);
	    exitButton = new TextButton("Exit", style);
	   
	    buttonDimensions();
	    buttonPosition(width, height);
	    listeners();
	    
	    LabelStyle ls = new LabelStyle(white, Color.WHITE);
	    label = new Label("Burning Skies", ls);
	    label.setX(0);
	    label.setY((float)(height*0.95));
	    label.setWidth(width);
	    label.setAlignment(Align.center);
	   	
	    stage.addActor(startButton);
	    stage.addActor(optionsButton);
	    stage.addActor(scoresButton);
	    stage.addActor(helpButton);
	    stage.addActor(exitButton);
	    stage.addActor(label);

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

	    startButton.setX(width / 2 - startButton.getWidth() / 2);
	    optionsButton.setX(width / 2 - optionsButton.getWidth() / 2);
	    scoresButton.setX(width / 2 - scoresButton.getWidth() / 2);
	    helpButton.setX(width / 2 - helpButton.getWidth() / 2);
	    exitButton.setX(width / 2 - exitButton.getWidth() / 2);
	    
	    optionsButton.setY(height / 2 - optionsButton.getHeight() / 2);
	    startButton.setY(optionsButton.getY() + (2 * (startButton.getHeight() + 10)));
	    scoresButton.setY(optionsButton.getY() + (startButton.getHeight() + 10));
	    helpButton.setY(optionsButton.getY() - (startButton.getHeight() + 10));
	    exitButton.setY(optionsButton.getY() - (2 * (startButton.getHeight() + 10)));
	}
	
	public void listeners() {
		startButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            	game.setScreen(new PlayScreen(game));
            }
		});
    
		optionsButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionsScreen(game));
			}
		});
    
	    scoresButton.addListener(new InputListener() {
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	                return true;
	        }
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	        	game.setScreen(new ScoreScreen(game));
	        }
	    });
    
	    helpButton.addListener(new InputListener() {
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	        	return true;
	        }
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	        	game.setScreen(new HelpScreen(game));
	        }
	    });
	    
	    exitButton.addListener(new InputListener() {
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	        	return true;
	        }
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	        	ArcadeSystem.goToGame(ArcadeSystem.UI);
	        }
	    });
	}
}
