package deco2800.arcade.towerdefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import static com.badlogic.gdx.graphics.GL20.*;

public class MenuScreen implements Screen{

	private final TowerDefence game;
	Stage stage;
	BitmapFont black;
    BitmapFont white;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton newGameButton, continueButton, multiplayerButton,  optionsButton, 
			creditsButton, loreButton, quitButton;
	float buttonSpacing = 10f;
	float buttonHeight = 50f;
	float buttonWidth = 200f;
	
	
	public MenuScreen(final TowerDefence game) {
		this.game = game;
		buttonSpacing = (Gdx.graphics.getHeight() - 7*buttonHeight)/6;
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
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
				
		stage.act(delta);
		
		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();
		
		Gdx.input.setInputProcessor(stage);
				
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
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		game.setScreen(game.gameScreen); //Set to gameScreen when implemented instead of splashScreen.
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
        
        loreButton = new TextButton("Lore", style);
        loreButton.setWidth(buttonWidth);
        loreButton.setHeight(buttonHeight);
        loreButton.setX(Gdx.graphics.getWidth() / 2 - loreButton.getWidth() / 2);
        loreButton.setY(optionsButton.getY() - optionsButton.getHeight() - buttonSpacing);
        
        creditsButton = new TextButton("Credits", style);
        creditsButton.setWidth(buttonWidth);
        creditsButton.setHeight(buttonHeight);
        creditsButton.setX(Gdx.graphics.getWidth() / 2 - creditsButton.getWidth() / 2);
        creditsButton.setY(loreButton.getY() - loreButton.getHeight() - buttonSpacing);
        
        quitButton = new TextButton("Quit", style);
        quitButton.setWidth(buttonWidth);
        quitButton.setHeight(buttonHeight);
        quitButton.setX(Gdx.graphics.getWidth() / 2 - quitButton.getWidth() / 2);
        quitButton.setY(creditsButton.getY() - creditsButton.getHeight() - buttonSpacing); 
      
        //adding the buttons to the stage
        stage.addActor(newGameButton);
        stage.addActor(continueButton);
        stage.addActor(multiplayerButton);
        stage.addActor(optionsButton);
        stage.addActor(loreButton);
        stage.addActor(creditsButton);
        stage.addActor(quitButton);
        buttonSpacing = (Gdx.graphics.getHeight() - 7*quitButton.getHeight())/6;
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
	}
}