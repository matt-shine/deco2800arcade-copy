package deco2800.arcade.deerforest.models.cardContainers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class Graveyard implements CardStack {

	//Consider having a variable called "isSorted", this will allow searching
	//be faster as you can check only a section of the card list
	//Alternatively, have different holders (array, Linked list, etc) for each card type
	
	
	//Initialize graveyard
	public Graveyard() {
		
	}
	
	//check if grave only has spell cards
	public boolean onlySpells() {
		return false;
	}
	
	//check if grave only has monsters
	public boolean onlyMonsters() {
		return false;
	}
	
	//check if grave only has monsters of specific type
	public boolean onlyMonsters(String type) {
		return false;
	}
	
	//Sort graveyard by cards
	public void sort() {
		
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
