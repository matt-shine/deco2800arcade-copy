package deco2800.arcade.userui.test;

import static org.junit.Assert.*;

import java.util.Set;

import javax.swing.ImageIcon;

import junit.framework.Assert;

import org.junit.Test;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Games;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.controller.ControllerAchievement;
import deco2800.arcade.userui.view.AchievementScreen;

public class AchievementTest {
	
	private ArcadeSystem arcadesystem;
	private NetworkClient networkclient;
	private AchievementClient achievementclient;
	private Model theModel;
	
	private Achievement masterpaddle;
	private Achievement toogood;
	private Achievement bam;
	private Achievement hatsoff;
	
	@Test
	public void initialise() {

		this.theModel = new Model();
		arcadesystem = new ArcadeSystem();
		achievementclient = new AchievementClient(networkclient);;
		
		masterpaddle = new Achievement();
		masterpaddle.awardThreshold = 3;
		masterpaddle.description = "some award";
		masterpaddle.id = "bam";
		toogood = new Achievement();
		toogood.awardThreshold = 3;
		toogood.description = "some award";
		toogood.id = "bam";
		bam = new Achievement();
		bam.awardThreshold = 3;
		bam.description = "some award";
		bam.id = "bam";
		hatsoff = new Achievement();
		hatsoff.awardThreshold = 3;
		hatsoff.description = "some award";
		hatsoff.id = "bam";
			
	}

	@Test
	public void setGameNameTest() {

		String name = "Pong";
		assertEquals(name, theModel.PONG.getName());
		
	}
	
	@Test
	public void setGameDescriptionTest() {

		String description = "Tennis, without that annoying 3rd dimension!";
		assertEquals(description, theModel.PONG.getDescription());
		
	}
	
	@Test
	public void pullAchievementTest() {

		assertTrue(true);
		
	}

}
