package deco2800.arcade.arcadeui;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeException;
import deco2800.arcade.client.ArcadeSystem;

public class ClientInterfaceTests {
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void canLaunchAllGames() {
		
		Arcade arcade = new Arcade(null);
		ArcadeSystem.setArcadeInstance(arcade);
		arcade.addCanvas();
		try {
			arcade.connectToServer();
		} catch (ArcadeException e) {
			e.printStackTrace();
		}
		
		Set<String> games = ArcadeSystem.getGamesList();
		
		for (String id : games) {
			ArcadeSystem.goToGame(id);
			
		}
	}
		
	
}
