package deco2800.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.esotericsoftware.kryonet.Connection;

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
	private Timer timer;

	/* Singleton */
	private static MatchmakerQueue instance;



	private MatchmakerQueue() {
		this.queuedUsers = new ArrayList<ArrayList<Object>>();
		this.activeServers = new HashMap<Integer, MultiplayerServer>();
		this.database = new PlayerGameStorage();
		this.serverNumber = 0;
		this.timer = new Timer();
		class ListTask extends TimerTask {
			public void run() {
				checkList();
			}
		}
		timer.schedule(new ListTask(), 0, 10000);
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
	 * Returns a copy of the current server list
	 */
	public Map<Integer, MultiplayerServer> getServerList() {
		Map<Integer, MultiplayerServer> map = new HashMap<Integer, MultiplayerServer>(activeServers);
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
		try {
			System.out.println("Rating: " + database.getPlayerRating(playerID, gameId));
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				launchGame(player1, player2);
				queuedUsers.remove(i);
				return;
			}		
		}
		add(request, connection);
	}
	
	public void addLobbyGame(int player1Id, int player2Id, Connection player1Connection, 
			Connection player2Connection, String gameId) {
			MultiplayerServer gameServer = new MultiplayerServer(player1Id, player2Id, player1Connection, 
					player2Connection, gameId, serverNumber);
			activeServers.put(serverNumber, gameServer);
			NewMultiSessionResponse session = new NewMultiSessionResponse();
			session.sessionId = serverNumber;
			serverNumber++;
			session.gameId = gameId;
			player1Connection.sendTCP(session);
			player2Connection.sendTCP(session);
	}
	
	private boolean goodGame(ArrayList<Object> player1, ArrayList<Object> player2) {
		int p1Rating = 0, p2Rating = 0, ratingDiff;
		long timeAllowance;
		int p1ID = (Integer)player1.get(2);
		int p2ID = (Integer)player2.get(2);
		String gameID = (String)player1.get(0);
		try {
			p1Rating = database.getPlayerRating(p1ID,gameID);
			p2Rating = database.getPlayerRating(p2ID,gameID);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (p1Rating == 0) {
			p1Rating = 1500;
			try {
				database.updatePlayerRating(p1ID, gameID, 1500);
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (p2Rating == 0) {	
			p2Rating = 1500;
			try {
				database.updatePlayerRating(p2ID, gameID, 1500);
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		long time = (Long)player2.get(1);
		timeAllowance = (((System.currentTimeMillis() - time) / 60000)+1)*100;
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
	
	private void launchGame(ArrayList<Object> player1, ArrayList<Object> player2) {
		int p1Rating = 0, p2Rating = 0;
		int player1ID = (Integer)player1.get(2);
		int player2ID = (Integer)player2.get(2);
		Connection player1Conn = (Connection)player1.get(3);
		Connection player2Conn = (Connection)player2.get(3);
		String game = (String)player1.get(0);
		try {
			p1Rating = database.getPlayerRating(player1ID, game);
			p2Rating = database.getPlayerRating(player2ID, game);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MultiplayerServer gameServer = new MultiplayerServer(player1ID, player2ID, player1Conn,
				player2Conn, game, serverNumber, this, p1Rating, p2Rating);
		activeServers.put(serverNumber, gameServer);
		
		NewMultiSessionResponse session = new NewMultiSessionResponse();
		session.sessionId = serverNumber;
		serverNumber++;
		session.gameId = ((String) player1.get(0));
		session.host = true;
		((Connection) player1.get(3)).sendTCP(session);
		session.host = false;
		((Connection) player2.get(3)).sendTCP(session);
	}
	
	
	private void checkList() {
		if (queuedUsers.size() < 2) {
			return;
		}
		for (int i = 0; i + 1 < queuedUsers.size(); i++) {
			for (int j = i+1; j < queuedUsers.size(); j++) {
				if (((String)queuedUsers.get(i).get(0)).equals(((String)queuedUsers.get(j).get(0)))) {
					if (goodGame(queuedUsers.get(i), queuedUsers.get(j))) {
						ArrayList<Object> player1 = new ArrayList<Object>(queuedUsers.get(i));
						ArrayList<Object> player2 = new ArrayList<Object>(queuedUsers.get(j));
						launchGame(player1, player2);
						queuedUsers.remove(j);
						queuedUsers.remove(i);
						j--;
						i--;
					}
				}
			}
		}
	}
	
	
	
	public void gameOver(int session, int player1ID, int player2ID, String gameID, int winner) {
		System.out.println("GAME OVER WINNER IS : " + winner);
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
			database.updatePlayerRating(player2ID, gameID, newScore[1]);
		} catch (Exception e) {
			System.out.println(e);
		}
		dropServer(session);
	}
	
	public void dropServer(int session) {
		activeServers.remove(session);
	}
	
	public Map<Integer, MultiplayerServer> getActiveServers() {
		Map<Integer, MultiplayerServer> mapCopy = new HashMap<Integer, MultiplayerServer>(activeServers);
		return mapCopy;
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
			double change = (1 - (1.0f / (Math.pow(10, ((-diff / 400) + 1)))));
			score = k * change;			
			newElo[0] = (int) Math.floor(p1Rating += score);
			score = -k * change;
			newElo[1] = (int) Math.floor(p2Rating += score);
		} else if (winner == p2ID) {
			double score;
			double diff = p2Rating - p1Rating;
			double change = (1 - (1.0f / (Math.pow(10, ((-diff / 400) + 1)))));
			score = -k * change;			
			newElo[0] = (int) Math.floor(p1Rating += score);
			score = k * change;
			newElo[1] = (int) Math.floor(p2Rating += score);
		} else {
			newElo[0] = p1Rating;
			newElo[1] = p2Rating;
		}
		return newElo;
	}
}
