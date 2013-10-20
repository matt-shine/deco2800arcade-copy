package deco2800.arcade.server;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.esotericsoftware.kryonet.Connection;

import deco2800.server.Lobby;
import deco2800.server.LobbyMatch;

public class LobbyTest {
	
	int player1ID;
	int player2ID;
	Connection player1Connection;
	Connection player2Connection;
	
	String testGame;
	
	Lobby l;
	
	@Before
	public void setup() {
		player1ID = 101;
		player2ID = 102;
		player1Connection = mock(Connection.class);
		player2Connection = mock(Connection.class);
		l = Lobby.instance();
		testGame = "paprika";
	}
	
	@Test
	public void testAddAndRemovePlayer() {
		l.addPlayerToLobby(player1ID, player1Connection);
		Map<Integer, Connection> test = new HashMap<Integer, Connection>();
		test.put(player1ID, player1Connection);
		//check that the list of connectedPlayers includes the test player1
		assertEquals("Player not added", test, l.getConnectedPlayers());
		
		//try adding the same player a second time
		l.addPlayerToLobby(player1ID, player1Connection);
		assertEquals("Add existing player failure", test, l.getConnectedPlayers());
		
		//test removal
		test.remove(player1ID);
		l.removePlayerFromLobby(player1ID);
		assertEquals("Player not removed", test, l.getConnectedPlayers());
		
	}
	
	@Test 
	public void testMatchCreationAndDeletion() {
		l.createMatch(testGame, player1ID, player1Connection);
		LobbyMatch testMatch = new LobbyMatch(testGame, player1ID, player1Connection, 0);
		LobbyMatch returnedMatch = l.getLobbyGames().get(0);
		assertTrue(testMatch.getGameId() == returnedMatch.getGameId());
		assertTrue(testMatch.getHostConnection() == returnedMatch.getHostConnection());
		assertTrue(testMatch.getGameId() == returnedMatch.getGameId());
		assertTrue(testMatch.getMatchId() == returnedMatch.getMatchId());
		
		//test create match when user has a match
		l.createMatch(testGame, player1ID, player1Connection);
		assertEquals(1, l.getLobbyGames().size());
		
		//test destroy match with invalid id
		l.destroyMatch(1);
		assertEquals(1, l.getLobbyGames().size());
		
		//test destroy with valid id
		l.destroyMatch(0);
		assertEquals(0, l.getLobbyGames().size());
	}
	
	@Test
	public void testUserHasMatch() {
		l.createMatch(testGame, player1ID, player1Connection);
		assertTrue(l.userHasMatch(player1ID));
		assertFalse(l.userHasMatch(player2ID));
	}
	
	
	
	
	

}
