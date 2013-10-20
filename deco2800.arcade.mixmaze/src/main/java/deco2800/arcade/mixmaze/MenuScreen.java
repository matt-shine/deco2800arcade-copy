package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import deco2800.arcade.client.ArcadeInputMux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

final class MenuScreen implements Screen {

	final Logger logger = LoggerFactory.getLogger(MenuScreen.class);

	private final MixMaze game;
	private final Skin skin;
	private final Stage stage;
	private final TextButton hostButton;
	private final TextButton clientButton;

	/**
	 * This constructor associate MenuScreen with MixMaze.
	 * 
	 * @param game
	 *            the MixMaze game
	 */
	MenuScreen(final MixMaze game) {
		Table rootTable = new Table();

		this.game = game;
		this.skin = game.skin;
		this.stage = new Stage();

		rootTable.setFillParent(true);
		stage.addActor(rootTable);

		hostButton = new TextButton("Host", skin);
		clientButton = new TextButton("Client", skin);
		rootTable.add(hostButton);
		rootTable.add(clientButton);
	}

	@Override
	public void render(float delta) {
		if (hostButton.isChecked()) {
			hostButton.toggle(); // set back to unchecked
			logger.debug("switching to host screen");
			game.setScreen(game.hostScreen);
		} else if (clientButton.isChecked()) {
			clientButton.toggle(); // set back to unchecked
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
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(stage);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
