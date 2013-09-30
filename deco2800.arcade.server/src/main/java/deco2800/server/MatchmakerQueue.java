package deco2800.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;



public class MatchmakerQueue {

	/* Players currently in the matchmaking queue - gameId, map of usernames/connections */
	private Map<String, Map<Integer, Connection>> queuedUsers;

	private ArrayList<MultiplayerServer> activeServers;

	/* Singleton */
	private static MatchmakerQueue instance;


	public MatchmakerQueue() {
		this.queuedUsers = new HashMap<String, Map<Integer, Connection>>();
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
	public Map<String, Map<Integer, Connection>> getQueue() {
		Map<String, Map<Integer, Connection>> map = new HashMap<String, Map<Integer, Connection>>(queuedUsers);
		return map;
	}

	/**
	 * Returns a copy of the given games current queue.
	 * @param gameId - the gameId of the game.
	 * @return the queue for the given game.
	 */
	public Map<Integer, Connection> getGameQueue(String gameId) {
		Map<Integer, Connection> map = new HashMap<Integer, Connection>(queuedUsers.get(gameId));
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
			Map<Integer, Connection> player = new HashMap<Integer, Connection>();
			player.put(request.playerID, connection);
			this.queuedUsers.put(request.gameId, player);
		} else {
			this.queuedUsers.get(request.gameId).put(request.playerID, connection);
		}

	}

	/**
	 * Removes the given username from the matchmaking queue (all games)
	 * @param username - the players username
	 */
	public void remove(Integer playerID) {
		/* Might be a more efficient way to do this (will gameId be available?) */
		for (Map.Entry<String, Map<Integer, Connection>> entry : queuedUsers.entrySet()) {
			Map<Integer, Connection> players = entry.getValue();
			for (Map.Entry<Integer, Connection> e : players.entrySet()) {
				if (e.getKey() == playerID) {
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
	public void remove(Integer playerID, String gameId) {
		if (queuedUsers.containsKey(gameId)) {
			Map<Integer, Connection> entry = queuedUsers.get(gameId);
			if (entry.containsKey(playerID)) {
				entry.remove(playerID);
			}
		}
	}

	public void checkForGame(NewMultiGameRequest request, Connection connection) {
		int playerID = request.playerID;
		String gameId = request.gameId;
		System.out.println("gameID: " + gameId);
		System.out.println("size: " + queuedUsers.size());
		if (queuedUsers.get(gameId) == null) {
			Map<Integer, Connection> userConnection = new HashMap<Integer, Connection>();
			userConnection.put(playerID, connection);
			queuedUsers.put(gameId, userConnection);
			return;
		} else {
			Map<Integer, Connection> player2 = queuedUsers.get(gameId);
			Integer player2ID = (Integer) player2.keySet().toArray()[0];
			Connection player2Connection = player2.get(player2ID);			
			MultiplayerServer gameServer =  new MultiplayerServer(playerID, player2ID, connection, 
					player2Connection, gameId, activeServers.size());

			activeServers.add(gameServer);
			queuedUsers.remove(gameId);
			NewMultiSessionResponse session = new NewMultiSessionResponse();
			session.sessionId = activeServers.size()-1;
			session.gameId = gameId;
			connection.sendTCP(session);
			player2Connection.sendTCP(session);
		}

	}

	public ArrayList<MultiplayerServer> getActiveServers() {
		return activeServers;
	}

}
