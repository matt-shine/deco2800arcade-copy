package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

public class StatusMain {
	
	private StatusScreen statusView;
	private UserScreen userView;
	private Model theModel;

	/**
	 * 
	 * @param theModel
	 * @param statusView
	 * @param userView
	 */
	public StatusMain(Model theModel, StatusScreen statusView, UserScreen userView){
		
		this.theModel = theModel;
		this.statusView = statusView;
		this.userView = userView;
		
		this.statusView.addSaveListener(new SaveListener());
		this.statusView.addCancelListener(new CancelListener());
		
	}

	class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			statusView.dispose();
			statusView.getStatusSelection();
			userView.setStatus(theModel.statusIcon);
			
		}
		
	}
	
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			statusView.dispose();
			
		}
		
	}
	

}
