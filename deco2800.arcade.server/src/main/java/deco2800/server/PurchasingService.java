package deco2800.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.GamePlayToken;
import deco2800.arcade.model.Player;
import deco2800.server.database.CreditStorage;
import deco2800.server.database.DatabaseException;

/**
 * Handles unusual payment plans...
 */
public class PurchasingService {
	
	CreditStorage creditStorage;
	
	public void setCreditStorage(CreditStorage cs) {
		this.creditStorage = cs;
	}
	
	/**
	 * Defines the discounts for prepurchasing multiple games
	 */
	private double discountFactor(int numUnits) {
		if (numUnits < 5) {
			return 1d;
		} else if (numUnits < 10) {
			return 0.6d;
		} else if (numUnits < 25) {
			return 0.5d;
		} else {
			return 0.4d;
		}
		
	}

	public GamePlayToken bulkPurchase(
			Player p, 
			Game g, 
			int numPlays
    ) throws DatabaseException {
		
		int price = (int)(g.pricePerPlay * numPlays * discountFactor(numPlays));
		
		int playerID = p.getID();
		int credits = creditStorage.getUserCredits(playerID);
		
		if (credits >= price) {
			creditStorage.deductUserCredits(playerID, price);
			return new GamePlayToken(g, numPlays);
		} else {
			// TODO: This isn't a nice thing to return!
			return null;
		}
	}
	
	public Set<GamePlayToken> teamBulkPurchase(
			Set<Player> players,
			Game g,
			int numPlays) throws DatabaseException {
		HashSet<GamePlayToken> tokens = new HashSet<GamePlayToken>();
		int totalPrice = (int)(players.size() * g.pricePerPlay * numPlays * discountFactor(numPlays));
		int requiredCredits = totalPrice/players.size();
		//boolean allCanAfford = true;
		for (Player p: players) {

			int playerID = p.getID();
			int credits = creditStorage.getUserCredits(playerID);

			if (credits < requiredCredits) {
				//allCanAfford = false;
				return Collections.emptySet();
			}
		}
		for (Player p: players) {
			creditStorage.deductUserCredits(p.getID(), requiredCredits);
			tokens.add( new GamePlayToken(g, numPlays));

		}
		return tokens;
	}
	
}
