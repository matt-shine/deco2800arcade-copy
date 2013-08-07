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
		algernon = new Player("Algernon");
		barnaby = new Player("Barnaby");
		tiddlywinks = new Game();
		tiddlywinks.name = "tiddlywinks";
		tiddlywinks.pricePerPlay = 1;
		purchasingService.setCreditStorage(mockCS);
	}
	
	@Test
	public void canAffordIt() throws Exception {		
		when(mockCS.getUserCredits("Algernon")).thenReturn(50);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				1
		);
		
		verify(mockCS).getUserCredits("Algernon");
		
		assertEquals("Wrong game", tiddlywinks, gpt.getGame());
		assertEquals("Wrong number of plays",
				1, gpt.getPlays());
	}
	
	@Test
	public void cannotAffordIt() throws Exception {		
		when(mockCS.getUserCredits("Algernon")).thenReturn(0);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				1
		);
		
		verify(mockCS).getUserCredits("Algernon");
		
		assertEquals("Wrong game", null, gpt);
	}
	
	@Test
	public void discountOnFive() throws Exception {		
		when(mockCS.getUserCredits("Algernon")).thenReturn(50);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				5
		);
		
		verify(mockCS).getUserCredits("Algernon");
		verify(mockCS).deductUserCredits("Algernon", 3);
		
		assertEquals("Wrong game", tiddlywinks, gpt.getGame());
		assertEquals("Wrong number of plays",
				5, gpt.getPlays());
	}	
	
	@Test
	public void discountOnTen() throws Exception {		
		when(mockCS.getUserCredits("Algernon")).thenReturn(50);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				10
		);
		
		verify(mockCS).getUserCredits("Algernon");
		verify(mockCS).deductUserCredits("Algernon", 5);
		
		assertEquals("Wrong game", tiddlywinks, gpt.getGame());
		assertEquals("Wrong number of plays",
				10, gpt.getPlays());
	}		
	
	@Test
	public void discountOnHundred() throws Exception {		
		when(mockCS.getUserCredits("Algernon")).thenReturn(50);
		
		GamePlayToken gpt = purchasingService.bulkPurchase(
				algernon,
				tiddlywinks,
				100
		);
		
		verify(mockCS).getUserCredits("Algernon");
		verify(mockCS).deductUserCredits("Algernon", 40);
		
		assertEquals("Wrong game", tiddlywinks, gpt.getGame());
		assertEquals("Wrong number of plays",
				100, gpt.getPlays());
	}
	
	@Test
	public void testTwentyTeamPlay() throws Exception {
		HashSet players = new HashSet<Player>();
		players.add(algernon);
		players.add(barnaby);
		
		when(mockCS.getUserCredits("Algernon")).thenReturn(50);
		when(mockCS.getUserCredits("Barnaby")).thenReturn(50);

		Set<GamePlayToken> gpts = purchasingService.teamBulkPurchase(players, 
				tiddlywinks,
				25
		);
		
		verify(mockCS).getUserCredits("Algernon");
		verify(mockCS).deductUserCredits("Algernon", 10);
		verify(mockCS).getUserCredits("Barnaby");
		verify(mockCS).deductUserCredits("Barnaby", 10);

		for (GamePlayToken gpt : gpts) {
			assertEquals("Wrong game", tiddlywinks, gpt.getGame());
			assertEquals("Wrong number of plays",
					25, gpt.getPlays());
		}
		
	
	}
	
}
