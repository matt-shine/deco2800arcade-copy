package deco2800.arcade.guesstheword.test;

import java.util.HashMap;

import org.junit.Test;
import org.mockito.Mock;

import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.guesstheword.GUI.GuessTheWord;
import deco2800.arcade.guesstheword.gameplay.Pictures;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class PictureTest {

	@Mock
	GuessTheWord guess =  mock(GuessTheWord.class);
	
	@Test
	public void testNumberOfPictures(){
//		Pictures p = new Pictures(); 
		Pictures p = guess.loadGamePicture();
		
		System.out.println(guess.loadGamePicture());
	
//		HashMap<String, HashMap<String, Texture>> level1 = p.getLevel1();
//		HashMap<String, HashMap<String, Texture>> level2 = p.getLevel2();
//		HashMap<String, HashMap<String, Texture>> level3 = p.getLevel3();
//		
//		assertEquals(5, level1.size());
//		assertEquals(5, level2.size());
//		assertEquals(5, level3.size());
	}

}
