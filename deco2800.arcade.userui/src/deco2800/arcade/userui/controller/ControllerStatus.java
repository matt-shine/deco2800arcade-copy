package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

public class ControllerStatus {
	
	private AchievementScreen achievementView;
	private StatusScreen statusView;
	private UserScreen userView;
	private Model theModel;

	/**
	 * Controller for the status page
	 * @param theModel
	 * @param statusView
	 */
	public ControllerStatus(Model theModel, StatusScreen statusView){
		
		this.theModel = theModel;
		this.statusView = statusView;
		
		this.statusView.addSaveListener(new SaveListener());
		this.statusView.addCancelListener(new CancelListener());
		
	}

	class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			statusView.dispose();
			statusView.getStatusSelection();
			
		}
		
	}
	
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			statusView.dispose();
			
		}
		
	}
	

}
