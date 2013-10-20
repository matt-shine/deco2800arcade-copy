package deco2800.arcade.protocol;

import java.security.Key;

/**
 * Helper class for sealing and extracting secure network messages. The class
 * takes an asymmetric key (one of PublicKey or PrivateKey) which determines
 * whether the class can seal or unseal SealedObjects.
 * 
 * @author Team Mashup
 * 
 */
public class AsymmetricSealer extends Sealer {
	
	/**
	 * Keeps track of the 'secret' used when transmitting objects.
	 * Also stores the type of algorithm used in transmission.
	 * Class only has either Public or Private key
	 * so only one of seal/unSeal will work as expected.
	 * @param secret
	 */
	public AsymmetricSealer(Key secret) {
		this.secret = secret;
		this.algorithm = "RSA";
	}
}
