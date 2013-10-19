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
import deco2800.arcade.protocol.lobby.ClearListRequest;
import deco2800.arcade.protocol.lobby.CreateMatchResponse;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchRequest;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchResponse;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchResponseType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
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

	private int matchIdCounter;

	/**
	 * Constructor - hidden to enforce single instance.
	 * 
	 */
	private Lobby() {
		this.lobbyGames = new ArrayList<LobbyMatch>();
		this.connectedPlayers = new HashMap<Integer, Connection>();
		this.matchIdCounter = 0;
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
		LobbyMatch match = new LobbyMatch(gameId, playerId, connection, this.matchIdCounter);
		this.matchIdCounter++;
		lobbyGames.add(match);
		CreateMatchResponse response = new CreateMatchResponse();
		response.matchId = match.getMatchId();
		connection.sendTCP(response);
		
		this.sendGamesToLobbyUsers();
	}

	public void joinMatch(int matchId, int playerId, Connection connection) {
		LobbyMatch match = getMatchById(matchId);
		if (match == null) {
			/* Match wasn't found */
			JoinLobbyMatchResponse response = new JoinLobbyMatchResponse();
			response.responseType = JoinLobbyMatchResponseType.NOTFOUND;
			connection.sendTCP(response);
		} else {
			/* Match found */
			MatchmakerQueue.instance().addLobbyGame(match.getHostPlayerId(), playerId, match.getHostConnection(), connection, match.getGameId());
			JoinLobbyMatchResponse response = new JoinLobbyMatchResponse();
			response.responseType = JoinLobbyMatchResponseType.OK;
			connection.sendTCP(response);
		}
	}
	
	/**
	 * Returns the LobbyMatch instance of the given id.
	 * 
	 * @param matchId
	 * @return
	 */
	private LobbyMatch getMatchById(int matchId) {
		for (int i = 0; i < lobbyGames.size(); i++) {
			LobbyMatch current = lobbyGames.get(i);
			if (current.getMatchId() == matchId) {
				return current;
			}
		}
		return null;
	}
	
	/**
	 * Add a player to the list of lobby players
	 * @param playerId
	 * @param connection
	 */
	public void addPlayerToLobby(int playerId, Connection connection) {
		if (connectedPlayers.containsKey(playerId)) {
			//Players already in the lobby list - remove to re-add
			connectedPlayers.remove(playerId);
		}
		connectedPlayers.put(playerId, connection);
	}
	
	/**
	 * Remove a player from the list of lobby players
	 * @param playerId
	 * @param connection
	 */
	public void removePlayerFromLobby(int playerId) {
		if (connectedPlayers.containsKey(playerId)) {
			connectedPlayers.remove(playerId);
			System.out.println("Removed player " + playerId + " from lobby");
		}
	}
	
	/**
	 * Sends the current active matches (i.e. matches with a host and a free
	 * 	spot) to the users in the lobby.
	 * 
	 * Currently unused.
	 */
	public void sendGamesToLobbyUsers() {
		if (connectedPlayers.size() > 0 && lobbyGames.size() > 0) {
		
		
			for (int i=0;i<connectedPlayers.size();i++) {
				ClearListRequest clr = new ClearListRequest();
				connectedPlayers.get(i).sendTCP(clr);
				for (int j=0;j<lobbyGames.size();j++) {
					/* Create the ActiveMatchDetails object to send */
					ActiveMatchDetails amd = new ActiveMatchDetails();
					amd.gameId = lobbyGames.get(j).getGameId();
					amd.hostPlayerId = lobbyGames.get(j).getHostPlayerId();
					amd.matchId = lobbyGames.get(j).getMatchId();
					
					/* Send it to the client */
					connectedPlayers.get(i).sendTCP(amd);
				}
			}
		}
	}
	
	/**
	 * Sends the current active matches (i.e. matches with a host and 
	 * a free spot) to the specified user.
	 * @param playerId - The id of the player to send the matches to.
	 */
	public void sendGamesToLobbyUser(int playerId) {

		/* Send a clear list request to the players client */
		ClearListRequest clr = new ClearListRequest();
		connectedPlayers.get(playerId).sendTCP(clr);
		
		/* Now send the matches through to the client */
		for (int i=0;i<lobbyGames.size();i++) {
			/* Create the ActiveMatchDetails object to send */
			ActiveMatchDetails amd = new ActiveMatchDetails();
			amd.gameId = lobbyGames.get(i).getGameId();
			amd.hostPlayerId = lobbyGames.get(i).getHostPlayerId();
			amd.matchId = lobbyGames.get(i).getMatchId();
			
			/* Send it to the client */
			connectedPlayers.get(playerId).sendTCP(amd);
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
