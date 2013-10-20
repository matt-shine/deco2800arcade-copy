package deco2800.arcade.protocol.connect;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import deco2800.arcade.protocol.AsymmetricSealer;

/**
 * Helper class to manage the session keys for a client/server session
 */
public class SessionKeyExchange {
	private SealedObject sessionKey;

	/**
	 * Get the server's private key
	 * @param clientPrivateKey
	 * @return SecretKey serverKey
	 * @throws Exception
	 */
	public SecretKey getSessionKey(PrivateKey clientPrivateKey)
			throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(clientPrivateKey);
		SecretKey serverKey = (SecretKey) sealer.unSeal(sessionKey);

		return serverKey;
	}

	/**
	 * Set the client's public key
	 * @param sessionKey
	 * @param clientPublicKey
	 * @throws Exception
	 */
	public void setSessionKey(SecretKey sessionKey, PublicKey clientPublicKey)
			throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(clientPublicKey);
		this.sessionKey = sealer.seal(sessionKey);
	}
}
