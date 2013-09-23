package deco2800.arcade.forum;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.forum.ForumTestResponse;

/**
 * This models client connection to server. 
 * At the moment this is for testing connection to server.
 * 
 * @author Junya
 * @see deco2800.arcade.client.network.NetworkClient
 */
public class ClientConnection {
	/* Kryonet client connection */
	private Client client;
	/* Timeout for connection waits */
	private static final int TIMEOUT = 10000;
	
	public ClientConnection(String serverAddress, int tcpPort, int udpPort) throws ForumException {
		this.client = new Client();
		this.client.start();
		try {
			client.connect(TIMEOUT, serverAddress, tcpPort, udpPort);
			Protocol.register(client.getKryo());
			client.addListener(new Listener() {
				public void received(Connection con, Object object) {
					if (object instanceof ForumTestResponse) {
						ForumTestResponse response = (ForumTestResponse) object;
						System.out.println(response.result);
					}
				}
			});
			System.out.println("Client is connected");
		} catch (IOException e) {
			throw new ForumException("Unable to connect to the server" + e);
		}
	}
	
	public Client getClient() {
		return this.client;
	}
	
	
}
