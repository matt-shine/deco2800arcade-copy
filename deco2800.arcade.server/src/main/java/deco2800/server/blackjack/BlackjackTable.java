package deco2800.server.blackjack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.derby.impl.sql.execute.CardinalityCounter;

import com.esotericsoftware.kryonet.Connection;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.network.NetworkClient;

/**
 * The Blackjack table that people play and connect to.
 * The logic of the Blackjack game exists here.
 */
public class BlackjackTable extends Thread {
	
	private Map<String, Connection> players;
	private int betLimit = -1;
	private int state = 0;
	private ArrayList<String> dealerHand;
	private ArrayList<BlackjackUser> JoiningPlayers;
	private ArrayList<BlackjackUser> Watchers;
	private ArrayList<BlackjackPlayer> Playerarray;
	public BlackjackDeck deck;
	private Timer gametimer;
	private String token;
	private boolean betsplaced;
	private int numofplayersbet;
	private NetworkClient NetworkClient;
	/**
	 * Creates a new Blackjack table.
	 * Default method and should not be used.
	 */
	public BlackjackTable() {
		players = new HashMap<String, Connection>();
		this.start();
	}
	
	/**
	 * Creates a new Blackjack table with a betting limit.
	 * @param the betting limit of the table
	 */
	public BlackjackTable(int _betLimit) {
		players = new HashMap<String, Connection>();
		JoiningPlayers = new ArrayList<BlackjackUser>();
		//filling in the "seat spots" with null values
		//if i find a better way to do this i'll change this
		JoiningPlayers.add(null);
		JoiningPlayers.add(null);
		JoiningPlayers.add(null);
		JoiningPlayers.add(null);
		JoiningPlayers.add(null);
		Playerarray = new ArrayList<BlackjackPlayer>();
		Playerarray.add(null);
		Playerarray.add(null);
		Playerarray.add(null);
		Playerarray.add(null);
		Playerarray.add(null);
		Watchers = new ArrayList<BlackjackUser>();
		betLimit = _betLimit;
		deck = new BlackjackDeck();
		deck.shuffle();
		dealerHand = new ArrayList<String>();
		gametimer = new Timer();
		state = 0;
		token = null;
		betsplaced = false;
		numofplayersbet = 0;
	}
	
	public synchronized void run() {
		//this is only executed once to start the game thread
		//everything else is then handled back and forth between the switchstates and individual state functions (state0, state1, etc.)
			switchstates();
		}
	
	private void state0() {
		//this is supposed to just wait till sitdown calls interrupt
		try {
			wait();
		} catch (InterruptedException e) {
			state = 1;
			switchstates();
		}
	}
	
	private void state1() {
		addPlayersToTable();
		if (playercount(Playerarray) == 0) {
			state = 0;
			switchstates();
		}
		else {
			state = 2;
			switchstates();
		}
	}

	private void state2() {
		gametimer.schedule(new TimerTask() {
			@Override
			public void run() {
				//60 sec timer boots out players who have not bet to watchers array
				for (int i = 0; i < Playerarray.size(); i++) {
					if (Playerarray.get(i) != null) {
						BlackjackPlayer player = Playerarray.get(i);
					if (player.bet == 0) {
						Playerarray.set(i, null);
						Watchers.add(player);
					}
					}
				}
				interrupt();
			}
		}, 60000);
		while (betsplaced == false) {
			if (numofplayersbet == playercount(Playerarray)) {
				betsplaced = true;
				interrupt();
			}
		}
		try {
			wait();
		} catch (InterruptedException e) {
		}
		dealcards();
		for (int i = 0; i < Playerarray.size(); i++) {
			if (Playerarray.get(i) != null) {
			}
		}
		if (playercount(Playerarray) == 0) {
			state = 0;
		}
		else {
			state = 3;
		}
		switchstates();
	}

	private void state3() {
		//only player with token can interact with table thread
		//player interacts with game until either the timer runs out 
		//or they call the stay or bust function
		//stay function passes the token to the next player
		for (int i = 0; i < Playerarray.size(); i++) {
			if (Playerarray.get(i) != null) {
			token = Playerarray.get(i).username;
			try {
				wait();
			} catch (InterruptedException e) {
			}
			gametimer.schedule(new TimerTask() {
				public void run() {
				interrupt();
				}
				}, 60000);
			}
		}
		state = 4;
		switchstates();
	}

	private void state4() {
		dealer();
		state = 5;
		switchstates();
	}

	private void state5() {
		for (int i = 0; i < Playerarray.size(); i++) {
			if (Playerarray.get(i) != null) {
			Playerarray.get(i).hand.clear();
			if (totalhandNum(Playerarray.get(i).hand) > totalhandNum(dealerHand) && totalhandNum(Playerarray.get(i).hand) <= 21) {
				Playerarray.get(i).chippile += Playerarray.get(i).bet*3;
			}
			Playerarray.get(i).bet = 0;
		}
		}
		dealerHand.clear();
		numofplayersbet = 0;
		betsplaced = false;
		state = 1;
		switchstates();
	}
	
	public void switchstates() {
		switch (state) {
		case 0: state0();
			break;
		case 1: state1();
			break;
		case 2: state2();
			break;
		case 3: state3();
			break;
		case 4: state4();
			break;
		case 5: state5();
			break;
		}
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
		//Watchers.add(new BlackjackUser(username, 500, connection));
	}
	
