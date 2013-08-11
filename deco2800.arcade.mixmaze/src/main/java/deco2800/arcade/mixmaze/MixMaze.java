package deco2800.arcade.mixmaze;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

@ArcadeGame(id="mixmaze")
public class MixMaze extends GameClient {
	// class name for logging
	public static final String LOG = MixMaze.class.getSimpleName();

	public Screen menuScreen;
	public Screen gameScreen;

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private int angle;
	private FPSLogger fpsLogger;

	SpriteBatch batch;
	BitmapFont font;

	public MixMaze(Player player, NetworkClient networkClient) {
		super(player, networkClient);

		// System.out.println("LOG=" + LOG);
	}

	@Override
	public void create() {
		// set Application.LOG_NONE to mute all logging
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug(MixMaze.LOG, "Creating game");
		fpsLogger = new FPSLogger();

		super.create();

		batch = new SpriteBatch();
		font = new BitmapFont();

		this.setScreen(new MenuScreen(this));

		//// Initialise camera
		// camera = new OrthographicCamera();
		// camera.setToOrtho(false);

		// shapeRenderer = new ShapeRenderer();

		// angle = 0;

		// System.out.println(Gdx.graphics.getHeight());
		// System.out.println(Gdx.graphics.getWidth());

	}

	@Override
	public void dispose() {
		Gdx.app.debug(MixMaze.LOG, "Disposing game");
		super.dispose();
		//shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
	}

	@Override
	public void pause() {
		Gdx.app.debug(MixMaze.LOG, "Pausing game");
		super.pause();
	}

	@Override
	public void render() {
		super.render();

		// Gdx.gl.glClearColor(0, 0, 0, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// fpsLogger.log();

		// camera.update();
		// shapeRenderer.setProjectionMatrix(camera.combined);

		// if (Gdx.input.isTouched()) {
		// 	angle = (angle + 15) % 360;
		// 	System.out.println("screen touched" + angle);
		// }

		// shapeRenderer.rotate(0, 0, 1, angle);

		// shapeRenderer.begin(ShapeType.Line);
		// shapeRenderer.setColor(1, 1, 0, 1);
		// shapeRenderer.line(10, 10, 630, 470);
		// shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.debug(MixMaze.LOG, "Resizing game to "
				+ width + "x" + height);

		super.resize(width, height);
	}

	@Override
	public void resume() {
		// This method will never be called on the desktop.
		Gdx.app.debug(MixMaze.LOG, "Resuming game");
		super.resume();
	}

	private static Set<Achievement> achievements =
			new HashSet<Achievement>();

	private static final Game game;
	static {
		game = new Game();
		game.gameId = "mixmaze";
		game.name = "Mix Maze";
		game.availableAchievements = achievements;
	}

	@Override
	public Game getGame() {
		return game;
	}
}
