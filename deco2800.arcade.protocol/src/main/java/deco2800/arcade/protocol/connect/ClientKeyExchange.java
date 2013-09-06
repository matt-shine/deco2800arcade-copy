package deco2800.arcade.protocol.connect;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SealedObject;

import deco2800.arcade.protocol.AsymmetricSealer;

/**
 * Ready client public key for transmission as part of client-server handshake.
 * The public key is sealed and unsealed using the server public and private key
 * pair.
 * 
 * @author Team Mashup
 * 
 */
public class ClientKeyExchange {
	private SealedObject clientPublicKey;

	/**
	 * Retrieve the client public key from the message.
	 * 
	 * @param serverPrivateKey
	 * @return The client public key stored in the message
	 * @throws Exception
	 *             if unsealing the key fails
	 */
	public PublicKey getClientKey(PrivateKey serverPrivateKey) throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(serverPrivateKey);
		PublicKey serverKey = (PublicKey) sealer.unSeal(clientPublicKey);

		return serverKey;
	}

	/**
	 * Store the client public key in the message.
	 * 
	 * @param clientPublicKey
	 *            the client public key to be stored in the message
	 * @param serverPublicKey
	 *            the key used to seal the message
	 * @throws Exception
	 *             if sealing the key fails
	 */
	public void setClientKey(PublicKey clientPublicKey,
			PublicKey serverPublicKey) throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(serverPublicKey);
		this.clientPublicKey = sealer.seal(clientPublicKey);
	}
}
