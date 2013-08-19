package deco2800.arcade.deerforest.models.gameControl;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.*;

public class GameSystem {

	//Initialise the game, should get player data from controller (note players own their deck)
	public GameSystem(Player player1, Player player2) {
		
	}

	//returns the current phase
	public String getPhase() {
		return null;
	}

	//returns the player whose turn it is
	public Player currentPlayer() {
		return null;
	}

	//do AI turn
	public boolean AITurn() {
		return false;
	}
	
	//Turn sections, sets turn to correct phase, controller class relays information about
	//what to do to this class, then if phase is ok does it

	//Start the turn, returns true if succeed
	public boolean startPhase() {
		return false;
	}

	//sets to draw phase, returns true if succeed
	public boolean drawPhase() {
		return false;
	}

	//sets to main phase, returns true if succeed
	public boolean mainPhase() {
		return false;
	}

	//sets to battle phase, returns true if succeed
	public boolean battlePhase() {
		return false;
	}
	
	//conducts a battle between two monsters
	public void doBattle(AbstractMonster mon1, AbstractMonster mon2) {
		
	}

	//sets to end phase, returns true if succeed, changes current player
	public boolean endPhase() {
		return false;
	}

	//Checks if either player has won, returns victor if exists
	public Player checkVictory() {
		return null;
	}
	
	//Possible Actions Section, what a controller can call based on user input

	//Summons a monster card
	public boolean summon(AbstractMonster monster) {
		return false;
	}
	
	//Activate a field spell
	public boolean activate(FieldSpell fSpell) {
		return false;
	}

	//Activate a general spell
	public boolean activate(GeneralSpell gSpell) {
		return false;
	}

	//Activate a search card
	public CardCollection searchWithCardEffect(AbstractCard card) {
		return null;
	}
	
	//Select card from search, with where it should go
	public boolean selectSearchSelection (CardCollection cardLocation) {
		return false;
	}
	
	//Select card to destroy
	public boolean destroyCard (Player p, CardCollection cardLocation, AbstractCard card) {
		return false;
	}

	//Sort graveyard
	public void sortGrave() {
		
	}

}
