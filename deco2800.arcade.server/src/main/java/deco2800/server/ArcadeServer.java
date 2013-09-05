package deco2800.server;

import java.io.IOException;
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
//import deco2800.arcade.packman.PackageServer;

/** 
 * Implements the KryoNet server for arcade games which uses TCP and UDP
 * transport layer protocols. 
 * 
 * @see http://code.google.com/p/kryonet/ 
 */
public class ArcadeServer {

	// Keep track of which users are connected
	SessionManager sessionManager = new SessionManager();
	
	//singleton pattern
	private static ArcadeServer instance;
	
	// Package manager
	//private PackageServer packServ;
	
	/**
	 * Retrieve the singleton instance of the server
	 * @return the game server
	 */
	public static ArcadeServer instance() {
		if (instance == null) {
			instance = new ArcadeServer();
		}
		return instance;
	}
	
	/**
	 * Initializes and starts Server
	 * Binds Ports
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArcadeServer server = new ArcadeServer();
		server.start();
	}

	// Credit storage service
	private CreditStorage creditStorage;
	//private PlayerStorage playerStorage;
	//private FriendStorage friendStorage;
	
	/**
	 * Access the server's credit storage facility
	 * @return
	 */
	public CreditStorage getCreditStorage() {
		return this.creditStorage;
	}
	
	/**
	 * Create a new Arcade Server.
	 * This should generally not be called.
	 * @see ArcadeServer.instance()
	 */
	public ArcadeServer() {
		this.creditStorage = new CreditStorage();
		//this.playerStorage = new PlayerStorage();
		//this.friendStorage = new FriendStorage();
		
//		this.packServ = new PackageServer();
		
		//initialize database classes
		try {
			creditStorage.initialise();
			//playerStorage.initialise();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Start the server running
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
		
		Protocol.register(server.getKryo());
		server.addListener(new ConnectionListener(sessionManager));
		server.addListener(new CreditListener());
		server.addListener(new GameListener());
		server.addListener(new CommunicationListener(server));
	}
}
