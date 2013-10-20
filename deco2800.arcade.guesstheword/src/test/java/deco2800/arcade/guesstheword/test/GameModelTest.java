package deco2800.arcade.guesstheword.test;

import java.io.File;

import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import deco2800.arcade.guesstheword.GUI.GameScreen;
import deco2800.arcade.guesstheword.GUI.GuessTheWord;
import deco2800.arcade.model.Player;

public class GameModelTest {
	
//	private GuessTheWord game;
//	private GameScreen gameScreen;
//	public void initContents(){
//		LwjglApplicationConfiguration cfg =
//				new LwjglApplicationConfiguration();
//		Player p1 = new Player(1, "Bob", "default.png");
////		NetworkClient nwc =  new NetworkClient("Bob", 0, 1);
//		game =  new GuessTheWord();
//		gameScreen = new GameScreen();
//		FileHandle skinfile = new FileHandle("src/main/resources/uiskin.json");
//		Skin skin =  new Skin(skinfile);
//		gameScreen.textfield1 = new TextField("", skin);
//		gameScreen.textfield2 = new TextField("", skin);
//		gameScreen.textfield3 = new TextField("", skin);
//		gameScreen.textfield4 = new TextField("", skin);
//	}
//
//	@Test
//	public void tryTest(){
//		initContents();
//		game.gs.generateWord("Noun");
//		gameScreen.textfield1.setMessageText("F");
//		gameScreen.textfield2.setMessageText("o");
//		gameScreen.textfield3.setMessageText("N");
//		gameScreen.textfield4.setMessageText("D");
//		
//		StringBuilder sb = new StringBuilder();
//		sb.append(gameScreen.textfield1.getMessageText());
//		sb.append(gameScreen.textfield2.getMessageText());
//		sb.append(gameScreen.textfield3.getMessageText());
//		sb.append(gameScreen.textfield4.getMessageText());
//		System.out.println(sb.toString());
//		
//		System.out.println(game.gs.checkWordOccurance("String"));
//	}
}
