package deco2800.server;

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
	 * Defines the discounts for purchasing multiple tokens
	 * @author Addison Gourluck
	 */
	private double discountFactor(int numUnits) {
		if (numUnits < 10) { // buying 1-9 gives no discount
			return 1d;
		} else if (numUnits < 20) { // buying 10-19 gives discount of 0.8
			return 0.8d;
		} else if (numUnits < 50) { // buying 20-49 gives discount of 0.6
			return 0.6d;
		} else if (numUnits < 100) { // buying 50-99 gives discount of 0.5
			return 0.5d;
		} else { // buying 100+ gives discount of 0.4
			return 0.4d;
		}
	}
	
	/**
	 * Defines the discounts bulk purchasing tokens in a group.
	 * @author Addison Gourluck
	 */
	private double groupDiscountFactor(int numUnits) {
		if (numUnits < 10) { // buying 0-9 gives discount of 0.9
			return 0.9d;
		} else if (numUnits < 20) { // buying 10-19 gives discount of 0.75
			return 0.75d;
		} else if (numUnits < 50) { // buying 20-49 gives discount of 0.5
			return 0.5d;
		} else if (numUnits < 100) { // buying 50-99 gives discount of 0.4
			return 0.4d;
		} else { // buying 100+ gives discount of 0.3
			return 0.3d;
		}
	}
	
	public GamePlayToken bulkPurchase(Player p, Game g, int numPlays)
			throws DatabaseException {
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
	
	/**
	 * This method takes a set of players to undergo a group bulk purchase.
	 * Upon all players being able to afford the transaction, it will deduct
	 * the funds from their credit storage, and return a set of gameplay
	 * tokens(token amount specified also) of the game specified.
	 * 
	 * @param players
	 * @param g
	 * @param numPlays
	 * @return Set<GamePlayToken>
	 * @throws DatabaseException
	 */
	public Set<GamePlayToken> teamBulkPurchase(Set<Player> players, Game g, int numPlays)
			throws DatabaseException {
		HashSet<GamePlayToken> tokens = new HashSet<GamePlayToken>();
		// Calculate the individual cost for each player.
		int requiredCredits = (int)(g.pricePerPlay * numPlays * groupDiscountFactor(numPlays));
		
		// Check all players can afford purchase
		for (Player p: players) {
			int playerID = p.getID();
			int credits = creditStorage.getUserCredits(playerID);
			if (credits < requiredCredits) {
				return null;
			}
		}
		// All playes must be able to afford the purchase, so deduct credits.
		for (Player p : players) {
			creditStorage.deductUserCredits(p.getID(), requiredCredits);
			tokens.add(new GamePlayToken(g, numPlays));
		}
		return tokens;
	}
	
}
