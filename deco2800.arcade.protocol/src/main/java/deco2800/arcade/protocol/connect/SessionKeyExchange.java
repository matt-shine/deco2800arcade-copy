package deco2800.arcade.protocol.connect;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import deco2800.arcade.protocol.AsymmetricSealer;

public class SessionKeyExchange {
	private SealedObject sessionKey;

	public SecretKey getSessionKey(PrivateKey clientPrivateKey)
			throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(clientPrivateKey);
		SecretKey serverKey = (SecretKey) sealer.unSeal(sessionKey);

		return serverKey;
	}

	public void setSessionKey(SecretKey sessionKey, PublicKey clientPublicKey)
			throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(clientPublicKey);
		this.sessionKey = sealer.seal(sessionKey);
	}
}
