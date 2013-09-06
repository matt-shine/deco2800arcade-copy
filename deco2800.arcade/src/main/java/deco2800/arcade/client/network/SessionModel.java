package deco2800.arcade.client.network;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SessionModel {
	private String password;
	private String username;
	private PublicKey serverKey;
	private PrivateKey serverCert;
	private Key sessionSecret;
	
	private KeyPair clientKeyPair;
	
	public SessionModel(PrivateKey serverCert, KeyPair clientKeyPair) {
		this.serverCert = serverCert;
		this.clientKeyPair = clientKeyPair;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public Boolean hasPassword() {
		return this.password != null;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public Boolean hasUsername() {
		return this.username != null;
	}
	
	public void setSessionSecret(Key secret) {
		this.sessionSecret = secret;
	}

	public void setServerCert(PrivateKey serverCert) {
		// FIXME do integrity checks first?
		this.serverCert = serverCert;
	}

	public PrivateKey getServerCert() {
		// FIXME do integrity checks first?
		return this.serverCert;
	}
	
	public  void setServerKey(PublicKey serverKey) {
		// FIXME do integrity checks first?
		this.serverKey = serverKey;
	}
	
	public PublicKey getServerKey() {
		return this.serverKey;
	}
	
	public PublicKey getClientPublicKey() {
		return this.clientKeyPair.getPublic();
	}
	
	public PrivateKey getClientPrivateKey() {
		return this.clientKeyPair.getPrivate();
	}
	
	// Takes a PublicKey that has been sealed with the server's
	// certificate and tries to extract it.
	public void extractPublicKey() {
		// stub
	}
	
}
