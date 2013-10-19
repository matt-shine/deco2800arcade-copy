package deco2800.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CertificateHandler {

	private String algorithm;
	private String pathClient;
	private String pathServer;

	public CertificateHandler() {
		algorithm = "RSA";
		pathClient = "client.cert";
		pathServer = "server.cert";
	}

	/**
	 * Generate server and client certificates
	 */
	public KeyPair generateCertificates() {
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		KeyPair keyPair = kpg.generateKeyPair();

		writeKeyPair(keyPair);
		
		return keyPair;
	}

	/**
	 * Get client certificate from file if it exists
	 * @return PrivateKey client certificate
	 */
	public PrivateKey getClientCertificate() {
		KeyPair keyPair = null;
		try {
			keyPair = readKeyPair();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return keyPair.getPrivate();
	}

	/**
	 * Get server certificate from file if it exists
	 * @return PublicKey server certificate
	 */
	public PublicKey getServerCertificate() {
		KeyPair keyPair = null;
		try {
			keyPair = readKeyPair();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return keyPair.getPublic();
	}

	private void writeKeyPair(KeyPair keyPair) {
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		// Write the server certificate to file
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		writeEncodedKeySpec(publicKeySpec.getEncoded(), pathServer);

		// Write the client certificate to file
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				privateKey.getEncoded());
		writeEncodedKeySpec(privateKeySpec.getEncoded(), pathClient);
	}

	private void writeEncodedKeySpec(byte[] encodedKeySpec, String path) {
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(path);
			stream.write(encodedKeySpec);
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private KeyPair readKeyPair() throws InvalidKeySpecException,
			FileNotFoundException {
		byte[] encodedPublicKey = readEncodedKey(pathServer);
		byte[] encodedPrivateKey = readEncodedKey(pathClient);

		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				encodedPublicKey);
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				encodedPrivateKey);

		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return new KeyPair(publicKey, privateKey);
	}

	private byte[] readEncodedKey(String path) throws FileNotFoundException {
		File file = new File(path);
		FileInputStream stream = new FileInputStream(path);
		byte[] encodedKey = new byte[(int) file.length()];

		try {
			stream.read(encodedKey);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return encodedKey;
	}

}
