/*
 * MixMazeStarter
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MixMazeStarter{
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg =
				new LwjglApplicationConfiguration();

		cfg.title = "Mix Maze (developing)";
		cfg.useGL20 = true;
		cfg.width = 720;
		cfg.height = 720;

		new LwjglApplication(new MixMaze(null, null), cfg);
	}
}
