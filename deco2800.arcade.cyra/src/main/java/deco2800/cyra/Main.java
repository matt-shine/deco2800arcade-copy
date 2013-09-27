package deco2800.cyra;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import deco2800.cyra.game.TestGame2;

/**
 * Class used to run the game independent of the arcade. Useful for quick testing.
 *
 */
public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "testplatformer2";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new TestGame2(), cfg);
	}
}
