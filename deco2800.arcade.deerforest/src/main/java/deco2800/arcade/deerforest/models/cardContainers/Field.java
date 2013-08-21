package deco2800.arcade.deerforest.models.cardContainers;

import deco2800.arcade.deerforest.models.cards.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.effects.AbstractEffect;

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
	
	public Object[] monstersToArray() {
		return fieldMonster.toArray();
	}
	
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
	
	public boolean monsterIsFull() {
		if(sizeMonsters() == MAX_CARDS_MONSTER) {
			return true;
		}//TODO consider making this one method with spellIsFull
		return false;
	}
	
	public boolean spellIsFull() {
		if(sizeSpells() == MAX_CARDS_EFFECTS) {
			return true;
		}
		return false;
	}
	
	public int sizeMonsters() {
		return fieldMonster.size();
	}
	
	public int sizeSpells() {
		return fieldSpells.size();
	}
	
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
	
	@Override
	public boolean addAll(Collection<? extends AbstractCard> c) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveCard(AbstractCard card, CardCollection moveLocation) {
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

	@Override
	public CardCollection destroyAllCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardCollection destroyRandom(int number) {
		// TODO Auto-generated method stub
		return null;
	}

}
