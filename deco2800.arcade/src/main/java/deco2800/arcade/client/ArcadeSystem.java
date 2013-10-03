package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.Set;

import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;

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
    
    public static void createMatch(NewMultiGameRequest matchRequest) {
    	arcade.createMatch(matchRequest);
    }
    
    public static void addPlayerToLobby() {
    	arcade.addPlayerToLobby();
    }
}
