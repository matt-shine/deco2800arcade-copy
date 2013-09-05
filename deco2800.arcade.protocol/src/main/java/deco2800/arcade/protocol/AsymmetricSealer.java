package deco2800.arcade.protocol;

import java.security.Key;

// FIXME: class only has either Public or Private key
// so only one of seal/unSeal will work as expected.
// This should be made clearer or another implementation used.
public class AsymmetricSealer extends Sealer {
	
	public AsymmetricSealer(Key secret) {
		this.secret = secret;
		this.algorithm = "RSA";
	}
}
