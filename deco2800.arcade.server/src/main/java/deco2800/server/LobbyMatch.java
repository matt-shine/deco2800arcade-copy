package deco2800.server;

import com.esotericsoftware.kryonet.Connection;

public class LobbyMatch {

	private int hostPlayerId;
	private Connection hostConnection;
	private String gameId;

	public LobbyMatch(String gameId, int hostPlayerId, Connection connection) {
		this.hostPlayerId = hostPlayerId;
		this.hostConnection = connection;
		this.gameId = gameId;
	}

	public int getHostPlayerId() {
		return this.hostPlayerId;
	}

	public Connection getHostConnection() {
		return this.hostConnection;
	}

	public String getGameId() {
		return this.gameId;
	}


}
