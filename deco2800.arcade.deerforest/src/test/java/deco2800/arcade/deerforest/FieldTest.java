package deco2800.arcade.deerforest;

import org.junit.Test;

import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cards.DarkMonster;
import deco2800.arcade.deerforest.models.cards.FireMonster;
import deco2800.arcade.deerforest.models.cards.GeneralSpell;
import deco2800.arcade.deerforest.models.cards.LightMonster;
import deco2800.arcade.deerforest.models.cards.NatureMonster;
import deco2800.arcade.deerforest.models.cards.WaterMonster;
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
	public void TestContains() {
		Field playerField = new Field();
		
		LightMonster monster = new LightMonster(100, null);
		GeneralSpell spell = new GeneralSpell(null);
		
		assertEquals(false, playerField.contains(monster));
		assertEquals(false, playerField.contains(spell));
		
		playerField.add(spell);
		playerField.add(monster);
		
		assertEquals(true, playerField.contains(monster));
		assertEquals(true, playerField.contains(spell));
	}
	
	@Test
	public void TestIsFull() {
		Field playerField = new Field();
		
		assertEquals(false, playerField.isFull());
		
		LightMonster monster = new LightMonster(100, null);
		DarkMonster monster1 = new DarkMonster(10,null);
		NatureMonster monster2 = new NatureMonster(90,null);
		WaterMonster monster3 = new WaterMonster(40, null);
		FireMonster monster4 = new FireMonster(50,null);
		
		playerField.add(monster);
		playerField.add(monster1);
		playerField.add(monster2);
		playerField.add(monster3);
		playerField.add(monster4);
		
		assertEquals(false, playerField.isFull());
		assertEquals(true, playerField.monsterIsFull());
		
		GeneralSpell spell = new GeneralSpell(null);
		GeneralSpell spell1 = new GeneralSpell(null);
		
		playerField.add(spell);
		playerField.add(spell1);
		
		assertEquals(true, playerField.spellIsFull());
		assertEquals(true, playerField.isFull());
	}
	
	@Test
	public void TestRemove() {
		Field playerField = new Field();
		
		LightMonster monster = new LightMonster(100, null);
		DarkMonster monster1 = new DarkMonster(10,null);
		
		playerField.add(monster);
		playerField.add(monster1);
		
		assertEquals(2, playerField.sizeMonsters());
		
		playerField.remove(monster1);
		assertEquals(1, playerField.sizeMonsters());
		
		playerField.remove(monster);
		assertEquals(true, playerField.isEmpty());
		
		GeneralSpell spell = new GeneralSpell(null);
		GeneralSpell spell1 = new GeneralSpell(null);
		
		playerField.add(spell);
		playerField.add(spell1);
		
		assertEquals(2, playerField.sizeSpells());
		
		playerField.remove(spell1);
		assertEquals(1, playerField.sizeSpells());
		
		playerField.remove(spell);
		assertEquals(true, playerField.isEmpty());
		
		assertEquals(false, playerField.remove(monster1));
	}
	
}