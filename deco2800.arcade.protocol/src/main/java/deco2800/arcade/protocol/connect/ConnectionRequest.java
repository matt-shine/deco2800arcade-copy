package deco2800.arcade.protocol.connect;

import java.security.NoSuchAlgorithmException;

import deco2800.arcade.protocol.UserRequest;

import javax.crypto.KeyGenerator;

public class ConnectionRequest extends UserRequest {
	private String password;
	private byte[] key;
	
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
	
	/**
	 * @return user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set user password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return ConnectionRequest encryption key
	 */
	public byte[] getKey() {
		return key;
	}

	/**
	 * Sets the encryption key for ConnectionRequest
	 * @param key
	 */
	public void setKey(byte[] key) {
		this.key = key;
	}
}
