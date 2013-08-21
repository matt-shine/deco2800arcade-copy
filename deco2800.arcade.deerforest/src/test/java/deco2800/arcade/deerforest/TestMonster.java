package deco2800.arcade.deerforest;

import static org.junit.Assert.*;

import org.junit.Test;

import deco2800.arcade.deerforest.models.cards.DarkMonster;


public class TestMonster {
	
	@Test
	public void testToString() {
		DarkMonster dark = new DarkMonster(100, null);
		assertEquals("Type: Dark, Health: 100, Attacks: null", dark.toString());
	}
	
	
}
