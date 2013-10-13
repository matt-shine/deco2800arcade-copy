package deco2800.arcade.model.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.FriendInvites;
import deco2800.arcade.model.User;

public class FriendInvitedTest {

	private FriendInvites f1;
	private FriendInvites f2;

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
		f1 = new FriendInvites();
		
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
	 * Creates a new FriendInvites, adds Users to it, and then checks if said users
	 * are in FriendInvites. Also checks getUpdatedID() and getAdded().
	 */
	public void FriendInvitesTest1() {

		f1.add(user1);
		f1.add(user1);
		Assert.assertTrue(f1.contains(user1));
		Assert.assertTrue(f1.getUpdatedID() == user1.getID());
		Assert.assertTrue(f1.getAdded());

		f1.add(user2);
		f1.add(user2);
		Assert.assertTrue(f1.contains(user2));
		Assert.assertTrue(f1.getUpdatedID() == user2.getID());
		Assert.assertTrue(f1.getAdded());

		f1.add(user3);
		f1.add(user3);
		Assert.assertTrue(f1.contains(user3));
		Assert.assertTrue(f1.getUpdatedID() == user3.getID());
		Assert.assertTrue(f1.getAdded());

		f1.add(user4);
		f1.add(user4);
		Assert.assertTrue(f1.contains(user4));
		Assert.assertTrue(f1.getUpdatedID() == user4.getID());
		Assert.assertTrue(f1.getAdded());

	}

	@Test
	/**
	 * Adds a set of new Users to FriendInvites, and then checks if said users
	 * are in FriendInvites.
	 */
	public void FriendInvitesTest2() {
		
		Set<User> set = new HashSet<User>();
		set.add(user5);
		set.add(user6);
		set.add(user7);
		set.add(user8);

		f1.addAll(set);
		Assert.assertTrue(f1.contains(user5));
		Assert.assertTrue(f1.contains(user6));
		Assert.assertTrue(f1.contains(user7));
		Assert.assertTrue(f1.contains(user8));

	}

	@Test
	/**
	 * Removed users from FriendInvites, and then checks that removed users are not
	 *  in FriendInvites. Also Checks getAdded().
	 */
	public void FriendInvitesTest3() {
		f1 = new FriendInvites();
		
		Set<User> set = new HashSet<User>();
		set.add(user5);
		set.add(user6);
		set.add(user7);
		set.add(user8);

		f1.addAll(set);
		
		f1.remove(user5);
		f1.remove(user5);
		Assert.assertTrue(!f1.contains(user5));
		System.out.println(f1.getUpdatedID());
		Assert.assertTrue(f1.getUpdatedID() == user5.getID());
		Assert.assertTrue(!f1.getAdded());

		f1.remove(user6);
		f1.remove(user6);
		Assert.assertTrue(!f1.contains(user6));
		Assert.assertTrue(f1.getUpdatedID() == user6.getID());
		Assert.assertTrue(!f1.getAdded());

		f1.remove(user7);
		f1.remove(user7);
		Assert.assertTrue(!f1.contains(user7));
		Assert.assertTrue(f1.getUpdatedID() == user7.getID());
		Assert.assertTrue(!f1.getAdded());

		f1.remove(user8);
		f1.remove(user8);
		Assert.assertTrue(!f1.contains(user8));
		Assert.assertTrue(f1.getUpdatedID() == user8.getID());
		Assert.assertTrue(!f1.getAdded());

	}

	@Test
	/**
	 * Tests getSet method
	 */
	public void FriendInvitesTest4() {
		f1 = new FriendInvites();
		
		Set<User> set = new HashSet<User>();
		set.add(user5);
		set.add(user6);
		set.add(user7);
		set.add(user8);

		f1.addAll(set);
		
		Assert.assertEquals(set, f1.getSet());

	}
	
	@Test
	/**
	 * Tests FriendInvites(FriendInvites) constructor
	 */
	public void FriendInvitesTest5() {
		f1 = new FriendInvites();
		
		Set<User> set = new HashSet<User>();
		set.add(user5);
		set.add(user6);
		set.add(user7);
		set.add(user8);

		f1.addAll(set);
		
		f2 = new FriendInvites(f1);
		
		Assert.assertTrue(f2.contains(user5));
		Assert.assertTrue(f2.contains(user6));
		Assert.assertTrue(f2.contains(user7));
		Assert.assertTrue(f2.contains(user8));


	}



}
