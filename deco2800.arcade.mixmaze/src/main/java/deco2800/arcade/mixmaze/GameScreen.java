package deco2800.arcade.mixmaze;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.mixmaze.domain.IMixMazeModel;
import deco2800.arcade.mixmaze.domain.PlayerModel;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static deco2800.arcade.mixmaze.TileViewModel.*;

/**
 * GameScreen draws all elements in a game session.
 */
abstract class GameScreen implements Screen {

	final Logger logger = LoggerFactory.getLogger(GameScreen.class);

	static TextureRegion p1HeadRegion;
	static TextureRegion p2HeadRegion;

	protected final Stage stage;
	protected final ShapeRenderer renderer;

	protected Group gameArea;
	protected PlayerViewModel p1;
	protected PlayerViewModel p2;
	protected IMixMazeModel model;
	protected Table tileTable;
	protected int countdown;
	protected Label timerLabel;
	protected Table endGameTable;
	protected TextButton backMenu;
	protected Label resultLabel;
	protected SidePanel left;
	protected SidePanel right;
	protected ScoreBar[] scorebar;

	protected final MixMaze game;
	private final Skin skin;

	protected int[] p1Controls = new int[6];
	protected int[] p2Controls = new int[6];

	/**
	 * Constructor
	 *
	 * @param game
	 *            the MixMaze game
	 */
	GameScreen(MixMaze game) {
		this.game = game;
		this.skin = game.skin;

		/* This should be the only ShapeRender used on this stage. */
		this.renderer = new ShapeRenderer();
		this.stage = new Stage();
		setupLayout();
	}

	/**
	 * Set up the layout on stage.
	 */
	private void setupLayout() {
		Stack gameBoard = new Stack();
		Table root = new Table();
		Table middle = new Table();
		root.debug();

		left = new SidePanel();
		right = new SidePanel();

		scorebar = new ScoreBar[2];
		scorebar[0] = new ScoreBar(true);
		scorebar[1] = new ScoreBar(false);

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

		middle.add(scorebar[0]).left();
		middle.add(timerLabel);
		middle.add(scorebar[1]).right();
		middle.row();
		middle.add(gameBoard).colspan(3);

		root.setFillParent(true);
		root.debug();
		stage.addActor(root);

		root.add(left);
		root.add(middle);
		root.add(right);

		/*
		 * gameBoard has two layers. The bottom one is the tileTable and the top
		 * is gameArea.
		 *
		 * gameArea is used as a place holder as Stack always places its
		 * children at (0, 0), but children of gameArea can move freely in
		 * stage.
		 */
		gameBoard.add(tileTable);
		gameBoard.add(gameArea);
		gameBoard.add(endGameTable);

		/*
		 * This listener should be added only once.
		 */
		gameArea.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				for (Actor child : gameArea.getChildren()) {
					if (child.notify(event, false)){
						return true;
					}
				}

				return false;
			}
		});
	}

	@Override
	public void resize(int width, int height) {
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
	public void show() {
		newGame();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	/**
	 * Starts a new game session.
	 */
	protected abstract void newGame();

	/**
	 * Sets up the game board.
	 */
	protected abstract void setupGameBoard();

	/**
	 * Sets up the timers for a game session.
	 *
	 * @param timeLimit
	 *            the time limit of this session
	 */
	protected abstract void setupTimer(int timeLimit);

	/**
	 * SidePanel displays the player status.
	 */
	protected class SidePanel extends Table {

		private Label nameLabel;
		private Image[] frameImages;
		private Image[] brickImages;
		private Image pickImage;
		private Image emptyPickImage;
		private Image tntImage;
		private Image emptyTntImage;

		SidePanel() {
			Table brickTable = new Table();
			Stack[] itemStacks = new Stack[3];

			nameLabel = new Label("", skin);
			frameImages = new Image[3];
			brickImages = new Image[10];
			pickImage = new Image(PICK_REGION);
			emptyPickImage = new Image(EMPTY_PICK_REGION);
			tntImage = new Image(TNT_REGION);
			emptyTntImage = new Image(EMPTY_TNT_REGION);
			pickImage = new Image(PICK_REGION);

			/* layout */
			this.add(nameLabel).height(48);
			this.row();

			for (int i = 0; i < 3; i++) {
				itemStacks[i] = new Stack();
				this.add(itemStacks[i]).size(210, 210);
				if (i < 2){
					this.row();
				}

				frameImages[i] = new Image(SELECTION_REGION);
				itemStacks[i].add(frameImages[i]);
			}

			itemStacks[0].add(brickTable);
			itemStacks[1].add(emptyPickImage);
			itemStacks[1].add(pickImage);
			itemStacks[2].add(emptyTntImage);
			itemStacks[2].add(tntImage);

			/* bricks */
			for (int i = 0; i < 10; i++) {
				brickImages[i] = new Image(BRICK_REGION);
				brickTable.add(brickImages[i]).size(48, 48);
				if ((i + 1) % 4 == 0){
					brickTable.row();
				}
			}
		}

		void setPlayerName(String name) {
			nameLabel.setText(name);
		}

		void updateBrick(int amount) {
			for (int i = 0; i < 10; i++){
				brickImages[i].setVisible(i < amount);
			}
		}

		void updateAction(PlayerModel.Action action) {
			for (int i = 0; i < 3; i++){
				frameImages[i].setVisible(false);
			}
			switch (action) {
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

		void updatePick(boolean hasPick) {
			pickImage.setVisible(hasPick);
			emptyPickImage.setVisible(!hasPick);
		}

		void updateTnt(boolean hasTnt) {
			tntImage.setVisible(hasTnt);
			emptyTntImage.setVisible(!hasTnt);
		}

	}

	/**
	 * ScoreBar displays the player score.
	 */
	protected class ScoreBar extends Table {

		private Scorebox box;
		private Label scoreLabel;

		ScoreBar(boolean alignLeft) {
			box = new Scorebox(WHITE_REGION);
			scoreLabel = new Label("0", skin);

			if (alignLeft) {
				this.add(box).size(60, 60).pad(4);
				this.add(scoreLabel);
			} else {
				this.add(scoreLabel);
				this.add(box).size(60, 60).pad(4);
			}
		}

		void setBoxColor(Color c) {
			box.setColor(c);
		}

		void update(int score) {
			scoreLabel.setText(String.valueOf(score));
		}

		private class Scorebox extends Image {

			Scorebox(TextureRegion region) {
				super(region);
			}

			public void draw(SpriteBatch batch, float parentAlpha) {
				Color old = batch.getColor();

				batch.setColor(this.getColor());
				super.draw(batch, parentAlpha);
				batch.setColor(old);
			}

		};

	}

	public class Settings {
		public Settings(int[] innerP1Controls, int[] innerP2Controls) {
			for(int i = 0; i<innerP1Controls.length;i++){
				p1Controls[i] = innerP1Controls[i];
				p2Controls[i] = innerP2Controls[i];
			}
		}
		public Settings() {

		}
	}
}
