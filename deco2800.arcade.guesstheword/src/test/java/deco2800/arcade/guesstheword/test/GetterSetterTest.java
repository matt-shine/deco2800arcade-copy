package deco2800.arcade.guesstheword.test;

import org.junit.Test;

import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.guesstheword.gameplay.GetterSetter;
import static org.junit.Assert.*;

public class GetterSetterTest {

	@Test
	public void testButtonGetSet(){
		GetterSetter gs = new GetterSetter();
		
		gs.setButton1("A"); 
		gs.setButton2("B");
		
		assertEquals("A", gs.getButton1());
		assertEquals("B", gs.getButton2());
	}
	
	@Test
	public void testTextFieldGetSet(){
		GetterSetter gs = new GetterSetter();
		
		gs.setText1("A"); 
		gs.setText2("B");
		gs.setText3("C");
		gs.setText4("D");
		gs.setText5("E");
		gs.setText6("F");
		
		assertEquals("A", gs.getText1());
		assertEquals("B", gs.getText2());
		assertEquals("C", gs.getText3());
		assertEquals("D", gs.getText4());
		assertEquals("E", gs.getText5());
		assertEquals("F", gs.getText6());
	}
	
	@Test
	public void testLevelGetSet(){
		GetterSetter gs = new GetterSetter();
		gs.setLevel("LEVEL 1");
		assertEquals("LEVEL 1", gs.getLevel());
	}
	
	@Test
	public void testScoreGetSet(){
		GetterSetter gs = new GetterSetter();
		gs.setScore(100);
		assertEquals(100, gs.getScore());
	}
	
	@Test
	public void testCategoryGetSet(){
		GetterSetter gs = new GetterSetter();
		gs.setCategory("Brand");
		assertEquals("Brand", gs.getCategory());
	}
	
/***	@Test
//	public void testTextureGetSet(){
		Texture texture = new Texture("Images/level1/animals/bear/bear_1.png");
		GetterSetter gs = new GetterSetter();
		gs.setTexture(texture);
		
		assertEquals(texture, gs.getTexture());
	}  */
	
}
