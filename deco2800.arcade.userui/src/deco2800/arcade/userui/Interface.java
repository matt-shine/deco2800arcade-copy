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
		Model modelprofile = new Model();
		Model m = new Model();
		Model amodel = new Model();
		
		UserScreen view = new UserScreen(modelprofile);
		//EditScreen v = new EditScreen(m);
		AchievementScreen achievementview = new AchievementScreen(modelprofile);
		
		//Controller controller = new Controller(modelprofile,view);
		
		
	}

}
