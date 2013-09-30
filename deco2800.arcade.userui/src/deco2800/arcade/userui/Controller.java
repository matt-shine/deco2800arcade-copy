package deco2800.arcade.userui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Controller {
	
	private EditScreen editView;
	private UserScreen userView;
	private Model theModel;
	
	public Controller(Model theModel, UserScreen userView) {
		
		this.theModel = theModel;
		this.userView = userView;
		
		this.userView.addEditListener(new EditListener());
		this.userView.addHomeListener(new HomeListener());
		this.userView.addForumListener(new ForumListener());
		this.userView.addStoreListener(new StoreListener());
		this.userView.addLibraryListener(new LibraryListener());
		this.userView.addFriendListener(new FriendListener());
		this.userView.addStatusListener(new StatusListener());

	
	}
	
	class EditListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the edit page
			
		}
		
	}
	
	class HomeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the home page
			
		}
		
	}
	
	class ForumListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the forum
			
		}
		
	}
	
	class StoreListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the game store
			
		}
		
	}
	
	class LibraryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the Library page
			
		}
		
	}
	
	class FriendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Add Friend to List
			
		}
		
	}
	
	class StatusListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open status popup
			
		}
		
	}
	

}
