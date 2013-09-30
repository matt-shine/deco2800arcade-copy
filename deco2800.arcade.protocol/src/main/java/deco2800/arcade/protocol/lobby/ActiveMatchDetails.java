package deco2800.arcade.protocol.lobby;

import java.util.UUID;

import com.esotericsoftware.kryonet.Connection;

public class ActiveMatchDetails extends LobbyRequest {

	public int hostPlayerId;
	//public Connection hostConnection; NOT SURE IF NECESSARY YET.
	public String gameId;
	public UUID matchId;
	
}
