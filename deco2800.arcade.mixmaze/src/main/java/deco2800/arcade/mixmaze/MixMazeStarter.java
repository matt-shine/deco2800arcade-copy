/*
 * MixMazeStarter
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.apache.log4j.BasicConfigurator;

public class MixMazeStarter{
	public static void main(String[] args) {
		BasicConfigurator.configure();

		LwjglApplicationConfiguration cfg =
				new LwjglApplicationConfiguration();

		cfg.title = "Mix Maze (developing)";
		cfg.useGL20 = true;
		cfg.width = 1280;
		cfg.height = 720;

		new LwjglApplication(new MixMaze(null, null), cfg);
	}
}
