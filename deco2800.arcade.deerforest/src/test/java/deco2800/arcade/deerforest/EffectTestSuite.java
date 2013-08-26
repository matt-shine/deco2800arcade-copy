package deco2800.arcade.deerforest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.deerforest.models.effects.*;

public class EffectTestSuite {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLowerBoundaryAttack() {
		
		//Define list of effect categories (smallest amount)
		ArrayList<String> effectCat = new ArrayList<String>();
		effectCat.add("Destroy");
		
		//Define parameter list for lower effect categories
		ArrayList<ArrayList<Integer>> effectParam = new ArrayList<ArrayList<Integer>>();
		//create effect category list
		ArrayList<Integer> destroyParam = new ArrayList<Integer>();
		destroyParam.add(0);
		destroyParam.add(0);
		destroyParam.add(0);
		destroyParam.add(0);
		destroyParam.add(0);
		destroyParam.add(0);
		effectParam.add(destroyParam);
		
		try {
			Attack lowerBounds = new Attack(0, "Fire", null, effectCat, effectParam);
			assertEquals(lowerBounds.effectCategories(), effectCat);
			assertEquals(effectParam, lowerBounds.effectParameter());
			assertEquals(lowerBounds.getAttackType(), "Fire");
			assertNull(lowerBounds.affectsTypes());
		} catch (IncorrectEffectException e) {
			System.out.println(e);
			fail("Attack bounds was considered an incorrect effect");
		}
	}
	
	@Test
	public void testUpperBoundaryAttack() {
		
		Random intGenerator = new Random();
		
		//Define Set of all possible types
		HashSet<String> effectTypes = new HashSet<String>();
		effectTypes.add("Fire");
		effectTypes.add("Nature");
		effectTypes.add("Water");
		effectTypes.add("Dark");
		effectTypes.add("Light");
		
		//Define list of effect categories (smallest amount)
		ArrayList<String> effectCat = new ArrayList<String>();
		effectCat.add("Destroy");
		effectCat.add("Draw");
		effectCat.add("Monster");
		effectCat.add("Search");
		effectCat.add("Player");
		
		//Define parameter list for lower effect categories
		ArrayList<ArrayList<Integer>> effectParam = new ArrayList<ArrayList<Integer>>();
		//create destroy effect category list
		ArrayList<Integer> destroyParam = new ArrayList<Integer>();
		destroyParam.add(intGenerator.nextInt(10));
		destroyParam.add(intGenerator.nextInt(4));
		destroyParam.add(intGenerator.nextInt(4));
		destroyParam.add(intGenerator.nextInt(4));
		destroyParam.add(intGenerator.nextInt(3));
		destroyParam.add(intGenerator.nextInt(6));
		effectParam.add(destroyParam);
		//create draw effect category list
		ArrayList<Integer> drawParam = new ArrayList<Integer>();
		drawParam.add(intGenerator.nextInt(10));
		drawParam.add(intGenerator.nextInt(10));
		drawParam.add(intGenerator.nextInt(10));
		drawParam.add(intGenerator.nextInt(3));
		drawParam.add(intGenerator.nextInt(4));
		effectParam.add(drawParam);
		//create monster effect category list
		ArrayList<Integer> monsterParam = new ArrayList<Integer>();
		monsterParam.add(intGenerator.nextInt(3));
		monsterParam.add(intGenerator.nextInt(3));
		monsterParam.add(intGenerator.nextInt(10));
		monsterParam.add(intGenerator.nextInt(5));
		monsterParam.add(intGenerator.nextInt(6));
		effectParam.add(monsterParam);
		//create search effect category list
		ArrayList<Integer> searchParam = new ArrayList<Integer>();
		searchParam.add(intGenerator.nextInt(10));
		searchParam.add(intGenerator.nextInt(4));
		searchParam.add(intGenerator.nextInt(4));
		searchParam.add(intGenerator.nextInt(3));
		searchParam.add(intGenerator.nextInt(3));
		searchParam.add(intGenerator.nextInt(10));
		searchParam.add(intGenerator.nextInt(10) + 10);
		searchParam.add(intGenerator.nextInt(10));
		searchParam.add(intGenerator.nextInt(10) + 10);
		searchParam.add(intGenerator.nextInt(5));
		effectParam.add(searchParam);
		//create player effect category list
		ArrayList<Integer> playerParam = new ArrayList<Integer>();
		playerParam.add(intGenerator.nextInt(3));
		playerParam.add(intGenerator.nextInt(2));
		playerParam.add(intGenerator.nextInt(10));
		effectParam.add(playerParam);
		
		try {
			Attack upperBounds = new Attack(0, "Water", effectTypes, effectCat, effectParam);
			assertEquals(upperBounds.effectCategories(), effectCat);
			assertEquals(effectParam, upperBounds.effectParameter());
			assertEquals(upperBounds.getAttackType(), "Water");
			assertEquals(upperBounds.affectsTypes(), effectTypes);
		} catch (IncorrectEffectException e) {
			System.out.println(e);
			fail("Attack bounds was considered an incorrect effect");
		}
		
	}
	
	@Test
	public void equalsTest() {
		// Define a bunch of lists and stuff
//		Set<String> typeEffects1 = new HashSet<String>();
//		typeEffects1.add("Water");
//		
//		Set<String> typeEffects2 = new HashSet<String>();
//		typeEffects2.add("Fire");
//		
//		List<String> effectCategories1 = new ArrayList<String>();
//		effectCategories1.add("Draw");
//		
//		List<? extends List<Integer>> effectParams1 = null;
//		
//		
//		try {
//			// Make 3 SpellEffects. The first two the same and the third different
////			SpellEffect attack1 = new SpellEffect(typeEffects1, effectCategories1, effectParams1);
////			SpellEffect attack2 = new SpellEffect(typeEffects1, effectCategories1, effectParams1);
////			SpellEffect attack3 = new SpellEffect(typeEffects2, effectCategories1, effectParams1);
//			
//			// Make sure the first two are equal
//			assertEquals(attack1, attack2);
//			// Make sure the last two are not equal
//			assertNotEquals(attack1, attack3);
//			
//		} catch (IncorrectEffectException e) {
//			e.printStackTrace();
//		}
	}

}
