package deco2800.arcade.userui;

import javax.swing.SwingUtilities;

public class Interface {
	
	/**
	 * 
	 * This class calls model, view and controller classes
	 * 
	 */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				runApp();
			}
			
			
		});
	}
	
	public static void runApp() {
		
		Model theModel = new Model();
		
		UserScreen userView = new UserScreen(theModel);
		AchievementScreen achievementView = new AchievementScreen(theModel);
		//EditScreen editView = new EditScreen(theModel);
		//StatusScreen statusView = new StatusScreen(theModel);
		
		ControllerMain maincontroller = new ControllerMain(theModel,userView);
		ControllerAchievement achievementcontroller = new ControllerAchievement(theModel, achievementView);
		//ControllerEdit editcontroller = new ControllerEdit(theModel, editView);
			
	}

}
