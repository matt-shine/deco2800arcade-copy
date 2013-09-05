package deco2800.arcade.protocol;

import javax.crypto.SecretKey;

public class SymmetricSealer extends Sealer {
	
	public SymmetricSealer(SecretKey secret) {
		this.secret = secret;
		this.algorithm = "AES";
	}
}
