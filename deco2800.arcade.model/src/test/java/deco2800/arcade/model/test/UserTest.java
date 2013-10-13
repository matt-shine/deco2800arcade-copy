package deco2800.arcade.model.test;

import org.junit.Test;
import org.junit.Assert;

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

	}

}
