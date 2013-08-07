package deco2800.server;

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
		} else {
			return 0.5d;
		}
		
	}

	public GamePlayToken bulkPurchase(
			Player p, 
			Game g, 
			int numPlays
    ) throws DatabaseException {
		
		int price = (int)(g.pricePerPlay * numPlays * discountFactor(numPlays));
		
		String username = p.getUsername();
		int credits = creditStorage.getUserCredits(username);
		
		if (credits >= price) {
			creditStorage.deductUserCredits(username, price);
			return new GamePlayToken(g, numPlays);
		} else {
			// TODO: This isn't a nice thing to return!
			return null;
		}
	}
	
	
}
