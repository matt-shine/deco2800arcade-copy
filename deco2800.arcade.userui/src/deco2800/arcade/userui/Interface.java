package deco2800.arcade.userui;

import javax.swing.SwingUtilities;

import deco2800.arcade.userui.controller.Controller;
import deco2800.arcade.userui.model.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.UserProfile;

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
		Model modelprofile = new Model();
		Model m = new Model();
		Model amodel = new Model();
		
		UserProfile view = new UserProfile(modelprofile);
		//EditProfile v = new EditProfile(m);
		//AchievementScreen achievementview = new AchievementScreen(amodel);
		
		Controller controller = new Controller(modelprofile,view);
		
		
	}

}
