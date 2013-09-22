package deco2800.arcade.towerdefence;

import org.junit.Assert;
import org.junit.Test;

public class towerDefenceTest {
	
	@Test
	public void mortalDamageTest(){
		Alien unarmoured = new Alien(50, 0);
		Alien armoured = new Alien(50, 10);
		
		//Damage the unarmoured alien with a non-penetrating attack
		unarmoured.takeDamage(25);
		Assert.assertEquals(25, unarmoured.health());
		//Damage the unarmored alien with a penetrating attack
		unarmoured.takeDamage(20, 10);
		Assert.assertEquals(5, unarmoured.health());

		//Damage the armoured alien with a non-penetrating attack
		armoured.takeDamage(30);
		Assert.assertEquals(30, armoured.health());
		//Damage the armored alien with a penetrating attack
		//Penetration less than armor
		armoured.takeDamage(10, 5);
		Assert.assertEquals(25, armoured.health());
		//Damage the armored alien with a penetrating attack
		//Penetration more than armor
		armoured.takeDamage(10, 15);
		Assert.assertEquals(15, armoured.health());
		
	}
	

}
