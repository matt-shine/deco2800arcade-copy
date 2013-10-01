package deco2800.arcade.userui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerAchievement {
	
	private AchievementScreen achievementView;
	private Model theModel;

	/**
	 * Controller for the achievement page
	 * @param theModel
	 * @param achievementView
	 */
	public ControllerAchievement(Model theModel, AchievementScreen achievementView){
		
		this.theModel = theModel;
		this.achievementView = achievementView;
		
		this.achievementView.addHomeListener(new HomeListener());
		this.achievementView.addForumListener(new ForumListener());
		this.achievementView.addStoreListener(new StoreListener());
		this.achievementView.addLibraryListener(new LibraryListener());
		this.achievementView.addProfileListener(new MyProfileListener());
		this.achievementView.addFriendListener(new FriendListener());
		this.achievementView.addStatusListener(new StatusListener());
		
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
	

}
