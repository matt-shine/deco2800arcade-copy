package deco2800.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.PlayerGameStorage;

/**
 * A Matchmaking Queue for use within the arcade to facilitate ranked games
 * 
 * @author Joey
 * 
 */
public class MatchmakerQueue {

	// Players currently in the matchmaking queue
	private ArrayList<ArrayList<Object>> queuedUsers;
	// Servers currently hosting games
	private Map<Integer, MultiplayerServer> activeServers;
	// The PlayerGameStorage database
	private PlayerGameStorage database;
	// The current number of servers started since the last restart
	private int serverNumber;
	// A timer to control the queue
	private Timer timer;

	/* Singleton */
	private static MatchmakerQueue instance;

	/**
	 * The basic constructor for the queue Initialises the global variables
	 */
	private MatchmakerQueue() {
		this.queuedUsers = new ArrayList<ArrayList<Object>>();
		this.activeServers = new HashMap<Integer, MultiplayerServer>();
		this.setDatabase(new PlayerGameStorage());
		this.serverNumber = 0;
		this.timer = new Timer();
		class ListTask extends TimerTask {
			public void run() {
				// Checks the queued players list to see if any games have
				// become available
				checkList();
			}
		}
		timer.schedule(new ListTask(), 0, 10000);
	}

	/**
	 * Get the singleton Matchmaker instance
	 * 
	 * @return: the matchmaker instance
	 */
	public static MatchmakerQueue instance() {
		if (instance == null) {
			instance = new MatchmakerQueue();
		}
		return instance;
	}

	/**
	 * Returns a copy of the (complete) current queue.
	 * 
	 * @return A copy of the complete queue.
	 */
	public ArrayList<ArrayList<Object>> getQueue() {
		ArrayList<ArrayList<Object>> map = new ArrayList<ArrayList<Object>>(
				queuedUsers);
		return map;
	}

	/**
	 * Returns a copy of the current server list
	 * 
	 * @return A copy of the current server list
	 */
	public Map<Integer, MultiplayerServer> getServerList() {
		Map<Integer, MultiplayerServer> map = new HashMap<Integer, MultiplayerServer>(
				activeServers);
		return map;
	}

	/**
	 * Add a user to the matchmaking queue.
	 * 
	 * @param username: the players username
	 * 
	 * @param connection: the players connection
	 */
	private void add(NewMultiGameRequest request, Connection connection) {
		// TODO: handle players already in queue? allow multi-game queue
		// placement?
		ArrayList<Object> player = new ArrayList<Object>();
		player.add(request.gameId);
		player.add(System.currentTimeMillis());
		player.add(request.playerID);
		player.add(connection);
		queuedUsers.add(player);
	}

	/**
	 * Removes the given username from the matchmaking queue (all games)
	 * 
	 * @param username: the players username
	 */
	public void remove(Integer playerID) {
		/* Might be a more efficient way to do this (will gameId be available?) */
		for (int i = 0; i < queuedUsers.size(); i++) {
			if (queuedUsers.get(i).get(2) == playerID) {
				queuedUsers.remove(i);
				i--;
			}
		}
	}

