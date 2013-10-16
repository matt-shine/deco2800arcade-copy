package deco2800.arcade.client;


import java.util.ArrayList;
import java.util.Set;

import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;

//TODO commenting?
import deco2800.arcade.model.Game;

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
    public static void goToGame(GameClient gameClient, Boolean isMultiPlayer) {
        arcade.stopGame();
        arcade.setGame(gameClient);
        arcade.startGame(gameClient);
    }

    /**
     * Open connection to both server and file server
     */
	public static void openConnection() {
	    arcade.startConnection();
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
	
    public static void setMultiplayerEnabled(boolean b) {
    	arcade.setMultiplayerEnabled(b);
    }
    
    public static boolean isMultiplayerEnabled() {
    	return arcade.isMultiplayerEnabled();
    }
    
    public static ArrayList<ActiveMatchDetails> requestLobbyGamesList() {
    	return Arcade.getMatches();
    }
    
    public static void createMultiplayerGame(NewMultiGameRequest multigameRequest) {
    	arcade.createMultiplayerGame(multigameRequest);
    }
    
    public static void createMatch(CreateMatchRequest matchRequest) {
    	arcade.createMatch(matchRequest);
    }
    
    public static void addPlayerToLobby() {
    	arcade.addPlayerToLobby();
    }
    
    public static void removePlayerFromLobby() {
    	arcade.removePlayerFromLobby();
    }

	public static void setPlayerBetting(boolean b) {
		arcade.setPlayerBetting(b);
		
	}
	 public static boolean isPlayerBetting() {
	    	return arcade.isPlayerBetting();
    }
	 
	public static void setMatchMaking(boolean b) {
			arcade.setMatchMaking(b);	
		}
	
	public static boolean isMatchMaking() {
		    return arcade.isMatchMaking();
	    }
	 
	 public static void initializeLobbyMatchList() {
		 arcade.populateMatchList();
	 }
    
    public static void newMultiplayerGame(NewMultiSessionResponse response) {
    	int playerID = response.playerID;
    	String gameID = response.gameId;
    	int session = response.sessionId;
    	goToGame(gameID);
    	
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
