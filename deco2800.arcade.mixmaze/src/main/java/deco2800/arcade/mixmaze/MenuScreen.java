package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuScreen implements Screen {
	final MixMaze game;
	OrthographicCamera camera;

	public MenuScreen(final MixMaze game) {
		this.game = game;
		this.game.menuScreen = this;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 720, 480);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "This is main screen"
				+ " (press left mouse button to game screen)",
				100, 150);
		game.batch.end();

		if (game.gameScreen == null) {
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				game.setScreen(new GameScreen(game));
			}
		} else {
			if (!Gdx.input.isKeyPressed(Input.Keys.T)) {
				game.setScreen(game.gameScreen);
			}
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
	}
}
