package deco2800.arcade.userui.test;



import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.Player;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.controller.ControllerEdit;
import deco2800.arcade.userui.view.EditScreen;
import deco2800.arcade.userui.view.UserScreen;

public class EditTest {
	
	private Player player;	

	@Before
	public void initialise() {
		
		List<String> info = new ArrayList<String>();
		info.add("Foo");
		info.add("Foo Bar");
		info.add("foo@bar.com");
		info.add("IT");
		info.add("#Rickroll");
		info.add("20");
		
		ArrayList<Boolean> privset = new ArrayList<Boolean>();
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);

		player = new Player(123, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);
	}
	@Test
	public void initialTest(){
		Assert.assertTrue(player.getUsername().equals("Foo"));
		Assert.assertTrue(player.getID() == 123);
	}
	
	@Test
	public void setUsernameTest() {
		player.setUsername("Jason B");
		player.setUsername(null);
		Assert.assertTrue(player.getUsername().equals("Jason B"));
	}
	@Test
	public void setRealNameTest(){
		player.setName("Doge");
		Assert.assertTrue(player.getName().equals("Doge"));
		
	}
	@Test
	public void setAboutMeTest(){
		player.setBio("Such Bio Wow");
		player.setName(null);
		Assert.assertTrue(player.getBio().equals("Such Bio Wow"));
		
	}
		
		
		
		
	}
	

	
	


