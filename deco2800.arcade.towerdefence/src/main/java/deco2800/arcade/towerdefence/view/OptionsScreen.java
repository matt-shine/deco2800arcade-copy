package deco2800.arcade.towerdefence.view;

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
import deco2800.arcade.towerdefence.controller.TowerDefence;

public class OptionsScreen implements Screen {

	private final TowerDefence game;
	Stage stage;
	SpriteBatch batch;
	TextureAtlas atlas;
	Button backButton;
	BitmapFont white;
	Skin skin;
	TextButtonStyle style;

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
		Gdx.gl.glClearColor(1, 1, 1, 1);
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

		ArcadeInputMux.getInstance().addProcessor(stage);

		backButton = new TextButton("BACK", style);
		backButton.setWidth(buttonWidth);
		backButton.setHeight(buttonHeight);
		backButton.setX(Gdx.graphics.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2);
		backButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(game.menuScreen);
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
