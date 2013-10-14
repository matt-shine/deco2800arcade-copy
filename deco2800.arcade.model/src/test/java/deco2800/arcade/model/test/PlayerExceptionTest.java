package deco2800.arcade.model.test;

import org.junit.Assert;
import org.junit.Test;

import deco2800.arcade.model.PlayerException;

public class PlayerExceptionTest {

	@Test
	public void PlayerExceptionTest1() {
		PlayerException p = new PlayerException("Message");

		Assert.assertTrue(p.getMessage() == "Message");

		PlayerException p2 = new PlayerException();

		Assert.assertTrue(p2.getMessage() == null);

	}

}
