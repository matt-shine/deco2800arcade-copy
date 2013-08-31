package deco2800.server;

import java.util.HashMap;
import java.util.Map;
import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.NewMatchmakingRequest;

public class MatchmakerQueue {

	/* Players currently in the matchmaking queue - gameId, map of usernames/connections */
	private Map<String, Map<String, Connection>> queuedUsers;
	
	/* Singleton */
	private static MatchmakerQueue instance;
	
	
	private MatchmakerQueue() {
		this.queuedUsers = new HashMap<String, Map<String, Connection>>();
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
		
	
	/**
	 * Returns a copy of the (complete) current queue.
	 * @return
	 */
	public Map<String, Map<String, Connection>> getQueue() {
		Map<String, Map<String, Connection>> map = new HashMap<String, Map<String, Connection>>(queuedUsers);
		return map;
	}
	
	/**
	 * Returns a copy of the given games current queue.
	 * @param gameId - the gameId of the game.
	 * @return the queue for the given game.
	 */
	public Map<String, Connection> getGameQueue(String gameId) {
		Map<String, Connection> map = new HashMap<String, Connection>(queuedUsers.get(gameId));
		return map;
	}
	
	/**
	 * Add a user to the matchmaking queue.
	 * @param username - the players username
	 * @param connection - the players connection
	 */
	public void add(NewMatchmakingRequest request, Connection connection) {
		//TODO: handle players already in queue? allow multi-game queue placement?
		if (!this.queuedUsers.containsKey(request.gameId)) {
			Map<String, Connection> player = new HashMap<String, Connection>();
			player.put(request.username, connection);
			this.queuedUsers.put(request.gameId, player);
		} else {
			this.queuedUsers.get(request.gameId).put(request.username, connection);
		}
	}
	
	/**
	 * Removes the given username from the matchmaking queue (all games)
	 * @param username - the players username
	 */
	public void remove(String username) {
		/* Might be a more efficient way to do this (will gameId be available?) */
		for (Map.Entry<String, Map<String, Connection>> entry : queuedUsers.entrySet()) {
			Map<String, Connection> players = entry.getValue();
			for (Map.Entry<String, Connection> e : players.entrySet()) {
				if (e.getKey() == username) {
					queuedUsers.remove(e);
				}
			}
		}
	}
	
	/**
	 * Removes the given username from the specified games matchmaking queue
	 * @param username - the players username
	 * @param gameId - the game queue to remove the player from
	 */
	public void remove(String username, String gameId) {
		if (queuedUsers.containsKey(gameId)) {
			Map<String, Connection> entry = queuedUsers.get(gameId);
			if (entry.containsKey(username)) {
				entry.remove(username);
			}
		}
	}
	
	

	
}
