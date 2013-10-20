package deco2800.arcade.guesstheword.test;

import org.junit.Test;

import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.guesstheword.gameplay.GetterSetter;
import static org.junit.Assert.*;

public class GetterSetterTest {

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
		gs.setCategoryItem("Prada");
		assertEquals("Brand", gs.getCategory());
		assertEquals("Prada", gs.getCategoryItem());
	}
	
	@Test
	public void testHintGetSet(){
		GetterSetter gs = new GetterSetter();
		gs.setHint(false);
		gs.setHintPosition(2);
		assertEquals(false, gs.getHint());
		assertEquals(2 , gs.getHintPosition());
	}
	
	@Test
	public void testAnswersGetSet(){
		GetterSetter gs = new GetterSetter();
		gs.setAnswerCount(0);
		assertEquals(0 , gs.getAnswerCount());
	}
	
//	@Test
//	public void testTextureGetSet(){
//		Texture texture = new Texture("src/main/resources/Images/level1/animals/bear/bear_1.png");
//		GetterSetter gs = new GetterSetter();
//		gs.setTexture(texture);
//		
//		assertEquals(texture, gs.getTexture());
//	}  
	
}
