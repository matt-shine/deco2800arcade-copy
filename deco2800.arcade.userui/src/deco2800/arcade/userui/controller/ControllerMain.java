package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import org.apache.log4j.Logger;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.EditScreen;
import deco2800.arcade.userui.view.AddFriendScreen;
import deco2800.arcade.userui.view.RemoveFriendScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

public class ControllerMain {
	
	private EditScreen editView;
	private UserScreen userView;
	private StatusScreen statusView;
	private AddFriendScreen addfriendView;
	private RemoveFriendScreen removefriendView;
	
	private AchievementScreen achievementView;
	private Model theModel;
	

	//Logger
	static Logger log = Logger.getLogger(UserScreen.class);
	
	/**
	 * Controller for the main profile page
	 * @param theModel
	 * @param userView
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
					
		checkstatus();
			
	}
	
	public void checkstatus(){
		
		userView.setStatus(theModel.getStatusIcon());

	}
	
	class EditListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			
			
		}
		
	}
	
	public class HomeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the home page
			log.info("this works");
			
		}
		
	}
	
	class ForumListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the forum
			System.out.println("Forum Button Works");
			
		}
		
	}
	
	class StoreListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the game store
			System.out.println("Store Button Works");
			
		}
		
	}
	
	class LibraryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the Library page
			System.out.println("Library Button Works");
			
		}
		
	}
	
	class MyProfileListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			System.out.println("My Profile Button Works");
			
		}
		
	}
	
	class AddFriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Add Friend to List
			addfriendView = new AddFriendScreen(theModel);
			
		}
		
	}
	
	class RemoveFriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Remove a friend
			removefriendView = new RemoveFriendScreen(theModel);
			
		}
		
	}
	
	class StatusListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open status popup
			statusView = new StatusScreen(theModel);
			StatusMain statuscontrol = new StatusMain(theModel, statusView, userView);
			
		}
		
	}
	
	class AchievementListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open Achievement Screen
			userView.dispose();
			achievementView = new AchievementScreen(theModel);
			ControllerAchievement achievementcontrol = new ControllerAchievement(theModel, achievementView);
			
		}
		
	}
	

}
