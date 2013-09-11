package deco2800.arcade.landInvaders;


import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

//Main Class
//@ArcadeGame(id = "LandInvaders")
//public class LandInvaders extends GameClient  {
public class LandInvaders {
	private static final Game GAME;

	public LandInvaders(Player player, NetworkClient networkClient) {
		//super(player, networkClient);
		Invaders invader = new Invaders();
	}

	public void resume() {
		//super.resume();
	}

	static {
		GAME = new Game();
		GAME.id = "LandInvaders";
		GAME.name = "LandInvaders";
		GAME.description = "funny game!";
	}
	//@Override
	public Game getGame() {
		
		return GAME;
	}

}
