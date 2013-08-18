package deco2800.arcade.deerforest;

import static org.junit.Assert.*;

import java.util.ArrayList;import java.util.List;

import org.junit.Test;



import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.GeneralSpell;
import deco2800.arcade.deerforest.models.cards.WaterMonster;
import deco2800.arcade.deerforest.models.effects.Attack;
import deco2800.arcade.deerforest.models.effects.IncorrectEffectException;

public class FieldTest {
	
	@Test 
	public void testAdd()
	{
		Attack lol = null;
		try {
			lol = new Attack(100, "Fire", null, null, null);
		} catch (IncorrectEffectException e) {
			e.printStackTrace();
		}
		
		List<Attack> lion = new ArrayList<Attack>();
		lion.add(lol);
		
		GeneralSpell spell = new GeneralSpell(null);
		
		WaterMonster monster = new WaterMonster(10,lion);
		
		Field playerField = new Field();
		
		playerField.add(monster);
		playerField.add(spell);
		
		//tests addition
		assertEquals(1,playerField.sizeMonsters());
		assertEquals(1,playerField.sizeSpells());
		assertEquals(2,playerField.size());
	}
	
	@Test
	public void testToArray() {
		Attack lol = null;
		try {
			lol = new Attack(100, "Fire", null, null, null);
		} catch (IncorrectEffectException e) {
			e.printStackTrace();
		}
		
		List<Attack> lion = new ArrayList<Attack>();
		lion.add(lol);
		
		GeneralSpell spell = new GeneralSpell(null);
		
		WaterMonster monster = new WaterMonster(10,lion);
		
		Field playerField = new Field();
		
		playerField.add(monster);
		playerField.add(spell);
		
		assertEquals("[monster,spell]",playerField.toArray());
	}
	
}
