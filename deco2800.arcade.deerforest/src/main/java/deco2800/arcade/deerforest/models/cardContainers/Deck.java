package deco2800.arcade.deerforest.models.cardContainers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.effects.AbstractEffect;

public class Deck implements CardStack {
	
	private List<AbstractCard> deckCards;

	//Initialise the deck with list of cards (gotten from user deck database)
	public Deck(List<AbstractCard> cards) {
		
		// Add all the cards to a new list
		deckCards = new ArrayList<AbstractCard>();
		deckCards.addAll(cards);
	}

	//Draw a card from deck, null if not possible
	public AbstractCard draw() {
		
		// Return the first card is the deck isn't empty
		if (!deckCards.isEmpty()) {
			return deckCards.get(0);
		} else {
			// Otherwise return null
			return null;
		}
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

	@Override
	public boolean add(AbstractCard arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends AbstractCard> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
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
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
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
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void shuffle() {
		// TODO Auto-generated method stub
		
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
