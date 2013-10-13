package deco2800.server;

import com.esotericsoftware.kryonet.Connection;

public class LobbyMatch {

	private int hostPlayerId;
	private Connection hostConnection;
	private String gameId;
	private int matchId;

	public LobbyMatch(String gameId, int hostPlayerId, Connection connection, int matchId) {
		this.hostPlayerId = hostPlayerId;
		this.hostConnection = connection;
		this.gameId = gameId;
		this.matchId = matchId;
	}

	public int getHostPlayerId() {
		return this.hostPlayerId;
	}
	
	public int getMatchId() {
		return this.matchId;
	}

	public Connection getHostConnection() {
		return this.hostConnection;
	}

	public String getGameId() {
		return this.gameId;
	}



}
