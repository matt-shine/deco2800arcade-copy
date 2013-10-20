package deco2800.arcade.deerforest.models.cardContainers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;

/**
 * Abstract class for the CardCollection interface providing implementations for
 * all the CardCollection methods
 */
public abstract class AbstractCardCollection implements CardCollection {
	
	protected List<AbstractCard> cardList;
	
	/**
	 * Initialises the abstract card collection class
	 */
	public AbstractCardCollection() {
		cardList = new ArrayList<AbstractCard>();
	}

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
		
		// New empty list
		List<AbstractCard> cardsToDestroy = new ArrayList<AbstractCard>();
		// New empty list
		CardCollection cardsDestroyed = new CardCollectionList();
		
		// For each card
		for (AbstractCard card: cardList) {
			// If the card is of the given type
			if (card.getCardType().equals(type)) {
				cardsToDestroy.add(card);
			}
		}
		
		cardsDestroyed = destroyCards(cardsToDestroy);
		
		// Return list of cards destroyed 
		return cardsDestroyed;
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
		
		// New empty list
		CardCollectionList cardsDestroyed = new CardCollectionList();
		AbstractCard currentCard;
		
		for (int i = 0; i < number; i++) {
			if (!isEmpty()) {
				// Get a random number
				
				int min = 0;
				int max = cardList.size() - 1;
				
				int n = min + (int)(Math.random() * ((max - min) + 1));
				currentCard = cardList.get(n);
				
				if (currentCard != null) {
					if (remove(currentCard)) {
						cardsDestroyed.add(currentCard);
					}
				}
			}
		}
		
		return cardsDestroyed;
	}

}
