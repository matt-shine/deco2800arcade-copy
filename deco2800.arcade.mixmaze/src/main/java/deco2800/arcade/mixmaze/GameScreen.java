/*
 * GameScreen
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.MixMazeModel;
import deco2800.arcade.mixmaze.domain.MixMazeModel.MixMazeDifficulty;
import deco2800.arcade.mixmaze.domain.PlayerModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * GameScreen draws all elements in a game session.
 */
final class GameScreen implements Screen {
	private static final String LOG = GameScreen.class.getSimpleName();

	private final MixMaze game;
	private final Stage stage;
	private final ShapeRenderer renderer;
	private final Skin skin;

	private Label timerLabel;
	private int elapsed;
	private Table tileTable;
	private Group gameArea;
	private Table endGameTable;
	private PlayerViewModel p1;
	private PlayerViewModel p2;
	private Label[] userLabels;
	private Label[] scoreLabels;
	private Label[] itemLabels;
	private MixMazeModel model;
	private int timeLimit;
	private TextButton backMenu;
	private Label resultLabel;

	/**
	 * Constructor
	 */
	GameScreen(final MixMaze game) {
		this.game = game;
		this.skin = game.skin;

		/*
		 * This should be the only ShapeRender used in this
		 * stage.
		 */
		renderer = new ShapeRenderer();

		stage = new Stage();

		setupLayout();

	}

	/**
	 * Set up the layout on stage.
	 */
	private void setupLayout() {
		Stack gameBoard = new Stack();
		Table root = new Table();
		Table left = new Table();
		Table right = new Table();

		userLabels = new Label[2];
		scoreLabels = new Label[2];
		itemLabels = new Label[6];

		userLabels[0] = new Label("Player 1", skin);
		userLabels[1] = new Label("Player 2", skin);
		scoreLabels[0] = new Label("Player 1 box: 0", skin);
		scoreLabels[1] = new Label("Player 2 box: 0", skin);
		itemLabels[0] = new Label("brick: 0", skin);
		itemLabels[1] = new Label("brick: 0", skin);
		itemLabels[2] = new Label("pick: 0", skin);
		itemLabels[3] = new Label("pick: 0", skin);
		itemLabels[4] = new Label("TNT: 0", skin);
		itemLabels[5] = new Label("TNT: 0", skin);

		timerLabel = new Label("timer", skin);
		tileTable = new Table();
		gameArea = new Group();
		endGameTable = new Table();

		endGameTable.setVisible(false);
		resultLabel = new Label("result", skin);
		backMenu = new TextButton("back to menu", skin);
		endGameTable.add(resultLabel);
		endGameTable.row();
		endGameTable.add(backMenu);

		root.setFillParent(true);
		root.debug();
		stage.addActor(root);

		/* header */
		root.add(userLabels[0]).width(320);
		root.add(scoreLabels[0]);
		root.add(timerLabel).expandX();
		root.add(scoreLabels[1]);
		root.add(userLabels[1]).width(320);
		root.row();

		/* side panels */
		left.add(itemLabels[0]);
		left.row();
		left.add(itemLabels[2]);
		left.row();
		left.add(itemLabels[4]);
		right.add(itemLabels[1]);
		right.row();
		right.add(itemLabels[3]);
		right.row();
		right.add(itemLabels[5]);

		/* body */
		root.add(left);
		root.add(gameBoard).colspan(3);
		root.add(right);

		/*
		 * gameBoard has two layers. The bottom one is
		 * the tileTable and the top is gameArea.
		 *
		 * gameArea is used as a place holder as Stack
		 * always places its children at (0, 0), but
		 * children of gameArea can move freely in stage.
		 */
		gameBoard.add(tileTable);
		gameBoard.add(gameArea);
		gameBoard.add(endGameTable);
	}

	private void setupGameBoard() {
		int tileSize = 640 / model.getBoardSize();

		for (int j = 0; j < model.getBoardSize(); j++) {
			for (int i = 0; i < model.getBoardSize(); i++) {
				tileTable.add(new TileViewModel(
						model.getBoardTile(i, j),
						renderer,
						tileSize))
						.size(tileSize, tileSize);
			}

			/*
			 * Luran:
			 * Not sure if an extra row() after boardHeight
			 * will cause problem, but it is easy to check.
			 */
			if (j < model.getBoardSize())
				tileTable.row();
		}

		p1 = new PlayerViewModel(model.getPlayer1(), model, tileSize,
				1);
		p2 = new PlayerViewModel(model.getPlayer2(), model, tileSize,
				2);
		gameArea.addActor(p1);
		gameArea.addActor(p2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.13f, 0.13f, 0.13f, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		/*
		 * TODO: these status updates should be encapsulated
		 * in a method or a class.
		 */
		userLabels[0].setText("player 1: " + p1.getActionName());
		userLabels[1].setText("player 2: " + p2.getActionName());
		scoreLabels[0].setText("boxes: " + p1.getScore());
		scoreLabels[1].setText("boxes: " + p2.getScore());
		itemLabels[0].setText("bricks: "
				+ p1.getBrickAmount());
		itemLabels[1].setText("bricks: "
				+ p2.getBrickAmount());
		itemLabels[2].setText("pick: "
				+ ((p1.hasPick()) ? "YES" : "NO"));
		itemLabels[3].setText("pick: "
				+ ((p2.hasPick()) ? "YES" : "NO"));
		itemLabels[4].setText("TNT: "
				+ ((p1.hasTNT()) ? "YES" : "NO"));
		itemLabels[5].setText("TNT: "
				+ ((p2.hasTNT()) ? "YES" : "NO"));

		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);

		if (endGameTable.isVisible() && backMenu.isChecked()) {
			backMenu.toggle();
			endGameTable.setVisible(false);
			tileTable.clear();
			gameArea.clear();
			game.setScreen(game.menuScreen);
		}
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.debug(LOG, "resizing");

		stage.setViewport(1280, 720, true);
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
		Gdx.app.debug(LOG, "hiding");
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void show() {
		Gdx.app.debug(LOG, "showing");

		/* FIXME: game size and time limit should be passed from UI */
		model = new MixMazeModel(5, MixMazeDifficulty.Beginner, 2);
		timeLimit = 30;
		setupGameBoard();

		/* set timer */
		elapsed = 0;
		Timer.schedule(new Timer.Task() {
			public void run() {
				elapsed += 1;
				timerLabel.setText("Timer: "
						 + elapsed);
			}
		}, 1, 1, timeLimit);
		Timer.schedule(new Timer.Task() {
			public void run() {
				PlayerModel winner;

				stage.setKeyboardFocus(null);
				winner = model.endGame();
				if (winner == null) {
					/* draw */
					resultLabel.setText("Draw");
				} else {
					/* winner */
					resultLabel.setText("Player "
							+ winner.getPlayerID()
							+ " win");
				}
				endGameTable.setVisible(true);
			}
		}, timeLimit);

		/* start game */
		Gdx.input.setInputProcessor(stage);
		gameArea.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				Actor actor = event.getListenerActor();

				for (Actor child : gameArea.getChildren()) {
					if (child.notify(event, false))
						return true;
				}

				return false;
			}
		});
		stage.setKeyboardFocus(gameArea);
		model.startGame();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
