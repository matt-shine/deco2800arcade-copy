/*
 * GameScreen
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.MixMazeModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.*;

/**
 * GameScreen draws all elements in a game session.
 */
final class GameScreen implements Screen {
	private static final String LOG = GameScreen.class.getSimpleName();

	//TODO use this
	@SuppressWarnings("unused")
	private final MixMaze game;

	private final Stage stage;
	private final ShapeRenderer renderer;
	//private final Brick brick;

	private final MixMazeModel model;

	private Table gameBoard;
	private Label timerLabel;
	private int elapsed;

	/**
	 * This constructor associate GameScreen with MixMaze.
	 */
	GameScreen(final MixMaze game) {
		this.game = game;

		elapsed = 0;

		/*
		 * This should be only ShapeRender used in this
		 * stage.
		 */
		renderer = new ShapeRenderer();

		/* FIXME: game size should be passed from UI */
		model = new MixMazeModel(5, 5);

		stage = new Stage();
		setupLayout();

		/*
		 * FIXME: There should be a separate controller rather
		 * than using PacMan as the acting one.
		 */
		/*
		pacman = new PacMan(boxes);
		brick = new Brick();

		gameBoard.add(pacman).bottom().left();
		gameBoard.add(brick).bottom().right();
		Gdx.app.debug(LOG, "pacman Z " + pacman.getZIndex());
		stage.setKeyboardFocus(pacman);
		*/
	}

	/**
	 * Set up the layout on stage.
	 */
	private void setupLayout() {
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		Table root = new Table();
		Table left = new Table();
		Table right = new Table();
		Label[] userLabels = new Label[2];
		Label[] scoreLabels = new Label[2];

		userLabels[0] = new Label("user 1", skin);
		userLabels[1] = new Label("user 2", skin);
		scoreLabels[0] = new Label("user 1 score 0", skin);
		scoreLabels[1] = new Label("user 2 score 0", skin);

		timerLabel = new Label("timer", skin);
		gameBoard = new Table();

		root.setFillParent(true);
		root.debug();
		stage.addActor(root);

		root.add(userLabels[0]).width(160);
		root.add(scoreLabels[0]);
		root.add(timerLabel).expandX();
		root.add(scoreLabels[1]);
		root.add(userLabels[1]).width(160);
		root.row();

		root.add(left);
		root.add(gameBoard).size(640, 640).colspan(3);
		root.add(right);

		for (int i = 4; i >= 0; i--) {
			for (int j = 0; j < 5; j++) {
				gameBoard.add(new TileViewModel(
						model.getTile(i, j),
						renderer))
						.size(128f, 128f);
			}
			gameBoard.row();
		}
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

		stage.setViewport(960, 720, true);
		stage.getCamera().translate(-stage.getGutterWidth(),
				-stage.getGutterHeight(), 0);
	}

	@Override
	public void dispose() {
		renderer.dispose();
		stage.dispose();
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
				//Gdx.app.debug(LOG,
				//	"" + elapsed +" seconds passed");
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
