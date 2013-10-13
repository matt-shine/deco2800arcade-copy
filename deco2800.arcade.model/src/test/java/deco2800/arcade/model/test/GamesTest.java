package deco2800.arcade.model.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.Games;
import deco2800.arcade.model.Game;

public class GamesTest {

	private Games f1;
	private Games f2;

	private Game game1;
	private Game game2;
	private Game game3;
	private Game game4;
	private Game game5;
	private Game game6;
	private Game game7;
	private Game game8;
	
	@Before
	public void initialise(){
		f1 = new Games();
		
		game1 = new Game();
		game1.id = "1";
		game2 = new Game();
		game2.id = "2";
		game3 = new Game();
		game3.id = "3";
		game4 = new Game();
		game4.id = "4";
		game5 = new Game();
		game5.id = "5";
		game6 = new Game();
		game6.id = "6";
		game7 = new Game();
		game7.id = "7";
		game8 = new Game();
		game8.id = "8";
	}

	@Test
	/**
	 * Creates a new Games, adds Games to it, and then checks if said games
	 * are in Games. Also checks getUpdatedID() and getAdded().
	 */
	public void GamesTest1() {

		f1.add(game1);
		f1.add(game1);
		Assert.assertTrue(f1.contains(game1));
		Assert.assertTrue(f1.getUpdatedID() == game1.id);
		Assert.assertTrue(f1.getAdded());

		f1.add(game2);
		f1.add(game2);
		Assert.assertTrue(f1.contains(game2));
		Assert.assertTrue(f1.getUpdatedID() == game2.id);
		Assert.assertTrue(f1.getAdded());

		f1.add(game3);
		f1.add(game3);
		Assert.assertTrue(f1.contains(game3));
		Assert.assertTrue(f1.getUpdatedID() == game3.id);
		Assert.assertTrue(f1.getAdded());

		f1.add(game4);
		f1.add(game4);
		Assert.assertTrue(f1.contains(game4));
		Assert.assertTrue(f1.getUpdatedID() == game4.id);
		Assert.assertTrue(f1.getAdded());

	}

	@Test
	/**
	 * Adds a set of new Games to Games, and then checks if said games
	 * are in Games.
	 */
	public void GamesTest2() {
		
		Set<Game> set = new HashSet<Game>();
		set.add(game5);
		set.add(game6);
		set.add(game7);
		set.add(game8);

		f1.addAll(set);
		Assert.assertTrue(f1.contains(game5));
		Assert.assertTrue(f1.contains(game6));
		Assert.assertTrue(f1.contains(game7));
		Assert.assertTrue(f1.contains(game8));

	}

	@Test
	/**
	 * Removed games from Games, and then checks that removed games are not
	 *  in Games. Also Checks getAdded().
	 */
	public void GamesTest3() {
		f1 = new Games();
		
		Set<Game> set = new HashSet<Game>();
		set.add(game5);
		set.add(game6);
		set.add(game7);
		set.add(game8);

		f1.addAll(set);
		
		f1.remove(game5);
		f1.remove(game5);
		Assert.assertTrue(!f1.contains(game5));
		System.out.println(f1.getUpdatedID());
		Assert.assertTrue(f1.getUpdatedID() == game5.id);
		Assert.assertTrue(!f1.getAdded());

		f1.remove(game6);
		f1.remove(game6);
		Assert.assertTrue(!f1.contains(game6));
		Assert.assertTrue(f1.getUpdatedID() == game6.id);
		Assert.assertTrue(!f1.getAdded());

		f1.remove(game7);
		f1.remove(game7);
		Assert.assertTrue(!f1.contains(game7));
		Assert.assertTrue(f1.getUpdatedID() == game7.id);
		Assert.assertTrue(!f1.getAdded());

		f1.remove(game8);
		f1.remove(game8);
		Assert.assertTrue(!f1.contains(game8));
		Assert.assertTrue(f1.getUpdatedID() == game8.id);
		Assert.assertTrue(!f1.getAdded());

	}

	@Test
	/**
	 * Tests getSet method
	 */
	public void GamesTest4() {
		f1 = new Games();
		
		Set<Game> set = new HashSet<Game>();
		set.add(game5);
		set.add(game6);
		set.add(game7);
		set.add(game8);

		f1.addAll(set);
		
		Assert.assertEquals(set, f1.getSet());

	}
	
	@Test
	/**
	 * Tests Games(Games) constructor
	 */
	public void GamesTest5() {
		f1 = new Games();
		
		Set<Game> set = new HashSet<Game>();
		set.add(game5);
		set.add(game6);
		set.add(game7);
		set.add(game8);

		f1.addAll(set);
		
		f2 = new Games(f1);
		
		Assert.assertTrue(f2.contains(game5));
		Assert.assertTrue(f2.contains(game6));
		Assert.assertTrue(f2.contains(game7));
		Assert.assertTrue(f2.contains(game8));


	}



}
