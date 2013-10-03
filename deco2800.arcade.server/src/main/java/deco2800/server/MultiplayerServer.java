package deco2800.server;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;


public class MultiplayerServer {

	private int player1Id;
	private int player2Id;
	private Connection player1;
	private Connection player2;
	private String gameId;
	private int sessionId;
	private MatchmakerQueue queue;

	public MultiplayerServer(int player1Id, int player2Id, Connection player1, Connection player2, String gameId, int sessionId, MatchmakerQueue queue) {
		System.out.println("Multiplayer server started");
		this.player1Id = player1Id;
		this.player2Id = player2Id;;
		this.player1 = player1;
		this.player2 = player2;
		this.gameId = gameId;
		this.sessionId = sessionId;
		this.queue = queue;
		player1.sendTCP(this.sessionId);
		player2.sendTCP(this.sessionId);
	}

	public int getPlayer1() {
		return player1Id;
	}

	public int getplayer2() {
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
		System.out.println(request.playerID);
		if (request.gameOver == true) {
			queue.gameOver(sessionId, player1Id, player2Id, gameId, request.winner);
			return;
		}
		player1.sendTCP(request);	
		player2.sendTCP(request);
		//}
	}
}
