package deco2800.arcade.pacman;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;

/* 
 * so Gdx.files itself is null, I can happily make a Gdx() which isn't null, 
 * but if I try to .files it, that's still null which is odd because it's static
 * just read that there's initialisation stuff which causes this problem (that Gdx.files is null)
 */		

public class MainTest {

	private Pacman pacGame;
	private GameMap gameMap;
	
	@Before
	public void init() {
		//necessary stuff to initialise libGdx
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Pacman Test Window";
	    cfg.useGL20 = false;
	    cfg.width = 480;
	    cfg.height = 320;
	    new LwjglApplication(pacGame = new Pacman(Mockito.mock(Player.class), Mockito.mock(NetworkClient.class)), cfg);
	    UIOverlay overlayMock = Mockito.mock(UIOverlay.class);
		pacGame.addOverlayBridge(overlayMock);
		//apparently this works now?
		FileHandle file = Gdx.files.internal("wallsAndPellets.png");
		Texture test = new Texture(file);
		TextureRegion[][] tileSprites = TextureRegion.split(test, 8, 8);
	    pacGame.create();
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
