package deco2800.arcade.towerdefence.tests;

import org.junit.Assert;
import org.junit.Test;

import deco2800.arcade.towerdefence.*;

public class TDTests {
	
	@Test
	public void EnemyDamageTest(){
		Enemy unarmoured = new Enemy(50, 0, 0, 0, 0.0, null);
		Enemy armoured = new Enemy(50, 10, 0, 0, 0.0, null);
		
		//Damage the unarmoured Enemy with a non-penetrating attack
		unarmoured.takeDamage(25);
		Assert.assertEquals(25, unarmoured.health());
		//Damage the unarmored Enemy with a penetrating attack
		unarmoured.takeDamage(20, 10);
		Assert.assertEquals(5, unarmoured.health());

		//Damage the armoured Enemy with a non-penetrating attack
		armoured.takeDamage(30);
		Assert.assertEquals(30, armoured.health());
		//Damage the armored Enemy with a penetrating attack
		//Penetration less than armor
		armoured.takeDamage(10, 5);
		Assert.assertEquals(25, armoured.health());
		//Damage the armored Enemy with a penetrating attack
		//Penetration more than armor
		armoured.takeDamage(10, 15);
		Assert.assertEquals(15, armoured.health());
		
	}
	
	@Test
	public void EnemyHealingTest(){
		Enemy meatbag = new Enemy(50, 0, 0, 0, 0.0, null);
		//Try healing from maximum health
		meatbag.heal(100);
		Assert.assertEquals(50, meatbag.health());
		//Damage the Enemy
		meatbag.takeDamage(40);
		//Heal them a small amount
		meatbag.heal(10);
		Assert.assertEquals(20, meatbag.health());
		//Heal them over the maximum
		meatbag.heal(100);
		Assert.assertEquals(50, meatbag.health());
	}
	
	@Test
	public void gridMovementTest(){
		Grid grid = new Grid(200, 200, "grid", 20, null);
		GridObject object = new GridObject(0, 0, grid);
		Assert.assertTrue(grid.buildObject(object));
	}
}
