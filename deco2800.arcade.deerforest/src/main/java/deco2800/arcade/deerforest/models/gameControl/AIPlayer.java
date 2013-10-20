package deco2800.arcade.deerforest.models.gameControl;

import deco2800.arcade.deerforest.models.cardContainers.Deck;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;

/**
 * Subclass of the player class providing AI functionality
 */
public class AIPlayer extends DeerForestPlayer {

	//Clearly have all methods of Player just implemented slightly differently

	/**
	 * Initialise the player with deck
	 */
	public AIPlayer(Deck playerDeck) {
		super(playerDeck);
	}

	/**
	 * Do turn
	 */
	public boolean doTurn() {
		drawPhase();
		mainPhase();
		battlePhase();
		endPhase();
		compare(null, null);
		return false;
	}

	// Private methods for each turn section
	private boolean drawPhase() {
		return false;
	}

	private boolean mainPhase() {
		return false;
	}

	private boolean battlePhase() {
		return false;
	}
	
	private boolean endPhase() {
		return false;
	}

	/**
	 * Compare two cards (monsters in this example, could also be spells)
	 * @param card1
	 * @param card2
	 * @return
	 */
	private AbstractMonster compare(AbstractMonster card1, AbstractMonster card2) {
		return null;
	}
	
}
