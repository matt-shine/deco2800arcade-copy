package deco2800.arcade.pacman;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

@ArcadeGame(id="pacman")
public class Pacman extends GameClient {

	
	public Pacman(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
	}

	// Game variable for Pacman- used by the arcade
	private static final Game game;
	static {
		game = new Game();
		game.id = "pacman";
		game.name = "Pac man";
		game.description = "An implementation of the classic arcade game Pac "
		+ "man." + System.lineSeparator() + "Still in progress- additional " + 
		"features may be added later. Note: will not currently run.";
		// game.icon- to be added later once the icon part is fully implemented
	}
	
	public Game getGame() {
		return game;
	}
}
