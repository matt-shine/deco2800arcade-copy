package deco2800.arcade.model.test;

import org.junit.Assert;

import org.junit.Test;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.GamePlayToken;

public class GamePlayTokenTest {
	
	@Test
	public void GamePlayTokenTest1(){
		Game game = new Game();
		game.id = "We've known each other for so long";
		game.setName("Your heart's been aching but you're too shy to say it");
		game.setDescription("Inside we both know what's been going on");
		
		GamePlayToken g = new GamePlayToken(game, 5);
		
		Assert.assertEquals(game, g.getGame());
		Assert.assertEquals(5, g.getPlays());
	}

}
