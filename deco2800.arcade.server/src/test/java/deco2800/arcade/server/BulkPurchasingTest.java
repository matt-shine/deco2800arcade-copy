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
	Player algernon;
	Game tiddlywinks;
	CreditStorage mockCS = mock(CreditStorage.class);
	private Player barnaby;
	
	@Before
	public void setup() {
		purchasingService = new PurchasingService();
		
		algernon = new Player(0, "Algernon", null);
		barnaby = new Player(1, "Barnaby", null);
		
		tiddlywinks = new Game();
		tiddlywinks.name = "tiddlywinks";
		tiddlywinks.pricePerPlay = 1;
		purchasingService.setCreditStorage(mockCS);
	}
	
	@Test
	public void canAffordIt() throws Exception {		
		when(mockCS.getUserCredits(algernon.getID())).thenReturn(50);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				1
		);
		
		verify(mockCS).getUserCredits(algernon.getID());
		
		assertEquals("Wrong game", tiddlywinks, gpt.getGame());
		assertEquals("Wrong number of plays",
				1, gpt.getPlays());
	}
	
	@Test
	public void cannotAffordIt() throws Exception {		
		when(mockCS.getUserCredits(algernon.getID())).thenReturn(0);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				1
		);
		
		verify(mockCS).getUserCredits(algernon.getID());
		
		assertEquals("Wrong game", null, gpt);
	}
	
	@Test
	public void discountOnFive() throws Exception {		
		when(mockCS.getUserCredits(algernon.getID())).thenReturn(50);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				5
		);
		
		verify(mockCS).getUserCredits(algernon.getID());
		verify(mockCS).deductUserCredits(algernon.getID(), 3);
		
		assertEquals("Wrong game", tiddlywinks, gpt.getGame());
		assertEquals("Wrong number of plays",
				5, gpt.getPlays());
	}	
	
	@Test
	public void discountOnTen() throws Exception {		
		when(mockCS.getUserCredits(algernon.getID())).thenReturn(50);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				10
		);
		
		verify(mockCS).getUserCredits(algernon.getID());
		verify(mockCS).deductUserCredits(algernon.getID(), 5);
		
		assertEquals("Wrong game", tiddlywinks, gpt.getGame());
		assertEquals("Wrong number of plays",
				10, gpt.getPlays());
	}		
	
	@Test
	public void discountOnHundred() throws Exception {		
		when(mockCS.getUserCredits(algernon.getID())).thenReturn(50);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				100
		);
		
		verify(mockCS).getUserCredits(algernon.getID());
		verify(mockCS).deductUserCredits(algernon.getID(), 40);
		
		assertEquals("Wrong game", tiddlywinks, gpt.getGame());
		assertEquals("Wrong number of plays",
				100, gpt.getPlays());
	}
	
	@Test
	public void testTwentyTeamPlay() throws Exception {
		HashSet<Player> players = new HashSet<Player>();
		players.add(algernon);
		players.add(barnaby);
		
		when(mockCS.getUserCredits(algernon.getID())).thenReturn(50);
		when(mockCS.getUserCredits(barnaby.getID())).thenReturn(50);

		Set<GamePlayToken> gpts = purchasingService.teamBulkPurchase(players, 
				tiddlywinks,
				25
		);

		verify(mockCS).getUserCredits(algernon.getID());
		verify(mockCS).deductUserCredits(algernon.getID(), 10);
		verify(mockCS).getUserCredits(barnaby.getID());
		verify(mockCS).deductUserCredits(barnaby.getID(), 10);

		for (GamePlayToken gpt : gpts) {
			assertEquals("Wrong game", tiddlywinks, gpt.getGame());
			assertEquals("Wrong number of plays",
					25, gpt.getPlays());
		}
		
	
	}
	
}
