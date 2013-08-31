package deco2800.arcade.userui.view;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import deco2800.arcade.userui.model.Model;

public class View extends JFrame {
	
	private Model model;

	public View(Model model) throws HeadlessException {
		super("User Profile");
		
		this.model = model;
		
		setSize(1280,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	
}
