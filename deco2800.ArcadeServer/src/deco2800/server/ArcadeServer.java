package deco2800.server;

import java.io.IOException;
import java.util.HashSet;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.ConnectRequest;
import deco2800.arcade.protocol.ConnectionOK;
import deco2800.arcade.protocol.NewGameRequest;
import deco2800.arcade.protocol.OKMessage;
import deco2800.arcade.protocol.Protocol;

public class ArcadeServer {

	private static HashSet<String> connectedUsers = new HashSet<String>();
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Protocol.register(server.getKryo());
		
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof ConnectRequest) {
					ConnectRequest request = (ConnectRequest) object;
					connectedUsers.add(request.username);
					connection.sendTCP(new ConnectionOK());
				} else if (object instanceof NewGameRequest) {
					connection.sendTCP(new OKMessage());
				}
			}
		});
		
	}
	
}
