package deco2800.arcade.userui.test;



import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.Player;
import deco2800.arcade.model.test.PlayerTestFactory;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.controller.ControllerEdit;
import deco2800.arcade.userui.view.EditScreen;
import deco2800.arcade.userui.view.UserScreen;

public class EditTest {
	
	private Player player;
	private EditScreen editView;
	private UserScreen userView;
	private ControllerEdit editController;
	private Model theModel;
	

	@Before
	public void initialise() {
		
		this.theModel = new Model();
		this.editView = new EditScreen(theModel);
		this.editController = new ControllerEdit(theModel, editView, userView);
		this.player = PlayerTestFactory.createPlayer(1, "Jason");
	}
	@Test
	public void initialTest(){
		Assert.assertTrue(player.getUsername().equals("Jason"));
		Assert.assertTrue(player.getID() == 1);
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
	

	
	


