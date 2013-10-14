package deco2800.arcade.model.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.Blocked;
import deco2800.arcade.model.User;

public class BlockedTest {

	private Blocked b1;
	private Blocked b2;

	private User user1;
	private User user2;
	private User user3;
	private User user4;
	private User user5;
	private User user6;
	private User user7;
	private User user8;
	
	@Before
	public void initialise(){
		b1 = new Blocked();
		
		user1 = new User(1);
		user2 = new User(2);
		user3 = new User(3);
		user4 = new User(4);		
		user5 = new User(5);
		user6 = new User(6);
		user7 = new User(7);
		user8 = new User(8);
	}

	@Test
	/**
	 * Creates a new Blocked, adds Users to it, and then checks if said users
	 * are in Blocked. Also checks getUpdatedID() and getAdded().
	 */
	public void BlockedTest1() {

		b1.add(user1);
		b1.add(user1);
		Assert.assertTrue(b1.contains(user1));
		Assert.assertTrue(b1.getUpdatedID() == user1.getID());
		Assert.assertTrue(b1.getAdded());

		b1.add(user2);
		b1.add(user2);
		Assert.assertTrue(b1.contains(user2));
		Assert.assertTrue(b1.getUpdatedID() == user2.getID());
		Assert.assertTrue(b1.getAdded());

		b1.add(user3);
		b1.add(user3);
		Assert.assertTrue(b1.contains(user3));
		Assert.assertTrue(b1.getUpdatedID() == user3.getID());
		Assert.assertTrue(b1.getAdded());

		b1.add(user4);
		b1.add(user4);
		Assert.assertTrue(b1.contains(user4));
		Assert.assertTrue(b1.getUpdatedID() == user4.getID());
		Assert.assertTrue(b1.getAdded());

	}

	@Test
	/**
	 * Adds a set of new Users to Blocked, and then checks if said users
	 * are in Blocked.
	 */
	public void BlockedTest2() {
		
		Set<User> set = new HashSet<User>();
		set.add(user5);
		set.add(user6);
		set.add(user7);
		set.add(user8);

		b1.addAll(set);
		Assert.assertTrue(b1.contains(user5));
		Assert.assertTrue(b1.contains(user6));
		Assert.assertTrue(b1.contains(user7));
		Assert.assertTrue(b1.contains(user8));

	}

	@Test
	/**
	 * Removed users from Blocked, and then checks that removed users are not
	 *  in Blocked. Also Checks getAdded().
	 */
	public void BlockedTest3() {
		b1 = new Blocked();
		
		Set<User> set = new HashSet<User>();
		set.add(user5);
		set.add(user6);
		set.add(user7);
		set.add(user8);

		b1.addAll(set);
		
		b1.remove(user5);
		b1.remove(user5);
		Assert.assertTrue(!b1.contains(user5));
		System.out.println(b1.getUpdatedID());
		Assert.assertTrue(b1.getUpdatedID() == user5.getID());
		Assert.assertTrue(!b1.getAdded());

		b1.remove(user6);
		b1.remove(user6);
		Assert.assertTrue(!b1.contains(user6));
		Assert.assertTrue(b1.getUpdatedID() == user6.getID());
		Assert.assertTrue(!b1.getAdded());

		b1.remove(user7);
		b1.remove(user7);
		Assert.assertTrue(!b1.contains(user7));
		Assert.assertTrue(b1.getUpdatedID() == user7.getID());
		Assert.assertTrue(!b1.getAdded());

		b1.remove(user8);
		b1.remove(user8);
		Assert.assertTrue(!b1.contains(user8));
		Assert.assertTrue(b1.getUpdatedID() == user8.getID());
		Assert.assertTrue(!b1.getAdded());

	}

	@Test
	/**
	 * Tests getSet method
	 */
	public void BlockedTest4() {
		b1 = new Blocked();
		
		Set<User> set = new HashSet<User>();
		set.add(user5);
		set.add(user6);
		set.add(user7);
		set.add(user8);

		b1.addAll(set);
		
		Assert.assertEquals(set, b1.getSet());

	}
	
	@Test
	/**
	 * Tests Blocked(Blocked) constructor
	 */
	public void BlockedTest5() {
		b1 = new Blocked();
		
		Set<User> set = new HashSet<User>();
		set.add(user5);
		set.add(user6);
		set.add(user7);
		set.add(user8);

		b1.addAll(set);
		
		b2 = new Blocked(b1);
		
		Assert.assertTrue(b2.contains(user5));
		Assert.assertTrue(b2.contains(user6));
		Assert.assertTrue(b2.contains(user7));
		Assert.assertTrue(b2.contains(user8));


	}



}
