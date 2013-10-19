package deco2800.arcade.model.test;

import org.junit.Assert;
import org.junit.Test;

import deco2800.arcade.model.Game;

public class GameTest {
	
	@Test
	public void GameTest1(){
		Game game = new Game();
		
		String desc = "We're no strangers to love; you know the rules and so do I.";
		game.setDescription(desc);
		Assert.assertEquals(desc, game.getDescription());
		
		String id = "A full commitment's what I'm thinking of; You wouldn't get this from any other guy";
		game.id = id;
		Assert.assertEquals(id, game.id);
		
		
		String name = "Never going to give you up.";
		game.setName(name);
		Assert.assertEquals(name, game.getName());
		
		game.setIcon(null);
		Assert.assertEquals(null, game.getIcon());
		
		Game game2 = new Game();
		game2.setName("Pong");
		
		Assert.assertTrue(game.compareTo(game2) < 0);
		Assert.assertTrue(game2.compareTo(game) > 0);
		
		
		
	}

}