	public void dealcards() {
		String card;
		for (int i = 0; i < Playerarray.size(); i++) {
			if (Playerarray.get(i) != null) {
			card = deck.getCard();
			Playerarray.get(i).hand.add(card);
			card = deck.getCard();
			Playerarray.get(i).hand.add(card);
			}
		}
		card = deck.getCard();
		dealerHand.add(card);
		card = deck.getCard();
		dealerHand.add(card);
	}
	
	public void dealer() {
		while (totalhandNum(dealerHand) <= 16) {
			dealerHand.add(deck.getCard());
		}
	}
	
	public void takeSeat(BlackjackUser user, int seatposition) {
		if (JoiningPlayers.get(seatposition) == null && Playerarray.get(seatposition) == null) {
			JoiningPlayers.set(seatposition, user);
			Watchers.remove(user);
			if (state == 0) {
				interrupt();
			}
		}
		else {
			GameStatusUpdate msg = new GameStatusUpdate();
			msg.message = "Seat is taken";
			sendGameStatusUpdate(msg, user.username);
		}
	}
	
	public void addPlayersToTable() {
		for (int i = 0; i < JoiningPlayers.size(); i++) {
			if (JoiningPlayers.get(i) != null) {
			BlackjackPlayer newPlayer = new BlackjackPlayer(JoiningPlayers.get(i));
				JoiningPlayers.set(i, null);
				Playerarray.set(i, newPlayer);
			}
		}
	}
	
	public int convertNum(String card) {
		int result = 0;
		String cardstr = card.substring(1);
		if (cardstr.matches("\\d") == true ||cardstr.matches("\\d\\d") == true) {
			int cardint = Integer.parseInt(cardstr);
			if (cardint >= 2 && cardint <= 10) {
				result = cardint;
			}
		}
		
		if (cardstr.equals("K") || cardstr.equals("Q") || cardstr.equals("J")) {
			result = 10;
		}
		if (cardstr.equals("A")) {
			result = 2;
		}
		return result;
	}
	
	public void bet(BlackjackPlayer player, int amount){
		player.bet = amount;
		player.chippile = player.chippile - player.bet;
		numofplayersbet += 1; 
	}
	
	public void hit(BlackjackPlayer player) {
		if (token == player.username) {
 		String card = deck.getCard();
		player.hand.add(card);	
		if (totalhandNum(player.hand) > 21) {
			interrupt();
		}
		}
		else {
		}
		Connection c = null;
		//c.sendTCP(new GameStatusUpdate());
	}
	
	public void stay(BlackjackPlayer player) {
		if (token == player.username) {
			if (!player.has_split) {
				interrupt();	
			}
			else {
				String card = player.split_hand.get(0);
				player.split_hand = player.hand;
				player.hand.clear();
				player.hand.add(card);
				int bet = player.split_bet;
				player.split_bet = player.bet;
				player.bet = bet;
				player.has_split = false;
				
			}
		}
	}
	
	public void doubleDown(BlackjackPlayer player, int doubledownamount){
		if (token == player.username) {
		player.bet += doubledownamount;
		player.hand.add(deck.getCard());
		interrupt();
		}
	}
	
	public int totalhandNum(ArrayList<String> Hand) {
		int result = 0;
		for (int i = 0; i < Hand.size(); i++) {
			result = result + convertNum(Hand.get(i));
		}
		return result;
	}
	
	public int playercount(ArrayList Array) {
		int result = 0;
		for (int i = 0; i < Array.size(); i++) {
			if (Array.get(i) != null) {
				result += 1;
			}
		}
		return result;
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
	
	public Map<String, Connection> getPlayers() {
		return players;
	}
	
	public boolean containsUser(String username) {
		boolean result = false;
		if (players.containsKey(username)) {
		result = true;
		}
		return result;
		
	}
	
	/**
	 * Provides the betting limit of the table.
	 * @return the betting limit
	 */
	public int getBetLimit() {
		return betLimit;
	}
	
	public void receiveMessage(GameStatusUpdate update) {
		// TODO Auto-generated method stub
		if (update.message.equals("hit")) {
			for (int i = 0; i < Playerarray.size(); i++) {
				BlackjackPlayer Player = Playerarray.get(i);
				if (Player.username == update.username) {
					hit(Player);
					return;
				}
			}
		}
		if (update.message.contains("bet")) {
			for (int i = 0; i < Playerarray.size(); i++) {
				BlackjackPlayer Player = Playerarray.get(i);
				String[] splitmsg = update.message.split("|");
				String betamount = splitmsg[1];
				if (Player.username == update.username) {
					bet(Player,Integer.parseInt(betamount));
					return;
				}
		}
		if (update.message.contains("doubledown")) {
			for (int i = 0; i < Playerarray.size(); i++) {
				BlackjackPlayer Player = Playerarray.get(i);
				String[] splitmsg = update.message.split("|");
				String betamount = splitmsg[1];
				if (Player.username == update.username) {
					doubleDown(Player,Integer.parseInt(betamount));
					return;
				}
			 }
	 	  }
		if (update.message.contains("takeseat")) {
			for (int i = 0; i < Playerarray.size(); i++) {
				BlackjackPlayer Player = Playerarray.get(i);
				String[] splitmsg = update.message.split("|");
				String seatno = splitmsg[1];
				if (Player.username == update.username) {
					takeSeat(Player,Integer.parseInt(seatno));
					return;
				}
			 }
	 	  }
		}
	}
}
