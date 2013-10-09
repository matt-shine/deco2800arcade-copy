package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.RemoveFriendScreen;

public class RemoveFriend {
	
	private RemoveFriendScreen friendView;
	private Model theModel;

	/**
	 * Controller for the status page
	 * @param theModel
	 * @param friendView
	 */
	public RemoveFriend(Model theModel, RemoveFriendScreen friendView){
		
		this.theModel = theModel;
		this.friendView = friendView;
		
		this.friendView.addRemoveFriendListener(new RemoveFriendListener());
		
	}

	class RemoveFriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			friendView.dispose();
			
		}
		
	}
	

	

}
