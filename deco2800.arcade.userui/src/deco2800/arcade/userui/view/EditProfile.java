package deco2800.arcade.userui.view;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import deco2800.arcade.userui.model.Model;

public class EditProfile extends JFrame {
	
	private Model m;
	
	public EditProfile(Model m) throws HeadlessException {
		super("Edit Profile");
		
	// Set the  view window constraints
	setSize(400,600);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setVisible(true);
	
	}

}
