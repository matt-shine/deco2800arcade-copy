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

import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameStatusUpdate;

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
		System.out.println(deck.deck);
		dealerHand = new ArrayList<String>();
		gametimer = new Timer();
		state = 0;
	}
	
	public synchronized void run() {
		//this is only executed once to start the game thread
		//everything else is then handled back and forth between the switchstates and individual state functions (state0, state1, etc.)
			switchstates();
		}
	
	private void state0() {
		//this is supposed to just wait till an outside function calls interrupt
		//then change to state 1 which basically adds the joiningplayers to main player array
		//not sure what's happening here exactly as far as waiting for user response, need to talk to group about this
		System.out.println("STATE IS 0");
		try {
			wait();
		} catch (InterruptedException e) {
			state = 1;
			switchstates();
		}
	}
	
	private void state1() {
		System.out.println("STATE IS 1");
		addPlayersToTable();
		if (Playerarray.size() == 0) {
			state = 0;
			switchstates();
		}
		else {
			state = 2;
			switchstates();
		}
	}

	private void state2() {
		System.out.println("STATE IS 2");
		gametimer.schedule(new TimerTask() {
			@Override
			public void run() {
				//60 sec timer boots out players who have not bet to watchers array
				for (int i = 0; i < Playerarray.size(); i++) {
					if (Playerarray.get(i) != null) {
						BlackjackPlayer player = Playerarray.get(i);
					if (player.bet == 0) {
						Playerarray.add(i, null);
						Watchers.add(player);
					}
					}
				}
				interrupt();
			}
		}, 60000);
		try {
			wait();
		} catch (InterruptedException e) {
			System.out.println("TEST INTERRUPT FOR SECOND STATE");
		}
		dealcards();
		if (Playerarray.isEmpty() == true) {
			state = 0;
		}
		else {
			state = 3;
		}
		switchstates();
	}

	private void state3() {
		System.out.println("STATE IS 3");
		for (int i = 0; i == Playerarray.size(); i++) {
			//assign token to player 1
			//only player with token can interact with table thread
			//player interacts with game until either the timer runs out 
			//or they call the stay or bust function
			//stay function passes the token to the next player
		}
		state = 4;
		switchstates();
	}

	private void state4() {
		System.out.println("STATE IS 4");
		dealer();
		state = 5;
		switchstates();
	}

	private void state5() {
		System.out.println("STATE IS 5");
		for (int i = 0; i < Playerarray.size(); i++) {
			Playerarray.get(i).hand.clear();
			if (totalhandNum(Playerarray.get(i).hand) > totalhandNum(dealerHand) && totalhandNum(Playerarray.get(i).hand) <= 21) {
				Playerarray.get(i).chippile += Playerarray.get(i).bet*3;
			}
			Playerarray.get(i).bet = 0;
		}
		dealerHand.clear();
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
		//adds player to watchers array
		//500 default value for credits at this point
		//credit info storage for players? needs to be sorted
		Watchers.add(new BlackjackUser(username, 500, connection));
	}
	
	public void dealcards() {
		String card;
		for (int i = 0; i < Playerarray.size(); i++) {
			card = deck.getCard();
			Playerarray.get(i).hand.add(card);
		}
		card = deck.getCard();
		dealerHand.add(card);
	}
	
	public void dealer() {
		while (totalhandNum(dealerHand) <= 16) {
			dealerHand.add(deck.getCard());
		}
	}
	
	public void takeSeat(BlackjackPlayer player, int seatposition) {
		if (JoiningPlayers.get(seatposition) == null) {
			JoiningPlayers.set(seatposition, player);
		}
		else {
			//sendGameStatusUpdate("Seat is Taken", player.username)
			//How do i instantiate GameStatusUpdate?
		}
		System.out.println(JoiningPlayers);
	}
	
	public void addPlayersToTable() {
		for (int i = 0; i < JoiningPlayers.size(); i++) {
			BlackjackPlayer newPlayer = new BlackjackPlayer(JoiningPlayers.get(i));
				Playerarray.add(i, newPlayer);
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
	
	public int totalhandNum(ArrayList<String> Hand) {
		int result = 0;
		for (int i = 0; i < Hand.size(); i++) {
			result = result + convertNum(Hand.get(i));
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
	
	/**
	 * Provides the betting limit of the table.
	 * @return the betting limit
	 */
	public int getBetLimit() {
		return betLimit;
	}

	public void addUser(BlackjackUser user) {
		JoiningPlayers.add(user);
	}
	

}
