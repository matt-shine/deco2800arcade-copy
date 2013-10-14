package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.AddFriendScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

public class AddFriend {
	
	private UserScreen userView;
	private AddFriendScreen friendView;
	private Model theModel;

	/**
	 * Controller for the status page
	 * @param theModel
	 * @param friendView
	 */
	public AddFriend(Model theModel, AddFriendScreen friendView, UserScreen userView){
		
		this.theModel = theModel;
		this.friendView = friendView;
		this.userView = userView;
		
		this.friendView.addFriendListener(new AddFriendListener());
		this.friendView.addCancelListener(new CancelListener());
		
	}

	class AddFriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			friendView.dispose();
			userView.setEnabled(true);
			
		}
		
	}
	
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			friendView.dispose();
			userView.setEnabled(true);
			
		}
		
	}
	

}
