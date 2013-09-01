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
		Assert.assertEquals("Mismatched player name", "Ezmerelda", p1.getUsername());
		Assert.assertEquals("Player name error", "Invalid username", p2.getUsername());

	}
	
	@Test
	public void SetUsernameTest() {
		Player p1 = new Player(111, "Bob", "String filepath");
		Player p2 = new Player(112, null, "String filepath");
		Player p3 = new Player(113, "Genie", "String filepath");
		Player p4 = new Player(114, "Zeus", "String filepath");
		Player p5 = new Player(115, "Dekota", "String filepath");
		p1.setUsername("Cornelious");
		p2.setUsername("GameDestroya");
		p3.setUsername("%$^&%@@#$");
		p4.setUsername(" ");
		p5.setUsername("Cornelious");
		Assert.assertEquals("Mismatched player name update", "Cornelious", p1.getUsername());
		Assert.assertEquals("Player name assigned to null value", "Invalid username update", p2.getUsername());
		Assert.assertEquals("Invalid player name characters", "Invalid chars", p3.getUsername());
		Assert.assertEquals("Invalid player name characters", "Invalid chars", p4.getUsername());
		Assert.assertEquals("Invalid player name, already in use", "Non-unique Username", p5.getUsername());
	}
	
	
	

}
