package deco2800.arcade.towerdefence.tests;

import org.junit.Assert;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.towerdefence.*;
import deco2800.arcade.towerdefence.pathfinding.Path;

public class TDTests {

	@Test
	public void EnemyDamageTest() {
		Enemy unarmoured = new Enemy(50, 0, 0, 0, 0.0, null);
		Enemy armoured = new Enemy(50, 10, 0, 0, 0.0, null);

		// Damage the unarmoured Enemy with a non-penetrating attack
		unarmoured.takeDamage(25);
		Assert.assertEquals(25, unarmoured.health());
		// Damage the unarmored Enemy with a penetrating attack
		unarmoured.takeDamage(20, 10);
		Assert.assertEquals(5, unarmoured.health());

		// Damage the armoured Enemy with a non-penetrating attack
		armoured.takeDamage(30);
		Assert.assertEquals(30, armoured.health());
		// Damage the armored Enemy with a penetrating attack
		// Penetration less than armor
		armoured.takeDamage(10, 5);
		Assert.assertEquals(25, armoured.health());
		// Damage the armored Enemy with a penetrating attack
		// Penetration more than armor
		armoured.takeDamage(10, 15);
		Assert.assertEquals(15, armoured.health());

	}

	@Test
	public void EnemyHealingTest() {
		Enemy meatbag = new Enemy(50, 0, 0, 0, 0.0, null);
		// Try healing from maximum health
		meatbag.heal(100);
		Assert.assertEquals(50, meatbag.health());
		// Damage the Enemy
		meatbag.takeDamage(40);
		// Heal them a small amount
		meatbag.heal(10);
		Assert.assertEquals(20, meatbag.health());
		// Heal them over the maximum
		meatbag.heal(100);
		Assert.assertEquals(50, meatbag.health());
	}

	@Test
	public void gridTest() {
		Grid grid = new Grid(200, 200, "grid", 20, null, null);
		GridObject object = new GridObject(0, 0, grid);
		// Check the object can be placed
		Assert.assertTrue(grid.buildObject(object));
		Assert.assertEquals(1, grid.getGridContents(0,0).size());
		//Move the object
		grid.moveObject(object, new Vector2(1,1));
		Assert.assertEquals(0, grid.getGridContents(0,0).size());
		Assert.assertEquals(1, grid.getGridContents(1,1).size());
		//Try building something on top of an alien - should be at 3,3 due to tilesize
		//Place the alien
		Enemy testAlien = new Enemy(5, 5, 60, 60, 5, grid);
		grid.buildObject(testAlien);
		Assert.assertEquals(1, grid.getGridContents(3,3).size());
		//Build something on it
		Assert.assertFalse(grid.buildObject(new GridObject(60, 60, grid)));
		//Check if a tile is blocked for pathing purposes
		Assert.assertTrue(grid.blocked(testAlien, 1, 1));
	}
	
	@Test
	public void mobileTest(){
		//Create the grid and enemy to use
		Grid grid = new Grid(200, 200, "grid", 20, null, new Vector2(19, 19));
		Enemy mobile = new Enemy(10, 10, 0, 0, 20, grid);
		grid.buildObject(mobile);
		//Test moving the object
		((Mobile) grid.getGridContents(0, 0).get(0)).moving(new Vector2(1,1));
		Assert.assertTrue(grid.getGridContents(0,0).size() == 0);
		Assert.assertTrue(grid.getGridContents(1,1).size() == 1);
		//Give the object a path
		Path path = new Path();
		path.appendStep(2, 2);
		((Mobile) grid.getGridContents(1, 1).get(0)).path(path);
		((Mobile) grid.getGridContents(1, 1).get(0)).followPath();
		Assert.assertTrue(grid.getGridContents(1,1).size() == 0);
		Assert.assertTrue(grid.getGridContents(2,2).size() == 1);
	}
}
