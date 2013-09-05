package deco2800.arcade.protocol.connect;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

public class ServerKeyExchange {
	private String algorithm = "RSA";
	
	// FIXME may want to seal (asymmetrically) this whole class instead
	public SealedObject serverPublicKey;

	public PublicKey extractServerKey(PrivateKey serverCert)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, serverCert);

		PublicKey serverKey = (PublicKey) this.serverPublicKey.getObject(cipher);

		return serverKey;
	}
}
