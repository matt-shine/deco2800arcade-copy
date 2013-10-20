package deco2800.server.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.game.CasinoServerUpdate;
import deco2800.server.GameServer;

/**
 * BlackjackServer is the controller of all Blackjack tables.
 * It handles adding tables and players to tables.
 */
public class BlackjackServer extends GameServer {
	
	List<BlackjackTable> tables;
	static int MAX_PLAYERS = 5;
	static int PREFERRED_PLAYERS = 3;
	
	/**
	 * Create a new BlackjackServer.
	 * It will spawn initial tables.
	 */
	public BlackjackServer() {
		tables = new ArrayList<BlackjackTable>();
		spawnInitialTables();
	}
	
	/**
	 * Spawns four Blackjack tables of varying bet limits.
	 */
	private void spawnInitialTables() {
		tables.add(new BlackjackTable(10));
		tables.add(new BlackjackTable(25));
		tables.add(new BlackjackTable(50));
		tables.add(new BlackjackTable(100));
	}
	
	/**
	 * Spawns a new empty Blackjack table.
	 * @param the bet limit of the table
	 * @return the table
	 */
	private BlackjackTable spawnNewTable(int limit) {
		BlackjackTable table = new BlackjackTable(limit);
		tables.add(table);
		return table;
	}
	
	/**
	 * Adds a player to a Blackjack table.
	 * @param the user's connection
	 * @param the user's username
	 * @param the table's limit
	 */
	private void addPlayerToTable(Connection connection, String username, int limit) {
		boolean foundTable = false;
		for (BlackjackTable table : tables) {
			if (table.getBetLimit() != limit) {
				continue;
			}
			if (table.amountOfPlayers() <= PREFERRED_PLAYERS) {
				foundTable = true;
			}
		}
		if (!foundTable) {
			BlackjackTable t = spawnNewTable(limit);
			tables.get(tables.indexOf(t)).addPlayer(connection, username);
		}
	}
	
	/**
	 * A generic toString method.
	 */
	public String toString() {
		return "I am a BlackjackServer.";
	}
	
	public BlackjackTable FindTable(String Username) {
		BlackjackTable table = null;
		for (int i = 0; i <tables.size(); i++) {
			if( tables.get(i).containsUser(Username)) {
				table = tables.get(i);
			}
		}
		return table;
	}

	/**
	 * Receives an event from a player.  The server will then action
	 * appropriately.
	 */
	public void receive(Connection connection, CasinoServerUpdate update) {
		
		//System.out.println("Received update: " + update.username + "|" + update.message);
		if (update.message.equals("addme#20")) {
			addPlayerToTable(connection, update.username, 25);
		} else if (update.message.equals("testme")) {
			update.message = "WHAT DO YOU GET GOOD SIR";
			connection.sendTCP(update);
		}
		else {
			BlackjackTable table = FindTable(update.username);
			table.receiveMessage(update);
		}
	}
	
}
