package deco2800.arcade.client.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.protocol.NetworkObject;
import deco2800.arcade.protocol.Protocol;

public class NetworkClient {

	private Client client;
	
	public NetworkClient(String serverAddress, int tcpPort, int udpPort) throws NetworkException{
		this.client = new Client();
		this.client.start();
		
		try {
			client.connect(5000, serverAddress, tcpPort, udpPort);
			Protocol.register(client.getKryo());
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException("Unable to connect to the server", e);
		}
	}
	
	public void sendNetworkObject(NetworkObject object){
		this.client.sendTCP(object);
	}
	
	public void addListener(NetworkListener listener){
		this.client.addListener(listener);
	}
	
	public void removeListener(NetworkListener listener){
		this.client.removeListener(listener);
	}
	
	
}
