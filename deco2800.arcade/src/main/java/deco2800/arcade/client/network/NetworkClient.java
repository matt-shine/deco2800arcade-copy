package deco2800.arcade.client.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.protocol.NetworkObject;
import deco2800.arcade.protocol.Protocol;

public class NetworkClient {

	private Client client;
	
	/**
	 * Creates a new network client
	 * 
	 * @param serverAddress
	 * @param tcpPort
	 * @param udpPort
	 * @throws NetworkException
	 */
	public NetworkClient(String serverAddress, int tcpPort, int udpPort) throws NetworkException{
		this.client = new Client();
		this.client.start();
		
		try {
			client.connect(5000, serverAddress, tcpPort, udpPort);
			Protocol.register(client.getKryo());
		} catch (IOException e) {
			throw new NetworkException("Unable to connect to the server", e);
		}
	}

    public Client kryoClient() {
        return client;
    }
	
	/**
	 * Sends a NetworkObject over TCP
	 * 
	 * @param object
	 */
	public void sendNetworkObject(NetworkObject object){
		this.client.sendTCP(object);
	}
	
	/**
	 * Adds a listener to the network client
	 * 
	 * @param listener
	 */
	public void addListener(NetworkListener listener){
		this.client.addListener(listener);
	}
	
	/**
	 * Removes a listener from the network client
	 * 
	 * @param listener
	 */
	public void removeListener(NetworkListener listener){
		this.client.removeListener(listener);
	}
	
	
}
