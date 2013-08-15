/*
 * GameScreen
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.badlogic.gdx.graphics.GL20.*;

final class GameScreen implements Screen {
	private static final String LOG = GameScreen.class.getSimpleName();
	private final MixMaze game;
	private final PacMan pacman;
	private final Stage stage;

	/**
	 * This constructor associate GameScreen with MixMaze.
	 */
	GameScreen(final MixMaze game) {
		this.game = game;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		pacman = new PacMan();
		stage.addActor(pacman);
		stage.setKeyboardFocus(pacman);
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void show() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
