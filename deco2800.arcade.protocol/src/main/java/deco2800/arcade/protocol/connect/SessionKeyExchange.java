package deco2800.arcade.protocol.connect;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;


public class SessionKeyExchange {
	private String algorithm = "RSA";
	
	// FIXME may want to seal (asymmetrically) this whole class instead
	public SealedObject sessionKey;

	public Key extractSessionKey(PrivateKey clientKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, clientKey);

		PublicKey serverKey = (PublicKey) this.sessionKey.getObject(cipher);

		return serverKey;
	}
}
