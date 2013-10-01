/*
 * MenuScreen
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

final class MenuScreen implements Screen {
	private static final String LOG = MenuScreen.class.getSimpleName();

	private final MixMaze game;
	private final Skin skin;
	private final Stage stage;
	private final TextButton startButton;
	private final TextButton settingsButton;

	/**
	 * This constructor associate MenuScreen with MixMaze.
	 */
	MenuScreen(final MixMaze game) {
		Table rootTable = new Table();

		this.game = game;
		this.skin = game.skin;
		this.stage = new Stage();

		rootTable.setFillParent(true);
		stage.addActor(rootTable);

		startButton = new TextButton("New Game", skin);
		settingsButton = new TextButton("Settings", skin);
		rootTable.add(startButton);
		rootTable.add(settingsButton);
		
		settingsButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(game.settingsScreen);
			}
		});
		
	}

	@Override
	public void render(float delta) {
		if (startButton.isChecked()) {
			// Start a game from the user's view.
			startButton.toggle();	// set back to unchecked
			Gdx.app.debug(LOG, "switching to game screen");
			game.setScreen(game.gameScreen);
		}

		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(1280, 720, true);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
