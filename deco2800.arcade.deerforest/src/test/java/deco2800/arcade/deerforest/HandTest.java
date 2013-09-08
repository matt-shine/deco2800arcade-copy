package deco2800.arcade.deerforest;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import deco2800.arcade.deerforest.models.cardContainers.Hand;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.DarkMonster;
import deco2800.arcade.deerforest.models.cards.LightMonster;


public class HandTest {
	@Test
	public void testDestroyAllCards() {
		Hand hand = new Hand();
		LightMonster light = new LightMonster(180,null, null);
		DarkMonster dark = new DarkMonster(190,null, null);
		Collection c = new ArrayList<AbstractCard>();
		
		hand.add(light);
		
		assertEquals(1,hand.size());
		
		hand.destroyAllCards();
		
		assertEquals(0,hand.size());
		
		c.add(dark);
		c.add(light);
		
		hand.addAll(c);
		assertEquals(2,hand.size());
		
		hand.destroyAllCards();
		assertEquals(0, hand.size());
	}
}
