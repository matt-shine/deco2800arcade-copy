package deco2800.arcade.userui.test;

import static org.junit.Assert.*;

import org.junit.Test;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.controller.ControllerMain;
import deco2800.arcade.userui.controller.StatusAchievement;
import deco2800.arcade.userui.controller.StatusMain;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

public class StatusTest {
	
	private AchievementScreen achievementView;
	private UserScreen userView;
	private StatusScreen statusView;
	private Model theModel;
	private StatusMain mainstatus;
	private StatusAchievement astatus;

	@Test
	public void inituser() {
		
		theModel = new Model();
		userView = new UserScreen(theModel);
		
	}
	
	@Test
	public void useronline() {
		
		theModel = new Model();
		userView = new UserScreen(theModel);
		statusView = new StatusScreen(theModel);
		mainstatus = new StatusMain(theModel, statusView, userView);
		
		statusView.onlineclick();
		statusView.saveclick();
		assertEquals("online", theModel.status);
		
	}
	
	@Test
	public void useroffline() {
		
		theModel = new Model();
		userView = new UserScreen(theModel);
		statusView = new StatusScreen(theModel);
		mainstatus = new StatusMain(theModel, statusView, userView);
		
		statusView.offlineclick();
		statusView.saveclick();
		assertEquals("offline", theModel.status);
		
	}
	
	
	@Test
	public void useraway() {
		
		theModel = new Model();
		userView = new UserScreen(theModel);
		statusView = new StatusScreen(theModel);
		mainstatus = new StatusMain(theModel, statusView, userView);
		
		statusView.onlineclick();
		statusView.saveclick();
		assertEquals("online", theModel.status);
		
	}
	
	@Test
	public void userbusy() {
		
		theModel = new Model();
		userView = new UserScreen(theModel);
		statusView = new StatusScreen(theModel);
		mainstatus = new StatusMain(theModel, statusView, userView);
		
		statusView.onlineclick();
		statusView.saveclick();
		assertEquals("online", theModel.status);
		
	}
	
	@Test
	public void initachievement() {
		
		theModel = new Model();
		achievementView = new AchievementScreen(theModel);
		
	}
	
	@Test
	public void achievementonline() {
		
		theModel = new Model();
		achievementView = new AchievementScreen(theModel);
		statusView = new StatusScreen(theModel);
		astatus = new StatusAchievement(theModel, statusView, achievementView);
		
		statusView.onlineclick();
		statusView.saveclick();
		assertEquals("online", theModel.status);
		
	}
	
	@Test
	public void achievementoffline() {
		
		theModel = new Model();
		achievementView = new AchievementScreen(theModel);
		statusView = new StatusScreen(theModel);
		astatus = new StatusAchievement(theModel, statusView, achievementView);
		
		statusView.offlineclick();
		statusView.saveclick();
		assertEquals("offline", theModel.status);
		
	}
	
	
	@Test
	public void achievementaway() {
		
		theModel = new Model();
		achievementView = new AchievementScreen(theModel);
		statusView = new StatusScreen(theModel);
		astatus = new StatusAchievement(theModel, statusView, achievementView);
		
		statusView.awayclick();
		statusView.saveclick();
		assertEquals("away", theModel.status);
		
	}
	
	@Test
	public void achievementbusy() {
		
		theModel = new Model();
		achievementView = new AchievementScreen(theModel);
		statusView = new StatusScreen(theModel);
		astatus = new StatusAchievement(theModel, statusView, achievementView);
		
		statusView.busyclick();
		statusView.saveclick();
		assertEquals("busy", theModel.status);
		
	}



}
