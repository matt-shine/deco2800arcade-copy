package deco2800.server.database;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.sun.org.apache.xml.internal.utils.Hashtree2Node;

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
	 * Close a database connection.
	 * 
	 * @throws DatabaseException 
	 */
	private void close(Connection c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the database.
	 * 
	 * @throws DatabaseException
	 */
	public void initialise() throws DatabaseException {
		
		// Get a connection to the database
		Connection connection = Database.getConnection();

		try {
			ResultSet tableData = connection.getMetaData().getTables(null,
					null, "AUTH", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE AUTH(playerID INT PRIMARY KEY,"
								+ "hash BLOB(64),"
								+ "salt BLOB(8),"
								+ "FOREIGN KEY (playerID)"
								+ "REFERENCES PLAYERS (playerID))");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create authentication table", e);
		} finally {
			close(connection);
		}
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
		byte[] salt = generateSalt();
		byte[] hash = generateHash(password, salt);
		// Get a connection to the database
		Connection connection = Database.getConnection();
		try {
			PreparedStatement statement = null;
			statement = connection.prepareStatement("INSERT INTO AUTH "
					+ "(playerID, hash, salt) values (("
					+ "SELECT playerID FROM PLAYERS WHERE username = "
					+ "?), ?, ?)");
			statement.setString(1, username);
			statement.setBytes(2, hash);
			statement.setBytes(3, salt);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable register password", e);
		} finally {
			close(connection);
		}
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
		byte[] salt = generateSalt();
		byte[] hash = generateHash(password, salt);
		// Get a connection to the database
		Connection connection = Database.getConnection();
		try {
			PreparedStatement statement = null;
			statement = connection.prepareStatement("UPDATE AU "
					+ "SET AU.hash = ?, AU.salt = ? FROM AUTH As AU, "
					+ "PLAYERS WHERE AU.username = PLAYERS.username");
			statement.setBytes(1, hash);
			statement.setBytes(2, salt);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable register password", e);
		} finally {
			close(connection);
		}
	}

	/**
	 * Test whether a given username and password match the database records.
	 * Used to authenticate a player.
	 * 
	 * @param username
	 * @param password
	 *            the password to be compared with the database version
	 * @return true if password == the set password for the user
	 * @throws DatabaseException
	 */
	public Boolean checkPassword(String username, String password)
			throws DatabaseException {
		byte[] salt = getSalt(username);
		byte[] hash = generateHash(password, salt);
		byte[] hash2 = generateHash(password, salt);
		return Arrays.equals(hash, getHash(username));
	}

	/**
	 * Retrieve salted password hash from the database.
	 * 
	 * @param username
	 * @return hash
	 * @throws DatabaseException
	 */
	private byte[] getHash(String username) throws DatabaseException {
		// Get a connection to the database
		Connection connection = Database.getConnection();
		try {
			PreparedStatement statement = null;
			statement = connection.prepareStatement("SELECT hash FROM AUTH,"
					+ " PLAYERS WHERE AUTH.playerid = PLAYERS.playerid "
					+ "AND PLAYERS.username = ?");
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			result.next();
			return result.getBytes("hash");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get hash", e);
		} finally {
			close(connection);
		}
	}

	/**
	 * Retrieve salt from the database.
	 * 
	 * @param username
	 * @return salt
	 * @throws DatabaseException
	 */
	private byte[] getSalt(String username) throws DatabaseException {
		// Get a connection to the database
		Connection connection = Database.getConnection();
		try {
			PreparedStatement statement = null;
			statement = connection.prepareStatement("SELECT salt FROM AUTH,"
					+ " PLAYERS WHERE AUTH.playerid = PLAYERS.playerid "
					+ "AND PLAYERS.username = ?");
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			result.next();
			return result.getBytes("salt");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get salt", e);
		} finally {
			close(connection);
		}
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
	private byte[] generateHash(String password, byte[] salt) {
		MessageDigest sha512 = null;
		try {
			sha512 = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			// This should never be thrown because SHA-512 is built into java
			e.printStackTrace();
		}


		/* Prefix salt to digest */
		sha512.update(salt);
		sha512.update(password.getBytes());

		/* Generate the hash value from the salt & password combination */
		return sha512.digest();
	}

	/**
	 * Generate a random 64-bit salt.
	 * 
	 */
	private byte[] generateSalt() {
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			// This should never be thrown because SHA1PRNG is built into java
			e.printStackTrace();
		}

		/* Generate 64-bit salt */
		byte[] salt = new byte[8];
		random.nextBytes(salt);

		return salt;
	}
}
