package deco2800.arcade.hunter;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.hunter.screens.SplashScreen;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.platformergame.PlatformerGame;

@ArcadeGame(id="hunter")
public class Hunter extends PlatformerGame {
	
	public int screenWidth = 1280;
	public int screenHeight = 720;

	public Hunter(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}
	
	@Override
	public void create() {
		setScreen(new SplashScreen(this));
	}
}
