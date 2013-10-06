/*
 * GameScreen
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.MixMazeModel;
import deco2800.arcade.mixmaze.domain.MixMazeModel.MixMazeDifficulty;
import deco2800.arcade.mixmaze.domain.PlayerModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;

import static deco2800.arcade.mixmaze.TileViewModel.*;

import static deco2800.arcade.mixmaze.domain.PlayerModel.PlayerAction.*;
import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.*;

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
	private int countdown;
	private Table tileTable;
	private Group gameArea;
	private Table endGameTable;
	private PlayerViewModel p1;
	private PlayerViewModel p2;
	private MixMazeModel model;
	private TextButton backMenu;
	private Label resultLabel;
	private SidePanel left;
	private SidePanel right;

	private int[] p1Controls = {Keys.G,Keys.H,Keys.W,Keys.A,Keys.S,Keys.D};
	private int[] p2Controls = {Keys.O,Keys.P,Keys.UP,Keys.LEFT,Keys.DOWN,Keys.RIGHT};

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

		left = new SidePanel();
		right = new SidePanel();

		timerLabel = new Label("timer", skin, "timer-white");
		timerLabel.setFontScale(2f);
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
		root.add(timerLabel).expandX().colspan(3);
		root.row();

		/* body */
		root.add(left);
		root.add(gameBoard);
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

		/*
		 * This listener should be added only once.
		 */
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

		// Might rcommend not passing the entire model to the PlayerViewModel, just to keep good seperation
		p1 = new PlayerViewModel(model.getPlayer1(), model, tileSize,
				1,p1Controls);
		p2 = new PlayerViewModel(model.getPlayer2(), model, tileSize,
				2,p2Controls);
		gameArea.addActor(p1);
		gameArea.addActor(p2);

	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.13f, 0.13f, 0.13f, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		/* update player status on side panels */
		left.update(p1);
		right.update(p2);

		stage.act(delta);
		stage.draw();
		//Table.drawDebug(stage);

		if (endGameTable.isVisible() && backMenu.isChecked()) {
			/* clean up this session and go to menu screen */
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
		model = new MixMazeModel(5, MixMazeDifficulty.Beginner, 60*2);
		setupGameBoard();

		/* set timer */
		Label.LabelStyle style = timerLabel.getStyle();
		style.fontColor = WHITE;
		timerLabel.setStyle(style);

		countdown = model.getGameMaxTime();
		Timer.schedule(new Timer.Task() {
			public void run() {
				int min = countdown / 60;
				int sec = countdown % 60;

				if (countdown == 10) {
					Label.LabelStyle style = timerLabel
							.getStyle();
					style.fontColor = RED;
					timerLabel.setStyle(style);
				}
				timerLabel.setText(String.format("%s%d:%s%d",
						(min < 10) ? "0" : "", min,
						(sec < 10) ? "0" : "", sec));
				countdown -= 1;
			}
		}, 0, 1, model.getGameMaxTime());
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
		/*
		 * FIXME: this does not look like a good solution.
		 * It takes some time for timerLabel to change text,
		 * and therefore, without the extra 1, the game will end
		 * before the timer showing up 00:00.
		 */
		}, model.getGameMaxTime() + 1);

		/* start game */
		Gdx.input.setInputProcessor(stage);
		stage.setKeyboardFocus(gameArea);
		model.startGame();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	/*
	 * Draw the player item status on side panel.
	 */
	private class SidePanel extends Table {
		private Image[] frameImages;
		private Image[] brickImages;
		private Image pickImage;
		private Image tntImage;
		private Label scoreLabel;

		SidePanel() {
			Table brickTable = new Table();
			Stack[] itemStacks = new Stack[3];

			frameImages = new Image[3];
			brickImages = new Image[10];
			pickImage = new Image(PICK_REGION);
			tntImage = new Image(TNT_REGION);

			scoreLabel = new Label("", skin);

			/* layout */
			this.add(scoreLabel);
			this.row();

			for (int i = 0; i < 3; i++) {
				itemStacks[i] = new Stack();
				this.add(itemStacks[i]).size(210, 210);
				if (i < 2)
					this.row();

				frameImages[i] = new Image(UNKNOWN_REGION);
				itemStacks[i].add(frameImages[i]);
			}

			itemStacks[0].add(brickTable);
			itemStacks[1].add(pickImage);
			itemStacks[2].add(tntImage);

			/* bricks */
			for (int i = 0; i < 10; i++) {
				brickImages[i] = new Image(BRICK_REGION);
				brickTable.add(brickImages[i]).size(52, 52);
				if ((i + 1) % 4 == 0)
					brickTable.row();
			}
		}

		void update(PlayerViewModel p) {
			scoreLabel.setText("Player" + p.getId()
					+ " boxes " + p.getScore());

			for (int i = 0, n = p.getBrickAmount(); i < 10; i++)
				brickImages[i].setVisible(i < n);
			pickImage.setVisible(p.hasPick());
			tntImage.setVisible(p.hasTNT());

			for (int i = 0; i < 3; i++)
				frameImages[i].setVisible(false);

			switch (p.getAction()) {
			case USE_BRICK:
				frameImages[0].setVisible(true);
				break;
			case USE_PICK:
				frameImages[1].setVisible(true);
				break;
			case USE_TNT:
				frameImages[2].setVisible(true);
				break;
			}
		}
	}
	
	 class Settings{
		public Settings(int[] innerP1Controls,int[] innerP2Controls){
			p1Controls = innerP1Controls;
			p2Controls = innerP2Controls;
		}
		
	}
}
