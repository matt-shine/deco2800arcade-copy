package deco2800.server;

import java.security.Key;
import java.security.PublicKey;

/**
 * Represents a connection with a single client. The class stores the shared
 * secret that allows for symmetric communication between the client and server,
 * as well as the client's public key.
 * 
 * @author Team Mashup
 * 
 */
public class Session {

	private PublicKey clientPublicKey;
	private Key sessionKey;

	public Session(Key sessionKey, PublicKey clientPublicKey) {
		this.sessionKey = sessionKey;
		this.clientPublicKey = clientPublicKey;
	}

	public void setClientPublicKey(PublicKey clientPublicKey) {
		this.clientPublicKey = clientPublicKey;
	}

	public PublicKey getClientPublicKey() {
		return this.clientPublicKey;
	}

	public void setSessionKey(Key sessionKey) {
		this.sessionKey = sessionKey;
	}

	public Key getSessionKey() {
		return this.sessionKey;
	}
}
