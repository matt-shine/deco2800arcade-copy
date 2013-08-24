package deco2800.arcade.junglejump.GUI;

import java.util.HashSet;
import java.util.Set;

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
public class JungleJump extends GameClient {
	
	public JungleJump(Player player){
		super(player);
	}

	@Override
	public void create() {	
		super.create();
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
