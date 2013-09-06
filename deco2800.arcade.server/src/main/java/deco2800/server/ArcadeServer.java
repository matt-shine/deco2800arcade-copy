package deco2800.server;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.net.BindException;

import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.Protocol;
import deco2800.server.database.CreditStorage;
import deco2800.server.database.DatabaseException;
import deco2800.server.listener.CommunicationListener;
import deco2800.server.listener.ConnectionListener;
import deco2800.server.listener.CreditListener;
import deco2800.server.listener.GameListener;
import deco2800.server.database.HighscoreDatabase;
import deco2800.arcade.packman.PackageServer;

/**
 * Implements the KryoNet server for arcade games which uses TCP and UDP
 * transport layer protocols.
 * 
 * @see http://code.google.com/p/kryonet/
 */
public class ArcadeServer {

	// Keep track of which users are connected
	SessionManager sessionManager = new SessionManager();

	// singleton pattern
	private static ArcadeServer instance;

	// Public and private key pair help handshake with clients
	private KeyPair keyPair;
	private String algorithm = "RSA";

	// Package manager
	@SuppressWarnings("unused")
	private PackageServer packServ;

	/**
	 * Retrieve the singleton instance of the server
	 * 
	 * @return game server instance
	 */
	public static ArcadeServer instance() {
		if (instance == null) {
			instance = new ArcadeServer();
		}
		return instance;
	}

	/**
	 * Initializes and starts Server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArcadeServer server = new ArcadeServer();
		server.start();
	}

	// Credit storage service
	private CreditStorage creditStorage;
	// private PlayerStorage playerStorage;
	// private FriendStorage friendStorage;

	// Highscore database storage service
	private HighscoreDatabase highscoreDatabase;

	/**
	 * @return creditStorage service
	 */
	public CreditStorage getCreditStorage() {
		return this.creditStorage;
	}

	/**
	 * Create a new Arcade Server. This should generally not be called.
	 * 
	 * @see ArcadeServer.instance()
	 */
	private ArcadeServer() {
		this.creditStorage = new CreditStorage();
		// this.playerStorage = new PlayerStorage();
		// this.friendStorage = new FriendStorage();

		this.highscoreDatabase = new HighscoreDatabase();
		this.packServ = new PackageServer();

		// initialize database classes
		try {
			creditStorage.initialise();
			// playerStorage.initialise();

			highscoreDatabase.initialise();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Binds TCP/UDP ports to the server instance, registers classes and adds
	 * listeners
	 */
	public void start() {
		Server server = new Server();
		System.out.println("Server starting");
		server.start();
		try {
			server.bind(54555, 54777);
			System.out.println("Server bound");
		} catch (BindException b) {
			System.err.println("Error binding server: Address already in use");
		} catch (IOException e) {
			e.printStackTrace();
		}

		generateKeyPair();

		Protocol.register(server.getKryo());

		// Connection listener manages the handshake process and associates
		// clients with a shared secret key.
		server.addListener(new ConnectionListener(sessionManager, keyPair));

		// FIXME these need to be behind a proxy that unseals messages
		server.addListener(new CreditListener());
		server.addListener(new GameListener());
		server.addListener(new CommunicationListener(server));
	}

	/**
	 * Generate the server public and private key that is used to handshake with
	 * the client.
	 */
	private void generateKeyPair() {
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (kpg != null) {
			kpg.initialize(2048);
			KeyPair keyPair = kpg.generateKeyPair();

			this.keyPair = keyPair;
		} else {
			// Something went wrong
			this.keyPair = null;
		}
	}
}
