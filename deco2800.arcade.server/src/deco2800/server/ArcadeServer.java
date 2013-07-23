package deco2800.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.protocol.game.NewGameRequest;
import deco2800.arcade.protocol.game.NewGameResponse;
import deco2800.server.database.CreditStorage;
import deco2800.server.database.DatabaseException;
import deco2800.server.listener.ConnectionListener;

public class ArcadeServer {

	private static Set<String> connectedUsers = new HashSet<String>();
	
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
		
		server.addListener(new ConnectionListener(connectedUsers));
		
		try {
			Integer credits = CreditStorage.getUserCredits("Bob");
			System.out.println(credits == null ? "Null result" : credits);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
