package deco2800.arcade.towerdefence.view;

import static com.badlogic.gdx.graphics.Color.WHITE;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.towerdefence.model.TowerDefence;

/* A screen for the lore of the game
 * @author Tuddz
 */
public class LoreScreen implements Screen{
	
	private final TowerDefence game;
	Stage stage;
	Table table;
	SpriteBatch batch;
	TextureAtlas atlas;
	Button backButton;
	BitmapFont black, white;
	Skin skin;
	Label words;
	
	float buttonSpacing = 10f;
	float buttonHeight = 50f;
	float buttonWidth = 200f;
	
	public LoreScreen(final TowerDefence game) {
		this.game = game;
		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		table.debug();
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
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
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
		table.clear();
		
		ArcadeInputMux.getInstance().addProcessor(stage);

		// Setting the "Style of a TextButton",
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
						
		
		backButton = new TextButton("BACK" , style);
		backButton.setWidth(buttonWidth);
		backButton.setHeight(buttonHeight);
		backButton.addListener(new InputListener() { //adding listener to backButton
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		game.setScreen(game.menuScreen); //Set to menuScreen
        	}
        	
        });
		
		words = new Label("", new Label.LabelStyle(white, WHITE));
		words.setWrap(true);
		words.setText("The year is 2800, and humanity has spread across the stars, \n"
				+ "colonising many worlds across the galaxy. 2 months ago, Earth lost contact\n"
				+ " with several isolated planets, and a Defence of Earth Colonies Organization\n"
				+ " (DECO) vessel, the Arcadia, was sent to investigate. After the entire crew \n"
				+ "was killed by a strange alien bio-weapon and the ship crippled during an ambush,\n"
				+ " it is up to you, the AI of the Arcadia, to prevent hordes of monstrous aliens\n"
				+ " from reaching the portal to Earth on the bridge of the ship.");
		
		table.add(backButton).top().right();
		table.row();
		table.add(words).center().expand().fill();
		stage.addActor(table);
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
