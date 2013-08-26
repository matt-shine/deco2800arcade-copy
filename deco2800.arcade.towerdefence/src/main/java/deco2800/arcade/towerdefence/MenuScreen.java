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
	TextButton playButton;
	TextButton optionsButton;
	
	
	public MenuScreen(final TowerDefence game) {
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
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
		
		//add buttons with action listeners for options/starting game etc
		
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
		
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
		
		playButton = new TextButton("PLAY!", style);
		playButton.setWidth(400);
		playButton.setHeight(100);
		playButton.setX(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2);
        playButton.setY(Gdx.graphics.getHeight() - playButton.getHeight() - 20);
        
        playButton.addListener(new InputListener() {
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		game.setScreen(game.splashScreen); //Set to gameScreen when implemented
        	}
        	
        });
        
        optionsButton = new TextButton("Options", style);
        optionsButton.setWidth(400);
        optionsButton.setHeight(100);
        optionsButton.setX(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2);
        optionsButton.setY(Gdx.graphics.getHeight() / 2 - optionsButton.getHeight() / 2);
        	
      
        
        stage.addActor(playButton);
        stage.addActor(optionsButton);
		
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
