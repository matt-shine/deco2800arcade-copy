package deco2800.arcade.deerforest.models.cardContainers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.effects.AbstractEffect;

public abstract class AbstractCardCollection implements CardCollection {
	
	private List<AbstractCard> cardList;

	@Override
	public int size() {
		return cardList.size();
	}

	@Override
	public boolean isEmpty() {
		return cardList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if (!(o instanceof AbstractCard)) {
			return false;
		} else {
			return cardList.contains(o);
		}
	}

	@Override
	public Iterator<AbstractCard> iterator() {
		return cardList.iterator();
	}

	@Override
	public Object[] toArray() {
		return cardList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return cardList.toArray(a);
	}

	@Override
	public boolean add(AbstractCard e) {
		return cardList.add(e);
	}

	@Override
	public boolean remove(Object o) {
		if (!(o instanceof AbstractCard)) {
			return false;
		} else {
			return cardList.remove(o);
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return cardList.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends AbstractCard> c) {
		return cardList.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return cardList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return cardList.retainAll(c);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		cardList.clear();
	}

	@Override
	public boolean moveCard(AbstractCard card, CardCollection moveLocation) {
		if (cardList.contains(card)) {
			moveLocation.add(card);
			cardList.remove(card);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<AbstractCard> cards() {
		// Make a new list
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		list.addAll(cardList);
		return list;
	}

	@Override
	public CardCollection destroyCards(List<AbstractCard> cardsToDestroy) {
		
		// New empty list
		CardCollectionList cardsDestroyed = new CardCollectionList();
		
		// For each card
		for (AbstractCard card: cardsToDestroy) {
			// Try and remove the card
			if (cardList.remove(card)) {
				// If it's removed add it to the list of removed cards
				cardsDestroyed.add(card);
			}
		}
		// The list of cards destroyed 
		return cardsDestroyed;
	}

	@Override
	public CardCollection destroyCardType(String type) {
		
		/*
		// New empty list
		CardCollectionList cardsDestroyed = new CardCollectionList();
		
		// For each card
		for (AbstractCard card: cardsToDestroy) {
			// Try and remove the card
			if (cardList.remove(card) && card.) {
				// If it's removed add it to the list of removed cards
				cardsDestroyed.add(card);
			}
		}
		// The list of cards destroyed 
		return cardsDestroyed;
		*/
		return null;
	}

	@Override
	public CardCollection destroyAllCards() {
		CardCollectionList cardsDestroyed = new CardCollectionList();
		cardsDestroyed.addAll(cardList);
		
		cardList.clear();
		return cardsDestroyed;
	}

	@Override
	public CardCollection destroyRandom(int number) {
		// TODO Auto-generated method stub
		return null;
	}

}
