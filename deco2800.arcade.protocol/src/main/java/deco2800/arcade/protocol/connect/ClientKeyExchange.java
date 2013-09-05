package deco2800.arcade.protocol.connect;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

public class ClientKeyExchange {
	private String algorithm = "RSA";

	// FIXME may want to seal (asymmetrically) this whole class instead
	public SealedObject clientPublicKey;

	public PublicKey extractClientKey(PrivateKey serverPrivateKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, serverPrivateKey);

		PublicKey serverKey = (PublicKey) this.clientPublicKey
				.getObject(cipher);

		return serverKey;
	}

	public void setClientKey(PublicKey clientPublicKey,
			PublicKey serverPublicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);

		this.clientPublicKey = new SealedObject(clientPublicKey, cipher);
	}
}
