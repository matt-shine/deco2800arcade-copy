package deco2800.arcade.userui;

import javax.swing.SwingUtilities;

import deco2800.arcade.userui.controller.Controller;
import deco2800.arcade.userui.model.Model;
import deco2800.arcade.userui.view.EditProfile;
import deco2800.arcade.userui.view.View;

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
		Model model = new Model();
		Model m = new Model();
		
		View view = new View(model);
		EditProfile v = new EditProfile(m);
		
		Controller controller = new Controller(model,view);
		
		
	}

}
