package deco2800.server;

import com.esotericsoftware.kryonet.Connection;

public class LobbyMatch {

	private String hostUsername;
	private Connection hostConnection;
	private String gameId;

	public LobbyMatch(String gameId, String hostUsername, Connection connection) {
		this.hostUsername = hostUsername;
		this.hostConnection = connection;
		this.gameId = gameId;
	}

	public String getHostUsername() {
		return this.hostUsername;
	}

	public Connection getHostConnection() {
		return this.hostConnection;
	}

	public String getGameId() {
		return this.gameId;
	}


}
