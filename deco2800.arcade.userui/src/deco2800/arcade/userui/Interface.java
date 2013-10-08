package deco2800.arcade.userui;

import javax.swing.SwingUtilities;

import deco2800.arcade.userui.controller.ControllerMain;
import deco2800.arcade.userui.view.AddFriendScreen;
import deco2800.arcade.userui.view.UserScreen;

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
		ControllerMain maincontroller = new ControllerMain(theModel,userView);

	}

}
