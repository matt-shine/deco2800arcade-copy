/*
 * GameScreen
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Timer;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.*;

final class GameScreen implements Screen {
	private static final String LOG = GameScreen.class.getSimpleName();
	private final MixMaze game;
	private final Stage stage;
	private final ShapeRenderer shapeRenderer;
	private final Box[][] boxes;
	private final PacMan pacman;
	private final Brick brick;
	private final Table table;
	private final Table gameBoard;
	private final Label timerLabel;
	private final Skin skin;

	private int elapsed;

	/**
	 * This constructor associate GameScreen with MixMaze.
	 */
	GameScreen(final MixMaze game) {

		this.game = game;

		elapsed = 0;

		shapeRenderer = new ShapeRenderer();
		skin = new Skin();

		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		table.debug();
		stage.addActor(table);

		timerLabel = new Label("Timer", 
				new Label.LabelStyle(new BitmapFont(),
				CYAN));
		table.add(timerLabel).width(160);
		gameBoard = new Table();
		table.add(gameBoard).bottom().left().expand().fill()
				.width(640).height(640);

		boxes = new Box[5][5];
		for (int i = 4; i >= 0; i--) {
			for (int j = 0; j < 5; j++) {
				boxes[i][j] = new Box(i, j, shapeRenderer);
				gameBoard.add(boxes[i][j])
						.width(128).height(128);
				//Gdx.app.debug(LOG, ""
				//		+ boxes[i][j].getZIndex());
			}
			gameBoard.row();
		}
		//gameBoard.invalidate();

		/*
		 * FIXME: There should be a separate controller rather
		 * than using PacMan as the acting one.
		 */
		pacman = new PacMan(boxes);
		brick = new Brick();

		gameBoard.add(pacman).bottom().left();
		gameBoard.add(brick).bottom().right();
		Gdx.app.debug(LOG, "pacman Z " + pacman.getZIndex());
		stage.setKeyboardFocus(pacman);
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.debug(LOG, "resizing");

		stage.setViewport(800, 640, true);
		stage.getCamera().translate(-stage.getGutterWidth(),
				-stage.getGutterHeight(), 0);
	}

	@Override
	public void dispose() {
		timerLabel.getStyle().font.dispose();
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
		Timer.schedule(new Timer.Task() {
			public void run() {
				elapsed += 1;
				Gdx.app.debug(LOG, "3 seconds passed");
				timerLabel.setText("Timer: " 
						 + elapsed);
			}
		}, 1, 1, 60);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
