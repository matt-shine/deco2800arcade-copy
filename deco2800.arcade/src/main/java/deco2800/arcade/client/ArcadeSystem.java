package deco2800.arcade.client;

import deco2800.arcade.model.Game;

import java.util.Set;

//TODO commenting?
public class ArcadeSystem {
	
	public static String UI = "arcadeui";
	public static String OVERLAY = "arcadeoverlay";
	
	private static Arcade arcade = null;

    private static Set<Game> gameSet = null;
	
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

    public static Set<GameClient> getGameClientList() {
        return arcade.findPlayableGames();
    }
	
	public static void login(String username) {
		arcade.connectAsUser(username);
	}

    public static void requestGames() {
        arcade.requestGames();
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

    public static void updateGamesList(Set<Game> games) {
        gameSet = games;
    }

    public static Set<Game> getArcadeGames() {
        return gameSet;
    }
}
