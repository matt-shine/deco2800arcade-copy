package deco2800.arcade.protocol.connect;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SealedObject;

import deco2800.arcade.protocol.AsymmetricSealer;

public class ServerKeyExchange {
	public SealedObject serverPublicKey;

	public PublicKey getServerKey(PrivateKey serverCert) throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(serverCert);
		PublicKey serverKey = (PublicKey) sealer.unSeal(serverPublicKey);

		return serverKey;
	}

	public void setServerKey(PublicKey serverPublicKey, PublicKey serverCert)
			throws Exception {
		AsymmetricSealer sealer = new AsymmetricSealer(serverCert);
		this.serverPublicKey = sealer.seal(serverPublicKey);
	}
}
