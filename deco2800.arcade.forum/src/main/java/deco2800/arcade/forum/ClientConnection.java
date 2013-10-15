package deco2800.arcade.forum;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.forum.*;

/**
 * This models client connection to server. 
 * It connects to the ArcadeServer to communicate the forum data.
 * 
 * @author Junya, Unreal Estate
 * @see deco2800.arcade.client.network.NetworkClient
 */
public class ClientConnection {
	/* Kryonet client connection */
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
			/* Register client listener */
			Protocol.register(client.getKryo());
			client.addListener(new ForumClientListener());
			System.out.println("Client is connected");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ForumException("Unable to connect to the server, " + e.getMessage());
		}
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public static ClientConnection getClientConnection(String serverAddress, int tcpPort, int udpPort) throws ForumException {
		return new ClientConnection(serverAddress, tcpPort, udpPort);
	}
	
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
			Kryo kryo = connection.getKryo();
			kryo.register(GetForumUserRequest.class);
			kryo.register(GetForumUserResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ForumException("Unable to connect to the server, " + e.getMessage());
		}
		System.out.println("Client is connected");
		return connection;
	}
}
