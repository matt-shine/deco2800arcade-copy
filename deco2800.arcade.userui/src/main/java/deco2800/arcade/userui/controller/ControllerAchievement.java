package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

/**
 * Class used for the Achievement Controller
 * 
 */
public class ControllerAchievement {
	
	private AchievementScreen achievementView;
	private StatusScreen statusView;
	private Model theModel;
	private UserScreen userView;
	
	static Logger log = Logger.getAnonymousLogger();
	
	/**
	 * Controller for the achievement page
	 * @param theModel The Model
	 * @param achievementView The achievement screen
	 */
	public ControllerAchievement(Model theModel, AchievementScreen achievementView){
		
		this.theModel = theModel;
		this.achievementView = achievementView;
		
		this.achievementView.addHomeListener(new HomeListener());
		this.achievementView.addForumListener(new ForumListener());
		this.achievementView.addStoreListener(new StoreListener());
		this.achievementView.addLibraryListener(new LibraryListener());
		this.achievementView.addProfileListener(new MyProfileListener());
		this.achievementView.addStatusListener(new StatusListener());
		this.achievementView.addSelectListener(new SelectListener());
		
		checkstatus();
		
	}
	
	/**
	 *  Update status Icon using value stored in Model
	 */
	public void checkstatus(){

		achievementView.setStatus(theModel.getStatusIcon());

	}

	/**
	 *  Opens up the Edit Page
	 */
	class EditListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the edit page
		}
		
	}
	
	/**
	 *  Navigates to the Arcade Home Page
	 */
	public class HomeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the home page
			log.info("Home Button Works");
			
		}
		
	}
	
	/**
	 *  Navigates to the Arcade Forum 
	 */
	class ForumListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the forum
			log.info("Forum Button Works");
			
		}
		
	}
	
	/**
	 *  Navigates to the Arcade Store
	 */
	class StoreListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the game store
			log.info("Store Button Works");
			
		}
		
	}
	
	/**
	 *  Navigates to the Arcade Library
	 */
	class LibraryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open the Library page
			
		}
		
	}
	
	/**
	 *  Returns back to Main Profile Page
	 */
	class MyProfileListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Opens the Profile page
			log.info("My Profile Button Works");
			achievementView.dispose();
			userView = new UserScreen(theModel);
			ControllerMain maincontroller = new ControllerMain(theModel,userView);
			
		}
		
	}
	
	/**
	 *  Opens Status Selection Screen
	 */
	class StatusListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Open status popup
			statusView = new StatusScreen(theModel);
			StatusAchievement status = new StatusAchievement(theModel, statusView, achievementView);
			
		}
		
	}
	
	/**
	 *  Branch checks selected game and updates the AchievementScreen
	 *  to reflect the game's name, description, logo and achievements.
	 */
	class SelectListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (achievementView.getGameSelection() == "Pong"){
				achievementView.setGameName(theModel.PONG.getName());
				achievementView.setGameDescription(theModel.PONG.getDescription());
				achievementView.setGameLogo(theModel.pacmanlogo);
				//achievementView.setAchievementList(Pong);
			} else if (achievementView.getGameSelection() == "Chess"){
				achievementView.setGameName(theModel.CHESS.getName());
				achievementView.setGameDescription(theModel.CHESS.getDescription());
				achievementView.setGameLogo(theModel.chessLogo);
			} else if (achievementView.getGameSelection() == "Breakout"){
				achievementView.setGameName(theModel.BREAKOUT.getName());
				achievementView.setGameDescription(theModel.BREAKOUT.getDescription());
				achievementView.setGameLogo(theModel.breakoutLogo);
			} else if (achievementView.getGameSelection() == "Burning Skies"){
				achievementView.setGameName(theModel.BURNINGSKIES.getName());
				achievementView.setGameDescription(theModel.BURNINGSKIES.getDescription());
				achievementView.setGameLogo(theModel.astrosonicLogo);
			} else if (achievementView.getGameSelection() == "Mix Maze"){
				achievementView.setGameName(theModel.MIXMAZE.getName());
				achievementView.setGameDescription(theModel.MIXMAZE.getDescription());
				achievementView.setGameLogo(theModel.mixmazeLogo);
			} else if (achievementView.getGameSelection() == "Land Invaders"){
				achievementView.setGameName(theModel.LANDINVADERS.getName());
				achievementView.setGameDescription(theModel.LANDINVADERS.getDescription());
				achievementView.setGameLogo(theModel.landinvaderslogo);
			} else if (achievementView.getGameSelection() == "Pacman"){
				achievementView.setGameName(theModel.PACMAN.getName());
				achievementView.setGameDescription(theModel.PACMAN.getDescription());
				achievementView.setGameLogo(theModel.pacmanlogo);
			}
			
		}
		
	}

}
