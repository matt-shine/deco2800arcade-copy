package deco2800.arcade.deerforest.models.gameControl;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.*;
import deco2800.arcade.deerforest.models.effects.Attack;
import deco2800.arcade.deerforest.models.effects.MonsterEffect;

public class GameSystem {

	private DeerForestPlayer p1;
	private DeerForestPlayer p2;
	private String currentPhase = null;
	private DeerForestPlayer currentPlayer = null;
	private List<AbstractCard> selectionChoices = null;
	//define if summon has happened this turn
	private boolean summoned;
	
	//Initialize the game, should get player data from controller (note players own their deck)
	public GameSystem(DeerForestPlayer player1, DeerForestPlayer player2) {
		p1 = player1;
		p2 = player2;
	}

	public boolean startgame(boolean player1ToStart) {
		//check that the game hasn't alreday started
		if(currentPhase == null && currentPlayer == null) {
			//set correct current player
			currentPlayer = player1ToStart?p1:p2;
			//start phase
			currentPhase = "StartPhase";
			summoned = false;
		}
		//game already started
		return false;
	}
	
	//returns the current phase
	public String getPhase() {
		return currentPhase;
	}

	//returns the player whose turn it is
	public DeerForestPlayer currentPlayer() {
		return currentPlayer;
	}

	//returns the first player
	public DeerForestPlayer player1() {
		return p1;
	}
	
	//do AI turn
	public boolean AITurn() {
		return false;
	}
	
	//Turn sections, sets turn to correct phase, controller class relays information about
	//what to do to this class, then if phase is ok does it

	//go to the next phase
	public boolean nextPhase() {
		
		if(currentPhase.equals("EndPhase")) {
			startPhase();
		} else if(currentPhase.equals("StartPhase")) {
			drawPhase();
		} else if(currentPhase.equals("DrawPhase")) {
			mainPhase();
		} else if(currentPhase.equals("MainPhase")) {
			battlePhase();
		} else if(currentPhase.equals("BattlePhase")) {
			endPhase();
		}
		
		return false;
	}
	
	//Start the turn, returns true if succeed
	public boolean startPhase() {
		
		if(currentPhase.equals("EndPhase")) {
			currentPhase = "StartPhase";
			currentPlayer = currentPlayer==p1?p2:p1;
			summoned = false;
			return true;
        }
		return false;
	}

	//sets to draw phase, returns true if succeed
	public boolean drawPhase() {
		
		if(currentPhase.equals("StartPhase")) {
			currentPhase = "DrawPhase";
			return true;
		}
		
		return false;
	}

	//sets to main phase, returns true if succeed
	public boolean mainPhase() {
		
		if(currentPhase.equals("DrawPhase")) {
			currentPhase = "MainPhase";
			return true;
		}
		
		return false;
	}

	//sets to battle phase, returns true if succeed
	public boolean battlePhase() {
		
		if(currentPhase.equals("MainPhase")) {
			currentPhase = "BattlePhase";
			return true;
		}
		
		return false;
	}
	
	//conducts a battle between two monsters
	public void doBattle(AbstractMonster mon1, AbstractMonster mon2) {
		
	}

	//sets to end phase, returns true if succeed, changes current player
	public boolean endPhase() {
		
		if(currentPhase.equals("BattlePhase")) {
			currentPhase = "EndPhase";
			return true;
		}
		
		return false;
	}

	//Checks if either player has won, returns victor if exists
	public DeerForestPlayer checkVictory() {
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
	public int summon(AbstractMonster monster, DeerForestPlayer p) {
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
	 * @param p player that activated the card
	 * @return error code
	 */
	public int activate(FieldSpell fSpell, DeerForestPlayer p) {
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
	public int activate(GeneralSpell gSpell, DeerForestPlayer p) {
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
	public int activateMonsterEffect(MonsterEffect effect, DeerForestPlayer p) {
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
	public boolean destroyCard (DeerForestPlayer p, CardCollection cardLocation, AbstractCard card) {
		return false;
	}

	//Updates all cards to reflect the continuous effects
	//Updates player modifier list to reflect current effects
	public void updateContinousEffects() {
		
	}
	
	
	//get the selection choices
	public List<AbstractCard> getSelectionOptions() {
		return this.selectionChoices;
	}
	
	//clear selection options
	public void clearSelections() {
		this.selectionChoices = new ArrayList<AbstractCard>();
	}
	
	//Sort graveyard
	public void sortGrave(DeerForestPlayer p) {
		p.getGraveyard().sort();
	}

    public void inflictDamage(int player, int amount) {

        if(player == 1) {
            p1.inflictDamage(amount);
        } else if(player == 2) {
            p2.inflictDamage(amount);
        }
    }

	public boolean getSummoned() {
		return summoned;
	}
	
	public void setSummoned(boolean b) {
		summoned = b;
	}
	
	public int playerLP(int player) {
		return player==1?p1.getLifePoints():p2.getLifePoints();
	}
	
	public CardCollection getCardCollection(int player, String area) {
		
		if(area.equals("Hand")) {
			return player==1?p1.getHand():p2.getHand();
		} else if(area.equals("Deck")) {
			return player==1?p1.getDeck():p2.getDeck();
		} else if(area.equals("Field")) {
			return player==1?p1.getField():p2.getField();
		} else if(area.equals("Graveyard")) {
			return player==1?p1.getGraveyard():p2.getGraveyard();
		}
		
		return null;
	}
	
	public AbstractCard draw(int player) {
		return player==1?p1.draw():p2.draw();
	}

	public boolean moveCards(int player, List<AbstractCard> cards, CardCollection oldLocation, CardCollection newLocation) {
		return player==1?p1.moveCards(cards, oldLocation, newLocation):p2.moveCards(cards, oldLocation, newLocation);
	}
}