	/**
	 * Checks to see if the user may immediately enter a matchmaking game and if
	 * not, adds them to the queue
	 * 
	 * @param request: the request sent in by the client
	 * 
	 * @param connection: the client's connection
	 */
	public void checkForGame(NewMultiGameRequest request, Connection connection) {
		int playerID = request.playerID;
		String gameId = request.gameId;
		// Search for an appropriate game for the user to join
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

	/**
	 * Adds a game started in MultiplayerLobby to the Active Server List to
	 * allow the listener to control communication
	 * 
	 * @param player1Id: The ID of player 1
	 * 
	 * @param player2Id: The ID of player 2
	 * 
	 * @param player1Connection: The connection of player 1
	 * 
	 * @param player2Connection: The connection of player 2
	 * 
	 * @param gameId: The ID of the game to be played
	 */
	public int addLobbyGame(int player1Id, int player2Id,
			Connection player1Connection, Connection player2Connection,
			String gameId) {
		MultiplayerServer gameServer = new MultiplayerServer(player1Id,
				player2Id, player1Connection, player2Connection, gameId,
				serverNumber);
		activeServers.put(serverNumber, gameServer);
		serverNumber++;
		return serverNumber - 1;
		//player1Connection.sendTCP(session);
		//player2Connection.sendTCP(session);
	}

	/**
	 * Checks to see if a game can be formed between two players in the queue
	 * based off the amount of time spent waiting and each player's matchmaking
	 * ranks.
	 * 
	 * @param player1: The first player to check
	 * 
	 * @param player2: The second player to check
	 * 
	 * @return True if a game can be started between the two players
	 */
	private boolean goodGame(ArrayList<Object> player1,
			ArrayList<Object> player2) {
		int p1Rating = 0, p2Rating = 0, ratingDiff;
		long timeAllowance;
		int p1ID = (Integer) player1.get(2);
		int p2ID = (Integer) player2.get(2);
		String gameID = (String) player1.get(0);
		// Get the player's rankings
		try {
			p1Rating = getDatabase().getPlayerRating(p1ID, gameID);
			p2Rating = getDatabase().getPlayerRating(p2ID, gameID);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

		// Making sure each player has a rating
		if (p1Rating == 0) {
			p1Rating = 1500;
			try {
				getDatabase().updatePlayerRating(p1ID, gameID, 1500);
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}
		if (p2Rating == 0) {
			p2Rating = 1500;
			try {
				getDatabase().updatePlayerRating(p2ID, gameID, 1500);
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}

		// As time goes on in the queue relax the requirements to find a high
		// quality
		// game in favour of finding one immediately
		long time = (Long) player2.get(1);
		timeAllowance = (((System.currentTimeMillis() - time) / 60000) + 1) * 100;
		if (p1Rating >= p2Rating) {
			ratingDiff = p1Rating - p2Rating;
		} else {
			ratingDiff = p2Rating - p1Rating;
		}
		return (!(ratingDiff > timeAllowance && timeAllowance < 500));
	}

	/**
	 * Responsible for starting the multiplayer server to host a game between
	 * two players Also informs each client that the game may be started.
	 * 
	 * @param player1: The first player
	 * 
	 * @param player2: The second player
	 */
	private void launchGame(ArrayList<Object> player1, ArrayList<Object> player2) {
		int p1Rating = 0, p2Rating = 0;
		int player1ID = (Integer) player1.get(2);
		int player2ID = (Integer) player2.get(2);
		Connection player1Conn = (Connection) player1.get(3);
		Connection player2Conn = (Connection) player2.get(3);
		String game = (String) player1.get(0);
		try {
			p1Rating = getDatabase().getPlayerRating(player1ID, game);
			p2Rating = getDatabase().getPlayerRating(player2ID, game);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MultiplayerServer gameServer = new MultiplayerServer(player1ID,
				player2ID, player1Conn, player2Conn, game, serverNumber, this,
				p1Rating, p2Rating);
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

	/**
	 * Checks the queued user list to see if games may be formed between users
	 * due to relaxed rank settings due to extra time in the queue
	 */
	private void checkList() {
		if (queuedUsers.size() < 2) {
			return;
		}
		for (int i = 0; i + 1 < queuedUsers.size(); i++) {
			for (int j = i + 1; j < queuedUsers.size(); j++) {
				if (((String) queuedUsers.get(i).get(0))
						.equals(((String) queuedUsers.get(j).get(0)))) {
					if (goodGame(queuedUsers.get(i), queuedUsers.get(j))) {
						ArrayList<Object> player1 = new ArrayList<Object>(
								queuedUsers.get(i));
						ArrayList<Object> player2 = new ArrayList<Object>(
								queuedUsers.get(j));
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

	/**
	 * Handles the end of a game. Updating players ranks and removing the server
	 * from the active list
	 * 
	 * @param session: The session number of the server
	 * 
	 * @param player1ID: The ID of player 1
	 * 
	 * @param player2ID: The ID of player 2
	 * 
	 * @param gameID: The ID of the game they were playing
	 * 
	 * @param winner: The ID of the winner
	 */
	public void gameOver(int session, int player1ID, int player2ID,
			String gameID, int winner) {
		int player1Rating = 0;
		int player2Rating = 0;
		try {
			player1Rating = getDatabase().getPlayerRating(player1ID, gameID);
			player2Rating = getDatabase().getPlayerRating(player2ID, gameID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Find the new ratings of each player based off the finished game
		int[] newScore = elo(player1ID, player1Rating, player2ID,
				player2Rating, winner);
		// Update the rating in the database
		try {
			getDatabase().updatePlayerRating(player1ID, gameID, newScore[0]);
			getDatabase().updatePlayerRating(player2ID, gameID, newScore[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Remove the server from the active list
		dropServer(session);
	}

	/**
	 * Removes the given session from the active server list after the game is
	 * over
	 * 
	 * @param session: The game to remove
	 */
	public void dropServer(int session) {
		activeServers.remove(session);
	}

	/**
	 * Matchmaking uses an ELO system -- players ranks are changed based on the
	 * difference in their ranks at the start of the game and the expected
	 * outcome
	 * 
	 * @param p1ID: The ID of player 1
	 * 
	 * @param p1Rating: The current rating of player 1
	 * 
	 * @param p2ID: The ID of player 2
	 * 
	 * @param p2Rating: The current rating of player 2
	 * 
	 * @param winner: The winner of the game
	 * 
	 * @return An array containing the new rating of both player 1 and player 2
	 */
	public int[] elo(int p1ID, int p1Rating, int p2ID, int p2Rating, int winner) {
		// k is the scaling factor (Determines how many points are gained and
		// lost per game)
		int k = 32;
		int[] newElo = new int[2];
		double player1Rating = (double) p1Rating;
		double player2Rating = (double) p2Rating;
		if (p1Rating < 2100 || p2Rating < 2100) {
			k = 32;
		} else if ((p1Rating < 2401 && p1Rating >= 2100)
				|| (p2Rating < 2401 && p1Rating >= 2100)) {
			k = 24;
		} else {
			k = 16;
		}
		// Calculate the new ratings. Formula is based off old ratings and
		// expected winners.
		// The value 400 also helps in determining how quickly rating is gained
		// and lost
		if (winner == p1ID) {
			double score;
			double diff = p1Rating - p2Rating;
			double change = (1 - (1.0f / (Math.pow(10, ((-diff / 400) + 1)))));
			score = k * change;
			player1Rating += score;
			newElo[0] = (int) Math.floor(player1Rating);
			score = -k * change;
			player2Rating += score;
			newElo[1] = (int) Math.floor(player2Rating);
		} else if (winner == p2ID) {
			double score;
			double diff = p2Rating - p1Rating;
			double change = (1 - (1.0f / (Math.pow(10, ((-diff / 400) + 1)))));
			score = -k * change;
			player1Rating += score;
			newElo[0] = (int) Math.floor(player1Rating);
			score = k * change;
			player2Rating += score;
			newElo[1] = (int) Math.floor(player2Rating);
		} else {
			newElo[0] = p1Rating;
			newElo[1] = p2Rating;
		}
		return newElo;
	}
	
	
	/* TEST HELPER METHODS */
	
	/**
	 * For testing purposes
	 * @return - The database instance
	 */
	public PlayerGameStorage getDatabase() {
		return database;
	}

	/**
	 * Set the database instance
	 * @param database
	 */
	public void setDatabase(PlayerGameStorage database) {
		this.database = database;
	}
	
	public void resetQueuedUsers() {
		this.queuedUsers = new ArrayList<ArrayList<Object>>();
	}
	
	public void resetActiveServers() {
		this.activeServers = new HashMap<Integer, MultiplayerServer>();
		this.serverNumber = 0;
	}
	
	/* END TEST HELPER METHODS */
}
