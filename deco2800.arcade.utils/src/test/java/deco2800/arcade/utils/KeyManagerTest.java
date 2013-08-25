/*
 * KeyManagerTest
 */
package deco2800.arcade.utils;

import org.junit.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static com.badlogic.gdx.Input.Keys.*;

public class KeyManagerTest {

	@Test
	public void initWithNull() {
		KeyManager km = new KeyManager(null);

		assertEquals("The same key should be returned.",
				ANY_KEY, km.get(ANY_KEY));
	}

	@Test
	public void initWithMapping() {
		KeyManager km;
		Map<Integer, Integer> mapping = new HashMap<Integer, Integer>();

		mapping.put(A, LEFT);
		km = new KeyManager(mapping);

		assertEquals("The binded key should be returned.",
				LEFT, km.get(A));
		assertEquals("The same key should be returned.",
				ANY_KEY, km.get(ANY_KEY));
	}
}
