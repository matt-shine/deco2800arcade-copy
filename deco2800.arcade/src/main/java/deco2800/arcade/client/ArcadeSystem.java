package deco2800.arcade.client;

import java.util.Set;
import deco2800.arcade.model.Game;

//TODO commenting?
public class ArcadeSystem {
	
	public static String UI = "arcadeui";
	public static String OVERLAY = "arcadeoverlay";
	
	private static Arcade arcade = null;
	
	public static void setArcadeInstance(Arcade a) {
		arcade = a;
	}
	
	public static void goToGame(String gameid) {
		arcade.stopGame();
		arcade.startGame(gameid);
	}

    public static void goToGame(GameClient gameClient) {
        arcade.stopGame();
        arcade.setGame(gameClient);
        arcade.startGame(gameClient);
    }
	
	public static void openConnection() {
		try {
			arcade.connectToServer();
		} catch (ArcadeException e) {
			e.printStackTrace();
		}
	}
	
	public static Set<String> getGamesList() {
		return arcade.findPlayableIds();
	}

    public static Set<GameClient> getGameList() {
        return arcade.findPlayableGames();
    }
	
	public static void login(String username) {
		arcade.connectAsUser(username);
	}
	
	public static boolean isLoggedIn() {
		return arcade.hasPlayer();
	}

    public static void exit() {
        arcade.arcadeExit();
    }

	public static GameClient getCurrentGame() {
		return arcade.getCurrentGame();
	}
}
