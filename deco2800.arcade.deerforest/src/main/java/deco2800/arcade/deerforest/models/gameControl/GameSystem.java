package deco2800.arcade.deerforest.models.gameControl;

import java.util.List;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.*;
import deco2800.arcade.deerforest.models.effects.Attack;
import deco2800.arcade.deerforest.models.effects.MonsterEffect;

public class GameSystem {

	private Player p1;
	private Player p2;
	private String currentPhase = null;
	private Player currentPlayer = null;
	private List<AbstractCard> selectionChoices = null;
	private List<AbstractCard> currentSelection = null;
	
	//Initialize the game, should get player data from controller (note players own their deck)
	public GameSystem(Player player1, Player player2) {
		p1 = player1;
		p2 = player2;
	}

	public boolean startgame(boolean player1ToStart) {
		//check that the game hasn't alreday started
		if(currentPhase == null && currentPlayer == null) {
			//set correct current player
			currentPlayer = player1ToStart?p1:p2;
			//start phase
			startPhase();
		}
		//game already started
		return false;
	}
	
	//returns the current phase
	public String getPhase() {
		return currentPhase;
	}

	//returns the player whose turn it is
	public Player currentPlayer() {
		return currentPlayer;
	}

	//do AI turn
	public boolean AITurn() {
		return false;
	}
	
	//Turn sections, sets turn to correct phase, controller class relays information about
	//what to do to this class, then if phase is ok does it

	//go to the next phase
	public boolean nextPhase() {
		return false;
	}
	
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

	/**
	 * Tries to summon a monster card to the given players side of the field
	 * Returns an int representing if the summon was successful or whether
	 * additional actions need to be taken (such as if monster has effect, 
	 * field full, etc)
	 * 
	 * Return parameter is as follows:
	 * 0: monster summoned successfully
	 * 1: monster summoned successfully, has effect that needs to be activated
	 * 2: field is full, cannot summon
	 * 3: error
	 * 
	 * @param monster Card to be placed on the field
	 * @param p Player to summon monster to 
	 * @return error code
	 */
	public int summon(AbstractMonster monster, Player p) {
		return 0;
	}
	
	/**
	 * Activate a field spell, plays it on the specified players side of the
	 * field. Returns an int representing if further actions need to be taken
	 * or if an error occurred
	 * 
	 * Return parameter is as follows:
	 * 0: Card activated successfully
	 * 1: Card requires some selection
	 * 2: field is full, cannot activate
	 * 3: error
	 * 
	 * @param fSpell spell card to activate
	 * @param player player that activated the card
	 * @return error code
	 */
	public int activate(FieldSpell fSpell, Player p) {
		return 0;
	}

	/**
	 * Activate a general spell from players side of field
	 * 
	 * Return parameter is as follows
	 * 0: card activated successfully
	 * 1: card requires some selection
	 * 2: card currently has no effect (eg destroy when no cards on the field)
	 * 3: error
	 * 
	 * @param gSpell spell card to activate
	 * @param p Player who activated card
	 * @return error code
	 */
	public int activate(GeneralSpell gSpell, Player p) {
		return 0;
	}

	/**
	 * activate a monster effect
	 * 
	 * Return parameter is as follows:
	 * 0: card activated successfully
	 * 1: card requires some selection
	 * 2: card currently has no effect
	 * 3: error
	 * 
	 * @param effect monster effect to activate
	 * @param p Player who owns monster
	 * @return error code
	 */
	public int activateMonsterEffect(MonsterEffect effect, Player p) {
		return 0;
	}
	
	/**
	 * activate an attack effect
	 * 
	 * * Return parameter is as follows:
	 * 0: card activated successfully
	 * 1: card requires some selection
	 * 2: card currently has no effect
	 * 3: error
	 * 
	 * @param attack attack with effect to activate
	 * @return error code
	 */
	public int activateAttackEffect(Attack attack) {
		return 0;
	}
		
	//Activate a search card
	public int searchWithCardEffect(AbstractCard card) {
		return 0;
	}
	
	//Select card from search, with where it should go
	public boolean selectSearchSelection (CardCollection cardLocation, List<AbstractCard> cards) {
		return false;
	}
	
	//Select card to destroy
	public boolean destroyCard (Player p, CardCollection cardLocation, AbstractCard card) {
		return false;
	}

	//Updates all cards to reflect the continuous effects
	//Updates player modifier list to reflect current effects
	public void updateContinousEffects() {
		
	}
	
	//Set the currentSelection
	public void setSelection(List<AbstractCard> selection) {
		this.currentSelection = selection;
	}
	
	//get the selection choices
	public List<AbstractCard> getSelectionOptions() {
		return this.selectionChoices;
	}
	
	//clear selection options / current selection
	public void clearSelections() {
		this.selectionChoices = null;
		this.currentSelection = null;
	}
	
	//Sort graveyard
	public void sortGrave(Player p) {
		p.getGraveyard().sort();
	}

}
