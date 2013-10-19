package deco2800.arcade.deerforest.models.cardContainers;

import deco2800.arcade.deerforest.models.cards.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class Field extends AbstractCardCollection {
	private final int MAX_CARDS_EFFECTS = 2;
	private final int MAX_CARDS_MONSTER = 5;
	private ArrayList<AbstractMonster> fieldMonster;
	private ArrayList<AbstractSpell> fieldSpells;
	
	//Initialise field
	public Field() {
		fieldMonster = new ArrayList<AbstractMonster>();
		fieldSpells = new ArrayList<AbstractSpell>();
	}
	
	
	/**
	 * Returns true if field is empty.
	 */
	@Override
	public boolean isEmpty() {
		if(size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method adds a card to its matching list, ie. Monsters -> fieldMonster
	 * before adding however the method checks that the list isn't full.
	 * 
	 * @param AbstractCard e, The card to be added
	 * @return Return true if card has been added successfully.
	 */
	@Override
	public boolean add(AbstractCard e) {
		//If card is a monster
		if(e instanceof AbstractMonster) {
			if(fieldMonster.size() < MAX_CARDS_MONSTER) {
				fieldMonster.add((AbstractMonster)e);
				return true;
			} else { 
				return false;
			}
		
		//if card is a spell
		} else if(e instanceof AbstractSpell) {
			if(fieldSpells.size() < MAX_CARDS_EFFECTS) {
				fieldSpells.add((AbstractSpell)e);
			} else {
				return false;
			}
		} 
		
		return false;
	}
	
	@Override
	public int size() {
		return sizeMonsters() + sizeSpells();
	}
	
	/**
	 * Makes an array of monsters
	 * @return the monsters on the field in array form
	 */
	public Object[] monstersToArray() {
		return fieldMonster.toArray();
	}
	
	/**
	 * Makes an array of spells
	 * @return the spells on the field in array form
	 */
	public Object[] spellsToArray() {
		return fieldSpells.toArray();
	}
	
	@Override
	public Object[] toArray() {
		Object[] result = new Object[fieldMonster.toArray().length + fieldSpells.toArray().length];
		System.arraycopy(fieldMonster.toArray(),0, result, 0, fieldMonster.toArray().length);
		System.arraycopy(fieldSpells.toArray(),0, result,fieldMonster.toArray().length, fieldSpells.toArray().length);
		return result;
	}
	
	/**
	 * Returns true if field contains (AbstractCard) o
	 */
	@Override
	public boolean contains(Object o) {
		if(o instanceof AbstractMonster) {
			if(fieldMonster.contains(o)) {
				return true;
			}
		} else if (o instanceof AbstractSpell) {
			if(fieldSpells.contains(o)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks to see if field is full
	 * @return true only if both fieldMonster and fieldSpells are both full
	 */
	public boolean isFull() {
		if(monsterIsFull() && spellIsFull()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if monsters array is full
	 * @return true if full
	 */
	public boolean monsterIsFull() {
		if(sizeMonsters() == MAX_CARDS_MONSTER) {
			return true;
		}
		return false;
	}
	
	/**
	 *  Checks if spells array is full
	 * @return true if full
	 */
	public boolean spellIsFull() {
		if(sizeSpells() == MAX_CARDS_EFFECTS) {
			return true;
		}
		return false;
	}
	
	/**
	 *  Gets the size of the monster array
	 * @return size of monster array
	 */
	public int sizeMonsters() {
		return fieldMonster.size();
	}
	
	/**
	 * Gets the size of the spells array
	 * @return size of spell array
	 */
	public int sizeSpells() {
		return fieldSpells.size();
	}
	/**
	 * Removes object from its list
	 * @return true if removed
	 */
	@Override
	public boolean remove(Object o) {
		if(contains(o)) {
			if(o instanceof AbstractMonster) {
				return fieldMonster.remove(o);
			} else if( o instanceof AbstractSpell) {
				return fieldSpells.remove(o);
			}
		}
		return false;
	}
	
	
	/**
	 * checks if all the cards in the collection are in the list
	 * @return true if collection is contained  
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		int size = 0;
		for(Object card: c) {
			if(!contains(card)) {
				break;
			} else {
				size++;
			}
		}
		if(size == c.size()) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * Remove all elements in a collection form a list
	 * @return true once all elements are removed
	 */
	public boolean removeAll(Collection<?> c) {
		if(containsAll(c)) {
			for(Object card: c) {
				remove(card);
			}
			if(isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add all the elements in a collection to a this list
	 * @return true if successful
	 */
	@Override
	public boolean addAll(Collection<? extends AbstractCard> c) {
		for(AbstractCard card:c) {
			if (card instanceof AbstractMonster && !monsterIsFull()) {
				fieldMonster.add((AbstractMonster) card);
			} else if(card instanceof AbstractSpell && !spellIsFull()) {
				fieldMonster.add((AbstractMonster) card);
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CardCollection destroyCards(List<AbstractCard> cardsToDestroy) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CardCollection destroyCardType(String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Destroys all cards
	 */
	@Override
	public CardCollection destroyAllCards() {
		CardCollection c = (CardCollection) new ArrayList<AbstractCard>();
		for(AbstractCard card:fieldMonster) {
			c.add(card);
		}
		for(AbstractCard card:fieldSpells) {
			c.add(card);
		}
		fieldMonster.clear();
		fieldSpells.clear();
		return c;
	}

	@Override
	public CardCollection destroyRandom(int number) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Destroys all monsters on the field
	 */
    public void destroyAllMonsters() {
        this.fieldMonster = new ArrayList<AbstractMonster>();
    }
}
