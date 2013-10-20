package deco2800.arcade.client;

import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.model.Game;

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
	 * 
	 * @param a: Arcade
	 */
	public static void setArcadeInstance(Arcade a) {
		arcade = a;
	}

	/**
	 * Start Game
	 * 
	 * @param gameid: Game ID
	 */
	public static void goToGame(String gameid) {
		arcade.stopGame();
		arcade.startGame(gameid);
	}

	public static void goToGame(String gameid, int lobbySession) {
		arcade.stopGame();
		arcade.startGame(gameid);
		arcade.getCurrentGame().setLobbySession(lobbySession);
		System.out.println("Game with lobby: " + arcade.getCurrentGame().getGame().getName());
	}
	
	/**
	 * Start GameClient
	 * 
	 * @param gameClient: GameClient to start
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
	 * 
	 * @return Set of playable IDs
	 */
	public static Set<String> getGamesList() {
		return arcade.findPlayableIds();
	}

	/**
	 * Find playable games
	 * 
	 * @return Set of Playable Game Clients
	 */
	public static Set<GameClient> getGameClientList() {
		return arcade.findPlayableGames();
	}

    /**
     * Debug login command
     * @param username User to login
     */
	public static void login(String username) {
		arcade.connectAsUser(username, "");
	}
	
	/**
     * Login to arcade
     * @param username User to login
     * @param password password for login
     */
	public static void login(String username, String password) {
		arcade.connectAsUser(username, password);
	}
	
	/**
     * Login to arcade
     * @param username User to login
     * @param password password for login
     */
	public static void registerUser(String username, String password) {
		arcade.registerAsUser(username, password);
	}

	/**
	 * Request Games from arcade
	 */
	public static void requestGames() {
		arcade.requestGames();
	}

	/**
	 * Check if the arcade has a player
	 * 
	 * @return true if logged in, false otherwise
	 */
	public static boolean isLoggedIn() {
		return arcade.hasPlayer();
	}
	
	/**
     * Adds listener to client
	 * @param listener 
     * @return true if logged in, false otherwise
     */
	public static void addListener(NetworkListener listener) {
		arcade.getClient().addListener(listener);
	}

	/**
	 * Exit Arcade
	 */
	public static void exit() {
		arcade.arcadeExit();
	}

	/**
	 * Get the current Game Client
	 * 
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
	
	public static ArrayList<ArrayList<Object>> requestCurrentMultiplayerList() {
		arcade.requestActiveGames();
		return arcade.getActiveGames();
	}

	/**
	 * Forwards on a request from the game to create a new multiplayer game
	 * 
	 * @param multiGameRequest: the request for the game
	 */
	public static void createMultiplayerGame(
			NewMultiGameRequest multiGameRequest) {
		arcade.createMultiplayerGame(multiGameRequest);
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

	public static void setGameWaiting(boolean b) {
		arcade.setGameWaiting(b);
	}

	public static boolean isGameWaiting() {
		return arcade.isGameWaiting();
	}

	public static void setMatchMaking2(boolean b) {
		arcade.setMatchMaking2(b);
	}

	public static boolean isMatchMaking2() {
		return arcade.isMatchMaking2();
	}

	public static void initializeLobbyMatchList() {
		arcade.populateMatchList();
	}

	/**
	 * Creates a new multiplayer game based off a response from the server
	 * 
	 * @param response: The server's response
	 */
	public static void newMultiplayerGame(NewMultiSessionResponse response) {
		String gameID = response.gameId;
		goToGame(gameID);

	}

	/**
	 * Update set of available games
	 * 
	 * @param games: Set of Games
	 */
	public static void updateGamesList(Set<Game> games) {
		gameSet = games;
	}

	/**
	 * Get the set of Arcade Games
	 * 
	 * @return Set of Arcade Games
	 */
	public static Set<Game> getArcadeGames() {
		return gameSet;
	}

	public static boolean isBettingLobby() {
		return arcade.isBettingLobby();
	}

	public static void setBettingLobby(boolean b) {
		arcade.setBettingLobby(b);
	}

}
