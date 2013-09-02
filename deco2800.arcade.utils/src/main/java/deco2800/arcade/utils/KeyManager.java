/*
 * KeyManager
 */
package deco2800.arcade.utils;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.UNKNOWN;

public class KeyManager {
	private final Map<Integer, Integer> mapping;

	/**
	 * Constructs a new KeyManager with the specified mapping.
	 * If mapping is null, then every key is mapped to itself.
	 *
	 * @param mapping the mapping
	 */
	public KeyManager(Map<Integer, Integer> mapping) {
		this.mapping = new HashMap<Integer, Integer>();
		if (mapping != null) {
			this.mapping.putAll(mapping);
			for (Integer binded : mapping.values())
				this.mapping.put(binded, UNKNOWN);
		}
	}

	/**
	 * Returns the code binded to the specified keycode.
	 * If keycode is not managed, then itself will be returned.
	 * <p>
	 * For example, if A to SPACE is the only pair in the mapping,
	 * then get(A) returns SPACE whilst get(B) returns B.
	 *
	 * @param keycode the key
	 * @return the key binded to keycode
	 */
	public int get(int keycode) {
		Integer code;

		code = mapping.get(keycode);
		return (code == null) ? keycode : code;
	}
}
