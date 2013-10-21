package deco2800.arcade.userui.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.controller.ControllerAchievement;
import deco2800.arcade.userui.view.AchievementScreen;

public class AchievementTest {
	
	private ArcadeSystem arcadesystem;
	private AchievementClient achievementclient;
	private Model theModel;
	
	@Test
	public void initialise() {

		this.theModel = new Model();

		
	}
	
	@Test
	public void initial() {
		
		
		
	}
	
	@Test
	public void setGameNameTest() {

		String name = "Pong";
		assertEquals(name, theModel.PONG.getName());
		
	}
	
	@Test
	public void setGameDescriptionTest() {

		//ToDo
	}
	
	@Test
	public void setGameLogoTest() {

		//ToDo
	}
	
	@Test
	public void pullAchievementTest() {

		//ToDo
	}

}
