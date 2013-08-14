package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen {

	public static final String LOG = GameScreen.class.getSimpleName();

	final MixMaze game;

	private final Stage stage;
	private final PacMan pacman;

	public GameScreen(final MixMaze game) {
		this.game = game;
		this.game.gameScreen = this;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		pacman = new PacMan();
		stage.addActor(pacman);
		stage.setKeyboardFocus(pacman);

	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.T)) {
			game.setScreen(game.menuScreen);
		}

		Gdx.gl20.glClearColor(0.2f, 0.2f, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
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
