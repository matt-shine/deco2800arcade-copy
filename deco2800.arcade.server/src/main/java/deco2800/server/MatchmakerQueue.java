package deco2800.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;

public class MatchmakerQueue {

	/* Players currently in the matchmaking queue */
	private Map<String, Map<String, Connection[]>> queuedUsers;
	
	/* Singleton */
	private static MatchmakerQueue instance;
	
	
	private MatchmakerQueue() {
		this.queuedUsers = new HashMap<String, Map<String, Connection[]>>();
		}
	
	/**
	 * Add a user to the matchmaking queue.
	 * @param username
	 * @param connection
	 */
	public void add(NewMultiGameRequest request) {
		//TODO: figure out how to handle players already in the queue.
		if (!this.queuedUsers.containsKey(request.username)) {
			Connection[] con;
			this.queuedUsers.put(request.username, request.gameId, request.connectTo);
		}
	}
	
	/**
	 * Get the singleton Matchmaker instance
	 * @return - the matchmaker instance
	 */
	public static MatchmakerQueue instance() {
		if (instance == null) {
			instance = new MatchmakerQueue();
		}
		return instance;
	}
	
	
}
