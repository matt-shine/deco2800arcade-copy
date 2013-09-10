package deco2800.arcade.towerdefence.screen;

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

import deco2800.arcade.towerdefence.TowerDefence;

public class LoreScreen implements Screen{
	
	private final TowerDefence game;
	Stage stage;
	Table table;
	SpriteBatch batch;
	TextureAtlas atlas;
	Button backButton;
	BitmapFont black;
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
		//white.dispose();
		black.dispose();
		stage.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
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
		
		Gdx.input.setInputProcessor(stage);

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
		
		words = new Label("", new Label.LabelStyle(new BitmapFont(),
				WHITE));
		words.setWrap(true);
		words.setText("LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE \n" +
				"LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE \n" +
				"LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE \n" +
				"LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE \n" +
				"LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE \n" +
				"LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE LORE \n");
		
		table.add(backButton).top().right();
		table.row();
		table.add(words).center().expand().fill();
		stage.addActor(table);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
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
