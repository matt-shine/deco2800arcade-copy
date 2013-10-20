package deco2800.server.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BlackjackDeck {

	ArrayList<String> deck;
	String[] singledeck = {"HA","H2","H3","H4","H5","H6","H7","H8","H9","H10","HJ","HQ","HK",
			"DA","D2","D3","D4","D5","D6","D7","D8","D9","D10","DJ","DQ","DK",
			"CA","C2","C3","C4","C5","C6","C7","C8","C9","C10","CJ","CQ","CK",
			"SA","S2","S3","S4","S5","S6","S7","S8","S9","S10","SJ","SQ","SK",};
	
	public BlackjackDeck(){
		deck = new ArrayList<String>(4*52);
		for (int s=0;s<4;s++){
			for (int i=0;i<singledeck.length;i++){
				deck.add(singledeck[i]);
			}
		}
	}
	
	public BlackjackDeck(int numberofdecks){
		deck = new ArrayList<String>(numberofdecks*52);
		for (int s=0;s<numberofdecks;s++){
			for (int i=0;i<singledeck.length;i++){
				deck.add(singledeck[i]);
			}
		}
	}
	
	public void shuffle(){
		Collections.shuffle(deck);
	} 
	
	public String getCard(){
		String card = deck.get(0);
		deck.remove(0);
		return card;
	}
	
	
}
