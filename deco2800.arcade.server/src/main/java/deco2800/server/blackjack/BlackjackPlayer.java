package deco2800.server.blackjack;

import java.util.ArrayList;

public class BlackjackPlayer extends BlackjackUser{

	ArrayList<String> hand = new ArrayList<String>();
	int bet = 0;
	ArrayList<String> split_hand = new ArrayList();
	int split_bet = 0;
	boolean has_split = false;
	//some sort of table identifier needed?
	
	public BlackjackPlayer(BlackjackUser user){
		this.chippile = user.chippile;
		this.username = user.username;
		this.connection = user.connection;
	}
	

	public void addCard(){
		hand.add(/*table identifier.getDeck().getCard()*/"a card");
	}
}
