package deco2800.arcade.protocol.connect;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SealedObject;

import deco2800.arcade.protocol.AsymmetricSealer;

/**
 * Ready server public key for transmission as part of client-server handshake.
 * The public key is sealed and unsealed using the server certificate pair.
 * 
 * @author Team Mashup
 * 
 */
public class ServerKeyExchange {
	public SealedObject serverPublicKey;

	/**
	 * Retrieve the server public key from the message.
	 * 
	 * @param serverCert
	 * @return The server public key stored in the message
	 * @throws Exception
	 *             if unsealing the key fails
	 */
	public PublicKey getServerKey(PrivateKey serverCert) throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(serverCert);
		PublicKey serverKey = (PublicKey) sealer.unSeal(serverPublicKey);

		return serverKey;
	}

	/**
	 * Store the server public key in the message.
	 * 
	 * @param serverPublicKey
	 *            the key stored in the message
	 * @param serverCert
	 *            the certificate key used to seal the message
	 * @throws Exception
	 *             if sealing the key fails
	 */
	public void setServerKey(PublicKey serverPublicKey, PublicKey serverCert)
			throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(serverCert);
		this.serverPublicKey = sealer.seal(serverPublicKey);
	}
}
