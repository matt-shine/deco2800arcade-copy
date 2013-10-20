package deco2800.server.blackjack;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;

public class BlackjackUser {

	int playerID;
	int chippile;
	Connection connection;
	
	public BlackjackUser() {
		
	}

	public BlackjackUser(int playerID, int chippile, Connection connection) {
		this.playerID = playerID;
		this.connection = connection;
		this.chippile = chippile;
	}
	
}
