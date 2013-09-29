package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.Direction;
import deco2800.arcade.mixmaze.domain.PlayerModel;
import deco2800.arcade.mixmaze.domain.WallModel;
import deco2800.arcade.mixmaze.domain.view.IMixMazeModel;
import deco2800.arcade.mixmaze.domain.view.IMixMazeModel.Difficulty;
import deco2800.arcade.mixmaze.domain.view.IPlayerModel;
import deco2800.arcade.mixmaze.domain.view.ITileModel;
import deco2800.arcade.mixmaze.domain.view.IWallModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static deco2800.arcade.mixmaze.TileViewModel.*;
import static deco2800.arcade.mixmaze.domain.view.IPlayerModel.PlayerAction.*;
import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.*;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.*;

/**
 * GameScreen draws all elements in a game session.
 */
abstract class GameScreen implements Screen {

	final Logger logger = LoggerFactory.getLogger(GameScreen.class);

	protected final Stage stage;
	protected final ShapeRenderer renderer;

	protected Group gameArea;
	protected PlayerViewModel p1;
	protected PlayerViewModel p2;
	protected IMixMazeModel model;
	protected Table tileTable;
	protected int countdown;
	protected ObjectSpace os;
	protected Label timerLabel;
	protected Table endGameTable;
	protected TextButton backMenu;
	protected Label resultLabel;
	protected SidePanel left;
	protected SidePanel right;
	private Scorebar[] scorebar;

	private final MixMaze game;
	private final Skin skin;

	/**
	 * Constructor
	 *
	 * @param game	the MixMaze game
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

		scorebar = new Scorebar[2];
		scorebar[0] = new Scorebar(true);
		scorebar[1] = new Scorebar(false);

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

	protected abstract void setupGameBoard();

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.13f, 0.13f, 0.13f, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		model.spawnItems();

		/* update player status */
		left.update(p1);
		right.update(p2);
		scorebar[0].update(p1);
		scorebar[1].update(p2);

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
		Gdx.input.setInputProcessor(null);
		logger.debug("hided");
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	protected void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(IMixMazeModel.class);
		kryo.register(IWallModel.class);
		kryo.register(ITileModel.class);
		kryo.register(IPlayerModel.class);
	}

	/*
	 * Draw the player item status on side panel.
	 */
	private class SidePanel extends Table {
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
			this.add(nameLabel);
			this.row();

			for (int i = 0; i < 3; i++) {
				itemStacks[i] = new Stack();
				this.add(itemStacks[i]).size(210, 210);
				if (i < 2)
					this.row();

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
				brickTable.add(brickImages[i]).size(52, 52);
				if ((i + 1) % 4 == 0)
					brickTable.row();
			}
		}

		void update(PlayerViewModel p) {
			nameLabel.setText("Player " + p.getId());

			for (int i = 0, n = p.getBrickAmount(); i < 10; i++)
				brickImages[i].setVisible(i < n);
			pickImage.setVisible(p.hasPick());
			emptyPickImage.setVisible(!p.hasPick());
			tntImage.setVisible(p.hasTNT());
			emptyTntImage.setVisible(!p.hasTNT());

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

	private class Scorebar extends Table {

		private Scorebox box;
		private Label scoreLabel;

		Scorebar(boolean alignLeft) {
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

		void update(PlayerViewModel p) {
			box.setColor(p.getColor());
			scoreLabel.setText("" + p.getScore());
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

	public class Settings{
		protected int[] p1Controls = {Keys.W,Keys.S,Keys.A,Keys.D,Keys.G,Keys.H};
		protected int[] p2Controls = {Keys.UP,Keys.DOWN,Keys.LEFT,Keys.RIGHT,Keys.O,Keys.P};

		public Settings(int[] p1Controls,int[] p2Controls){
			this.p1Controls = p1Controls;
			this.p2Controls = p2Controls;
		}

		public Settings(){

		}
	}

}
