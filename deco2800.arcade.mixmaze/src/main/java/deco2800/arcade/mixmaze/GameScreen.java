/*
 * GameScreen
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.badlogic.gdx.graphics.GL20.*;

final class GameScreen implements Screen {
	private static final String LOG = GameScreen.class.getSimpleName();
	
	//TODO use this
	@SuppressWarnings("unused")
	private final MixMaze game;
	
	private final Stage stage;
	private final ShapeRenderer shapeRenderer;
	private final Box[][] boxes;
	private final PacMan pacman;

	/**
	 * This constructor associate GameScreen with MixMaze.
	 */
	GameScreen(final MixMaze game) {
		this.game = game;

		stage = new Stage();

		shapeRenderer = new ShapeRenderer();

		boxes = new Box[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boxes[i][j] = new Box(i, j, shapeRenderer);
				stage.addActor(boxes[i][j]);
				//Gdx.app.debug(LOG, ""
				//		+ boxes[i][j].getZIndex());
			}
		}

		/*
		 * FIXME: There should be a separate controller rather
		 * than using PacMan as the acting one.
		 */
		pacman = new PacMan(boxes);
		stage.addActor(pacman);
		Gdx.app.debug(LOG, "pacman Z " + pacman.getZIndex());
		stage.setKeyboardFocus(pacman);
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.debug(LOG, "resizing");

		stage.setViewport(640, 640, true);
		stage.getCamera().translate(-stage.getGutterWidth(),
				-stage.getGutterHeight(), 0);
	}

	@Override
	public void dispose() {
		stage.dispose();
		shapeRenderer.dispose();
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
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
