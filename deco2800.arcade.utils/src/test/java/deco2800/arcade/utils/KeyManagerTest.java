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

	private KeyManager km;

	@Before
	public void initialise() {
		Map<Integer, Integer> mapping = new HashMap<Integer, Integer>();

		mapping.put(A, LEFT);
		km = new KeyManager(mapping);
	}

	@Test
	public void initWithNull() {
		km = new KeyManager(null);

		assertEquals("The same key should be returned.",
				ANY_KEY, km.get(ANY_KEY));
	}

	@Test
	public void initWithMapping() {
		assertEquals("The binded key should be returned.",
				LEFT, km.get(A));
	}

	@Test
	public void unmappedKey() {
		assertEquals("The same key should be returned.",
				ANY_KEY, km.get(ANY_KEY));
	}

	@Test
	public void invalidateMappedKey() {
		assertEquals("An invalid key should be returned for any"
				+ " binded key.",
				UNKNOWN, km.get(LEFT));
	}
}
