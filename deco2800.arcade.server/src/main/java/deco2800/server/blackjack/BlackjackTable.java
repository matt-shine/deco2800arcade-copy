package deco2800.server.blackjack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.game.GameStatusUpdate;

/**
 * The Blackjack table that people play and connect to.
 * The logic of the Blackjack game exists here.
 */
public class BlackjackTable {
	
	private Map<String, Connection> players;
	private int betLimit = -1;
	
	/**
	 * Creates a new Blackjack table.
	 * Default method and should not be used.
	 */
	public BlackjackTable() {
		players = new HashMap<String, Connection>();
	}
	
	/**
	 * Creates a new Blackjack table with a betting limit.
	 * @param the betting limit of the table
	 */
	public BlackjackTable(int _betLimit) {
		players = new HashMap<String, Connection>();
		betLimit = _betLimit;
	}
	
	/**
	 * Adds a player to the table.
	 * @param the user's connection
	 * @param the user's username
	 */
	public void addPlayer(Connection connection, String username) {
		
		// Clear out previous session.  This should never need to run.
		if (players.containsKey(username)) {
			players.remove(username);
		}
		players.put(username, connection);
	}
	
	/**
	 * Broadcasts a game status update to all players
	 * @param the message to be sent
	 */
	public void broadcastGameStatusUpdate(GameStatusUpdate message) {
		Iterator<Map.Entry<String, Connection>> iter = players.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Connection> person = (Map.Entry<String, Connection>)iter.next();
			Connection connection = person.getValue();
			connection.sendTCP(message);
		}
	}
	
	/**
	 * Sends a message to an individual
	 * @param the message to be sent
	 * @param the username of the person
	 */
	public void sendGameStatusUpdate(GameStatusUpdate message, String username) {
		if (players.containsKey(username)) {
			players.get(username).sendTCP(message);
		}
	}
	
	/**
	 * Provides the amount of people playing on the table.
	 * @return the amount of players.
	 */
	public int amountOfPlayers() {
		return players.size();
	}
	
	/**
	 * Provides the betting limit of the table.
	 * @return the betting limit
	 */
	public int getBetLimit() {
		return betLimit;
	}

}
