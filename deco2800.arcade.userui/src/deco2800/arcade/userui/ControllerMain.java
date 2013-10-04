package deco2800.arcade.userui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.model.Achievement;

public class ControllerMain {
	
	private EditScreen editView;
	private UserScreen userView;
	private AchievementScreen achievementView;
	private Model theModel;
	
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
		this.userView.addFriendListener(new FriendListener());
		this.userView.addStatusListener(new StatusListener());
		this.userView.addAchievementListener(new AchievementListener());
	
	}
	
	class EditListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the edit page
			
		}
		
	}
	
	public class HomeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the home page
			System.out.println("Home Button Works");
			
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
	
	class FriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Add Friend to List
			System.out.println("Add Friend Button");
			
		}
		
	}
	
	class StatusListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open status popup
			System.out.println("Status Button Works");
			
		}
		
	}
	
	class AchievementListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open Achievement Screen
			System.out.println("Achievement opens");
			userView.dispose();
			achievementView = new AchievementScreen(theModel);
			ControllerAchievement achievementcontroller = new ControllerAchievement(theModel, achievementView);
			
		}
		
	}
	

}
