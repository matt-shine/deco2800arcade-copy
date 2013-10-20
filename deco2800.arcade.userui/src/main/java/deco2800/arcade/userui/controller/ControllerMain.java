package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import org.apache.log4j.Logger;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.BlockScreen;
import deco2800.arcade.userui.view.EditScreen;
import deco2800.arcade.userui.view.AddFriendScreen;
import deco2800.arcade.userui.view.InviteScreen;
import deco2800.arcade.userui.view.RemoveFriendScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

public class ControllerMain {
	
	private EditScreen editView;
	private UserScreen userView;
	private StatusScreen statusView;
	private AddFriendScreen addfriendView;
	private RemoveFriendScreen removefriendView;
	private InviteScreen inviteView;
	private AchievementScreen achievementView;
	private BlockScreen blockView;
	private Model theModel;
	
	/**
	 * Controller for the main profile page
	 * @param theModel The Model
	 * @param userView The Main Profile Screen
	 */
	public ControllerMain(Model theModel, UserScreen userView) {
		
		this.theModel = theModel;
		this.userView = userView;
		
		this.userView.addEditListener(new EditListener());
		this.userView.addHomeListener(new HomeListener());
		this.userView.addForumListener(new ForumListener());
		this.userView.addStoreListener(new StoreListener());
		this.userView.addLibraryListener(new LibraryListener());
		this.userView.addProfileListener(new MyProfileListener());
		this.userView.addStatusListener(new StatusListener());
		this.userView.addAchievementListener(new AchievementListener());
		this.userView.addFriendListener(new AddFriendListener());
		this.userView.addRemoveFriendListener(new RemoveFriendListener());
		this.userView.addInviteListener(new InviteListener());
		this.userView.addBlockListener(new BlockListener());
					
		checkstatus();
			
	}
	
	/**
	 *  Update status Icon using value stored in Model
	 */
	public void checkstatus(){
		
		userView.setStatus(theModel.getStatusIcon());

	}

	/**
	 *  Open on the Edit Page
	 */
	class EditListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			
			editView = new EditScreen(theModel);
			ControllerEdit editcontrol = new ControllerEdit(theModel, editView, userView);
						
		}
		
	}
	
	/**
	 *  Navigates to the Arcade Home Page
	 */
	public class HomeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the home page
			System.out.println("this works");
			System.out.println(theModel.getArcadesystem().getArcadeGames().toString());
			
		}
		
	}
	
	/**
	 *  Navigates to the Forum Page
	 */
	class ForumListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the forum
			System.out.println("Forum Button Works");
			
		}
		
	}
	
	/**
	 *  Navigates to the Game Store
	 */
	class StoreListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the game store
			System.out.println("Store Button Works");
			
		}
		
	}
	
	/**
	 *  Navigates to the Arcade Library
	 */
	class LibraryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the Library page
			System.out.println("Library Button Works");
			
		}
		
	}
	
	
	/**
	 *  Return to Main Profile Page
	 */
	class MyProfileListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			System.out.println("My Profile Button Works");
			
		}
		
	}
	
	/**
	 *  Opens the Add Friends Screen
	 */
	class AddFriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Add Friend to List
			addfriendView = new AddFriendScreen(theModel);
			AddFriend addfriend = new AddFriend(theModel, addfriendView, userView);
			userView.setEnabled(false);
			
		}
		
	}
	
	/**
	 *  Opens the Remove Friends Screen
	 */
	class RemoveFriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Remove a friend
			removefriendView = new RemoveFriendScreen(theModel);
			RemoveFriend removefriend = new RemoveFriend(theModel, removefriendView, userView);
			userView.setEnabled(false);
			
		}
		
	}
	
	/**
	 *  Opens the Status Selection Screen
	 */
	class StatusListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open status popup
			statusView = new StatusScreen(theModel);
			StatusMain statuscontrol = new StatusMain(theModel, statusView, userView);
			userView.setEnabled(false);
			
		}
		
	}
	
	/**
	 *  Navigates to the achievements page
	 */
	class AchievementListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open Achievement Screen
			userView.dispose();
			achievementView = new AchievementScreen(theModel);
			ControllerAchievement achievementcontrol = new ControllerAchievement(theModel, achievementView);
			
		}
		
	}
	
	/**
	 *  Lists the invites of the User
	 */
	class InviteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open invites Screen
			inviteView = new InviteScreen(theModel);
			Invite invite = new Invite(theModel, inviteView, userView);
			
		}
		
	}
	
	/**
	 *  Lists the Blocked users
	 */
	class BlockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open Blocked users Screen
			blockView = new BlockScreen(theModel);
			BlockFriend block = new BlockFriend(theModel, userView, blockView);
			
		}
		
	}
	

}
