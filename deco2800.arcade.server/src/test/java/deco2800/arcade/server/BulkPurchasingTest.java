package deco2800.arcade.server;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.Before;

import static org.mockito.Mockito.*;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.GamePlayToken;
import deco2800.arcade.model.Player;
import deco2800.server.PurchasingService;
import deco2800.server.database.CreditStorage;

public class BulkPurchasingTest {

	PurchasingService purchasingService;
	CreditStorage mockCS = mock(CreditStorage.class);
	private Player alpha;
	private Player beta;
	Game pong;
	
	@Before
	public void setup() {
		purchasingService = new PurchasingService();
		
		alpha = new Player(0, "Alpha", null);
		beta = new Player(1, "Beta", null);
		
		pong = new Game();
		pong.name = "Pong";
		pong.pricePerPlay = 1;
		purchasingService.setCreditStorage(mockCS);
	}

	/**
	 * @author Addison Gourluck
	 * @throws Exception
	 */
	@Test
	public void canAffordIt() throws Exception {		
		when(mockCS.getUserCredits(alpha.getID())).thenReturn(50);
		
		GamePlayToken gpt =
				purchasingService.bulkPurchase(alpha, pong, 1);
		
		verify(mockCS).getUserCredits(alpha.getID());
		
		assertEquals("Wrong game", pong, gpt.getGame());
		assertEquals("Wrong number of plays", 1, gpt.getPlays());
	}

	/**
	 * @author Addison Gourluck
	 * @throws Exception
	 */
	@Test
	public void cannotAffordIt() throws Exception {		
		when(mockCS.getUserCredits(alpha.getID())).thenReturn(0);
		
		GamePlayToken gpt =
				purchasingService.bulkPurchase(alpha, pong, 1);
		
		verify(mockCS).getUserCredits(alpha.getID());
		
		assertEquals("Wrong game", null, gpt);
	}

//	/**
//	 * @author Addison Gourluck
//	 * @throws Exception
//	 */
//	@Test
//	public void discountOnTen() throws Exception {		
//		when(mockCS.getUserCredits(alpha.getID())).thenReturn(500);
//		
//		GamePlayToken gpt =
//				purchasingService.bulkPurchase(alpha, pong, 10);
//		
//		verify(mockCS).getUserCredits(alpha.getID());
//		verify(mockCS).deductUserCredits(alpha.getID(), 8);
//		
//		assertEquals("Wrong game", pong, gpt.getGame());
//		assertEquals("Wrong number of plays", 10, gpt.getPlays());
//	}	
//
//	/**
//	 * @author Addison Gourluck
//	 * @throws Exception
//	 */
//	@Test
//	public void discountOnTwenty() throws Exception {		
//		when(mockCS.getUserCredits(alpha.getID())).thenReturn(500);
//		
//		GamePlayToken gpt =
//				purchasingService.bulkPurchase(alpha, pong, 20);
//		
//		verify(mockCS).getUserCredits(alpha.getID());
//		verify(mockCS).deductUserCredits(alpha.getID(), 12);
//		
//		assertEquals("Wrong game", pong, gpt.getGame());
//		assertEquals("Wrong number of plays", 20, gpt.getPlays());
//	}		
//
//	/**
//	 * @author Addison Gourluck
//	 * @throws Exception
//	 */
//	@Test
//	public void discountOnFifty() throws Exception {		
//		when(mockCS.getUserCredits(alpha.getID())).thenReturn(500);
//		
//		GamePlayToken gpt =
//				purchasingService.bulkPurchase(alpha, pong, 50);
//		
//		verify(mockCS).getUserCredits(alpha.getID());
//		verify(mockCS).deductUserCredits(alpha.getID(), 25);
//		
//		assertEquals("Wrong game", pong, gpt.getGame());
//		assertEquals("Wrong number of plays", 50, gpt.getPlays());
//	}
//
//	/**
//	 * @author Addison Gourluck
//	 * @throws Exception
//	 */
//	@Test
//	public void discountOnHundred() throws Exception {		
//		when(mockCS.getUserCredits(alpha.getID())).thenReturn(500);
//		
//		GamePlayToken gpt =
//				purchasingService.bulkPurchase(alpha, pong, 100);
//		
//		verify(mockCS).getUserCredits(alpha.getID());
//		verify(mockCS).deductUserCredits(alpha.getID(), 40);
//		
//		assertEquals("Wrong game", pong, gpt.getGame());
//		assertEquals("Wrong number of plays", 100, gpt.getPlays());
//	}
//
//	/**
//	 * @author Addison Gourluck
//	 * @throws Exception
//	 */
//	@Test
//	public void testTwentyTeamPlay() throws Exception {
//		HashSet<Player> players = new HashSet<Player>();
//		players.add(alpha);
//		players.add(beta);
//		
//		when(mockCS.getUserCredits(alpha.getID())).thenReturn(500);
//		when(mockCS.getUserCredits(beta.getID())).thenReturn(500);
//		
//		Set<GamePlayToken> gpts =
//				purchasingService.teamBulkPurchase(players, pong, 20);
//		
//		verify(mockCS).getUserCredits(alpha.getID());
//		verify(mockCS).deductUserCredits(alpha.getID(), 10);
//		verify(mockCS).getUserCredits(beta.getID());
//		verify(mockCS).deductUserCredits(beta.getID(), 10);
//
//		for (GamePlayToken gpt : gpts) {
//			assertEquals("Wrong game", pong, gpt.getGame());
//			assertEquals("Wrong number of plays", 20, gpt.getPlays());
//		}
//	}
//
//	/**
//	 * @author Addison Gourluck
//	 * @throws Exception
//	 */
//	@Test
//	public void testThreeHundredTeamPlay() throws Exception {
//		HashSet<Player> players = new HashSet<Player>();
//		players.add(alpha);
//		players.add(beta);
//		
//		when(mockCS.getUserCredits(alpha.getID())).thenReturn(500);
//		when(mockCS.getUserCredits(beta.getID())).thenReturn(500);
//		
//		Set<GamePlayToken> gpts =
//				purchasingService.teamBulkPurchase(players, pong, 100);
//		
//		verify(mockCS).getUserCredits(alpha.getID());
//		verify(mockCS).deductUserCredits(alpha.getID(), 30);
//		verify(mockCS).getUserCredits(beta.getID());
//		verify(mockCS).deductUserCredits(beta.getID(), 30);
//
//		for (GamePlayToken gpt : gpts) {
//			assertEquals("Wrong game", pong, gpt.getGame());
//			assertEquals("Wrong number of plays", 100, gpt.getPlays());
//		}
//	}
//	
//	/**
//	 * @author Addison Gourluck
//	 * @throws Exception
//	 */
//	@Test
//	public void testPlayerCannotAfford() throws Exception {
//		HashSet<Player> players = new HashSet<Player>();
//		players.add(alpha);
//		players.add(beta);
//		
//		when(mockCS.getUserCredits(alpha.getID())).thenReturn(500);
//		when(mockCS.getUserCredits(beta.getID())).thenReturn(10);
//		
//		assertEquals("One Player cannot afford the purchase", null,
//				purchasingService.teamBulkPurchase(players, pong, 200));
//	}
}
