package deco2800.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.PlayerGameStorage;



public class MatchmakerQueue {

	/* Players currently in the matchmaking queue - gameId, map of usernames/connections */
	private ArrayList<ArrayList<Object>> queuedUsers;
	private Map<Integer, MultiplayerServer> activeServers;
	PlayerGameStorage database;
	private int serverNumber;

	/* Singleton */
	private static MatchmakerQueue instance;


	public MatchmakerQueue() {
		this.queuedUsers = new ArrayList<ArrayList<Object>>();
		this.activeServers = new HashMap<Integer, MultiplayerServer>();
		this.database = new PlayerGameStorage();
		this.serverNumber = 0;
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
	public ArrayList<ArrayList<Object>> getQueue() {
		ArrayList<ArrayList<Object>> map = new ArrayList<ArrayList<Object>>(queuedUsers);
		return map;
	}

	/**
	 * Returns a copy of the given games current queue.
	 * @param gameId - the gameId of the game.
	 * @return the queue for the given game.
	 */
	public Map<Integer, Connection> getGameQueue(String gameId) {
		//Map<Integer, Connection> map = new HashMap<Integer, Connection>(queuedUsers.get(gameId));
		//return map;
		return null;
	}

	/**
	 * Add a user to the matchmaking queue.
	 * @param username - the players username
	 * @param connection - the players connection
	 */
	private void add(NewMultiGameRequest request, Connection connection) {
		//TODO: handle players already in queue? allow multi-game queue placement?
		ArrayList<Object> player = new ArrayList<Object>();
		player.add(request.gameId);
		player.add(System.currentTimeMillis());
		player.add(request.playerID);
		player.add(connection);
		queuedUsers.add(player);
	}

	/**
	 * Removes the given username from the matchmaking queue (all games)
	 * @param username - the players username
	 */
	public void remove(Integer playerID) {
		/* Might be a more efficient way to do this (will gameId be available?) */
		for (int i = 0; i < queuedUsers.size(); i++) {
			if (queuedUsers.get(i).get(2)==playerID) {
				queuedUsers.remove(i);
				i--;
			}
		}
	}

	/**
	 * Removes the given username from the specified games matchmaking queue
	 * @param username - the players username
	 * @param gameId - the game queue to remove the player from
	 */
	public void remove(Integer playerID, String gameId) {
	/*	if (queuedUsers.containsKey(gameId)) {
			Map<Integer, Connection> entry = queuedUsers.get(gameId);
			if (entry.containsKey(playerID)) {
				entry.remove(playerID);
			}
		}*/
	}

	public void checkForGame(NewMultiGameRequest request, Connection connection) {
		int playerID = request.playerID;
		String gameId = request.gameId;
		for (int i = 0; i < queuedUsers.size(); i++) {
			if (!gameId.equals(queuedUsers.get(i).get(0))) {
				continue;
			}
			ArrayList<Object> player1 = new ArrayList<Object>();
			player1.add(gameId);
			player1.add(System.currentTimeMillis());
			player1.add(playerID);
			player1.add(connection);
			ArrayList<Object> player2 = queuedUsers.get(i);
			if (goodGame(player1, player2)) {
				MultiplayerServer gameServer = new MultiplayerServer((int)player1.get(2), (int)player2.get(2), (Connection) player1.get(3),
						(Connection) player2.get(3), (String) player1.get(1), serverNumber, this);
				activeServers.put(serverNumber, gameServer);
				queuedUsers.remove(i);
				NewMultiSessionResponse session = new NewMultiSessionResponse();
				session.sessionId = serverNumber;
				serverNumber++;
				session.gameId = gameId;
				connection.sendTCP(session);
				((Connection) player2.get(3)).sendTCP(session);
			}
			
			
			
		}
		
		
		
		
		
		
	/*	if (queuedUsers.get(gameId) == null) {
			ArrayList<Object> player = new ArrayList<Object>();
			player.add(System.currentTimeMillis());
			player.add(playerID);
			player.add(connection);
			queuedUsers.put(gameId, player);
			return;
		} else {
			Map<Integer, Connection> player2 = queuedUsers.get(gameId);
			Integer player2ID = (Integer) player2.keySet().toArray()[0];
			Connection player2Connection = player2.get(player2ID);			
			MultiplayerServer gameServer =  new MultiplayerServer(playerID, player2ID, connection, 
					player2Connection, gameId, serverNumber, this);

			activeServers.put(serverNumber, gameServer);
			queuedUsers.remove(gameId);
			NewMultiSessionResponse session = new NewMultiSessionResponse();
			session.sessionId = serverNumber;
			serverNumber++;
			session.gameId = gameId;
			connection.sendTCP(session);
			player2Connection.sendTCP(session);
		}*/

	}
	
	private boolean goodGame(ArrayList<Object> player1, ArrayList<Object> player2) {
		int p1Rating = 0, p2Rating = 0, ratingDiff;
		long timeAllowance;
		try {
			p1Rating = database.getPlayerRating((int)player1.get(2),(String)player1.get(0));
			p2Rating = database.getPlayerRating((int)player2.get(2),(String)player2.get(0));
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (p1Rating == 0 || p2Rating == 0) {
			p1Rating = 1500;
			p2Rating = 1500;
		}
		timeAllowance = (((System.currentTimeMillis() - (long) player2.get(1)) / 60000)+1)*100;
		if (p1Rating >= p2Rating) {
			ratingDiff = p1Rating - p2Rating;
		} else {
			ratingDiff = p2Rating - p1Rating;
		}
		if (ratingDiff > timeAllowance && timeAllowance  < 500) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
	
	
	
	
	
	
	
	public void gameOver(int session, int player1ID, int player2ID, String gameID, int winner) {
		int player1Rating = 0;
		int player2Rating = 0;
		try {
			player1Rating = database.getPlayerRating(player1ID, gameID);
			player2Rating = database.getPlayerRating(player2ID, gameID);
		} catch (Exception e) {
			System.out.println(e);
		}		
		int[] newScore = elo(player1ID, player1Rating, player2ID, player2Rating, winner);
		try {
			database.updatePlayerRating(player1ID, gameID, newScore[0]);
			database.updatePlayerRating(player1ID, gameID, newScore[1]);
		} catch (Exception e) {
			System.out.println(e);
		}
		dropServer(session);
	}
	
	public void dropServer(int session) {
		activeServers.remove(session);
	}
	
	public Map<Integer, MultiplayerServer> getActiveServers() {
		return activeServers;
	}
	
	
	
	
	public int[] elo(int p1ID, int p1Rating, int p2ID, int p2Rating, int winner) {
		int k = 32;
		int[] newElo = new int[2];
		if (p1Rating < 2100 || p2Rating < 2100) {
			k = 32;
		} else if ((p1Rating < 2401 && p1Rating >= 2100) || (p2Rating < 2401 && p1Rating >= 2100)) {
			k = 24;
		} else {
			k = 16;
		}
		if (winner == p1ID) {
			double score;
			double diff = p1Rating - p2Rating;
			score = k * (1 - (1.0 / (Math.pow(10, (-(diff / 400) + 1)))));			
			newElo[0] = (int) Math.floor(p1Rating += score);
			score = k * (0 - (1.0 / (Math.pow(10, (-(diff / 400) + 1)))));
			newElo[1] = (int) Math.floor(p2Rating += score);
		} else if (winner == p2ID) {
			double score;
			double diff = p1Rating - p2Rating;
			score = k * (0 - (1.0 / (Math.pow(10, (-(diff / 400) + 1)))));			
			newElo[0] = (int) Math.floor(p1Rating += score);
			score = k * (1 - (1.0 / (Math.pow(10, (-(diff / 400) + 1)))));
			newElo[1] = (int) Math.floor(p2Rating += score);
		} else {
			newElo[0] = p1Rating;
			newElo[1] = p2Rating;
		}
		
		

		return newElo;
		
	}
	
	

}
