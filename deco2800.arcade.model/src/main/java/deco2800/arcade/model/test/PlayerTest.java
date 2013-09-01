package deco2800.arcade.model.test;

import org.junit.Assert;
import org.junit.Test;
import deco2800.arcade.*;
import deco2800.arcade.model.Player;

public class PlayerTest {

	@Test
	public void PlayerTest() {
		Player p1 = new Player(111, "Bob", "String filepath");
		Player p2 = new Player(112, "Betty", "String filepath");
		
	}

	@Test
	public void GetUsernameTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, " ", "String filepath");
		Player p3 = new Player(113, "%$^&%@@#$", "String filepath");
		Assert.assertEquals("Mismatched player name", "Ezmerelda", p1.getUsername());
		Assert.assertEquals("Player name error", "Invalid username", p2.getUsername());
		Assert.assertEquals("Invalid player name characters", "Invalid chars", p3.getUsername());
	}
	
	@Test
	public void SetUsernameTest() {
		
	}
	
	@Test
	public void GetGamesTest() {
		
	}
	

}
