package deco2800.arcade.junglejump.GUI;

import java.util.HashSet;
import java.util.Set;

import javax.security.auth.login.Configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * Main class for Jungle Jump Game Instantiates game with scene, player and
 * assets
 * 
 */
@ArcadeGame(id = "junglejump")
public class junglejump extends GameClient implements InputProcessor {
	PerspectiveCamera cam;
	Frustum camGone = new Frustum();
	private World world;
	// Store details about the activity of junglejump and the players
	public static final String messages = junglejump.class.getSimpleName();
	// FPS Animation helper
	private FPSLogger fpsLogger;
	private Player player;

	Music themeMusic;
	Sound jump, die, levelup, loselife, collect;

	/*
	 * I've added NetworkClient to the signature of your constructor, as you
	 * cannot reference it if you do not. - Leggy
	 */
	public junglejump(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
		// Replace "file" with chosen music
		try {
			themeMusic = Gdx.audio.newMusic(Gdx.files.internal(("file")));
			themeMusic.setLooping(true);
			themeMusic.play();
		} catch (Exception e) {
			Gdx.app.log(junglejump.messages,
					"Audio File for Theme Music Not Found");
		}
		// TODO: Additional sound files will be loaded here (jump, die, levelup
		// etc)

		createWorld();

	}

	/*
	 * World for holding the Jungle Jump Game Includes Physics for handling
	 * movement and gravity
	 */
	private void createWorld() {
		world = new World(new Vector2(0, 0), false);
		// TODO: Add vectors for containing the world and its players/assets
	}

	@Override
	public void create() {
		super.create();
		Gdx.app.log(junglejump.messages, "Launching Game");
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public void resize(int w, int h) {
		Gdx.app.log(junglejump.messages, "Resizing game width " + w
				+ " height " + h);
	}

	public void render() {
		// Clears the screen - not sure if this is needed
		Gdx.gl.glClearColor(0f, 1f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Logs current FPS
		fpsLogger.log();
	}

	@Override
	public void pause() {
		super.pause();
		Gdx.app.log(junglejump.messages, "Pause");
	}

	@Override
	public void resume() {
		super.resume();
		Gdx.app.log(junglejump.messages, "Resume");
	}

	private static final Game game;
	static {
		game = new Game();
		game.id = "junglejump";
		game.name = "Jungle Jump";
	}

	public Game getGame() {
		return game;
	}

	private void processInput() {

	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
