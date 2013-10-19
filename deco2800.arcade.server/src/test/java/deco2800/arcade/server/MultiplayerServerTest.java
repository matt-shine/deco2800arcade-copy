package deco2800.arcade.server;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.server.Lobby;
import deco2800.server.MatchmakerQueue;
import deco2800.server.MultiplayerServer;

public class MultiplayerServerTest {

	
	int player1ID;
	int player2ID;
	int p1Rating;
	int p2Rating;
	Connection player1Connection;
	Connection player2Connection;
	
	String testGame;
	int sessionId;
	int lobbyMatchId;
	
	MatchmakerQueue MMQ;
	Lobby l;
	
	MultiplayerServer matchmakerServer;
	MultiplayerServer lobbyServer;
	
	@Before
	public void setup() {
		player1ID = 001;
		player2ID = 002;
		p1Rating = 80;
		p2Rating = 75;
		player1Connection = mock(Connection.class);
		player2Connection = mock(Connection.class);
		
		l = Lobby.instance();
		MMQ = mock(MatchmakerQueue.class);
		
		sessionId = 54321;
		lobbyMatchId = 12345;
		
		testGame = "cinnamon";
		
		/* Create Matchmaker Server */
		matchmakerServer = new MultiplayerServer(player1ID, player2ID, player1Connection, 
				player2Connection, testGame, sessionId, MMQ, p1Rating, p2Rating);
		
		/* Create Lobby Game Server */
		lobbyServer = new MultiplayerServer(player1ID, player2ID, player1Connection, player2Connection,
				testGame, lobbyMatchId);
	}
	
	@Test
	public void testConstructors() {
		/* Matchmaking */
		assertEquals(player1ID, matchmakerServer.getPlayer1());
		assertEquals(player2ID, matchmakerServer.getplayer2());
		assertEquals(testGame, matchmakerServer.getGameId());
		assertEquals(sessionId, matchmakerServer.getSessionId());
		assertEquals(p1Rating, matchmakerServer.getPlayer1Rating());
		assertEquals(p2Rating, matchmakerServer.getPlayer2Rating());
		assertTrue(matchmakerServer.getServerType());
		verify(player1Connection, times(1)).sendTCP(sessionId);
		verify(player2Connection, times(1)).sendTCP(sessionId);
		
		/* Lobby */
		assertEquals(player1ID, lobbyServer.getPlayer1());
		assertEquals(player2ID, lobbyServer.getplayer2());
		assertEquals(testGame, lobbyServer.getGameId());
		assertEquals(lobbyMatchId, lobbyServer.getSessionId());
		assertEquals(0, lobbyServer.getPlayer1Rating());
		assertEquals(0, lobbyServer.getPlayer2Rating());
		assertFalse(lobbyServer.getServerType());
		verify(player1Connection, times(1)).sendTCP(lobbyMatchId);
		verify(player2Connection, times(1)).sendTCP(lobbyMatchId);
	}
	
	
	@Test
	public void testStateUpdate() {
		GameStateUpdateRequest req = new GameStateUpdateRequest();
		req.gameId = testGame;
		req.gameSession = sessionId;
		req.playerID = player1ID;
		req.gameOver = true;
		req.winner = player1ID;
		
		/* Test gameOver */
		matchmakerServer.stateUpdate(req);
		verify(MMQ, times(1)).gameOver(sessionId, player1ID, player2ID, testGame, player1ID);
		
		/* Test initial */
		req.gameOver = false;
		req.initial = true;
		matchmakerServer.stateUpdate(req);
		verify(player2Connection, times(1)).sendTCP(req);
		
		/* Test not-initial */
		req.gameOver = false;
		req.initial = false;
		matchmakerServer.stateUpdate(req);
		verify(player1Connection, atLeastOnce()).sendTCP(req);
		verify(player2Connection, atLeastOnce()).sendTCP(req);
	}
	
	
}
