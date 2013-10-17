package deco2800.server;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;


public class MultiplayerServer {

	private int player1Id;
	private int player2Id;
	private int p1Rating = 0;
	private int p2Rating = 0;
	private Connection player1;
	private Connection player2;
	private String gameId;
	private int sessionId;
	private MatchmakerQueue queue;
	private Boolean matchmakerGame;

	

	/**
	 * Creates a multiplayer server for a matchmaking game.
	 * @param player1Id
	 * @param player2Id
	 * @param player1
	 * @param player2
	 * @param gameId
	 * @param sessionId
	 * @param queue
	 */
	public MultiplayerServer(int player1Id, int player2Id, Connection player1, Connection player2, String gameId, int sessionId, 
			MatchmakerQueue queue, int p1Rating, int p2Rating) {
		this.player1Id = player1Id;
		this.player2Id = player2Id;
		this.player1 = player1;
		this.player2 = player2;
		this.gameId = gameId;
		this.sessionId = sessionId;
		this.queue = queue;
		this.matchmakerGame = true;
		this.p1Rating = p1Rating;
		this.p2Rating = p2Rating;
		player1.sendTCP(this.sessionId);
		player2.sendTCP(this.sessionId);
	}
	
	/**
	 * Creates a multiplayer server for a lobby game.
	 * @param player1Id
	 * @param player2Id
	 * @param player1
	 * @param player2
	 * @param gameId
	 * @param sessionId
	 * @param lobbyMatchId
	 */
	public MultiplayerServer(int player1Id, int player2Id, Connection player1, Connection player2, String gameId, int lobbyMatchId) {
		this.player1Id = player1Id;
		this.player2Id = player2Id;
		this.player1 = player1;
		this.player2 = player2;
		this.gameId = gameId;
		this.sessionId = lobbyMatchId;;
		this.matchmakerGame = false;
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
	
	public int getPlayer1Rating() {
		return p1Rating;
	}
	
	public int getPlayer2Rating() {
		return p2Rating;
	}

	public Boolean getServerType() {
		return matchmakerGame;
	}
	
	public void stateUpdate(GameStateUpdateRequest request) {
		//if (playerId.equals(player1Id) || playerId.equals(player2Id)) {
		if (request.gameOver == true && this.matchmakerGame) {
			queue.gameOver(sessionId, player1Id, player2Id, gameId, request.winner);
			return;
		}
		if (request.initial == false) {
			player1.sendTCP(request);	
			player2.sendTCP(request);
		} else {
			player2.sendTCP(request);			
		}
		//}
	}
}
