package deco2800.arcade.deerforest.models.cardContainers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.effects.AbstractEffect;

public class Hand extends AbstractCardCollection {
	private int limit;
	private ArrayList<AbstractCard> hand;
	
	//Initialize the hand
	public Hand() {
		limit = 6;
		hand = new ArrayList<AbstractCard>();
	}
	
	//Change card limit
	public void changeLimit(int newLimit) {
		limit = newLimit;
	}
		
	//Check if over limit
	public boolean overLimit() {
		if(hand.size() > limit) {
			return true;
		}
		return false;
	}
		
	@Override
	public boolean add(AbstractCard e) {
		// TODO Auto-generated method stub
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

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
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
