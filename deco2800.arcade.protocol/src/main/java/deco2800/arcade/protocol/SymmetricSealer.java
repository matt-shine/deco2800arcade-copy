package deco2800.arcade.protocol;

import java.security.Key;

/**
 * Helper class for sealing and extracting secure network messages. The class
 * takes a symmetric SecretKey and can seal and unseal SealedObjects.
 * 
 * @author Team Mashup
 * 
 */
public class SymmetricSealer extends Sealer {

	/**
	 * Keeps track of the 'secret' used when transmitting objects.
	 * Also stores the type of algorithm used in transmission.
	 * @param secret
	 */
	public SymmetricSealer(Key secret) {
		this.secret = secret;
		this.algorithm = "AES";
	}
}
