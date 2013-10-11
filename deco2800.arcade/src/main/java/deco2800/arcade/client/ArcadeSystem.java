package deco2800.arcade.client;

import deco2800.arcade.model.Game;

import java.util.Set;

public class ArcadeSystem {
	
	public static String UI = "arcadeui";
	public static String OVERLAY = "arcadeoverlay";
	
	private static Arcade arcade = null;

    private static Set<Game> gameSet = null;

    /**
     * Set Arcade Instance
     * @param a Arcade
     */
	public static void setArcadeInstance(Arcade a) {
		arcade = a;
	}

    /**
     * Start Game
     * @param gameid Game ID
     */
	public static void goToGame(String gameid) {
		arcade.stopGame();
		arcade.startGame(gameid);
	}

    /**
     * Start GameClient
     * @param gameClient GameClient to start
     */
    public static void goToGame(GameClient gameClient) {
        arcade.stopGame();
        arcade.setGame(gameClient);
        arcade.startGame(gameClient);
    }

    /**
     * Open connection to server
     */
	public static void openConnection() {
		try {
			arcade.connectToServer();
		} catch (ArcadeException e) {
			e.printStackTrace();
		}
	}

    /**
     * Get List of Playable Games
     * @return Set of playable IDs
     */
	public static Set<String> getGamesList() {
		return arcade.findPlayableIds();
	}

    /**
     * Find playable games
     * @return Set of Playable Game Clients
     */
    public static Set<GameClient> getGameClientList() {
        return arcade.findPlayableGames();
    }

    /**
     * Login to arcade
     * @param username User to login
     */
	public static void login(String username) {
		arcade.connectAsUser(username);
	}

    /**
     * Request Games from arcade
     */
    public static void requestGames() {
        arcade.requestGames();
    }

    /**
     * Check if the arcade has a player
     * @return true if logged in, false otherwise
     */
	public static boolean isLoggedIn() {
		return arcade.hasPlayer();
	}

    /**
     * Exit Arcade
     */
    public static void exit() {
        arcade.arcadeExit();
    }

    /**
     * Get the current Game Client
     * @return GameClient of current game
     */
	public static GameClient getCurrentGame() {
		return arcade.getCurrentGame();
	}

    /**
     * Update set of available games
     * @param games Set of Games
     */
    public static void updateGamesList(Set<Game> games) {
        gameSet = games;
    }

    /**
     * Get the set of Arcade Games
     * @return Set of Arcade Games
     */
    public static Set<Game> getArcadeGames() {
        return gameSet;
    }
}
