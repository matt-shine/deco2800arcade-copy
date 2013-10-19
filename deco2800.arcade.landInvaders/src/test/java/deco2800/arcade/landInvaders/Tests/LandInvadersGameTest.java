package deco2800.arcade.landInvaders.Tests;

import org.junit.Assert;
import org.junit.Test;

import deco2800.arcade.landInvaders.*;
import java.util.*;
import java.io.*;



/**
 * Basic tests for the LandInvaders Game implementation class.
 * Tests include :
 * 1) Check if Game Score works
 * 2) Check if player's life gets reduced
 * 3) Check if Stage changes when conditions are met
 * 4) Check if a tile from the wall will be removed when the wall gets hit
 * 5) Check if enemy will be removed when enemy gets hit
 * 6) Check if game will end if game victory conditions are met 
 */
public class LandInvadersGameTest {

	@Test
	public void testGameScore() {
		Invaders i = new Invaders();
		int score = 0;
		i.hitEnemy();
		//Takes 2 arguments (actual tests results, expected)
		Assert.assertEquals(score, 1);
	}

}
