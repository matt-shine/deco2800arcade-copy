package deco2800.arcade.deerforest.models.cardContainers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.effects.AbstractEffect;

public abstract class AbstractCardStack implements CardStack {
	
	protected List<AbstractCard> cardList;
	
	public AbstractCardStack() {
		cardList = new ArrayList<AbstractCard>();
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
		if (o instanceof AbstractCard) {
			return cardList.contains(o);
		} else {
			return false;
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
		return cardList.remove(o);
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
	public int count() {
		return cardList.size();
	}

	@Override
	public void shuffle() {
		Collections.shuffle(cardList);
	}

	@Override
	public CardCollection searchCard(AbstractCard card) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardCollection searchMonster(String type, int minHealth,
			int maxHealth, int minAttack, int MaxAttack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardCollection searchMonster(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardCollection searchMonster(String type, int minAttack,
			int minHealth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardCollection searchSpell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardCollection searchSpell(String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
