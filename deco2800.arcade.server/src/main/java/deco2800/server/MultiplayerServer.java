package deco2800.server;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;

public class MultiplayerServer {
	
	private String player1Id;
	private String player2Id;
	private Connection player1;
	private Connection player2;
	private String gameId;
	private int sessionId;
	
	public MultiplayerServer(String player1Id, String player2Id, Connection player1, Connection player2, String gameId, int sessionId) {
		System.out.println("Multiplayer server started");
		this.player1Id = player1Id;
		this.player2Id = player2Id;;
		this.player1 = player1;
		this.player2 = player2;
		this.gameId = gameId;
		this.sessionId = sessionId;
		player1.sendTCP(this.sessionId);
		player2.sendTCP(this.sessionId);
	}
	
	public String getPlayer1() {
		return player1Id;
	}
	
	public String getplayer2() {
		return player2Id;
	}
	
	public String getGameId() {
		return gameId;
	}
	
	public int getSessionId() {
		return sessionId;
	}
	
	public void stateUpdate(GameStateUpdateRequest request) {
		//if (playerId.equals(player1Id) || playerId.equals(player2Id)) {
			player1.sendTCP(request);	
			player2.sendTCP(request);
		//}
	}
}
