package deco2800.arcade.guesstheword.test;

import org.junit.Test;

import deco2800.arcade.guesstheword.gameplay.WordShuffler;

import static org.junit.Assert.*;

public class WordShufflerTest {
	
	@Test
	public void testWordLength(){
		WordShuffler ws = new WordShuffler();
		String [] word = ws.breakWord("Four");
		
		assertEquals(10, word.length);
	}
	
	@Test
	public void testWordShuffler(){
		WordShuffler ws = new WordShuffler();
		String [] word = ws.breakWord("Four");
		StringBuilder sb = new StringBuilder();
		for(String w : word){
			sb.append(w);
		}
		assertTrue(sb.toString().contains("F"));
		assertTrue(sb.toString().contains("o"));
		assertTrue(sb.toString().contains("u"));
		assertTrue(sb.toString().contains("r"));
	}
	
	@Test
	public void testGetHint(){
		WordShuffler ws = new WordShuffler();
		String s = "DECO2800";
		String hint = ws.getHint(s.toCharArray());
		
		assertTrue(s.toString().contains(hint));
	}

}
