package deco2800.arcade.protocol;

import java.io.Serializable;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

/**
 * Helper class for sealing and extracting secure network messages.
 * 
 * @author Team Mashup
 * 
 */
public abstract class Sealer {
	protected String algorithm;
	protected Key secret;
	
	/**
	 * Securely seals the given object.
	 * @param object 
	 * @return SealedObject
	 * @throws Exception
	 */
	public SealedObject seal(Serializable object) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, secret);

		SealedObject sealedObject = new SealedObject(object, cipher);
		return sealedObject;
	}

	/**
	 * Extracts object from the given sealed object.
	 * @param sealedObject 
	 * @return Object
	 * @throws Exception 
	 */
	public Object unSeal(SealedObject sealedObject) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, secret);

		NetworkObject object = (NetworkObject) sealedObject.getObject(cipher);

		return object;
	}
}
