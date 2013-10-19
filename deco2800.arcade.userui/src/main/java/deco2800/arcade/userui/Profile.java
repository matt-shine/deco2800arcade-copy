package deco2800.arcade.userui;

import javax.swing.SwingUtilities;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.userui.controller.ControllerMain;
import deco2800.arcade.userui.view.UserScreen;


@ArcadeGame(id = "profileui")
public class Profile extends GameClient {
	
	public Profile(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

	/**
	 * 
	 * This class calls model, view and controller classes
	 * 
	 */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				runApp();
			}
			
			
		});
	}
	
	public static void runApp() {
		
		Model theModel = new Model();
		
		UserScreen userView = new UserScreen(theModel);		
		ControllerMain maincontroller = new ControllerMain(theModel,userView);

	}
	
	private static final Game game;
	static {
		game = new Game();
		game.id = "profileui";
		game.name = "Profile UI";
	}

	@Override
	public Game getGame() {
		return game;
	}

}
