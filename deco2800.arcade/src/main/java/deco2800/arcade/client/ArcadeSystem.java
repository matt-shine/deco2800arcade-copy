package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.Set;

import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;

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
	
    public static void setMultiplayerEnabled(boolean b) {
    	arcade.setMultiplayerEnabled(b);
    }
    
    public static boolean isMultiplayerEnabled() {
    	return arcade.isMultiplayerEnabled();
    }
    
    public static ArrayList<ActiveMatchDetails> requestLobbyGamesList() {
    	return arcade.getMatches();
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

	public static void setPlayerBetting(boolean b) {
		arcade.setPlayerBetting(b);
		
	}
	 public static boolean isPlayerBetting() {
	    	return arcade.isPlayerBetting();
	    }
    
    public static void newMultiplayerGame(NewMultiSessionResponse response) {
    	int playerID = response.playerID;
    	String gameID = response.gameId;
    	int session = response.sessionId;
    	goToGame(gameID);
    	
    }

}
