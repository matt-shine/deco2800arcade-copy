package deco2800.arcade.protocol.lobby;


import com.esotericsoftware.kryonet.Connection;

public class CreateMatchRequest extends LobbyRequest {
	
	public int hostPlayerId;
	public Connection hostConnection;
	public String gameId;

}
