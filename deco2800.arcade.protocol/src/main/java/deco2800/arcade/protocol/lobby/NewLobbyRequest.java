package deco2800.arcade.protocol.lobby;

import deco2800.server.LobbyMatch;


public class NewLobbyRequest extends LobbyRequest {
	
	public LobbyRequestType requestType;
	public String gameId;
	public LobbyMatch match;
}
