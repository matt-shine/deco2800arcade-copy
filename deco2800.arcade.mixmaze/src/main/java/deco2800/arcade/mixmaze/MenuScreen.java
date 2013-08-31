/*
 * MenuScreen
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import static com.badlogic.gdx.Input.*;
import static com.badlogic.gdx.graphics.GL20.*;

final class MenuScreen implements Screen {
	private static final String LOG = MenuScreen.class.getSimpleName();

	private final MixMaze game;

	/**
	 * This constructor associate MenuScreen with MixMaze.
	 */
	MenuScreen(final MixMaze game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0.2f, 1);
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			// Start a game from the user's view.
			Gdx.app.debug(LOG, "switching to game screen");
			game.setScreen(game.gameScreen);
		}
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
