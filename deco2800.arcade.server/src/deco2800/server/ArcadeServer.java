package deco2800.server;

import java.io.IOException;
import java.util.HashSet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.protocol.game.NewGameRequest;
import deco2800.arcade.protocol.game.NewGameResponse;

public class ArcadeServer {

	private static HashSet<String> connectedUsers = new HashSet<String>();
	
	public static void main(String[] args) {
		Server server = new Server();
		System.out.println("Server starting");
		server.start();
		try {
			server.bind(54555, 54777);
			System.out.println("Server bound");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Protocol.register(server.getKryo());
		
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof ConnectionRequest) {
					ConnectionRequest request = (ConnectionRequest) object;
					System.out.println("Connection request for user: " + request.username);
					connectedUsers.add(request.username);
					connection.sendTCP(ConnectionResponse.OK);
					System.out.println("Connection granted");
				} else if (object instanceof NewGameRequest) {
					connection.sendTCP(NewGameResponse.OK);
				}
			}
		});
		
	}
	
}
