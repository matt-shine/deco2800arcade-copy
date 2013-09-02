package deco2800.arcade.protocol.connect;

import java.security.NoSuchAlgorithmException;

import deco2800.arcade.protocol.UserRequest;

import javax.crypto.KeyGenerator;

public class ConnectionRequest extends UserRequest {
	public String password;
	public byte[] key;
	
	/**
	 * Generates the symmetric key used by both the server and client 
	 */
	public void generateKey() {
		try {
			key = KeyGenerator.getInstance("Blowfish").generateKey()
					.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
