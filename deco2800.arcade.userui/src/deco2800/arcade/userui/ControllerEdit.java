package deco2800.arcade.userui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
