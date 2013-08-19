package deco2800.arcade.deerforest;

import org.junit.Test;

import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cards.DarkMonster;
import deco2800.arcade.deerforest.models.cards.GeneralSpell;
import static org.junit.Assert.*;


public class FieldTest {
	
	@Test
	public void testAdd() {
		DarkMonster monster1 = new DarkMonster(10,null);
		Field playerField = new Field();
		GeneralSpell spell = new GeneralSpell(null);
		
		playerField.add(monster1);
		playerField.add(spell);
		
		assertEquals(1,playerField.sizeMonsters());
		assertEquals(1,playerField.sizeSpells());
		assertEquals(2,playerField.size());
	}
	
	@Test
	public void testIsEmpty() {
	    Field playerField = new Field();
		assertEquals(true, playerField.isEmpty());
		
		GeneralSpell spell = new GeneralSpell(null);
		//DarkMonster monster1 = new DarkMonster(10,null);
		playerField.add(spell);
		
		assertEquals(false, playerField.isEmpty()); 
	}
	
	@Test
	public void TestClear() {
		
	}
}