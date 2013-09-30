package deco2800.server;

import com.esotericsoftware.kryonet.Connection;
import java.util.UUID;

public class LobbyMatch {

	private int hostPlayerId;
	private Connection hostConnection;
	private String gameId;
	private UUID matchId;

	public LobbyMatch(String gameId, int hostPlayerId, Connection connection) {
		this.hostPlayerId = hostPlayerId;
		this.hostConnection = connection;
		this.gameId = gameId;
		this.matchId = UUID.randomUUID();
	}

	public int getHostPlayerId() {
		return this.hostPlayerId;
	}
	
	public UUID getMatchId() {
		return this.matchId;
	}

	public Connection getHostConnection() {
		return this.hostConnection;
	}

	public String getGameId() {
		return this.gameId;
	}



}
