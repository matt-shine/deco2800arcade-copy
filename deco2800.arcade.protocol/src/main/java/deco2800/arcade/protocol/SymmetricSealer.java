package deco2800.arcade.protocol;

import javax.crypto.SecretKey;

/**
 * Helper class for sealing and extracting secure network messages. The class
 * takes a symmetric SecretKey and can seal and unseal SealedObjects.
 * 
 * @author Team Mashup
 * 
 */
public class SymmetricSealer extends Sealer {

	public SymmetricSealer(SecretKey secret) {
		this.secret = secret;
		this.algorithm = "AES";
	}
}
