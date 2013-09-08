package deco2800.arcade.guesstheword.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class GuessTheWordController {
	
	private GuessTheWordGUI gui;
	public GuessTheWordController(GuessTheWordGUI g) {
		gui = g;
//		this.game = game;

		g.achieveButtonListener(new AchieveListener());
		g.loginButtonListener(new LoginListener());
		g.helpButtonListener(new HelpListener());
		
//		game.nextButtonListener(new NextListener());
	}
	
	private class AchieveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class LoginListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println("What UP!!!");
			gui.openGamePanel(); 
		}
		
	}
	
	private class HelpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	//------------------------  GAME PAGE LISTENERS---------------------------------//
	// Next Button class
	private class NextListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			ImageIcon icon = new ImageIcon("images/rain2.jpg");
			//game.setImage(icon);
		}
		
	}

}
