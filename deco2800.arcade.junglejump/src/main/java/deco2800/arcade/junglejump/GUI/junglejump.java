package main.java.deco2800.arcade.junglejump.GUI;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;

/**
 * Main class for Jungle Jump Game
 * Instantiates game with scene, player and assets
 *
 */
@ArcadeGame(id="junglejump")
public class junglejump extends GameClient {
	// Store details about the activity of junglejump and the players
	public static final String messages = junglejump.class.getSimpleName();
	// FPS Animation helper
	private FPSLogger fpsLogger;

	public junglejump(Player player){
		super(player, networkClient);
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
		Gdx.app.log(junglejump.messages, "Resizing game width " + w + " height " + h); 
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
	
}
