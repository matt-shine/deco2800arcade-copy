package deco2800.arcade.protocol;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

public class Sealer {
	
	private Key secret;
	private String algorithm = "AES";
	
	public Sealer(Key secret) {
		this.secret = secret;
	}

	public SealedObject seal(NetworkObject object) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, secret);

		SealedObject sealedObject = new SealedObject(object, cipher);
		return sealedObject;
	}

	public Object unSeal(SealedObject sealedObject) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, secret);

		Object object = sealedObject.getObject(cipher);

		return object;
	}

}
