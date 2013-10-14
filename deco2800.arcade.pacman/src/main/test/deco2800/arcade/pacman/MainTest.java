package deco2800.arcade.pacman;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;

public class MainTest {

	private Pacman pacGame;
	private GameMap gameMap;
	
	@Before
	public void init() {
		UIOverlay overlayMock = Mockito.mock(UIOverlay.class);
		gameMap = Mockito.mock(GameMap.class);
		pacGame = new Pacman(Mockito.mock(Player.class), Mockito.mock(NetworkClient.class));
		//pacGame.addOverlayBridge(overlayMock);
		gameMap = pacGame.getGameMap();
	}
	
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
	
	@Test
	/**
	 * Checks if the map file exists in the directory
	 */
	public void mapFileExists() {
		gameMap.readMap(pacGame.getMapName());
	}	
	
//	@Test
//	public void checkMultiplexerExists() {
//		//PacController controller = new PacController();
//		//ArcadeInputMux.getInstance().addProcessor(controller);
//	}

}
