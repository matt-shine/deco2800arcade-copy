package deco2800.arcade.guesstheword.GUI;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import deco2800.arcade.model.Player;
public class GameStarter {
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg =
				new LwjglApplicationConfiguration();

		cfg.title = "Guess The Word Testing";
		cfg.useGL20 = true;
		cfg.width = 1280;
		cfg.height = 720;
//		Player p = new Player(0, null, null, null, null, null, null, null);
//		p.setUsername("Peter");
		GuessTheWord game =  new GuessTheWord(null, null);
//		game.picture.loadPictures();
		new LwjglApplication(game, cfg);
		
	}

}
