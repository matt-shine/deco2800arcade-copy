package deco2800.arcade.server;

import org.junit.Test;
import org.junit.Before;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;
import deco2800.server.MatchmakerQueue;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.PlayerGameStorage;

public class MatchmakerQueueTest {
	
	MatchmakerQueue MMQ;
	
	int player1ID;
	int player2ID;
	Connection player1Connection;
	Connection player2Connection;
	
	String testGame;
	
	@Before
	public void setup() {
		MMQ = MatchmakerQueue.instance();
		
		player1ID = 101;
		player2ID = 102;
		player1Connection = mock(Connection.class);
		player2Connection = mock(Connection.class);
		testGame = "paprika";
	}
	
	@Test
	public void testInstance() {
		assertTrue(MatchmakerQueue.instance() == MMQ);
	}
	
		
	@Test
	public void testQueue() throws DatabaseException {
		MMQ.setDatabase(mock(PlayerGameStorage.class));
		when(MMQ.getDatabase().getPlayerRating(player1ID, testGame)).thenReturn(2000);
		when(MMQ.getDatabase().getPlayerRating(player2ID, testGame)).thenReturn(2000);
		
		/* Empty Queue */
		MMQ.resetQueuedUsers();
		MMQ.resetActiveServers();
		assertTrue(MMQ.getQueue().size() == 0);
		/* Add one player */
		NewMultiGameRequest req = new NewMultiGameRequest();
		req.gameId = testGame;
		req.playerID = player1ID;
		MMQ.checkForGame(req, player1Connection);
		assertTrue(MMQ.getQueue().size() == 1);
		assertTrue(MMQ.getServerList().isEmpty());
		
		/* Add 2nd player for same game (Should start server) */
		NewMultiGameRequest req2 = new NewMultiGameRequest();
		req2.gameId = testGame;
		req2.playerID = player2ID;
		ArgumentCaptor arg = ArgumentCaptor.forClass(NewMultiSessionResponse.class);
		MMQ.checkForGame(req2, player2Connection);
		verify(player2Connection, atLeastOnce()).sendTCP(arg.capture());
		/* Capture the NewMultiSessionResponse to get sessionID */
		NewMultiSessionResponse response = (NewMultiSessionResponse)arg.getValue();
		int sessionId = response.sessionId;
		
		assertTrue(MMQ.getQueue().size() == 0);
		assertTrue(MMQ.getServerList().size() == 1);
		MMQ.dropServer(sessionId);
		assertTrue(MMQ.getServerList().isEmpty());
	}
	
	@Test
	public void testRemoveUser() {
		MMQ.resetQueuedUsers();
		NewMultiGameRequest req = new NewMultiGameRequest();
		req.gameId = testGame;
		req.playerID = player1ID;
		MMQ.checkForGame(req, player1Connection);
		assertTrue(MMQ.getQueue().size() == 1);
		
		MMQ.remove(player1ID);
		assertTrue(MMQ.getQueue().isEmpty());
	}
	
	@Test
	public void testGoodGame() throws DatabaseException {
		MMQ.resetQueuedUsers();
		MMQ.resetActiveServers();
		MMQ.setDatabase(mock(PlayerGameStorage.class));
		when(MMQ.getDatabase().getPlayerRating(player1ID, testGame)).thenReturn(2000);
		when(MMQ.getDatabase().getPlayerRating(player2ID, testGame)).thenReturn(100);
		when(MMQ.getDatabase().getPlayerRating(201, testGame)).thenReturn(2000);
		
		NewMultiGameRequest req = new NewMultiGameRequest();
		req.gameId = testGame;
		req.playerID = player1ID;
		MMQ.checkForGame(req, player1Connection);
		
		NewMultiGameRequest req2 = new NewMultiGameRequest();
		req2.gameId = testGame;
		req2.playerID = player2ID;
		MMQ.checkForGame(req2, player1Connection);
		
		NewMultiGameRequest req3 = new NewMultiGameRequest();
		req3.gameId = testGame;
		req3.playerID = 201;
		MMQ.checkForGame(req3, mock(Connection.class));
		
		
		
		/* Verify player1 and player 3(id:201) are matched */
		assertTrue(MMQ.getQueue().size() == 1);
		assertTrue(MMQ.getServerList().get(0).getPlayer1() == req3.playerID);
		assertTrue(MMQ.getServerList().get(0).getPlayer2() == player1ID);
		
	}
	
	
	
	
}
