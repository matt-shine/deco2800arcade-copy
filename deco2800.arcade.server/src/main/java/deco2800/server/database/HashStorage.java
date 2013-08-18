package deco2800.server.database;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HashStorage is an abstraction to the password database. Passwords are not
 * stored in plain text, instead they are put through a hashing function after
 * having been salted with a random sequence of bytes.
 * 
 * The class provides a set of methods that help to register and authenticate
 * players.
 * 
 * @author Team Mashup
 * @see <a
 *      href="https://github.com/UQdeco2800/deco2800-2013/wiki/Authentication-API">the
 *      wiki site</a>
 * 
 */
public class HashStorage {

	/**
	 * Create the database.
	 * 
	 * @throws DatabaseException
	 */
	public void initialise() throws DatabaseException {
		/* Not yet implemented */
	}

	/**
	 * Associate a password the the given username. Use when a player first
	 * registers an account.
	 * 
	 * @param username
	 * @param password
	 * @throws DatabaseException
	 * 
	 * @require no record for this user exists in the password database already
	 */
	public void registerPassword(String username, String password)
			throws DatabaseException {
		/* Not yet implemented */
	}

	/**
	 * Update the password of an existing user.
	 * 
	 * @param username
	 * @param password
	 *            the new password
	 * @throws DatabaseException
	 */
	public void updatePassword(String username, String password)
			throws DatabaseException {
		/* Not yet implemented */
	}

	/**
	 * Test whether a given username and password match the database records.
	 * Used to authenticate a player.
	 * 
	 * @param username
	 * @param password
	 *            the password to be compared with the database version
	 * @return
	 * @throws DatabaseException
	 */
	public Boolean checkPassword(String username, String password)
			throws DatabaseException {
		/* Not yet implemented */
		return null;
	}

	/**
	 * Retrieve salted password hash from the database.
	 * 
	 * @param username
	 * @return hash
	 * @throws DatabaseException
	 */
	private byte[] getHash(String username) throws DatabaseException {
		/* Not yet implemented */
		return null;
	}

	/**
	 * Retrieve salt from the database.
	 * 
	 * @param username
	 * @return salt
	 * @throws DatabaseException
	 */
	private byte[] getSalt(String username) throws DatabaseException {
		/* Not yet implemented */
		return null;
	}

	/**
	 * Update the value of the salt in the database.
	 * 
	 * @param username
	 * @param salt
	 * @throws DatabaseException
	 */
	private void updateSalt(String username, byte[] salt)
			throws DatabaseException {
		/* Not yet implemented */
	}

	/**
	 * Update the value of the salted password hash in the database.
	 * 
	 * @param username
	 * @param hash
	 * @throws DatabaseException
	 */
	private void updateHash(String username, byte[] hash)
			throws DatabaseException {
		/* Not yet implemented */
	}

	/**
	 * Find the hash value for the salt and password and check that it matches
	 * the given hash.
	 * 
	 */
	private Boolean checkPassword(String password, byte[] salt, byte[] hash)
			throws NoSuchAlgorithmException {
		byte[] loginHash = generateHash(password, salt);
		return java.util.Arrays.equals(loginHash, hash);
	}

	/**
	 * Generate a hash value using the given password prefixed with the salt.
	 * 
	 */
	private byte[] generateHash(String password, byte[] salt)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");

		/* Prefix salt to digest */
		digest.update(salt);
		digest.update(password.getBytes());

		/* Generate the hash value from the salt & password combination */
		return digest.digest();
	}

	/**
	 * Generate a random 64-bit salt.
	 * 
	 */
	private byte[] generateSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

		/* Generate 64-bit salt */
		byte[] salt = new byte[64];
		random.nextBytes(salt);

		return salt;
	}
}
