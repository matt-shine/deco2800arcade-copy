package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

final class MenuScreen implements Screen {

	final Logger logger = LoggerFactory.getLogger(MenuScreen.class);

	private final MixMaze game;
	private final Skin skin;
	private final Stage stage;
	private final TextButton localButton;
	private final TextButton hostButton;
	private final TextButton clientButton;
	private final TextButton settingsButton;

	/**
	 * This constructor associate MenuScreen with MixMaze.
	 *
	 * @param game	the MixMaze game
	 */
	MenuScreen(final MixMaze game) {
		Table rootTable = new Table();

		this.game = game;
		this.skin = game.skin;
		this.stage = new Stage();

		rootTable.setFillParent(true);
		stage.addActor(rootTable);

		localButton = new TextButton("Local", skin);
		hostButton = new TextButton("Host", skin);
		clientButton = new TextButton("Client", skin);
		settingsButton = new TextButton("Settings", skin);
		rootTable.add(localButton);
		rootTable.add(hostButton);
		rootTable.add(clientButton);
		rootTable.add(settingsButton);

		settingsButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(game.settingsScreen);
			}
		});

	}

	@Override
	public void render(float delta) {
		if (localButton.isChecked()) {
			localButton.toggle();	// set back to unchecked
			logger.debug("switching to single screen");
			game.setScreen(game.localScreen);
		} else if (hostButton.isChecked()) {
			hostButton.toggle();	// set back to unchecked
			logger.debug("switching to host screen");
			game.setScreen(game.hostScreen);
		} else if (clientButton.isChecked()) {
			clientButton.toggle();	// set back to unchecked
			logger.debug("switching to client screen");
			game.setScreen(game.clientScreen);
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
