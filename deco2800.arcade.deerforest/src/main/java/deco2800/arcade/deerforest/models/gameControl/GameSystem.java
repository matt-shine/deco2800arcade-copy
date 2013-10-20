package deco2800.arcade.deerforest.models.gameControl;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.*;
import deco2800.arcade.deerforest.models.effects.Attack;
import deco2800.arcade.deerforest.models.effects.MonsterEffect;

/**
 * Collection of helper methods for the MainGame Class
 */
public class GameSystem {

	private DeerForestPlayer p1;
	private DeerForestPlayer p2;
	private String currentPhase = null;
	private DeerForestPlayer currentPlayer = null;
	private List<AbstractCard> selectionChoices = null;
	//define if summon has happened this turn
	private boolean summoned;
    private boolean firstTurn;

    /**
     * Initialize the game, should get player data from controller (note players own their deck)
     * @param player1 the first player in the game
     * @param player2 the second player in the game
     */
	public GameSystem(DeerForestPlayer player1, DeerForestPlayer player2) {
		p1 = player1;
		p2 = player2;
	}

    /**
     * Starts the game, setting variables are required
     * @param player1ToStart true if player 1 shall start
     * @return true if game started, false if the game had already begun
     */
	public boolean startgame(boolean player1ToStart) {
		//check that the game hasn't alreday started
		if(currentPhase == null && currentPlayer == null) {
			//set correct current player
			currentPlayer = player1ToStart?p1:p2;
			//start phase
			currentPhase = "StartPhase";
			summoned = false;
            firstTurn = true;
		}
		//game already started
		return false;
	}

    /**
     * 	Turn sections, sets turn to correct phase, controller class relays information about
     * 	what to do to this class, then if phase is ok does it
     * 	goes to the next phase
     */
	public boolean nextPhase() {
		
		if(currentPhase.equals("EndPhase")) {
			startPhase();
		} else if(currentPhase.equals("StartPhase")) {
			drawPhase();
		} else if(currentPhase.equals("DrawPhase")) {
			mainPhase();
		} else if(currentPhase.equals("MainPhase") && !firstTurn) {
            battlePhase();
        } else if(currentPhase.equals("MainPhase") && firstTurn) {
            endPhase();
        } else if(currentPhase.equals("BattlePhase")) {
			endPhase();
		}
		
		return false;
	}

    /**
     * Start the turn
     * @return true if succeeded
     */
	public boolean startPhase() {
		
		if(currentPhase.equals("EndPhase")) {
			currentPhase = "StartPhase";
			currentPlayer = currentPlayer==p1?p2:p1;
			summoned = false;
			return true;
        }
		return false;
	}

    /**
     * sets to draw phase
     * @return true if successful
     */
	public boolean drawPhase() {
		
		if(currentPhase.equals("StartPhase")) {
			currentPhase = "DrawPhase";
			return true;
		}
		
		return false;
	}

    /**
     * sets to main phase
     * @return true if successful
     */
	public boolean mainPhase() {
		
		if(currentPhase.equals("DrawPhase")) {
			currentPhase = "MainPhase";
			return true;
		}
		
		return false;
	}

    /**
     * sets to battle phase
     * @return true if successful
     */
	public boolean battlePhase() {
		
		if(currentPhase.equals("MainPhase")) {
			currentPhase = "BattlePhase";
			return true;
		}
		
		return false;
	}

    /**
     * sets to end phase, changes the current player
     * @return true if successful
     */
	public boolean endPhase() {
		
		if(currentPhase.equals("BattlePhase") || (currentPhase.equals("MainPhase") && firstTurn)) {
			currentPhase = "EndPhase";
            firstTurn = false;
			return true;
		}
		
		return false;
	}

    /**
     * Inflicts damage of the specified amount to the chosen player
     * @param player the player to damage
     * @param amount the amount to damage by
     */
    public void inflictDamage(int player, int amount) {

        if(player == 1) {
            p1.inflictDamage(amount);
        } else if(player == 2) {
            p2.inflictDamage(amount);
        }
    }

    /**
     * Draws a card from a given player
     * @param player the player to draw from
     * @return
     */
    public AbstractCard draw(int player) {
        return player==1?p1.draw():p2.draw();
    }

    /**
     * Moves cards between locations, owned by player
     * @param player the owner of the cards
     * @param cards the cards to move
     * @param oldLocation where the cards are currently
     * @param newLocation where the card will move to
     * @return true if move was successful
     */
    public boolean moveCards(int player, List<AbstractCard> cards, CardCollection oldLocation, CardCollection newLocation) {
        return player==1?p1.moveCards(cards, oldLocation, newLocation):p2.moveCards(cards, oldLocation, newLocation);
    }

    /**
     * Returns the CardCollection of the given player for the given area
     */
    public CardCollection getCardCollection(int player, String area) {
		
		if(area.contains("Hand")) {
			return player==1?p1.getHand():p2.getHand();
		} else if(area.contains("Deck")) {
			return player==1?p1.getDeck():p2.getDeck();
		} else if(area.contains("Field")) {
			return player==1?p1.getField():p2.getField();
		} else if(area.contains("Graveyard")) {
			return player==1?p1.getGraveyard():p2.getGraveyard();
		}
		
		return null;
	}

    /**
     * Returns true if the card is summoned or false otherwise
     */
    public boolean getSummoned() {
        return summoned;
    }

    /**
     * Returns the current phase
     */
    public String getPhase() {
        return currentPhase;
    }

    /**
     * Returns the player whose turn it is
     */
    public DeerForestPlayer currentPlayer() {
        return currentPlayer;
    }

    /**
     * returns the first player
     */
    public DeerForestPlayer player1() {
        return p1;
    }

    /**
     * Returns the lifepoints of the given player
     */
    public int playerLP(int player) {
        return player==1?p1.getLifePoints():p2.getLifePoints();
    }

    /**
     * Sets the current player
     */
    public void setCurrentPlayer(int player) {
        this.currentPlayer = player==1?p1:p2;
    }

    /**
     * Sets whether this is the first turn
     */
    public void setFirstTurn(boolean b) {
        this.firstTurn = b;
    }

    /**
     * Sets whether summoned or not
     */
    public void setSummoned(boolean b) {
        summoned = b;
    }

}
