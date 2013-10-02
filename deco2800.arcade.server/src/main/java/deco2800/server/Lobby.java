package deco2800.server;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchResponse;
/**
 * The Lobby class - Singleton Pattern.
 * 
 * Holds the current state of the lobby.
 * 
 * @author matt-shine
 *
 */
public class Lobby {

	/* The Lobby instance */
	private static Lobby instance;


	/* Holds the 'matches' in the lobby (gameid, <host username, host connection>)*/
	private ArrayList<LobbyMatch> lobbyGames;
	
	/* Holds the players who are connected to the lobby */
	private Map<Integer, Connection> connectedPlayers;


	/**
	 * Constructor - hidden to enforce single instance.
	 * 
	 */
	private Lobby() {
		this.lobbyGames = new ArrayList<LobbyMatch>();
		this.connectedPlayers = new HashMap<Integer, Connection>();
	}

	/**
	 * Get the singleton Lobby instance
	 * @return the Lobby
	 */
	public static Lobby instance() {
		if (instance == null) {
			instance = new Lobby();
		}
		return instance;
	}

	/**
	 * Returns the current matches in the lobby.
	 * @return - ArrayList of current matches  in the lobby.
	 */
//	public ArrayList<LobbyMatch> getMatches() {
//		ArrayList<LobbyMatch> copy = new ArrayList<LobbyMatch>();
//		System.arraycopy(lobbyGames, 0, copy, 0, lobbyGames.size());
//		return copy;
//	}
	
	public boolean sendMatchesToClient(Connection connection) {
		if (lobbyGames.size() == 0) {
			return false;
		} else {
			for (int i=0; i< lobbyGames.size(); i++) {
				ActiveMatchDetails match = new ActiveMatchDetails();
				match.gameId = lobbyGames.get(i).getGameId();
				match.hostPlayerId = lobbyGames.get(i).getHostPlayerId();
				match.matchId = lobbyGames.get(i).getMatchId().toString();
				connection.sendTCP(match);
				System.out.println("SENDING MATCH TO CLIENT");
			}
 		}
		
		return true;
	}
	

	/** 
	 * Create a match to be displayed by the lobby.
	 * 
	 * @param gameId - the game
	 * @param user - Map <username, connection>  of the user
	 * @return - True if match created successfully, false otherwise.
	 */
	public void createMatch(String gameId, int playerId, Connection connection) {
		//TODO: handle user joining multiple matches?
		if (userHasMatch(playerId)) {
			//return false;
		}
		/* Create the match and add to array of matches */
		LobbyMatch match = new LobbyMatch(gameId, playerId, connection);
		lobbyGames.add(match);
		CreateMatchResponse response = new CreateMatchResponse();
		response.matchId = match.getMatchId().toString();
		connection.sendTCP(response);
		System.out.println("[SERVER] Create Match Response sent.");
		
		this.sendGamesToLobbyUsers();
	}

	public void joinMatch(LobbyMatch match, String gameId, int playerId, Connection connection) {

	}
	
	public void addPlayerToLobby(int playerId, Connection connection) {
		if (connectedPlayers.containsKey(playerId)) {
			//Players already in the lobby list - remove to re-add
			connectedPlayers.remove(playerId);
		}
		connectedPlayers.put(playerId, connection);
	}

	public void removePlayerFromLobby(int playerId, Connection connection) {
		if (connectedPlayers.containsKey(playerId)) {
			connectedPlayers.remove(playerId);
		}
	}
	
	/**
	 * Sends the current active matches (i.e. matches with a host and a free
	 * 	spot) to the users in the lobby.
	 * 
	 * Currently called exclusively by the ArcadeServer every 15 seconds.
	 */
	public void sendGamesToLobbyUsers() {
		System.out.println("[SERVER] in sendGamesToLobbyUsers, connectedPlayers: " + connectedPlayers.size() + ", lobbyGames: " + lobbyGames.size());
		if (connectedPlayers.size() > 0 && lobbyGames.size() > 0) {
		
			for (int i=0;i<connectedPlayers.size();i++) {
				for (int j=0;j<lobbyGames.size();j++) {
					/* Create the ActiveMatchDetails object to send */
					ActiveMatchDetails amd = new ActiveMatchDetails();
					amd.gameId = lobbyGames.get(j).getGameId();
					amd.hostPlayerId = lobbyGames.get(j).getHostPlayerId();
					amd.matchId = lobbyGames.get(j).getMatchId().toString();
					
					/* Send it to the client */
					connectedPlayers.get(i).sendTCP(amd);
				}
			}
		}
	}

	/**
	 * Checks if the user is hosting a match in the lobby.
	 * @param username - the username to check
	 * @return - true if user has a match in lobby, false otherwise.
	 */
	public boolean userHasMatch(int playerId) {
		for (int i = 0; i <lobbyGames.size(); i++) {
			if (lobbyGames.get(i).getHostPlayerId() == playerId) {
				return true; /* User  hosting a match in lobby */
			}
		}
		return false;
	}


	/**
	 * Removes the match from the lobby.
	 * @param gameId - the id of the game the match is for
	 */
	public void destroyMatch(int playerId) {
		for (int i = 0; i < lobbyGames.size(); i++) {
			if (lobbyGames.get(i).getHostPlayerId() == playerId) {
				lobbyGames.remove(i);
			}
		}
		this.sendGamesToLobbyUsers();
	}
}
