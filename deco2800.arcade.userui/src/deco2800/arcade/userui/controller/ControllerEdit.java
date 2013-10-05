package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.EditScreen;

public class ControllerEdit {
	
	private EditScreen editView;
	private Model theModel;
	
	/**
	 * Controller for the edit profile page
	 * @param theModel
	 * @param userView
	 */
	public ControllerEdit(Model theModel, EditScreen editView) {
		
		this.theModel = theModel;
		this.editView = editView;
	
	}
	

}
