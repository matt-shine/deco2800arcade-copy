package main.java.deco2800.arcade.junglejump.GUI;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;

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
	public static final String messages = junglejump.class.getSimpleName();
	
	
	
	
	
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
	
	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
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
