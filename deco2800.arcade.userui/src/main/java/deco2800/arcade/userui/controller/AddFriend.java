package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.model.Player;
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
	 * Controller for adding friends
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

	/**
	 *  Adds the username of friend inputed to the friends invite list
	 *  The user must exist in the database to be valid,
	 *  else throw a new InvalidUserException
	 *  
	 */
	class AddFriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			theModel.getPlayer().acceptFriendInvite(theModel.getUser());
			friendView.dispose();
			userView.setEnabled(true);
			
		}
		
	}
	
	/**
	 * The actions are dismissed and view is closed
	 *
	 */
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			friendView.dispose();
			userView.setEnabled(true);
			
		}
		
	}
	

}
