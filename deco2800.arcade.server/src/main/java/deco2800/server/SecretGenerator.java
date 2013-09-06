package deco2800.server;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecretGenerator {
	private static String algorithm = "AES";
	
	/**
	 * Generates the symmetric key used by both the server and client 
	 */
	public static SecretKey generateSecret() {
		SecretKey secret = null;
		try {
			secret = KeyGenerator.getInstance(algorithm).generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return secret;
	}
}
