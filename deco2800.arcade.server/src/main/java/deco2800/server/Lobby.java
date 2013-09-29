package deco2800.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
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


	/**
	 * Constructor - hidden to enforce single instance.
	 * 
	 */
	private Lobby() {
		this.lobbyGames = new ArrayList<LobbyMatch>();
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
	public ArrayList<LobbyMatch> getMatches() {
		ArrayList<LobbyMatch> copy = new ArrayList<LobbyMatch>();
		System.arraycopy(lobbyGames, 0, copy, 0, lobbyGames.size());
		return copy;
	}

	/** 
	 * Create a match to be displayed by the lobby.
	 * 
	 * @param gameId - the game
	 * @param user - Map <username, connection>  of the user
	 * @return - True if match created successfully, false otherwise.
	 */
	public boolean createMatch(String gameId, String username, Connection connection) {
		//TODO: handle user joining multiple matches?
		if (userHasMatch(username)) {
			return false;
		}
		/* Create the match and add to array of matches */
		LobbyMatch match = new LobbyMatch(gameId, username, connection);
		lobbyGames.add(match);
		return true;
	}

	public void joinMatch(LobbyMatch match, String gameId, String username, Connection connection) {

	}


	/**
	 * Checks if the user is hosting a match in the lobby.
	 * @param username - the username to check
	 * @return - true if user has a match in lobby, false otherwise.
	 */
	public boolean userHasMatch(String username) {
		for (int i = 0; i <lobbyGames.size(); i++) {
			if (lobbyGames.get(i).getHostUsername() == username) {
				return true; /* User  hosting a match in lobby */
			}
		}
		return false;
	}


	/**
	 * Removes the match from the lobby.
	 * @param gameId - the id of the game the match is for
	 */
	public void destroyMatch(String username) {
		for (int i = 0; i < lobbyGames.size(); i++) {
			if (lobbyGames.get(i).getHostUsername() == username) {
				lobbyGames.remove(i);
			}
		}
	}
}
