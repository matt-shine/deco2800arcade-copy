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

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

}
