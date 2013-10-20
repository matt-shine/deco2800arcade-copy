package deco2800.arcade.landInvaders;


import javax.swing.JFrame;

import com.badlogic.gdx.Screen;

import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.landInvaders.Screens.MenuScreen;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

//Main Class
@ArcadeGame(id = "landInvaders")
public class LandInvaders extends GameClient  {

	private static final Game GAME;

	public LandInvaders(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		MenuScreen w = new MenuScreen();
		//this.networkClient = networkClient;
		//HighscoreClient player1 = new HighscoreClient(player.getUsername(), GAME.id, networkClient);
		//player1.storeScore("points", invader.getHighScore());
		
	}
	
	public void resume() {
        super.resume();
}

static {
        GAME = new Game();
        GAME.id = "LandInvaders";
        GAME.name = "LandInvaders";
        GAME.description = "funny game!";
}
@Override
public Game getGame() {
        
        return GAME;
}

}
