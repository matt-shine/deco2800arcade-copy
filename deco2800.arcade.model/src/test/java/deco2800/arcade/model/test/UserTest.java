package deco2800.arcade.model.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.Assert;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.User;

public class UserTest {

	@Test
	/**
	 * Tests constructor, getID() method and toString() method.
	 */
	public void UserTest1() {
		User user = new User(5);
		Assert.assertTrue(user.getID() == 5);
		Assert.assertTrue(user.toString().equals("5"));

	}

	@Test
	/**
	 * Tests equals(), hashCode() and User(User) methods.
	 */
	public void UserTest2() {
		User user = new User(3);
		Assert.assertTrue(user.hashCode() == user.getID());

		User user2 = new User(user);
		Assert.assertTrue(user2.equals(user2));
		Assert.assertTrue(user2.equals(user));

		User user3 = new User(9001);
		Assert.assertTrue(!user3.equals(new LinkedList<Integer>()));
		Assert.assertTrue(!user3.equals(user));
		
		User u1 = new User();
		User u2 = new User(u1);
		Assert.assertEquals(u1, u2);

	}

	@Test
	public void UserTest3() {

		List<String> info = new ArrayList<String>();
		info.add("Ricky");
		info.add("Rick Astley");
		info.add("rick@astley.giveyouup");
		info.add("ARTS");
		info.add("#Rickroll");
		info.add("20");
		Set<User> blockset = new HashSet<User>();
		Set<User> friendset = new HashSet<User>();
		Set<User> pendset = new HashSet<User>();
		Set<Game> gameset = new HashSet<Game>();
		
		ArrayList<Boolean> privset = new ArrayList<Boolean>();
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);

		Player p1 = new Player(123, "THIS IS NOT A VALID PATH.html", info,
				friendset, pendset, blockset, gameset, privset);
		
		User user = new User(p1);
		Assert.assertTrue(user.getID() == p1.getID());

	}
}
