package deco2800.arcade.forum;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.KryoNetException;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.forum.*;

/**
 * This models client connection to server. 
 * It connects to the ArcadeServer to communicate the forum data.
 * <p>
 * Note;
 * <ul>
 * 	<li>It registers protocols at the same time via Protocol.register().</li>
 * 	<li>Do not register any more protocols. (modify Protocol.register() instead).</li>
 * 	<li>Add listener later on demand.</li>
 * </li>
 * 
 * @author Junya, Unreal Estate
 * @see deco2800.arcade.client.network.NetworkClient
 * @see http://kryonet.googlecode.com/svn/api/v2/index.html
 */
public class ClientConnection {
	/* KryoNet client connection */
	private Client client;
	/* Timeout for connection waits */
	private static final int TIMEOUT = 10000;
	/* Default server information */
	private static final String DEFAULT_SERVER_ADDRESS = "127.0.0.1";
	private static final int DEFAULT_TPC_PORT = 54555;
	private static final int DEFAULT_UDP_PORT = 54777;
	
	/**
	 * Constructor: It creates the Kryonet Client instance to establish the connection with the server.
	 * If parameters are {"", 0, 0}, it sets the default values for server connection.
	 * It maybe better to use static method.
	 * 
	 * @param serverAddress	String, IP address of server. Empty for default (localhost) value.
	 * @param tcpPort	Non-negative integer, Port number for TCP
	 * @param udpPort	Non-negative integer, Port number for UDP
	 * @throws ForumException	if invalid parameters or fail to connect server.
	 */
	public ClientConnection(String serverAddress, int tcpPort, int udpPort) throws ForumException {
		if (tcpPort < 0 || udpPort < 0) {
			throw new ForumException("Invalid port numbers.");
		}
		if (serverAddress == "") {
			serverAddress = DEFAULT_SERVER_ADDRESS;
		}
		if (tcpPort == 0) {
			tcpPort = DEFAULT_TPC_PORT;
		}
		if (udpPort == 0) {
			udpPort = DEFAULT_UDP_PORT;
		}
		this.client = new Client();
		this.client.start();
		try {
			client.connect(TIMEOUT, serverAddress, tcpPort, udpPort);
			this.registerProtocol();
			System.out.println("Client is connected");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ForumException("Unable to connect to the server, " + e.getMessage());
		}
	}
	
	public Client getClient() {
		return this.client;
	}
	
	/**
	 * Add a listener to the client. i.e. Client.addListener().
	 * 
	 * @param listener	KryoNet.Listener
	 * @throws ForumException	if KryoNetException
	 */
	public void addListener(Listener listener) throws ForumException {
		try {
			this.client.addListener(listener);
		} catch (KryoNetException e) {
			e.printStackTrace();
			throw new ForumException("Fail to add listener: " + e.getMessage());
		}
		return;
	}
	
	public void registerProtocol() {
		Protocol.register(this.client.getKryo());
	}
	
	public void closeConnection() {
		this.client.close();
	}
	
	/**
	 * Alias of constructor, but this returns Kryonet.Client for convention.
	 * 
	 * @param serverAddress
	 * @param tcpPort
	 * @param udpPort
	 * @return
	 * @throws ForumException
	 */
	public static Client getClient(String serverAddress, int tcpPort, int udpPort) throws ForumException {
		if (tcpPort < 0 || udpPort < 0) {
			throw new ForumException("Invalid port numbers.");
		}
		if (serverAddress == "") {
			serverAddress = DEFAULT_SERVER_ADDRESS;
		}
		if (tcpPort == 0) {
			tcpPort = DEFAULT_TPC_PORT;
		}
		if (udpPort == 0) {
			udpPort = DEFAULT_UDP_PORT;
		}
		Client connection = new Client();
		connection.start();
		try {
			connection.connect(TIMEOUT, serverAddress, tcpPort, udpPort);
			registerProtocol(connection);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ForumException("Unable to connect to the server, " + e.getMessage());
		}
		System.out.println("Client is connected");
		return connection;
	}
	
	/**
	 * Register protocols to client connection. It requires to register before adding listener or
	 * network communication.
	 * 
	 * @param connection	Client instance
	 * @throws ForumException	if KryoNetException or invalid parameter
	 */
	public static void registerProtocol(Client connection) throws ForumException {
		if (connection == null) {
			throw new ForumException("Parameter should not be null");
		}
		Protocol.register(connection.getKryo());
		return;
	}
}
