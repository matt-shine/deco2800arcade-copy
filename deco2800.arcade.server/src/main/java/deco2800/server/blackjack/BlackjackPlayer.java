package deco2800.server.blackjack;

import java.util.ArrayList;

public class BlackjackPlayer extends BlackjackUser{

	ArrayList<String> hand = new ArrayList<String>();
	int bet = 0;
	//some sort of table identifier needed?
	
	public BlackjackPlayer(BlackjackUser user){
		this.chippile = user.chippile;
		this.clientid = user.clientid;
		this.credits = user.credits;
		this.username = user.username;
	}
	
	public void bet(int amount){
		bet = amount;
		chippile = chippile - bet;
	}
	
	public int getHandValue(){
		int value = 0;
		int aces = 0;
		for(int i=0;i<hand.size();i++){
			String card = hand.get(i);
			if (card == "H2" || card == "D2" || card == "C2" || card == "S2"){value += 2;}
			else if (card == "H3" || card == "D3" || card == "C3" || card == "S3"){value += 3;}
			else if (card == "H4" || card == "D4" || card == "C4" || card == "S4"){value += 4;}
			else if (card == "H5" || card == "D5" || card == "C5" || card == "S5"){value += 5;}
			else if (card == "H6" || card == "D6" || card == "C6" || card == "S6"){value += 6;}
			else if (card == "H7" || card == "D7" || card == "C7" || card == "S7"){value += 7;}
			else if (card == "H8" || card == "D8" || card == "C8" || card == "S8"){value += 8;}
			else if (card == "H9" || card == "D9" || card == "C9" || card == "S9"){value += 9;}
			else if (card == "H10" || card == "D10" || card == "C10" || card == "S10" || 
					card == "SJ" || card == "SJ" || card == "SJ" || card == "SJ" || 
					card == "SQ" || card == "SQ" || card == "SQ" || card == "SQ" || 
					card == "SK" || card == "SK" || card == "SK" || card == "SK"){value += 10;}
			else if (card == "HA" || card == "DA" || card == "CA" || card == "SA"){aces += 1;}
			//Apparently switch can't be used on Strings.
		}
		for(int a=0;a<aces;a++){
			if (value+11<=21){value += 11;}
			else{value += 1;}
		}
		return value;
	}

	public void addCard(){
		hand.add(/*table identifier.getDeck().getCard()*/"a card");
	}
	
}
