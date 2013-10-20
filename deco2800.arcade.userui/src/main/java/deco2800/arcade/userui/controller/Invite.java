package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.AddFriendScreen;
import deco2800.arcade.userui.view.InviteScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

public class Invite {
	
	private UserScreen userView;
	private InviteScreen inviteView;
	private Model theModel;

	/**
	 * Controller for the status page
	 * @param theModel
	 * @param friendView
	 */
	public Invite(Model theModel, InviteScreen inviteView, UserScreen userView){
		
		this.theModel = theModel;
		this.inviteView = inviteView;
		this.userView = userView;
		
		this.inviteView.addAcceptListener(new AcceptListener());
		this.inviteView.addDeclineListener(new DeclineListener());
		
		theModel.getPlayer().addInvite(theModel.getUser());
		inviteView.displayinvites();
		
	}

	class AcceptListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			inviteView.dispose();
			userView.setEnabled(true);
			
		}
		
	}
	
	class DeclineListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			inviteView.dispose();
			userView.setEnabled(true);
			
		}
		
	}
	

}
