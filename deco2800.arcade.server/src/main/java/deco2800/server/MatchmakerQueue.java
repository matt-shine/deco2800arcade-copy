package deco2800.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import multiplayerGame.NewMultiGameRequest;
import multiplayerGame.NewMultiSessionResponse;

import com.esotericsoftware.kryonet.Connection;



public class MatchmakerQueue {

	/* Players currently in the matchmaking queue - gameId, map of usernames/connections */
	private Map<String, Map<String, Connection>> queuedUsers;

	private ArrayList<MultiplayerServer> activeServers;

	/* Singleton */
	private static MatchmakerQueue instance;


	public MatchmakerQueue() {
		this.queuedUsers = new HashMap<String, Map<String, Connection>>();
		this.activeServers = new ArrayList<MultiplayerServer>();
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
	private void add(NewMultiGameRequest request, Connection connection) {
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

	public void checkForGame(NewMultiGameRequest request, Connection connection) {
		String username = request.username;
		String gameId = request.gameId;
		System.out.println("gameID: " + gameId);
		System.out.println("size: " + queuedUsers.size());
		if (queuedUsers.get(gameId) == null) {
			Map<String, Connection> userConnection = new HashMap<String, Connection>();
			userConnection.put(username, connection);
			queuedUsers.put(gameId, userConnection);
			return;
		} else {
			Map<String, Connection> player2 = queuedUsers.get(gameId);
			String player2Name = (String) player2.keySet().toArray()[0];
			Connection player2Connection = player2.get(player2Name);			
			MultiplayerServer gameServer =  new MultiplayerServer(username, player2Name, connection, 
					player2Connection, gameId, activeServers.size());

			activeServers.add(gameServer);
			queuedUsers.remove(gameId);
			NewMultiSessionResponse session = new NewMultiSessionResponse();
			session.sessionId = activeServers.size()-1;
			session.gameId = gameId;
			//session.playerID = username; STRINGS OR INTS
			connection.sendTCP(session);
			player2Connection.sendTCP(session);
		}

	}

	public ArrayList<MultiplayerServer> getActiveServers() {
		return activeServers;
	}

}
