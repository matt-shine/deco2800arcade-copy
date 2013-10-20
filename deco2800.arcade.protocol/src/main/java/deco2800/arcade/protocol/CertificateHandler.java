package deco2800.arcade.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Helper class for reading server certificate key pair from file.
 * 
 * @author Team Mashup
 * @see CertificateGenerator for generating the certificates
 * 
 */
public class CertificateHandler {

	private static String algorithm = "RSA";

	private static String pathClient = "D:\\client.cert";
	private static String pathServer = "D:\\server.cert";


	/**
	 * Get client certificate from file if it exists
	 * 
	 * @return PrivateKey client certificate
	 */
	public static PrivateKey getClientCertificate() {
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
	 * 
	 * @return PublicKey server certificate
	 */
	public static PublicKey getServerCertificate() {
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

	private static KeyPair readKeyPair() throws InvalidKeySpecException,
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

	private static byte[] readEncodedKey(String path)
			throws FileNotFoundException {
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
