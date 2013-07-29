package deco2800.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.Protocol;
import deco2800.server.listener.ConnectionListener;
import deco2800.server.listener.CreditListener;
import deco2800.server.listener.GameListener;

public class ArcadeServer {

	private static Set<String> connectedUsers = new HashSet<String>();
	
	public static void main(String[] args) {
		Server server = new Server();
		System.out.println("Server starting"); //system output server starting
		server.start(); //server start
		try {
			server.bind(54555, 54777);
			System.out.println("Server bound");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		Protocol.register(server.getKryo());
		
		server.addListener(new ConnectionListener(connectedUsers)); //event listener for ConnectionListener
		server.addListener(new CreditListener()); 
		server.addListener(new GameListener());
	}
	
}
