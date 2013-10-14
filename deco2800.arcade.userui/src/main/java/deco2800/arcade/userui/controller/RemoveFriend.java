package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.RemoveFriendScreen;
import deco2800.arcade.userui.view.UserScreen;

public class RemoveFriend {
	
	private RemoveFriendScreen friendView;
	private UserScreen userView;
	private Model theModel;

	/**
	 * Controller for the status page
	 * @param theModel
	 * @param friendView
	 */
	public RemoveFriend(Model theModel, RemoveFriendScreen friendView, UserScreen userView){
		
		this.theModel = theModel;
		this.friendView = friendView;
		this.userView = userView;
		
		this.friendView.addRemoveFriendListener(new RemoveFriendListener());
		this.friendView.addCancelListener(new CancelListener());
		
	}

	class RemoveFriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			friendView.dispose();
			userView.setEnabled(true);
			
		}
		
	}
	
	class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			friendView.dispose();
			userView.setEnabled(true);
			
		}
		
	}
	

	

}
