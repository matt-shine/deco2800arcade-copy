package deco2800.arcade.mixmaze;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private int angle;

	public MixMaze(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {
		super.create();

		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false);

		shapeRenderer = new ShapeRenderer();

		angle = 0;

		System.out.println(Gdx.graphics.getHeight());
		System.out.println(Gdx.graphics.getWidth());

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);

		if (Gdx.input.isTouched()) {
			angle = (angle + 15) % 360;
			System.out.println("screen touched" + angle);
		}

		shapeRenderer.rotate(0, 0, 1, angle);

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);
		shapeRenderer.line(10, 10, 630, 470);
		shapeRenderer.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// This method will never be called on the desktop.
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
		// TODO Auto-generated method stub
		return game;
	}
}
