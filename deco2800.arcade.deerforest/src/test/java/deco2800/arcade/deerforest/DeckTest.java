package deco2800.arcade.deerforest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.CardCollectionList;
import deco2800.arcade.deerforest.models.cardContainers.Deck;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.DarkMonster;
import deco2800.arcade.deerforest.models.cards.FieldSpell;
import deco2800.arcade.deerforest.models.cards.FireMonster;
import deco2800.arcade.deerforest.models.cards.GeneralSpell;
import deco2800.arcade.deerforest.models.cards.WaterMonster;

public class DeckTest {
	
	private Deck d;
	private AbstractCard c1;
	private AbstractCard c2;
	private AbstractCard c3;
	private AbstractCard c4;
	
	public DeckTest() {
		// Create a new list of cards
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		
		// Create a new Card
		c1 = new WaterMonster(100, null, null);
		c2 = new FireMonster(100, null, null);
		c3 = new DarkMonster(100, null, null);
		c4 = new GeneralSpell(null, null);
		
		list.add(c1);
		list.add(c2);
		list.add(c3);
		
		// New Deck
		d = new Deck(list);
	}
	
	@Test
	public void initalStateTest() {

		// Test that the deck contains the cards given at initialisation
		Assert.assertFalse(d.isEmpty());
		Assert.assertEquals(3, d.size());
		Assert.assertTrue(d.contains(c1));
		Assert.assertTrue(d.contains(c2));
		Assert.assertTrue(d.contains(c3));
		
	}
	
	@Test
	public void testBasicCollectionMethods() {
		
		// Add a card
		d.add(c4);
		
		// Test that it's not empty
		Assert.assertFalse(d.isEmpty());
		
		// Test that it contains the card added
		Assert.assertTrue(d.contains(c4));
		
		// Test that the size of the array is 1
		Assert.assertEquals(4, d.size());
		
		// TODO Iterator test
		// TODO ToArray tests
		
		// Test removing the card
		Assert.assertTrue(d.remove(c4));
		
		// TODO Test more basic collection methods?
	}
	
	@Test
	public void moveCardTest() {
		
		// Create a new CardCollectionList
		CardCollectionList ccl = new CardCollectionList();
		
		// Move the card from the first collection to the second
		d.moveCard(c1, ccl);
		
		// Check that it worked
		Assert.assertTrue(ccl.contains(c1));
		Assert.assertFalse(d.contains(c1));

	}
	
	@Test
	public void cardsTest() {
		
		System.out.println(d.cards());
		
		// Create a List of cards
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		
		// Add the cards
		list.add(c1);
		list.add(c2);
		list.add(c3);
		
		Assert.assertEquals(list, d.cards());
	}
	
	@Test
	public void destroyCardsTest() {
		
		// Create a new CardCollectionList
		CardCollectionList ccl = new CardCollectionList();
		
		ccl.add(c2);
		ccl.add(c3);
		
		// Create a List of the cards
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		
		// Add the cards
		list.add(c2);
		list.add(c3);
		
		// Assert the method returned the cards destroyed
		// TODO Make an equals method for CardCollection so we don't need to resort to comparing the internal array?
		Assert.assertEquals(ccl.cards(), d.destroyCards(list).cards());
		
		// Assert the only remaining card is c1
		Assert.assertEquals(1, d.size());
		Assert.assertTrue(d.contains(c1));
	}
	
	@Test
	public void destroyCardTypeTest() {
		d.add(c4);
		
		// Create a new CardCollectionList
		CardCollectionList ccl = new CardCollectionList();
		
		ccl.add(c4);
		
		// Assert that the method returned the cards destroyed
		Assert.assertEquals(ccl.cards(), d.destroyCardType("Spell").cards());
		
		// Assert the only remaining cards are c1, c2 and c3
		Assert.assertEquals(3, d.size());
		Assert.assertFalse(d.contains(c4));
	}
	
	@Test
	public void drawTest() {
//		// Draw the first card and make sure it gives the correct card
//		Assert.assertEquals(c1, d.draw());
//		
//		// Make sure the size is now 2 and that the deck doesn't contain the card drawn
//		Assert.assertTrue(d.size() == 2);
//		Assert.assertFalse(d.contains(c1));
//		
//		// Draw the rest of the cards (2 more)
//		d.draw();
//		d.draw();
//		
//		// Make sure that drawing from an empty deck is null
//		Assert.assertNull(d.draw());
	}
	
	@Test
	public void destroyRandomTest() {
		Assert.assertTrue(d.destroyRandom(2).size() == 2);
		Assert.assertTrue(d.size() == 1);
		
		Assert.assertTrue(d.destroyRandom(100000).size() == 1);
		Assert.assertTrue(d.isEmpty());
		
	}
	
	@Test
	public void shuffleTest() {
		
		// Get a list of the decks cards
		List<AbstractCard> list = d.cards();
		
		// Shuffle the deck
		d.shuffle();
		
		// Make sure there is still the same number of cards
		Assert.assertTrue(d.size() == 3);
	}
	
	@Test
	public void searchCardTest() {
		CardCollection cc = new CardCollectionList();
		cc.add(c3);
		
		// Test searching for the card when there's only one in the deck
		Assert.assertEquals(cc.cards(), d.searchCard(c3).cards());
		
		cc.add(c3);
		d.add(c3);
		
		// Test searching for the card when the deck contains two
		Assert.assertEquals(cc.cards(), d.searchCard(c3).cards());
	
		cc.remove(c3);
		cc.remove(c3);
		
		// Test searching for the card when the deck contains none
		Assert.assertEquals(cc.cards(), d.searchCard(c4).cards());
	}
	
//	public void searchTest();
}
