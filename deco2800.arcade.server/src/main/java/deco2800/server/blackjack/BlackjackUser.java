package deco2800.server.blackjack;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;

public class BlackjackUser {

	String username;
	int chippile;
	Connection connection;
	
	public BlackjackUser() {
		this.username = "default";
	}
	
	public BlackjackUser(String username, int chippile, Connection connection) {
		
		this.username = username;
		this.connection = connection;
		this.chippile = chippile;
	}
	
}
