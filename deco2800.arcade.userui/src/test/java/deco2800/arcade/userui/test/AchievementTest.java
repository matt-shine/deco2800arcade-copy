package deco2800.arcade.userui.test;

import static org.junit.Assert.*;

import org.junit.Test;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.controller.ControllerAchievement;
import deco2800.arcade.userui.view.AchievementScreen;

public class AchievementTest {
	
	private ArcadeSystem arcadesystem;
	private AchievementClient achievementclient;
	private AchievementScreen achievementView;
	private Model theModel;
	private ControllerAchievement achievementcontroller;
	
	@Test
	public void initialise() {

		this.theModel = new Model();
		this.achievementView = new AchievementScreen(theModel);
		this.achievementcontroller = new ControllerAchievement(theModel, achievementView);
		
		String[] gamelist = {"", "Pong", "Chess", "Burning Skies",
				"Breakout","Mix Maze","Pacman"};
	}
	
	@Test
	public void initial() {
		
		
	}
	
	@Test
	public void setGameNameTest() {

		//ToDo
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
