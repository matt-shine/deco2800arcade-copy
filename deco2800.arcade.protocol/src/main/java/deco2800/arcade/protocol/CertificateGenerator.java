package deco2800.arcade.protocol;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CertificateGenerator {

	private static String algorithm = "RSA";
	private static String pathClient = "client.cert";
	private static String pathServer = "server.cert";
	
	/**
	 * Generates the server certificate keys.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO add automated gradle task to generate the certificates
		CertificateGenerator.generateCertificates();
	}

	/**
	 * Generate server and client certificates
	 */
	public static KeyPair generateCertificates() {
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		kpg.initialize(2048);
		KeyPair keyPair = kpg.generateKeyPair();

		writeKeyPair(keyPair);
		
		return keyPair;
	}

	private static void writeKeyPair(KeyPair keyPair) {
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

	private static void writeEncodedKeySpec(byte[] encodedKeySpec, String path) {
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
}