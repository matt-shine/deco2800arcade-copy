package deco2800.arcade.towerdefence.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.towerdefence.controller.TowerDefence;
import static com.badlogic.gdx.graphics.GL20.*;

/* Main Menu GUI, for advancing to numerous screens in the game
 * @author Tuddz
 */
public class MenuScreen implements Screen{
	private static final String LOG = MenuScreen.class.getSimpleName();

	private final TowerDefence game;
	Stage stage;
	BitmapFont black;
    BitmapFont white;
	TextureAtlas atlas;
	Texture texture;
	Skin skin;
	SpriteBatch batch;
	Music music;
	Sound click;
	Button newGameButton, continueButton, multiplayerButton,  optionsButton, 
			creditsButton, loreButton, quitButton;
	float buttonSpacing = 10f;
	float buttonHeight = 50f;
	float buttonWidth = 200f;
	long id;
	
	
	public MenuScreen(final TowerDefence game) {
		this.game = game;
		batch = new SpriteBatch();
		/*Background texture*/
		texture = new Texture(Gdx.files.internal("TDtile.png"));
		buttonSpacing = (Gdx.graphics.getHeight() - 7*buttonHeight)/6;
		/*Sound for button press*/
		click = Gdx.audio.newSound(Gdx.files.internal("menu_click.wav"));
		}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		white.dispose();
		black.dispose();
		stage.dispose();
		music.dispose();
		click.dispose();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
		/*Drawing background texture*/		
		batch.begin();
		batch.draw(texture, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch.end();
		/*Drawing stage - Buttons*/
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();
		
		ArcadeInputMux.getInstance().addProcessor(stage);
				
		//Setting the "Style of a TextButton", 
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
		
		//Instantiating new Text buttons and setting properties		
		newGameButton = new TextButton("PLAY!", style);
		newGameButton.setWidth(buttonWidth);
		newGameButton.setHeight(buttonHeight);
		newGameButton.setX(Gdx.graphics.getWidth() / 2 - newGameButton.getWidth() / 2);
        newGameButton.setY(Gdx.graphics.getHeight() - newGameButton.getHeight());
        
        newGameButton.addListener(new InputListener() { //adding listener to newGameButton
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		music.stop();
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
           		Gdx.app.debug(LOG, "switching to game screen");
           		click.play(1.0f);
        		game.setScreen(game.gameScreen); //Set to gameScreen
        	}
        	
        });
        
        continueButton = new TextButton("Continue", style);
        continueButton.setWidth(buttonWidth);
        continueButton.setHeight(buttonHeight);
        continueButton.setX(Gdx.graphics.getWidth() / 2 - continueButton.getWidth() / 2);
        continueButton.setY(newGameButton.getY() - newGameButton.getHeight() - buttonSpacing);
        
        multiplayerButton = new TextButton("CO-OP", style);
        multiplayerButton.setWidth(buttonWidth);
        multiplayerButton.setHeight(buttonHeight);
        multiplayerButton.setX(Gdx.graphics.getWidth() / 2 - multiplayerButton.getWidth() / 2);
        multiplayerButton.setY(continueButton.getY() - continueButton.getHeight() - buttonSpacing);
        
        optionsButton = new TextButton("Options", style);
        optionsButton.setWidth(buttonWidth);
        optionsButton.setHeight(buttonHeight);
        optionsButton.setX(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2);
        optionsButton.setY(multiplayerButton.getY() - multiplayerButton.getHeight() - buttonSpacing);
        optionsButton.addListener(new InputListener() { //adding listener to newGameButton
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		music.stop();
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		click.play(1.0f);
        		game.setScreen(game.optionsScreen);
        	}
        	
        });
        
        loreButton = new TextButton("Lore", style);
        loreButton.setWidth(buttonWidth);
        loreButton.setHeight(buttonHeight);
        loreButton.setX(Gdx.graphics.getWidth() / 2 - loreButton.getWidth() / 2);
        loreButton.setY(optionsButton.getY() - optionsButton.getHeight() - buttonSpacing);
        
        loreButton.addListener(new InputListener() { //adding listener to newGameButton
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		music.stop();
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		click.play(1.0f);
        		game.setScreen(game.loreScreen);
        	}
        	
        });
        
        creditsButton = new TextButton("Credits", style);
        creditsButton.setWidth(buttonWidth);
        creditsButton.setHeight(buttonHeight);
        creditsButton.setX(Gdx.graphics.getWidth() / 2 - creditsButton.getWidth() / 2);
        creditsButton.setY(loreButton.getY() - loreButton.getHeight() - buttonSpacing);
        
        creditsButton.addListener(new InputListener() { //adding listener to newGameButton
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		music.stop();
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		click.play(1.0f);
        		game.setScreen(game.creditsScreen); 
        	}
        	
        });
        
        quitButton = new TextButton("Quit", style);
        quitButton.setWidth(buttonWidth);
        quitButton.setHeight(buttonHeight);
        quitButton.setX(Gdx.graphics.getWidth() / 2 - quitButton.getWidth() / 2);
        quitButton.setY(creditsButton.getY() - creditsButton.getHeight() - buttonSpacing); 
        quitButton.addListener(new InputListener() { //adding listener to newGameButton
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		music.stop();
        		ArcadeSystem.goToGame(ArcadeSystem.UI);
        	}	
        });

        buttonSpacing = (Gdx.graphics.getHeight() - (7*(newGameButton.getHeight())))/6;
        /*adding the buttons to the stage*/
        stage.addActor(newGameButton);
        stage.addActor(continueButton);
        stage.addActor(multiplayerButton);
        stage.addActor(optionsButton);
        stage.addActor(loreButton);
        stage.addActor(creditsButton);
        stage.addActor(quitButton);
	}
	

	@Override
	public void resume() {
	
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		atlas = new TextureAtlas(Gdx.files.internal("button.pack"));
		skin = new Skin();
        skin.addRegions(atlas);
        white = new BitmapFont(Gdx.files.internal("white_font.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("black_font.fnt"), false);
        music = Gdx.audio.newMusic(Gdx.files.internal("space_menu_sound.wav")); //new music
    	music.play(); //start playing music.
    	music.setLooping(true); //once finished loops to start
	}
}
