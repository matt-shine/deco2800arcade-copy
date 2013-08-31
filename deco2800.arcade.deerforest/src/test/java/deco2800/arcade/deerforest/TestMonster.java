package deco2800.arcade.deerforest;

import static org.junit.Assert.*;

import org.junit.Test;

import deco2800.arcade.deerforest.models.cards.DarkMonster;
import deco2800.arcade.deerforest.models.cards.FireMonster;


public class TestMonster {
	
	@Test
	public void testToString() {
		DarkMonster dark = new DarkMonster(100, null, null);
		assertEquals("Type: Dark, Health: 100, Attacks: null", dark.toString());
	}
	
	@Test
	public void testEquals() {
		// Make two monsters the same
		DarkMonster m1 = new DarkMonster(100, null, null);
		DarkMonster m2 = new DarkMonster(100, null, null);
		
		// Make a monster thats different
		FireMonster m3 = new FireMonster(200, null, null);
		
		assertEquals(m1, m2);
		assertNotEquals(m2, m3);
	}
	
	
}
