package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import deco2800.arcade.model.Player;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.EditScreen;
import deco2800.arcade.userui.view.UserScreen;

public class ControllerEdit {
	
	private EditScreen editView;
	private Model theModel;
	private UserScreen userView;
	private Player player;
	private JFileChooser fileChooser;
	
	/**
	 * Controller for the edit profile page
	 * @param theModel
	 * @param userView
	 */
	public ControllerEdit(Model theModel, EditScreen editView, UserScreen userView) {
		
		this.theModel = theModel;
		this.editView = editView;
		this.userView = userView;
		
		System.out.println(editView.getAboutMeText());
		System.out.println(editView.getUsernameText());
		System.out.println(editView.getRealNameText());
		
		
		this.editView.SaveListener(new SaveListener());
		this.editView.CancelListener(new CancelListener());
		this.editView.UploadListener(new UploadListener());
	
	}
	
	class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Saves changes, leaves Edit Profile Page
			System.out.println("Save button works");
			System.out.println(editView.getUsernameText());
			
			
			player.setUsername(editView.getUsernameText());
			System.out.println(editView.getUsernameText());
			player.setName(editView.getRealNameText());
			player.setBio(editView.getAboutMeText());
			editView.dispose();
			
		}
		
	}
	
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Changes are not saved, leaves Edit Profile Page
			System.out.println("Cancel button works");
			editView.dispose();
			
		}
		
	}
	
	public class UploadListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
	        if (fileChooser == null) {
	        	editView.setAlwaysOnTop(false);
	            fileChooser = new JFileChooser("$HOME");
	        }
	        // Show the file chooser and get the value returned.
	        int returnVal = fileChooser.showOpenDialog(fileChooser);
	 
	        // Process the results.
	        // Case 1: the user selects file and clicks on open button
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            editView.upload.setText(fileChooser.getSelectedFile().getPath());
	        } 
	        // Case 2: the user clicks on cancel
	        else {
	        	editView.setAlwaysOnTop(false);
	            editView.upload.setText("");
	        }
	 
	        // Reset the file chooser for the next time it's shown.
	        fileChooser.setSelectedFile(null);
	        
	      
			
			//Uploads and updates avatar, leaves Edit Profile Page
			System.out.println("Upload button works");
			
			
		}
		
	}
	
	

}
