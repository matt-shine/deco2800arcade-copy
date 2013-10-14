package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.EditScreen;
import deco2800.arcade.userui.view.UserScreen;

public class ControllerEdit {
	
	private EditScreen editView;
	private Model theModel;
	private UserScreen userView;
	
	/**
	 * Controller for the edit profile page
	 * @param theModel
	 * @param userView
	 */
	public ControllerEdit(Model theModel, EditScreen editView, UserScreen userView) {
		
		this.theModel = theModel;
		this.editView = editView;
		this.userView = userView;
		
		this.editView.SaveListener(new SaveListener());
		this.editView.CancelListener(new CancelListener());
		this.editView.UploadListener(new UploadListener());
	
	}
	
	class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			editView.dispose();
			
		}
		
	}
	
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			editView.dispose();
			
		}
		
	}
	
	public class UploadListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			editView.dispose();
			
		}
		
	}
	
	

}
