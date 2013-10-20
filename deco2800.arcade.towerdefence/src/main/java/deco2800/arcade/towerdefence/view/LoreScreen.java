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
import deco2800.arcade.towerdefence.controller.TowerDefence;

public class LoreScreen implements Screen {

	private final TowerDefence game;
	Stage stage;
	Table table;
	SpriteBatch batch;
	TextureAtlas atlas;
	Button backButton;
	BitmapFont white;
	Skin skin;
	Label words;
	TextButtonStyle style;

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
		if (stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();
		table.clear();

		ArcadeInputMux.getInstance().addProcessor(stage);

		backButton = new TextButton("BACK", style);
		backButton.setWidth(buttonWidth);
		backButton.setHeight(buttonHeight);
		backButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true; // do nothing
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(game.menuScreen);
			}

		});

		words = new Label("", new Label.LabelStyle(white, WHITE));
		words.setWrap(true);
		words.setText("The year is 2800, and humanity has spread across the stars,\n"
				+ "colonising many worlds across the galaxy. 2 months ago, Earth\n"
				+ "lost contact with several isolated planets, and a Defence of\n"
				+ "Earth Colonies Organization (DECO) vessel, the Arcadia, was\n"
				+ "sent to investigate. After the entire crew was killed by a\n"
				+ "strange alien bio-weapon and the ship crippled during an ambush,\n"
				+ "it is up to you, the defense AI of the Arcadia, to prevent hordes\n"
				+ "of monstrous aliens from reaching the portal to Earth on the bridge\n"
				+ "of the ship.");

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
		atlas = new TextureAtlas(Gdx.files.internal("black_button.pack"));
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("white_font.fnt"), false);

		/* Setting the "Style of a TextButton", */
		style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = white;

	}
}
