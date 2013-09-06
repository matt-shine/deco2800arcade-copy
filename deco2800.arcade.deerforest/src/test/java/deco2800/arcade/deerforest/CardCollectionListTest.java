package deco2800.arcade.deerforest;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import org.junit.Test;

import deco2800.arcade.deerforest.models.cardContainers.CardCollectionList;
import deco2800.arcade.deerforest.models.cards.*;

public class CardCollectionListTest {
	
	@Test
	public void initalStateTest() {
		// Create a new CardCollectionList
		CardCollectionList ccl = new CardCollectionList();
		// Test it is empty
		Assert.assertTrue(ccl.isEmpty());
	}
	
	@Test
	public void testBasicCollectionMethods() {
		
		// Create a new CardCollectionList
		CardCollectionList ccl = new CardCollectionList();
		// Create a new Card
		AbstractCard c1 = new WaterMonster(100, 20, null);
		AbstractCard c2 = new FireMonster(100, 20, null);
		AbstractCard c3 = new DarkMonster(100, 20, null);
		
		// Add a card
		ccl.add(c1);
		
		// Test that it's not empty
		Assert.assertFalse(ccl.isEmpty());
		
		// Test that it contains the card added
		Assert.assertTrue(ccl.contains(c1));
		
		// Test that the size of the array is 1
		Assert.assertEquals(1, ccl.size());
		
		// TODO Iterator test
		// TODO ToArray tests
		
		// Test removing the card
		Assert.assertTrue(ccl.remove(c1));
		
		// TODO Test more basic collection methods?
	}
	
	@Test
	public void moveCardTest() {
		
		// Create a new CardCollectionList
		CardCollectionList ccl = new CardCollectionList();
		// Create a new Card
		AbstractCard c1 = new WaterMonster(100, 20, null);
		
		// Add card to the first CardCollectionList
		ccl.add(c1);
		
		// Create a second CardCollectionList
		CardCollectionList ccl2 = new CardCollectionList();
		
		// Move the card from the first collection to the second
		ccl.moveCard(c1, ccl2);
		
		Assert.assertTrue(ccl2.contains(c1));		
	}
	
	@Test
	public void cardsTest() {
		// Create a new CardCollectionList
		CardCollectionList ccl = new CardCollectionList();
		// Create a new Card
		AbstractCard c1 = new WaterMonster(100, 20, null);
		AbstractCard c2 = new FireMonster(100, 20, null);
		AbstractCard c3 = new DarkMonster(100, 20, null);
		
		// Add a cards
		ccl.add(c1);
		ccl.add(c2);
		ccl.add(c3);
		
		// Create a List of the cards
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		
		// Add the cards
		list.add(c1);
		list.add(c2);
		list.add(c3);
		
		Assert.assertEquals(list, ccl.cards());
	}
	
	@Test
	public void destroyCardsTest() {
		
		// Create a new CardCollectionList
		CardCollectionList ccl = new CardCollectionList();
		CardCollectionList ccl2 = new CardCollectionList();
		
		// Create a new Card
		AbstractCard c1 = new WaterMonster(100, 20, null);
		AbstractCard c2 = new FireMonster(100, 20, null);
		AbstractCard c3 = new DarkMonster(100, 20, null);
		
		// Add a cards
		ccl.add(c1);
		ccl.add(c2);
		ccl.add(c3);
		
		ccl2.add(c2);
		ccl2.add(c3);
		
		// Create a List of the cards
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		
		// Add the cards
		list.add(c2);
		list.add(c3);
		
		// Assert the method returned the cards destroyed
		// TODO Make an equals method for CardCollection so we don't need to resort to comparing the internal array?
		Assert.assertEquals(ccl2.cards(), ccl.destroyCards(list).cards());
		
		// Assert the only remaining card is c1
		Assert.assertEquals(1, ccl.size());
		Assert.assertTrue(ccl.contains(c1));
	}
	/*
	@Test
	public void destroyCardTypeTest() {
		
		// Create a new CardCollectionList
		CardCollectionList ccl = new CardCollectionList();
		CardCollectionList ccl2 = new CardCollectionList();
		
		// Create a new Card
		AbstractCard c1 = new WaterMonster(100, null);
		AbstractCard c2 = new FireMonster(100, null);
		AbstractCard c3 = new DarkMonster(100, null);
		AbstractCard c4 = new FieldSpell(null);
		
		// Add a cards
		ccl.add(c1);
		ccl.add(c2);
		ccl.add(c3);
		ccl.add(c4);
		
		ccl2.add(c4);
		
		// Assert the method returned the cards destroyed
		// TODO Make an equals method for CardCollection so we don't need to resort to comparing the internal array?
		Assert.assertEquals(ccl2.cards(), ccl.destroyCardType("spell").cards());
		
		// Assert the only remaining cards are c1, c2 and c3
		Assert.assertEquals(3, ccl.size());
		Assert.assertFalse(ccl.contains(c4));
		
		ccl2.remove(c4);
		ccl2.add(c1);
		ccl2.add(c2);
		ccl2.add(c3);
		
		Assert.assertEquals(ccl2.cards(), ccl.destroyCardType("monster").cards());
	}
	*/
	@Test
	public void destroyRandomTest() {
		CardCollectionList ccl = new CardCollectionList();
		
		AbstractCard c1 = new WaterMonster(100, 20, null);
		AbstractCard c2 = new FireMonster(100, 20, null);
		AbstractCard c3 = new DarkMonster(100, 20, null);
		
		ccl.add(c1);
		ccl.add(c2);
		ccl.add(c3);
		
		Assert.assertTrue(ccl.destroyRandom(2).size() == 2);
		Assert.assertTrue(ccl.size() == 1);
		
		Assert.assertTrue(ccl.destroyRandom(100000).size() == 1);
		Assert.assertTrue(ccl.isEmpty());
		
	}
}
