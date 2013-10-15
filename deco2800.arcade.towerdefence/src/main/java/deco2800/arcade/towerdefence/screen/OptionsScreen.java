package deco2800.arcade.towerdefence.screen;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import deco2800.arcade.towerdefence.TowerDefence;

/* Options screen was left just in case. Serves no purpose at this point in time.
 * @author Tuddz
 */
public class OptionsScreen implements Screen {
	
	private final TowerDefence game;
	Stage stage;
	SpriteBatch batch;
	TextureAtlas atlas;
	Button backButton;
	BitmapFont black;
	Skin skin;
	
	float buttonSpacing = 10f;
	float buttonHeight = 50f;
	float buttonWidth = 200f;
	
	public OptionsScreen(final TowerDefence game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		//white.dispose();
		black.dispose();
		stage.dispose();
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
		
		ArcadeInputMux.getInstance().addProcessor(stage);	

		// Setting the "Style of a TextButton",
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
						
		
		backButton = new TextButton("BACK" , style);
		backButton.setWidth(buttonWidth);
		backButton.setHeight(buttonHeight);
		backButton.setX(Gdx.graphics.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2);
		backButton.addListener(new InputListener() { //adding listener to backButton
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		game.setScreen(game.menuScreen); //Set to menuScreen
        	}
        	
        });
		
		stage.addActor(backButton);
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
        //white = new BitmapFont(Gdx.files.internal("white_font.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("black_font.fnt"), false);
		
	}

}
