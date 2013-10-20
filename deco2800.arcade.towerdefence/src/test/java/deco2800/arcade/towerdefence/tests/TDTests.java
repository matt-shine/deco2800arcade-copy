package deco2800.arcade.towerdefence.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.towerdefence.model.*;
import deco2800.arcade.towerdefence.model.creationclasses.Enemy;
import deco2800.arcade.towerdefence.model.creationclasses.Projectile;
import deco2800.arcade.towerdefence.model.pathfinding.Path;

public class TDTests {

	@Test
	public void EnemyDamageTest() {
		Enemy unarmoured = new Enemy(50, 0, 0, 0, 0.0, null, null, 0, 0, 0, 0,
				0, null, null, null, null, null);
		Enemy armoured = new Enemy(50, 10, 0, 0, 0.0, null, null, 0, 0, 0, 0,
				0, null, null, null, null, null);

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
		Enemy meatbag = new Enemy(50, 0, 0, 0, 0.0, null, null, 0, 0, 0, 0, 0,
				null, null, null, null, null);
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
		GridObject object = new GridObject(0, 0, grid, null, null);
		// Check the object can be placed
		Assert.assertTrue(grid.buildObject(object));
		Assert.assertEquals(1, grid.getGridContents(0, 0).size());
		// Move the object
		grid.moveObject(object, new Vector2(1, 1));
		Assert.assertEquals(0, grid.getGridContents(0, 0).size());
		Assert.assertEquals(1, grid.getGridContents(1, 1).size());
		// Try building something on top of an alien - should be at 3,3 due to
		// tilesize
		// Place the alien
		Enemy testAlien = new Enemy(5, 5, 60, 60, 5, grid, null, 0, 0, 0, 0, 0,
				null, null, null, null, null);
		Assert.assertTrue(grid.placeAlien(testAlien));
		Assert.assertEquals(1, grid.getGridContents(3, 3).size());
		// Build something on it
		Assert.assertFalse(grid.buildObject(new GridObject(60, 60, grid, null,
				null)));
		// Check if a tile is blocked for pathing purposes
		Assert.assertTrue(grid.blocked(testAlien, 1, 1));
	}

	@Test
	public void mobileTest() {
		// Create the grid and enemy to use
		Grid grid = new Grid(200, 200, "grid", 20, null, new Vector2(19, 19));
		Enemy mobile = new Enemy(10, 10, 0, 0, 20, grid, null, 0, 0, 0, 0, 0,
				null, null, null, null, null);
		grid.buildObject(mobile);
		// Test moving the object
		((Mobile) grid.getGridContents(0, 0).get(0)).moving(new Vector2(1, 1));
		Assert.assertTrue(grid.getGridContents(0, 0).size() == 0);
		Assert.assertTrue(grid.getGridContents(1, 1).size() == 1);
		// Give the object a path
		Path path = new Path();
		path.appendStep(2, 2);
		((Mobile) grid.getGridContents(1, 1).get(0)).path(path);
		((Mobile) grid.getGridContents(1, 1).get(0)).followPath();
		Assert.assertTrue(grid.getGridContents(1, 1).size() == 0);
		Assert.assertTrue(grid.getGridContents(2, 2).size() == 1);
	}

	@Test
	public void pathfindingTest() {
		// Create a grid
		Grid grid = new Grid(100, 100, "grid", 10, null, new Vector2(9, 4));
		// Add some obstacles
		grid.buildObject(new GridObject(10, 20, grid, null, null));
		grid.buildObject(new GridObject(10, 30, grid, null, null));
		grid.buildObject(new GridObject(10, 40, grid, null, null));
		grid.buildObject(new GridObject(10, 50, grid, null, null));
		grid.buildObject(new GridObject(30, 50, grid, null, null));
		grid.buildObject(new GridObject(30, 60, grid, null, null));
		// Try finding a path
		Path path = grid.pathfinder().findPath(null, 0, 4, 9, 4);
		Assert.assertEquals(9, path.getStep(path.getLength() - 1).getX());
		Assert.assertEquals(4, path.getStep(path.getLength() - 1).getY());
	}

	@Test
	public void projectileTest(){
		Grid grid = new Grid(100, 100, "grid", 10, null, null);
		//Create a projectile with no radius dealing 50 damage
		Projectile bullet = new Projectile(0, 0, grid, new Vector2(10,10), 200,
			Team.Player, null, 50, 0, 0);
		//Create a target
		Enemy victim = new Enemy(100, 0, 30, 30, 0, grid, Team.Computer, 0, 0, 0,
				0, 0, null, null, null, null, null);
		grid.placeAlien(victim);
		//Shoot the projectile
		bullet.move();
		Assert.assertEquals(50, ((Enemy)grid.getGridContents(3,3).get(0)).health());
	}
	
	@Test
	public void areaDamageTest(){
		//Create grid
		Grid grid = new Grid(100, 100, "grid", 10, null, null);
		//Create explosive with radius 2 and 50 damage
		Projectile explosive = new Projectile(0, 0, grid, new Vector2(10,10), 200,
			Team.Player, null, 50, 0, 2);
		//Create 3 targets at varying distances
		Enemy victim1 = new Enemy(100, 0, 30, 30, 0, grid, Team.Computer, 0, 0, 0,
				0, 0, null, null, null, null, null);
		Enemy victim2 = new Enemy(100, 0, 40, 30, 0, grid, Team.Computer, 0, 0, 0,
				0, 0, null, null, null, null, null);
		Enemy victim3 = new Enemy(100, 0, 50, 30, 0, grid, Team.Computer, 0, 0, 0,
				0, 0, null, null, null, null, null);
		grid.placeAlien(victim1);
		grid.placeAlien(victim2);
		grid.placeAlien(victim3);
		//Shoot the explosive
		explosive.move();
		Assert.assertEquals(50, ((Enemy)grid.getGridContents(3,3).get(0)).health());
		Assert.assertEquals(50, ((Enemy)grid.getGridContents(4,3).get(0)).health());
		Assert.assertEquals(50, ((Enemy)grid.getGridContents(5,3).get(0)).health());
	}
}
