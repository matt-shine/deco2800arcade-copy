package deco2800.arcade.protocol.connect;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SealedObject;

import deco2800.arcade.protocol.AsymmetricSealer;

public class ClientKeyExchange {
	private SealedObject clientPublicKey;

	public PublicKey getClientKey(PrivateKey serverPrivateKey) throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(serverPrivateKey);
		PublicKey serverKey = (PublicKey) sealer.unSeal(clientPublicKey);

		return serverKey;
	}

	public void setClientKey(PublicKey clientPublicKey,
			PublicKey serverPublicKey) throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(serverPublicKey);
		this.clientPublicKey = sealer.seal(clientPublicKey);
	}
}
