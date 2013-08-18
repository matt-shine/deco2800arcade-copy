package deco2800.arcade.deerforest.models.cardContainers;

import deco2800.arcade.deerforest.models.cards.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.effects.AbstractEffect;

public class Field implements CardCollection {
	private final int MAX_CARDS_EFFECTS = 2;
	private final int MAX_CARDS_MONSTER = 5;
	private ArrayList<AbstractMonster> fieldMonster;
	private ArrayList<AbstractSpell> fieldSpells;
	
	//Initialise field
	public Field() {
		fieldMonster = new ArrayList<AbstractMonster>();
		fieldSpells = new ArrayList<AbstractSpell>();
	}
	
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
	public boolean addAll(Collection<? extends AbstractCard> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	//check if full
	public boolean isFull() {
		return false;
	}
	
	@Override
	public Iterator<AbstractCard> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}


	public int sizeMonsters() {
		return fieldMonster.size();
	}
	
	public int sizeSpells() {
		return fieldSpells.size();
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

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean moveCard(AbstractCard card, CardCollection moveLocation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AbstractCard> cards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AbstractEffect> continuousEffects() {
		// TODO Auto-generated method stub
		return null;
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
